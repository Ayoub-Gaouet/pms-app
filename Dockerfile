# Étape 1 : Build du JAR avec Maven
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B
COPY src src
RUN ./mvnw clean package -DskipTests -B

# Étape 2 : Image runtime minimale
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/pms-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
