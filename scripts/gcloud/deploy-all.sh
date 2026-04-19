#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ID="${PROJECT_ID:-miniprojetjee}"
REGION="${REGION:-europe-west1}"
BACKEND_SERVICE_NAME="${BACKEND_SERVICE_NAME:-pms-backend}"

bash "${SCRIPT_DIR}/deploy-backend.sh"

BACKEND_URL="$(gcloud run services describe "${BACKEND_SERVICE_NAME}" --region "${REGION}" --project "${PROJECT_ID}" --format='value(status.url)')"
API_URL="${BACKEND_URL}/pms/api" bash "${SCRIPT_DIR}/deploy-frontend.sh"

echo "Full deployment completed."
