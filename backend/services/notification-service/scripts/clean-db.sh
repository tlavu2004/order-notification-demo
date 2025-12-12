#!/bin/bash

# Get the directory where script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVICE_DIR="$(dirname "$SCRIPT_DIR")"

# Check for .env file in service directory
if [ ! -f "$SERVICE_DIR/.env" ]; then
  echo ".env file not found in $SERVICE_DIR!"
  exit 1
fi

# Load variables from service .env
export $(grep -v '^#' "$SERVICE_DIR/.env" | xargs)

# Check required variables
if [ -z "$MONGO_URI" ] || [ -z "$MONGO_DB" ]; then
  echo "MONGO_URI or MONGO_DB missing in .env!"
  exit 1
fi

# Drop MongoDB database
mongosh "$MONGO_URI" --eval "db.getSiblingDB('$MONGO_DB').dropDatabase()"

echo "MongoDB database '$MONGO_DB' cleaned successfully!"