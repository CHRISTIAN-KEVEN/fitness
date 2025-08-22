FROM openjdk:17

WORKDIR /app

COPY src/main/resources/application.properties application.properties
COPY  log4j2.xml log4j2.xml

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 10093

ENTRYPOINT ["java", "-jar", "app.jar"]