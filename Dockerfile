# Stage 1: build
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /backend
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:21-jre
WORKDIR /backend

COPY --from=build /backend/target/pontolocal-backend.jar app.jar
CMD ["java", "-jar", "app.jar"]

