#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "$SCRIPT_DIR/../common/load-env.sh"

# Check required variables
if [ -z "$MONGO_URI" ] || [ -z "$MONGO_DB" ]; then
  echo "ERROR: MONGO_URI or MONGO_DB missing in .env!" >&2
  exit 1
fi

read -r -p "Are you sure you want to DROP MongoDB database '$MONGO_DB'? Type 'yes' to continue: " confirm
if [ "$confirm" != "yes" ]; then
  echo "Aborted."
  exit 0
fi

# Drop MongoDB database
mongosh "$MONGO_URI" --eval "db.getSiblingDB('$MONGO_DB').dropDatabase()"

echo "MongoDB database '$MONGO_DB' cleaned successfully!"