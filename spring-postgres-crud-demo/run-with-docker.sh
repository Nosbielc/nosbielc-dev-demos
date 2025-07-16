#!/bin/bash

echo "🏗️ Construindo aplicação..."

# Builda a aplicação
./mvnw clean package -DskipTests

echo "🚀 Iniciando aplicação completa com Docker..."

cd docker-infra

# Para tudo se estiver rodando
docker compose down

# Remove containers antigos
docker container prune -f

# Constrói e inicia tudo
docker compose up --build

echo "✅ Aplicação iniciada!"
echo "🌐 API: http://localhost:8080"
echo "📊 Health: http://localhost:8080/actuator/health"