#!/bin/bash

set -e

# Validate required variables
required_vars=(
  MONGO_CONTAINER_NAME
  MONGO_USER
  MONGO_PASSWORD
  MONGO_DB
)

for var in "${required_vars[@]}"; do
  if [ -z "${!var}" ]; then
    echo "ERROR: $var is not set" >&2
    exit 1
  fi
done

mongo_eval() {
  local js="$1"

  docker exec -i "$MONGO_CONTAINER_NAME" mongosh \
    --quiet \
    --username "$MONGO_USER" \
    --password "$MONGO_PASSWORD" \
    --authenticationDatabase admin \
    "$MONGO_DB" \
    --eval "$js"
}
