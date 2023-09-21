FROM maven:3.8.1-openjdk-17-slim AS builder

WORKDIR /app

# Copy the project's pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/

RUN mvn clean install

CMD ["mvn", "spring-boot:run"]
