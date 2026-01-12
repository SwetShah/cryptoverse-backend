# Stage 1: Build with Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: Run the jar
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar from stage 1
# Copy the built jar from stage 1
COPY --from=build /app/target/CryptoCompare-0.0.1-SNAPSHOT.jar ./CryptoVerse.jar

# Command to run your app
CMD ["java", "-jar", "CryptoVerse.jar"]
