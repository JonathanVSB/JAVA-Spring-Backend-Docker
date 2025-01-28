# Imagen base de Java con JDK 21
FROM openjdk:21-jdk-slim AS build

# Crear un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y el código fuente del proyecto
COPY ./pom.xml /app/
COPY ./src /app/src

# Instalar Maven y compilar el proyecto dentro del contenedor
RUN apt-get update && apt-get install -y maven \
    && mvn -f /app/pom.xml clean package -DskipTests

# Runtime stage
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/inditex-0.0.1-SNAPSHOT.jar /app/inditex.jar

# Exponer el puerto 3000 para la aplicación
EXPOSE 3000

# Ejecutar la aplicación usando el archivo JAR generado
CMD ["java", "-jar", "inditex.jar"]
