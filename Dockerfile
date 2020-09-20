# For Java 8, try this
# FROM openjdk:8-jdk-alpine

# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> its currently at snapshot
ARG JAR_FILE=target/spring-camel-demo-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-camel-demo-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]