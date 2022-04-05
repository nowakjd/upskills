FROM openjdk:17
ADD target/*.jar app.jar
CMD ["java","-jar","/app.jar"]