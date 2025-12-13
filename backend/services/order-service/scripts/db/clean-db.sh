#!/bin/bash

# Get the directory where script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVICE_DIR="$(dirname "$(dirname "$SCRIPT_DIR")")"

ENV_FILE="$SERVICE_DIR/.env"

# Check for .env file in service directory
if [ ! -f "$ENV_FILE" ]; then
  echo "ERROR: .env file not found at $ENV_FILE" >&2
  exit 1
fi

# Load variables from service .env
set -a
. "$SERVICE_DIR/.env"
set +a

# Validate required variables
required_vars=(
  POSTGRES_DB_URL
  POSTGRES_USER
  POSTGRES_PASSWORD
  POSTGRES_DB
)

for var in "${required_vars[@]}"; do
  if [ -z "${!var}" ]; then
    echo "ERROR: Required variable '$var' is missing in .env" >&2
    exit 1
  fi
done

# Confirm destructive action
read -r -p "Are you sure you want to DROP PostgreSQL database '$POSTGRES_DB'? Type 'yes' to continue: " confirm
if [ "$confirm" != "yes" ]; then
  echo "Aborted."
  exit 0
fi

# Change to service directory
cd "$SERVICE_DIR" || {
  echo "ERROR: Cannot cd to $SERVICE_DIR" >&2
  exit 1
}

# Set environment variables for Flyway (secure, cross-platform)
export FLYWAY_URL="$POSTGRES_DB_URL"
export FLYWAY_USER="$POSTGRES_USER"
export FLYWAY_PASSWORD="$POSTGRES_PASSWORD"

# Run Flyway clean
mvn flyway:clean -Dflyway.cleanDisabled=false

# Unset sensitive env vars after use
unset FLYWAY_URL
unset FLYWAY_USER
unset FLYWAY_PASSWORD

echo "PostgreSQL database '$POSTGRES_DB' cleaned successfully!"