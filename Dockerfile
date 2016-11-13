FROM openjdk:8-alpine

WORKDIR /opt/app/
EXPOSE 8080
COPY ./build/libs/webback-0.0.1-SNAPSHOT.jar /opt/app/

CMD ["java", "-jar", "webback-0.0.1-SNAPSHOT.jar"]
