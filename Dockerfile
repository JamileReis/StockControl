FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY src/main/java/com/stockcontrol/company .
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/*.jar"]
