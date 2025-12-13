#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "$SCRIPT_DIR/../common/load-env.sh"

mongosh "$MONGO_URI" --eval "
  db = db.getSiblingDB('$MONGO_DB');
  print('Total notifications: ' + db.notifications.countDocuments());
  print('Sample data:');
  db.notifications.find().limit(3).forEach(printjson);
"