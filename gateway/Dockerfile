FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/gateway-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "gateway-0.0.1-SNAPSHOT.jar" ]