FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar getbill.jar
EXPOSE 8090
ENTRYPOINT  ["java", "-jar", "getbill.jar"]
