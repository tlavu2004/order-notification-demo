#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVICE_DIR="$(dirname "$SCRIPT_DIR")"

if [ ! -f "$SERVICE_DIR/.env" ]; then
  echo ".env file not found!"
  exit 1
fi

export $(grep -v '^#' "$SERVICE_DIR/.env" | xargs)

# Seed sample notifications
mongosh "$MONGO_URI" --eval "
  db = db.getSiblingDB('$MONGO_DB');

  db.notifications.insertMany([
    {
      orderId: 'ORDER-001',
      message: 'Order placed successfully',
      status: 'sent',
      createdAt: new Date()
    },
    {
      orderId: 'ORDER-002',
      message: 'Order shipped',
      status: 'pending',
      createdAt: new Date()
    },
    {
      orderId: 'ORDER-003',
      message: 'Order delivered',
      status: 'sent',
      createdAt: new Date()
    }
  ]);

  print('Seeded ' + db.notifications.countDocuments() + ' notifications');
"