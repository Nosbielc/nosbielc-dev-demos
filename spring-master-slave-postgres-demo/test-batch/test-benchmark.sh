#!/bin/bash

# URL base da aplicação
URL="http://localhost:8080"

# Endpoint de teste com Apache Bench
AB_ENDPOINT="$URL/api/users"

# Endpoint de teste com curl paralelo
CURL_ENDPOINT="$URL/api/demo/load-test"
PARALLEL_REQUESTS=100

echo "📦 ========== Iniciando teste Basico com Apache Bench =========="
if command -v ab >/dev/null 2>&1; then
  ab -n 1000 -c 10 "$AB_ENDPOINT"
else
  echo "Apache Bench (ab) não está instalado. Instale com: brew install httpd"
fi

echo "✅ Teste basico finalizado."

echo "📦 Iniciando teste avançado com $PARALLEL_REQUESTS requisições paralelas para $CURL_ENDPOINT"
start=$(date +%s)

# Executa as requisições paralelas com medição de tempo individual
for i in $(seq 1 $PARALLEL_REQUESTS); do
  curl -s -o /dev/null -w "[%{http_code} %{time_total}s]\n" "$CURL_ENDPOINT" &
done
wait

end=$(date +%s)
echo "✅ Teste avançado finalizado em $((end - start)) segundos"
