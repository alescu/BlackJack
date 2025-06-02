# Stage 1: Build the application (construcció de l'aplicació)
# Utilitza una imatge base amb Maven i JDK 21 per compilar
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build

# Estableix el directori de treball a /app, que serà l'arrel del teu projecte dins del contenidor
WORKDIR /app 

# Copia el pom.xml primer per aprofitar la cache de Docker.
# Això permetrà que Docker no descarregui les dependències de Maven cada vegada si el pom.xml no canvia.
COPY pom.xml .

# Copia tot el codi font del projecte
COPY src ./src

# Construeix l'aplicació Spring Boot amb Maven.
# -DskipTests salta l'execució dels tests per accelerar la construcció de la imatge.
RUN mvn clean install -DskipTests

# ---

# Stage 2: Create the final image (creació de la imatge final lleugera)
# Utilitza una imatge base més petita amb només el JRE 21 (sense eines de desenvolupament)
FROM eclipse-temurin:21-jre-jammy

# Estableix el directori de treball a /app per on s'executarà l'aplicació
WORKDIR /app

# Copia el JAR executable generat des de la fase 'build' a la imatge final.
# La ruta CORRECTA dins de la fase 'build' és /app/target/NOM_DEL_TEU_JAR_COMPLET.jar
# Substitueix 'S05T01N01-0.0.1-SNAPSHOT.jar' pel nom exacte del teu JAR si fos diferent.
COPY --from=build /app/target/S05T01N01-0.0.1-SNAPSHOT.jar app.jar

# Expose el port en què la teva aplicació Spring Boot escolta (per defecte 8080)
EXPOSE 8080

# Defineix la comanda per executar l'aplicació quan s'iniciï el contenidor
ENTRYPOINT ["java", "-jar", "app.jar"]

# Opcional: Pots definir variables d'entorn si la teva aplicació les necessita (per exemple, perfils de Spring)
# ENV SPRING_PROFILES_ACTIVE=prod