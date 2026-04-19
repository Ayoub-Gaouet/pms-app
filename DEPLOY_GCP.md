# Déploiement sur Google Cloud Run

Guide complet pour déployer le **Production Management System** (backend Spring Boot + frontend Angular) sur Google Cloud.

## Architecture cible

```
┌─────────────────────────┐       ┌─────────────────────────┐       ┌──────────────────┐
│  Cloud Run              │       │  Cloud Run              │       │  Cloud SQL       │
│  pms-frontend (Angular) │──────▶│  pms-backend (Spring)   │──────▶│  MySQL 8.0       │
│  Nginx, port 80         │       │  Java 17, port 8080     │       │  pms-db / pms_db │
└─────────────────────────┘       └─────────────────────────┘       └──────────────────┘
         HTTPS public                HTTPS public                     Socket Cloud SQL
```

- **Projet GCP** : `miniprojetjee` (numéro `825270391279`)
- **Région** : `europe-west1`
- **Repos source** :
  - Backend : https://github.com/Ayoub-Gaouet/pms-app (branche `deployment`)
  - Frontend : https://github.com/Ayoub-Gaouet/pms-app-frontend (branche `deployment`)

## Prérequis

- Accès au projet GCP `miniprojetjee` avec rôles : `roles/run.admin`, `roles/cloudsql.admin`, `roles/artifactregistry.admin`, `roles/cloudbuild.builds.editor`.
- Cloud Shell (recommandé) — `gcloud` et `git` déjà disponibles, authentifié automatiquement.

## Étape 1 — Ouvrir Cloud Shell

Console GCP → icône `>_` en haut à droite. Vérifier que le projet sélectionné est bien `miniprojetjee` :

```bash
gcloud config set project miniprojetjee
```

## Étape 2 — Activer les APIs

```bash
gcloud services enable \
  run.googleapis.com \
  cloudbuild.googleapis.com \
  artifactregistry.googleapis.com \
  sqladmin.googleapis.com
```

## Étape 3 — Provisionner Cloud SQL (une seule fois)

### Créer l'instance MySQL

```bash
gcloud sql instances create pms-db \
  --database-version=MYSQL_8_0 \
  --tier=db-f1-micro \
  --region=europe-west1 \
  --root-password='CHANGE_ME_ROOT'
```

Environ 5 minutes. Si l'instance existe déjà, passer à la suite.

### Créer la base et l'utilisateur applicatif

```bash
gcloud sql databases create pms_db --instance=pms-db
gcloud sql users create pmsuser --instance=pms-db --password='CHANGE_ME_APP'
```

## Étape 4 — Cloner les repos

Les scripts supposent que `pms-app` et `pms-app-frontend` sont clonés **côte à côte** :

```bash
cd ~
git clone -b deployment https://github.com/Ayoub-Gaouet/pms-app.git
git clone -b deployment https://github.com/Ayoub-Gaouet/pms-app-frontend.git
```

Structure attendue :

```
~/
├── pms-app/              ← scripts/gcloud/ vit ici
│   ├── Dockerfile        ← multi-stage Maven + JRE
│   ├── pom.xml
│   └── scripts/gcloud/
│       ├── deploy-all.sh
│       ├── deploy-backend.sh
│       └── deploy-frontend.sh
└── pms-app-frontend/
    ├── Dockerfile        ← multi-stage Node + Nginx, consomme ARG API_URL
    └── cloudbuild.yaml   ← route _API_URL vers --build-arg
```

## Étape 5 — Exporter les variables d'environnement

```bash
export PROJECT_ID=miniprojetjee
export REGION=europe-west1
export CLOUDSQL_INSTANCE_CONNECTION_NAME='miniprojetjee:europe-west1:pms-db'
export SPRING_DATASOURCE_DRIVER_CLASS_NAME='com.mysql.cj.jdbc.Driver'
export SPRING_DATASOURCE_URL='jdbc:mysql:///pms_db?cloudSqlInstance=miniprojetjee:europe-west1:pms-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&serverTimezone=UTC'
export SPRING_DATASOURCE_USERNAME='pmsuser'
export SPRING_DATASOURCE_PASSWORD='CHANGE_ME_APP'
```

Ces variables sont perdues si le shell se ferme — réexporter avant chaque déploiement.

## Étape 6 — Déployer

```bash
cd ~/pms-app
bash scripts/gcloud/deploy-all.sh
```

Ce que fait le script :

1. `deploy-backend.sh` :
   - Crée le repository Artifact Registry `pms-images` si absent.
   - Soumet un build Cloud Build sur `pms-app/` → compile le JAR via le Dockerfile multi-stage, push l'image.
   - Déploie sur Cloud Run en tant que `pms-backend` avec toutes les variables `SPRING_DATASOURCE_*` et `--add-cloudsql-instances`.
2. `deploy-all.sh` récupère l'URL du backend déployé.
3. `deploy-frontend.sh` :
   - Soumet un build Cloud Build utilisant `cloudbuild.yaml` avec la substitution `_API_URL=<backend-url>/pms/api`.
   - Le Dockerfile frontend remplace le placeholder `__API_URL__` dans `environment.ts` via `sed`, puis `ng build --configuration=production`.
   - Push l'image, déploie `pms-frontend` sur Cloud Run.

Durée totale : **~8-12 minutes** au premier déploiement (téléchargement des dépendances Maven + npm). Les déploiements suivants sont plus rapides grâce au cache Cloud Build.

## Étape 7 — Vérifier

À la fin du script, les URLs sont affichées :

```
Backend URL : https://pms-backend-<hash>-ew.a.run.app
Frontend URL: https://pms-frontend-<hash>-ew.a.run.app
```

Tests rapides :

```bash
# Santé backend
curl -I https://pms-backend-<hash>-ew.a.run.app/pms/api/

# Frontend dans le navigateur
echo "Ouvre : https://pms-frontend-<hash>-ew.a.run.app"
```

Dans le navigateur, ouvrir DevTools → onglet Network → vérifier que les appels XHR partent bien vers le backend Cloud Run (pas `localhost`).

## Commandes utiles

### Lire les logs backend
```bash
gcloud run services logs read pms-backend --region=europe-west1 --limit=50
```

### Relancer un déploiement après une modif
Commit + push sur la branche `deployment`, puis :
```bash
cd ~/pms-app && git pull
cd ~/pms-app-frontend && git pull
cd ~/pms-app && bash scripts/gcloud/deploy-all.sh
```

Pour déployer uniquement un des deux :
```bash
bash scripts/gcloud/deploy-backend.sh   # backend seul
bash scripts/gcloud/deploy-frontend.sh  # frontend seul (auto-découvre l'URL backend)
```

### Rollback vers la révision précédente
```bash
gcloud run revisions list --service=pms-backend --region=europe-west1
gcloud run services update-traffic pms-backend \
  --region=europe-west1 --to-revisions=<REVISION_NAME>=100
```

### Supprimer les services (nettoyage)
```bash
gcloud run services delete pms-backend  --region=europe-west1
gcloud run services delete pms-frontend --region=europe-west1
gcloud sql instances delete pms-db
```

## Dépannage

| Symptôme | Cause | Solution |
|---|---|---|
| `COPY failed: target/pms-app-0.0.1-SNAPSHOT.jar not found` | Ancien Dockerfile single-stage | Vérifier que le Dockerfile commence par `FROM eclipse-temurin:17-jdk-jammy AS build` et contient un stage Maven. `git pull` sur la branche `deployment`. |
| Frontend appelle `http://localhost:8080` | Placeholder `__API_URL__` non substitué | Vérifier que `deploy-frontend.sh` utilise `--config cloudbuild.yaml --substitutions=_API_URL=...`. |
| Backend : `Communications link failure` | Variables `SPRING_DATASOURCE_*` absentes ou `--add-cloudsql-instances` manquant | Réexporter les vars et redéployer. Vérifier `gcloud run services describe pms-backend --region=europe-west1`. |
| `Permission denied on secret` | Rôles IAM insuffisants | Donner `roles/cloudsql.client` au compte de service Cloud Run (`<project-number>-compute@developer.gserviceaccount.com`). |
| Build Cloud Build échoue avec `quota exceeded` | Quota Cloud Build atteint | Attendre la fin du quota journalier ou augmenter dans la console. |

## Points d'amélioration (à considérer plus tard)

- **Secrets** : les mots de passe DB sont en clair dans les vars d'env Cloud Run. Migrer vers **Secret Manager** :
  ```bash
  gcloud secrets create db-password --data-file=-
  gcloud run services update pms-backend \
    --set-secrets=SPRING_DATASOURCE_PASSWORD=db-password:latest
  ```
- **Health checks** : ajouter `spring-boot-starter-actuator` et configurer `/actuator/health` comme startup probe.
- **Domaine custom** : `gcloud run domain-mappings create` pour brancher un domaine propre.
- **CI/CD** : remplacer le déploiement manuel par un trigger Cloud Build sur push GitHub.
- **CORS** : si besoin, configurer le backend pour autoriser uniquement l'origine du frontend Cloud Run.

## Récapitulatif des fichiers clés

| Fichier | Rôle |
|---|---|
| `pms-app/Dockerfile` | Multi-stage : Maven build → JRE runtime |
| `pms-app/pom.xml` | Dépendances MariaDB (local) + MySQL + Cloud SQL connector (prod) |
| `pms-app/scripts/gcloud/deploy-backend.sh` | Build + déploie `pms-backend` |
| `pms-app/scripts/gcloud/deploy-frontend.sh` | Build + déploie `pms-frontend` (auto-découvre l'URL backend) |
| `pms-app/scripts/gcloud/deploy-all.sh` | Orchestre les deux |
| `pms-app-frontend/Dockerfile` | Multi-stage : Node build (substitue `__API_URL__`) → Nginx |
| `pms-app-frontend/cloudbuild.yaml` | Route la substitution `_API_URL` vers `docker --build-arg` |
| `pms-app-frontend/src/environments/environment.ts` | Contient le placeholder `__API_URL__` |
