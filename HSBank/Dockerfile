#
# Build stage
#
FROM maven:3.8.1-openjdk-17-slim AS build
RUN mkdir -p /app

WORKDIR /app

COPY src ./src
COPY pom.xml ./

RUN mvn -f ./pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/HSBank-0.0.1-SNAPSHOT.jar /usr/local/lib/HSBank.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/HSBank.jar"]