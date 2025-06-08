# Etapa 1: Build do JAR
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Execução do JAR
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV SPRING_DATASOURCE_URL="jdbc:mysql://mysql:3306/peladapro?useSSL=false&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true"
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
