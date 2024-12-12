FROM maven:3.9.9-amazoncorretto-21 AS build
LABEL authors="TimV22"

WORKDIR /home/app

COPY pom.xml /home/app
COPY src /home/app/src

RUN mvn clean package -DskipTests

FROM openjdk:23

COPY --from=build /home/app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
