# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar Spring Boot
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 10000
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]

