FROM openjdk:11
LABEL description="API - Clientes"
LABEL maintainer="Ronivon Sampaio"
ADD target/sboot-clientes-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]
EXPOSE 8080
