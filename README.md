# PMS-APP (Production Management System)

## Description du projet
PMS-APP est une application de gestion de production permettant de gérer les machines, les maintenances, les ordres de fabrication, les produits, les fournisseurs et les techniciens. Elle fournit une API REST complète pour la gestion des entités de production.

## Technologies utilisées
- Java 17
- Spring Boot
- MariaDB
- Docker & Docker Compose

## Instructions d'installation et d'exécution

### Prérequis
- [Docker](https://www.docker.com/products/docker-desktop) et [Docker Compose](https://docs.docker.com/compose/) installés

### Construction et lancement avec Docker Compose
1. Construire le projet Java (générer le jar) :
   ```powershell
   ./mvnw clean package
   ```
2. Lancer les conteneurs (application + base de données) :
   ```powershell
   docker-compose up --build
   ```
3. L’application sera accessible sur : http://localhost:8080/pms

### Arrêt des conteneurs
```powershell
docker-compose down
```

## Variables importantes
- Port application : 8080
- Port base de données : 3307 (externe) → 3306 (interne MariaDB)
- Utilisateur MariaDB : root
- Mot de passe MariaDB : root
- Base de données : pms_db

## Déploiement sur Google Cloud
Pour déployer sur Google Cloud, vous pouvez utiliser Google Cloud Run ou Google Kubernetes Engine (GKE) après avoir poussé l’image sur Google Container Registry (GCR).

---
