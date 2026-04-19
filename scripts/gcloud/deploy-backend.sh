#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/../.." && pwd)"

PROJECT_ID="${PROJECT_ID:-miniprojetjee}"
REGION="${REGION:-europe-west1}"
REPOSITORY="${REPOSITORY:-pms-images}"
SERVICE_NAME="${SERVICE_NAME:-pms-backend}"
SOURCE_DIR="${SOURCE_DIR:-${REPO_ROOT}}"
SPRING_DATASOURCE_URL="${SPRING_DATASOURCE_URL:?Set SPRING_DATASOURCE_URL before running this script.}"
SPRING_DATASOURCE_USERNAME="${SPRING_DATASOURCE_USERNAME:?Set SPRING_DATASOURCE_USERNAME before running this script.}"
SPRING_DATASOURCE_PASSWORD="${SPRING_DATASOURCE_PASSWORD:?Set SPRING_DATASOURCE_PASSWORD before running this script.}"
SPRING_DATASOURCE_DRIVER_CLASS_NAME="${SPRING_DATASOURCE_DRIVER_CLASS_NAME:-org.mariadb.jdbc.Driver}"
CLOUDSQL_INSTANCE_CONNECTION_NAME="${CLOUDSQL_INSTANCE_CONNECTION_NAME:-}"
IMAGE="${REGION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/${SERVICE_NAME}:latest"

gcloud config set project "${PROJECT_ID}"

gcloud services enable \
  run.googleapis.com \
  cloudbuild.googleapis.com \
  artifactregistry.googleapis.com

if ! gcloud artifacts repositories describe "${REPOSITORY}" --location="${REGION}" >/dev/null 2>&1; then
  gcloud artifacts repositories create "${REPOSITORY}" \
    --repository-format=docker \
    --location="${REGION}" \
    --description="Docker repository for the production management system"
fi

gcloud builds submit "${SOURCE_DIR}" --tag "${IMAGE}"

DEPLOY_ARGS=(
  run deploy "${SERVICE_NAME}"
  --image "${IMAGE}"
  --platform managed
  --region "${REGION}"
  --allow-unauthenticated
  --port 8080
  --set-env-vars "SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL},SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME},SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD},SPRING_DATASOURCE_DRIVER_CLASS_NAME=${SPRING_DATASOURCE_DRIVER_CLASS_NAME}"
)

if [[ -n "${CLOUDSQL_INSTANCE_CONNECTION_NAME}" ]]; then
  DEPLOY_ARGS+=(--add-cloudsql-instances "${CLOUDSQL_INSTANCE_CONNECTION_NAME}")
fi

gcloud "${DEPLOY_ARGS[@]}"

BACKEND_URL="$(gcloud run services describe "${SERVICE_NAME}" --region "${REGION}" --format='value(status.url)')"

echo "Backend deployed successfully."
echo "Base URL: ${BACKEND_URL}"
echo "API URL: ${BACKEND_URL}/pms/api"
