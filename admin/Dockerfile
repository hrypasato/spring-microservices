FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/admin-0.0.1-SNAPSHOT.jar .

EXPOSE 8086

ENTRYPOINT [ "java", "-jar", "admin-0.0.1-SNAPSHOT.jar" ]