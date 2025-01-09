# Use a multi-stage build to optimize the image size

# Stage 1: Build stage
FROM maven:3.9.0-eclipse-temurin-17 as builder

# Set working directory in the container
WORKDIR /app

# Copy the pom.xml and dependency files first for dependency caching
COPY pom.xml .

# Download project dependencies
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-alpine

# Set working directory in the container
WORKDIR /app

# Copy the Spring Boot jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (match this to the port your app runs on)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
