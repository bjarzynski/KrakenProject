# syntax=docker/dockerfile:1

FROM openjdk:15-jdk-alpine

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "test"]