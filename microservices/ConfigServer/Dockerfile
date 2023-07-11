FROM openjdk:17
ADD target/config-server.jar config-server.jar
#WORKDIR usr/src
ENTRYPOINT ["java","-jar", "config-server.jar"]
