# Use OpenJDK as base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file built by Maven
COPY target/auth-service-1.0-SNAPSHOT.jar app.jar

# Expose the port used by the application
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
