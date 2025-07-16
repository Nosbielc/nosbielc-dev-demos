#!/bin/bash
set -e

echo "⏳ Running replica-init.sh..."

rm -rf /var/lib/postgresql/data/*

PGPASSWORD=repl_password123 pg_basebackup -h postgres-master -D /var/lib/postgresql/data -U replicator -v -P --write-recovery-conf

echo "✅ Replica initialized. Starting PostgreSQL..."