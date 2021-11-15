FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} storage-ms.jar
ENTRYPOINT ["java", "-jar", "storage-ms.jar"]