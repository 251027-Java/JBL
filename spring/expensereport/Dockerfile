# syntax=docker/dockerfile:1

FROM eclipse-temurin:25-jdk-jammy AS deps

WORKDIR /build

COPY ./ ./

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B dependency:go-offline

FROM deps AS package

WORKDIR /build

RUN --mount=type=cache,target=/root/.m2 \  
    ./mvnw -B package -DskipTests

FROM eclipse-temurin:25-jre-jammy AS final

COPY --from=package build/target/expensereport-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=docker", "app.jar" ]
