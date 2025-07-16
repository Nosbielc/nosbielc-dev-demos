FROM openjdk:17-jdk-slim

# Define o nome do container
LABEL container.name="spring-master-slave-postgres-demo-app"

WORKDIR /app

# Copia arquivos de build
COPY target/spring-master-slave-postgres-demo-*.jar app.jar

# Exponhe porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
