# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy POM first and cache dependencies
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline || true

# Copy source and build
COPY src ./src
RUN mvn -B -ntp clean package -DskipTests

# ---------- Run Stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the JAR from builder stage
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080

# Healthcheck (optional for Render)
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s CMD curl -f http://localhost:8080/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]