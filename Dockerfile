# Utilise l'image officielle Eclipse Temurin Java 17 comme image de base
FROM eclipse-temurin:17-jdk-jammy

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copie le jar généré dans le conteneur
COPY target/pms-app-0.0.1-SNAPSHOT.jar app.jar

# Expose le port utilisé par Spring Boot
EXPOSE 8080

# Commande de lancement de l'application
ENTRYPOINT ["java", "-jar", "app.jar"]