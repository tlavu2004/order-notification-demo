#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "$SCRIPT_DIR/../common/load-mongo-env.sh"
source "$SCRIPT_DIR/../common/mongo-client.sh"

echo "Checking MongoDB database '$MONGO_DB' via container '$MONGO_CONTAINER_NAME'..."

mongo_eval "
  db = db.getSiblingDB('$MONGO_DB');

  print('Total notifications: ' + db.notifications.countDocuments());
  print('Sample data:');
  db.notifications.find().limit(3).forEach(printjson);
"

echo "MongoDB check completed."
