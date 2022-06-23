FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/customer-0.0.1-SNAPSHOT.jar .

EXPOSE 8092

ENTRYPOINT [ "java", "-jar", "customer-0.0.1-SNAPSHOT.jar" ]