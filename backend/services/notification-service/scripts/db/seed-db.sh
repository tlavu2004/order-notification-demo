#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "$SCRIPT_DIR/../common/load-mongo-env.sh"
source "$SCRIPT_DIR/../common/mongo-client.sh"

echo "Seeding MongoDB database '$MONGO_DB' via container '$MONGO_CONTAINER_NAME'..."

mongo_eval "
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

echo "MongoDB seed completed successfully."