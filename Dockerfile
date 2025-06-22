# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

COPY gradle/ gradle/
COPY build.gradle settings.gradle gradlew ./
RUN chmod +x gradlew
COPY src/ src/

RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/authentication-server-0.0.1-SNAPSHOT.jar app.jar

# Limit memory usage
ENV JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8090

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
