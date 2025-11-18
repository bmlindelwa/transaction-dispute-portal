# ========= Build Stage =========
FROM node:20-alpine AS frontend-build
WORKDIR /frontend
COPY frontend/package*.json ./
# npm ci
RUN npm install
COPY frontend/ .
RUN npm run build

# ========= Java Build Stage =========
FROM maven:3.9.9-eclipse-temurin-21-alpine AS backend-build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

# ========= Final Runtime Image =========
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy backend JAR
COPY --from=backend-build /app/target/dispute-backend-0.0.1-SNAPSHOT.jar app.jar


# Copy built React app
COPY --from=frontend-build /frontend/build ./static

# Non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]