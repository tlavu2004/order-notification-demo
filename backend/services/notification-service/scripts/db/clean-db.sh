#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "$SCRIPT_DIR/../common/load-mongo-env.sh"
source "$SCRIPT_DIR/../common/mongo-client.sh"

read -r -p "Are you sure you want to DROP MongoDB database '$MONGO_DB'? Type 'yes' to continue: " confirm
if [ "$confirm" != "yes" ]; then
  echo "Aborted."
  exit 0
fi

echo "Dropping MongoDB database '$MONGO_DB' via container '$MONGO_CONTAINER_NAME'..."

mongo_eval "
  db.getSiblingDB('$MONGO_DB').dropDatabase();
  print('Database dropped successfully');
"

echo "MongoDB database '$MONGO_DB' cleaned successfully."
