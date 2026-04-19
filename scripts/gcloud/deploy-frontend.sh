#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/../.." && pwd)"

PROJECT_ID="${PROJECT_ID:-miniprojetjee}"
REGION="${REGION:-europe-west1}"
REPOSITORY="${REPOSITORY:-pms-images}"
SERVICE_NAME="${SERVICE_NAME:-pms-frontend}"
BACKEND_SERVICE_NAME="${BACKEND_SERVICE_NAME:-pms-backend}"
SOURCE_DIR="${SOURCE_DIR:-${REPO_ROOT}/../pms-app-frontend}"
IMAGE="${REGION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/${SERVICE_NAME}:latest"
API_URL="${API_URL:-}"

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

if [[ -z "${API_URL}" ]]; then
  BACKEND_URL="$(gcloud run services describe "${BACKEND_SERVICE_NAME}" --region "${REGION}" --format='value(status.url)')"
  API_URL="${BACKEND_URL}/pms/api"
fi

gcloud builds submit "${SOURCE_DIR}" \
  --config="${SOURCE_DIR}/cloudbuild.yaml" \
  --substitutions="_API_URL=${API_URL},_IMAGE=${IMAGE}"

gcloud run deploy "${SERVICE_NAME}" \
  --image "${IMAGE}" \
  --platform managed \
  --region "${REGION}" \
  --allow-unauthenticated \
  --port 80

FRONTEND_URL="$(gcloud run services describe "${SERVICE_NAME}" --region "${REGION}" --format='value(status.url)')"

echo "Frontend deployed successfully."
echo "Frontend URL: ${FRONTEND_URL}"
echo "Configured API URL: ${API_URL}"
