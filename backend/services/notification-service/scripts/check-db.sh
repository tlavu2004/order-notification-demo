#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVICE_DIR="$(dirname "$SCRIPT_DIR")"

if [ ! -f "$SERVICE_DIR/.env" ]; then
  echo ".env file not found!"
  exit 1
fi

export $(grep -v '^#' "$SERVICE_DIR/.env" | xargs)

mongosh "$MONGO_URI" --eval "
  db = db.getSiblingDB('$MONGO_DB');
  print('Total notifications: ' + db.notifications.countDocuments());
  print('Sample data:');
  db.notifications.find().limit(3).forEach(printjson);
"