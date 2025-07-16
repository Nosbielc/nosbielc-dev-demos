#!/bin/bash

echo "🚀 Iniciando infraestrutura do PostgreSQL..."

cd docker-infra

# Para a aplicação se estiver rodando
docker compose down

# Remove containers antigos
docker container prune -f

# Inicia apenas o PostgreSQL
docker compose up -d postgres

echo "⏳ Aguardando PostgreSQL inicializar..."
sleep 10

echo "✅ PostgreSQL está rodando!"
echo "📊 Conexão: localhost:5432"
echo "🗄️ Database: crud_demo"
echo "👤 Usuário: postgres"
echo "🔐 Senha: postgres"
echo ""
echo "🏃 Para executar a aplicação:"
echo "   ./mvnw spring-boot:run"
echo ""
echo "🐳 Para executar tudo com Docker:"
echo "   ./run-with-docker.sh"