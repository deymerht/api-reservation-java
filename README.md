# API Reservation

Proyecto Java Spring Boot para la gestión de reservas.

## Documentación de la API


La documentación OpenAPI se encuentra en `src/main/resources/openapi.yaml` y es validada automáticamente con Redocly CLI.

### Visualización interactiva

Si ejecutas la aplicación, puedes acceder a la documentación interactiva generada automáticamente por Springdoc en:
- [http://localhost:300/swagger-ui.html](http://localhost:300/swagger-ui.html)
- [http://localhost:300/v3/api-docs](http://localhost:300/v3/api-docs)

### Endpoints documentados

#### `GET /ping`
- **Resumen:** Ping de prueba
- **Seguridad:** Requiere header `X-API-KEY` (ApiKeyAuth)
- **Respuestas:**
  - `200`: Respuesta exitosa
  - `400`: Solicitud incorrecta

#### Ejemplo de uso

```http
GET /ping HTTP/1.1
Host: localhost:300
X-API-KEY: tu-api-key
```

### Seguridad
- Todos los endpoints requieren el header `X-API-KEY`.

### Validación de la especificación

Puedes validar la especificación OpenAPI ejecutando:

```
npx @redocly/cli lint src/main/resources/openapi.yaml
```

o usando Gradle:

```
./gradlew lintOpenApi
```

### Generación automática de OpenAPI

La especificación OpenAPI también se genera automáticamente a partir de los controladores y anotaciones de Spring Boot usando Springdoc.

---


## Instalación y ejecución

### 1. Requisitos previos
- Java 21 o superior
- Node.js y npm (solo si quieres validar OpenAPI con Redocly CLI)

### 2. Clona el repositorio
```bash
git clone <url-del-repositorio>
cd api-reservation-java
```

### 3. Instala dependencias de Node (opcional, solo para validación OpenAPI)
```bash
npm install --save-dev @redocly/cli
```

### 4. Ejecuta la aplicación
En Windows PowerShell:
```powershell
./gradlew bootRun
```
En Linux/Mac:
```bash
./gradlew bootRun
```

### 5. Accede a la API y documentación
- API: http://localhost:300
- Documentación Swagger UI: http://localhost:300/swagger-ui.html
- Especificación OpenAPI JSON: http://localhost:300/v3/api-docs

---

## Estructura relevante del proyecto

- `src/main/resources/openapi.yaml`: Especificación OpenAPI manual.
- `build.gradle`: Incluye tarea `lintOpenApi` para validación.
- `package.json`: Incluye Redocly CLI como devDependency.
- `.gitignore`: Ignora node_modules y archivos temporales.

---

## Licencia
MIT

---

## Autor

**Deymer Hernández**  
[LinkedIn](https://www.linkedin.com/in/deymerh/)
