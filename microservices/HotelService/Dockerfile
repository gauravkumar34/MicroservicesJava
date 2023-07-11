FROM openjdk:17
ADD target/hotel-service.jar hotel-service.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "hotel-service.jar"]
