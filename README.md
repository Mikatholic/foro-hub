# ForoHub API 🚀
Backend Challenge - Alura LATAM

ForoHub es una API REST robusta diseñada para replicar el funcionamiento de un foro de discusión. Permite a los usuarios registrarse, autenticarse, crear tópicos, responder a dudas y marcar soluciones, todo bajo un entorno seguro y documentado.

# 🛠️ Tecnologías Utilizadas
Java 21: Uso de Records y nuevas funcionalidades del lenguaje.
- Spring Boot 3.4.1: Framework principal.
- Spring Security & JWT: Autenticación y autorización basada en tokens.
- Spring Data JPA: Gestión de la capa de persistencia.
- MySQL: Base de datos relacional.
- Flyway: Control de versiones y migraciones de la base de datos.
- Lombok: Reducción de código repetitivo (Boilerplate).
- Maven: Gestión de dependencias.
- SpringDoc / Swagger: Documentación interactiva de la API.

# 📋 Características Principales
## 1 Seguridad:
- Cifrado de contraseñas con BCrypt.
- Protección de endpoints mediante JWT (JSON Web Tokens).
- Borrado lógico de usuarios (atributo activo).

## 2 Gestión de Tópicos:
- CRUD completo de temas.
- Validación de tópicos duplicados (mismo título y mensaje).
- Filtros de búsqueda por nombre de curso y año.

## 3 Respuestas:
- Vinculación automática con el autor autenticado.
- Lógica para marcar respuestas como la solución oficial.

## 4 Calidad de API:
- Manejo global de excepciones con respuestas personalizadas (400, 404, 500).
- Uso de DTOs (Records) para la transferencia de datos.

# 🚀 Instalación y Configuración
## 1. Requisitos previos
- JDK 21 instalado.
- Maven 3.x.
- MySQL Server corriendo localmente.

## 2. Base de Datos
Crea la base de datos en tu entorno local:
```SQL
CREATE DATABASE forohub_db;
```
## 3. Variables de Entorno
El proyecto utiliza una variable de entorno para la firma de los tokens JWT. Puedes configurarla en tu sistema o editar el archivo application.properties:

```Properties
JWT_SECRET=tu_clave_secreta_aqui
```
## 4. Ejecución
Clona el repositorio y ejecuta el proyecto:

```Bash
mvn spring-boot:run
```
_Las tablas se crearán automáticamente gracias a Flyway._

# 📖 Documentación de la API
Una vez que la aplicación esté corriendo, puedes acceder a la interfaz interactiva de Swagger para probar todos los endpoints en:

👉 http://localhost:8080/swagger-ui.html

Endpoints Principales:
- POST /login: Autenticación y obtención del token.
- GET /topicos: Listado paginado de tópicos.
- POST /usuarios: Registro de nuevos usuarios (Público).
- POST /respuestas: Crear una respuesta (Requiere Token).

# ✒️ Autor
## Miguel Angel Ferro Escalante
