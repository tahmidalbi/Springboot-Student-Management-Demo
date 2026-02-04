# Multi-stage build for Spring Boot application

# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8888

# Set environment variables (can be overridden by docker-compose)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/student_mgmt
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=2021

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
