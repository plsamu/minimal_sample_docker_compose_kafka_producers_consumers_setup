# first run this command: mvn install
# then build the dockerfile
FROM openjdk:18-jdk-alpine3.14
WORKDIR /opt

RUN apk update && apk upgrade
RUN apk add tzdata curl unzip bash 
RUN rm -rf /var/cache/apk/*

COPY target/demo-0.0.1-SNAPSHOT.jar /app.war
CMD ["java", "-jar", "-Dspring.profiles.active=test", "/app.war"]