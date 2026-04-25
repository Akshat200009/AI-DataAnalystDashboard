# Use Java 17
FROM openjdk:17-jdk-slim

# Copy project
WORKDIR /app
COPY . .

# Build app
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Run app
CMD ["java", "-jar", "target/*.jar"]