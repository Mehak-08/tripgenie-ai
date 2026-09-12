# Multi-stage Dockerfile for TripGenie AI (Java 24 + Spring Boot 3.4.3)

# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-24 AS build
WORKDIR /app
COPY pom.xml .
# Pre-fetch dependencies for caching
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Minimal runtime image
FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=build /app/target/tripgenie-ai-1.0.0.jar app.jar
EXPOSE 8080

# Environment defaults
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]
