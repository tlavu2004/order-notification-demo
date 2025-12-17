#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

. "$SCRIPT_DIR/../common/load-mongo-env.sh"
. "$SCRIPT_DIR/../common/mongo-client.sh"

echo "Seeding MongoDB database '$MONGO_DB' via container '$MONGO_CONTAINER_NAME'..."

mongo_eval "
  db = db.getSiblingDB('$MONGO_DB');

  db.notification_logs.insertMany([
    {
      order_id: '550e8400-e29b-41d4-a716-446655440000',
      message: 'Order placed successfully',
      type: 'ORDER_CREATED',
      status: 'SENT',
      sentAt: new Date()
    },
    {
      order_id: '550e8400-e29b-41d4-a716-446655440001',
      message: 'Order shipped',
      type: 'ORDER_UPDATED',
      status: 'PENDING',
      sentAt: new Date()
    },
    {
      order_id: '550e8400-e29b-41d4-a716-446655440002',
      message: 'Order delivered',
      type: 'ORDER_CANCELLED',
      status: 'SENT',
      sentAt: new Date()
    }
  ]);

  print('Seeded ' + db.notification_logs.countDocuments() + ' notification logs');
"

echo "MongoDB seed completed successfully."