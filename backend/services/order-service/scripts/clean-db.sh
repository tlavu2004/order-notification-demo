#!/bin/bash

# Get the directory where script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVICE_DIR="$(dirname "$SCRIPT_DIR")"  # Go up one level to service root

# Check for .env file in service directory
if [ ! -f "$SERVICE_DIR/.env" ]; then
  echo ".env file not found in $SERVICE_DIR!"
  exit 1
fi

# Load variables from service .env
export $(grep -v '^#' "$SERVICE_DIR/.env" | xargs)

# Check required variables
if [ -z "$POSTGRES_DB_URL" ] || [ -z "$POSTGRES_USER" ] || [ -z "$POSTGRES_PASSWORD" ]; then
  echo "Required PostgreSQL variables missing in .env!"
  exit 1
fi

# Run Flyway clean
cd "$SERVICE_DIR"
./mvnw flyway:clean \
  -Dflyway.cleanDisabled=false \
  -Dflyway.url="$POSTGRES_DB_URL" \
  -Dflyway.user="$POSTGRES_USER" \
  -Dflyway.password="$POSTGRES_PASSWORD"

echo "PostgreSQL database '$POSTGRES_DB' cleaned successfully!"