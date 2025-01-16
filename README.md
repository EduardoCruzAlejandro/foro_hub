# Foro Hub API

Foro Hub es una API REST desarrollada en Java con Spring Boot que permite la gestión de tópicos en un foro. La aplicación permite realizar operaciones CRUD sobre los tópicos, gestionar autenticación y autorización mediante JWT (JSON Web Tokens), y almacenar datos en una base de datos PostgreSQL.

## Tecnologías utilizadas

- **Java 17**: Lenguaje de programación.
- **Spring Boot**: Framework para la creación de la API REST.
- **Spring Security**: Manejo de autenticación y autorización.
- **Spring Data JPA**: Interacción con la base de datos PostgreSQL.
- **JWT (JSON Web Tokens)**: Seguridad mediante tokens de acceso.
- **PostgreSQL**: Base de datos relacional.

## Descripción

La API de Foro Hub permite realizar las siguientes operaciones:

- **Crear Tópicos**: Registrar nuevos tópicos con título, mensaje, autor y curso.
- **Leer Tópicos**: Listar todos los tópicos o buscar uno en particular por su ID.
- **Actualizar Tópicos**: Modificar los detalles de un tópico.
- **Eliminar Tópicos**: Eliminar un tópico específico.

Además, la API cuenta con un servicio de autenticación basado en JWT para asegurar las rutas y permitir el acceso solo a usuarios autenticados.

## Endpoints

### Autenticación

- **POST /login**: Autenticar usuario y obtener un token JWT.
  - **Request Body**:
    ```json
    {
      "username": "usuario",
      "password": "contraseña"
    }
    ```
  - **Response**:
    ```json
    {
      "token": "jwt_token"
    }
    ```

### Tópicos

- **GET /api/topicos**: Obtener la lista de todos los tópicos.
- **GET /api/topicos/{id}**: Obtener detalles de un tópico por ID.
- **POST /api/topicos**: Crear un nuevo tópico (requiere autenticación).
  - **Request Body**:
    ```json
    {
      "titulo": "Título del Tópico",
      "mensaje": "Contenido del Tópico",
      "autor": "Nombre del Autor",
      "curso": "Nombre del Curso"
    }
    ```
- **PUT /api/topicos/{id}**: Actualizar un tópico (requiere autenticación).
  - **Request Body**:
    ```json
    {
      "titulo": "Nuevo Título",
      "mensaje": "Nuevo Contenido",
      "autor": "Nuevo Autor",
      "curso": "Nuevo Curso"
    }
    ```
- **DELETE /api/topicos/{id}**: Eliminar un tópico (requiere autenticación).

## Instalación

### Requisitos previos

- Java 17
- PostgreSQL
- Maven

### Pasos para ejecutar el proyecto

1. Clona este repositorio:
    ```bash
    git clone https://github.com/tu_usuario/foro_hub.git
    cd foro-hub
    ```

2. Crea una base de datos en PostgreSQL llamada `foroHUB` y configura las credenciales en el archivo `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost/foroHUB
    spring.datasource.username=usuario
    spring.datasource.password=contraseña
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.format-sql=true
    ```

3. Compila y ejecuta el proyecto:
    ```bash
    mvn spring-boot:run
    ```

4. La aplicación estará disponible en `http://localhost:8080`.

## Configuración de JWT

El `secret` para firmar y verificar los tokens JWT se encuentra en el archivo `application.properties`:

```properties
jwt.secret=123456
jwt.expiration=86400000  # 1 día en milisegundos
```
Asegúrate de que estas configuraciones estén correctamente definidas para que la autenticación JWT funcione.

## Pruebas

Para probar la API puedes usar herramientas como [Postman](https://www.postman.com/) o [Insomnia](https://insomnia.rest/). Asegúrate de enviar el token JWT en los encabezados de las solicitudes que requieren autenticación.

- **Authorization**: `Bearer {jwt_token}`

## Licencia

Este proyecto está bajo la Licencia MIT. Para más detalles, consulta el archivo [LICENSE](LICENSE).


