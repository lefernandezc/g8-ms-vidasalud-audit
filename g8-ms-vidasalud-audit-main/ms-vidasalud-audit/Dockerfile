FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app


COPY pom.xml .
COPY src ./src

# Compilar el proyecto ignorando las pruebas unitarias
RUN mvn clean package -DskipTests

# Ejecución del microservicio
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el archivo .jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]