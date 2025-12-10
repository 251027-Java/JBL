# syntax=docker/dockerfile:1

FROM eclipse-temurin:25-jdk-jammy AS java_deps

WORKDIR /app

# COPY --chmod=0755 mvnw mvnw
# COPY .mvn/ .mvn/

COPY ./ ./

RUN ./mvnw dependency:go-offline
RUN ./mvnw package -DskipTests

ENTRYPOINT [ "java", "-jar", "target/expensereport-0.0.1-SNAPSHOT.jar" ]
