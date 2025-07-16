#!/bin/bash

echo "=== Testando Scaling de Leitura e Escrita ==="

BASE_URL="http://localhost:8080/api"

echo "1. Criando usuários (WRITE -> Master)..."
curl -X POST $BASE_URL/users \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@test.com"}'

curl -X POST $BASE_URL/users \
  -H "Content-Type: application/json" \
  -d '{"username":"bob","email":"bob@test.com"}'

echo -e "\n\n2. Criando produtos (WRITE -> Master)..."
curl -X POST $BASE_URL/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop Gaming","description":"High-end gaming laptop","price":2499.99,"stockQuantity":5}'

echo -e "\n\n3. Aguardando replicação..."
sleep 3

echo -e "\n\n4. Lendo usuários (READ -> Slave)..."
curl -X GET $BASE_URL/users

echo -e "\n\n5. Lendo produtos (READ -> Slave)..."
curl -X GET $BASE_URL/products

echo -e "\n\n6. Contando usuários (READ -> Slave)..."
curl -X GET $BASE_URL/users/count

echo -e "\n\n7. Teste de carga (READ -> Slave)..."
curl -X GET $BASE_URL/demo/load-test

echo -e "\n\n8. Operações em massa (WRITE -> Master)..."
curl -X POST $BASE_URL/demo/bulk-operations

echo -e "\n\nTeste concluído!"