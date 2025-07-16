# slave.Dockerfile
FROM postgres:15

# Copia config e script de replicacao
COPY ./slave/postgresql-slave.conf /etc/postgresql/postgresql.conf
COPY ./slave/replica-init.sh /replica-init.sh

# Permissao de execucao
RUN chmod +x /replica-init.sh

# Mantem entrypoint original
ENTRYPOINT ["docker-entrypoint.sh"]