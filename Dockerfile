FROM maven:3.8.1-openjdk-17-slim AS builder

WORKDIR /app

# Copy the project's pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/

RUN mvn clean install

# Use a smaller base image for running the application
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy the compiled JAR file from the builder image
COPY --from=builder /app/target/my-app.jar .

CMD ["java", "-jar", "spring-boot.jar"]
