#!/bin/bash

set -e

# Resolve paths
SERVICE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"

SERVICE_ENV="$SERVICE_DIR/.env"
INFRA_ENV="$SERVICE_DIR/../../.env"

# Load infra env
if [ ! -f "$INFRA_ENV" ]; then
  echo "ERROR: Infrastructure .env not found at $INFRA_ENV" >&2
  exit 1
fi

set -a

# shellcheck disable=SC1090
source "$INFRA_ENV"

# Load service env
if [ ! -f "$SERVICE_ENV" ]; then
  echo "ERROR: Service .env not found at $SERVICE_ENV" >&2
  exit 1
fi

# shellcheck disable=SC1090
source "$SERVICE_ENV"

set +a

# Validate required variables
required_vars=(
  MONGO_CONTAINER_NAME
  MONGO_DB
)

for var in "${required_vars[@]}"; do
  if [ -z "${!var}" ]; then
    echo "ERROR: $var is missing" >&2
    exit 1
  fi
done