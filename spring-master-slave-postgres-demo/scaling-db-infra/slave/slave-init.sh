#!/bin/bash
# slave-init.sh

set -e

# Aguarda o master estar disponível
until pg_isready -h postgres-master -p 5432 -U admin -d scalingdb; do
  echo "Aguardando PostgreSQL master..."
  sleep 2
done

# Remove dados existentes
#rm -rf /var/lib/postgresql/data/*
rm -rf /var/lib/postgresql/data/lost+found

# Faz backup do master
#pg_basebackup -h postgres-master -D /var/lib/postgresql/data -U replicator -v -P -W
PGPASSWORD=repl_password123 pg_basebackup -h postgres-master -D /var/lib/postgresql/data -U replicator -v -P

# Cria arquivo de configuração de standby
cat << EOF > /var/lib/postgresql/data/postgresql.auto.conf
primary_conninfo = 'host=postgres-master port=5432 user=replicator password=repl_password123'
primary_slot_name = 'slave_slot'
EOF

# Cria arquivo standby.signal
touch /var/lib/postgresql/data/standby.signal

# Ajusta permissões
chown -R postgres:postgres /var/lib/postgresql/data
chmod 700 /var/lib/postgresql/data