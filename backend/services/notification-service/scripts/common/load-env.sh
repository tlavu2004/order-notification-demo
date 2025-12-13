#!/bin/bash
set -e

SERVICE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"

ENV_FILE="$SERVICE_DIR/.env"

if [ ! -f "$ENV_FILE" ]; then
  echo "ERROR: .env file not found at $ENV_FILE" >&2
  exit 1
fi

set -a
source "$ENV_FILE"
set +a
