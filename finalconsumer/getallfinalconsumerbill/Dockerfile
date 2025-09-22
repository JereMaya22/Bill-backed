FROM eclipse-temurin:21-jdk
WORKDIR /app
ARG JAR_NAME=getallfinalconsumerbill-0.0.1-SNAPSHOT.jar
COPY target/${JAR_NAME} getbill.jar
EXPOSE 8090
ENTRYPOINT  ["java", "-jar", "getbill.jar"]
