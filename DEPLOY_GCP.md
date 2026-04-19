# Deploy on Google Cloud

This repository is ready for deployment on Google Cloud Run with `gcloud`.

## Services

- Backend: Spring Boot service deployed as `pms-backend`
- Frontend: Angular + Nginx service deployed as `pms-frontend`

## Important note about the database

The backend currently uses MariaDB settings locally.
Cloud SQL does not provide MariaDB, so the recommended Google Cloud path is Cloud SQL for MySQL.

The backend now supports both:

1. Local or external MariaDB with the MariaDB driver.
2. Cloud SQL for MySQL with the MySQL driver and Cloud SQL Java connector.

## Default project settings

- Project ID: `miniprojetjee`
- Project number: `825270391279`
- Default region in the scripts: `europe-west1`

## Deploy everything from Cloud Shell

From the repository root:

```bash
export PROJECT_ID=miniprojetjee
export REGION=europe-west1
export SPRING_DATASOURCE_URL='jdbc:mariadb://YOUR_DB_HOST:3306/pms_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC'
export SPRING_DATASOURCE_USERNAME='root'
export SPRING_DATASOURCE_PASSWORD='change-me'

bash scripts/gcloud/deploy-all.sh
```

## Recommended Cloud SQL for MySQL values

For Cloud SQL for MySQL, use a datasource URL like this:

```bash
export CLOUDSQL_INSTANCE_CONNECTION_NAME='miniprojetjee:europe-west1:pms-db'
export SPRING_DATASOURCE_DRIVER_CLASS_NAME='com.mysql.cj.jdbc.Driver'
export SPRING_DATASOURCE_URL='jdbc:mysql:///pms_db?cloudSqlInstance=miniprojetjee:europe-west1:pms-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&serverTimezone=UTC'
export SPRING_DATASOURCE_USERNAME='pmsuser'
export SPRING_DATASOURCE_PASSWORD='change-me'
```

Then deploy the backend:

```bash
bash scripts/gcloud/deploy-backend.sh
```

## Deploy backend only

```bash
export PROJECT_ID=miniprojetjee
export REGION=europe-west1
export SPRING_DATASOURCE_URL='jdbc:mariadb://YOUR_DB_HOST:3306/pms_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC'
export SPRING_DATASOURCE_USERNAME='root'
export SPRING_DATASOURCE_PASSWORD='change-me'

bash scripts/gcloud/deploy-backend.sh
```

## Deploy frontend only

If the backend is already deployed, the script can reuse its Cloud Run URL automatically:

```bash
export PROJECT_ID=miniprojetjee
export REGION=europe-west1

bash scripts/gcloud/deploy-frontend.sh
```

Or set the API URL manually:

```bash
export PROJECT_ID=miniprojetjee
export REGION=europe-west1
export API_URL='https://YOUR_BACKEND_URL/pms/api'

bash scripts/gcloud/deploy-frontend.sh
```

## What the scripts do

1. Select the Google Cloud project.
2. Enable the required APIs.
3. Create an Artifact Registry Docker repository if needed.
4. Build container images with Cloud Build.
5. Deploy both services to Cloud Run.
