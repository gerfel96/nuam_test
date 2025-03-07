# Prueba técnica NUAM

## Arquitectura del Proyecto

El proyecto sigue una arquitectura de capas (MVC):
- **Controller**: Gestiona las solicitudes HTTP y delega la lógica al servicio.
- **Service**: Contiene la lógica de negocio para la gestión de productos.
- **Repository**: Interactúa con la base de datos mediante JPA/Hibernate.
- **Entity**: Representa el modelo de datos en la base de datos.

Tecnologías principales:
- **Spring Boot**: Para la creación de la API REST.
- **Spring Data JPA**: Para la persistencia con PostgreSQL.
- **Lombok**: Para reducir el código boilerplate.
- **Jakarta Validation**: Para validar automáticamente las solicitudes HTTP.

---

## Decisiones Técnicas Tomadas

1. **Uso de Spring Boot con JPA**: Simplifica el acceso a la base de datos y permite un desarrollo más ágil.
2. **Lombok**: Se eligió para facilitar la generación de getters, setters y constructores, mejorando la legibilidad.
3. **Validación con `@Valid`**: Para asegurar la integridad de los datos recibidos en las solicitudes.
4. **Anotación `@RestController` y `@RequestMapping`**: Para facilitar la creación de rutas y el manejo de las respuestas HTTP.

---

## Explicación del Dockerfile y docker-compose

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/test-0.0.1-SNAPSHOT.jar product-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "product-api.jar"]
```

- WORKDIR establece la carpeta de trabajo en /app.
- COPY copia el JAR compilado desde target/ al contenedor.
- EXPOSE define el puerto 8080 para la aplicación.
- ENTRYPOINT ejecuta el JAR al iniciar el contenedor.

### docker-compose
```docker-compose.yml
version: '3.9'

services:
  db:
    image: postgres:15
    container_name: postgres-db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: nuamdb
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - app-network
  app:
    container_name: spring-app
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DATASOURCE_URL= jdbc:postgresql://db:5432/nuamdb
    networks:
      - app-network

volumes:
  pgdata:

networks:
  app-network:
    driver: bridge

```
1. Base de Datos (db):

  - Utiliza la imagen postgres:15.
  - Define el contenedor con el nombre postgres-db.
  - Configura el usuario, la contraseña y la base de datos
  - Expone el puerto 5432 tanto en el contenedor como en el host.
  - Usa un volumen pgdata para persistencia de datos.
  - Carga un script de inicialización (init.sql) en el contenedor.
  - Se conecta a la red app-network.

2. Aplicación (app)

  - Construye la imagen desde un Dockerfile en el directorio actual. 
  - Define el contenedor con el nombre spring-app.
  - Expone el puerto 8080.
  - La opción depends_on asegura que el contenedor de la base de datos se inicie antes de la aplicación.
  - Crea las variables de entorno que ejecutará en el docker

