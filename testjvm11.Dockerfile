FROM openjdk:11-jdk

COPY .gradle .gradle
COPY gradle gradle
COPY build.gradle ./
COPY gradlew ./
COPY settings.gradle ./
COPY src src

RUN ["./gradlew", "test"]