# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
# Download dependencies (cache)
RUN mvn -B -ntp -q dependency:go-offline

COPY src ./src
RUN mvn -B -ntp -q clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
# copy jar (the Spring Boot maven plugin generates a single jar)
COPY --from=builder /workspace/target/todo-backend-1.0.0.jar app.jar

# Expose port (Render uses it)
EXPOSE 8080

# Health check endpoint used in render.yaml
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s CMD curl -f http://localhost:8080/health || exit 1

# Run the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]