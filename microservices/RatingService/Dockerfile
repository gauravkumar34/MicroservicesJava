FROM openjdk:17
EXPOSE 8083
ADD target/rating-service.jar rating-service.jar
#WORKDIR usr/src
ENTRYPOINT ["java","-jar", "rating-service.jar"]