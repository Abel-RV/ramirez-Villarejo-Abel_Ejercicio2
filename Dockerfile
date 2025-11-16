# ETAPA 1: Compilar con Maven
FROM maven:3.9.5-eclipse-temurin-21 AS build
# ↑ Usa Maven + Java 17 (Temurin = OpenJDK oficial)

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
# ↑ Compila tu proyecto Spring Boot

# ETAPA 2: Ejecutar la aplicación
FROM eclipse-temurin:21-jre-alpine
# ↑ Usa solo Java Runtime (más ligero)

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]