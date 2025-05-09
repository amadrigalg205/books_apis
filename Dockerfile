FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/spring-boot-react-library-0.0.1.jar
COPY ${JAR_FILE} spring-boot-react-library.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-react-library.jar"]