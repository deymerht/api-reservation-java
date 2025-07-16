# Redocly CLI integration for OpenAPI validation

This file describes how to use Redocly CLI to validate your OpenAPI documentation in this project.

## Instalación

Puedes instalar Redocly CLI globalmente usando npm:

```
npm install -g @redocly/cli
```

O como dependencia de desarrollo en tu proyecto:

```
npm install --save-dev @redocly/cli
```

## Validación manual

Para validar tu archivo OpenAPI (por ejemplo, `openapi.yaml` o `openapi.json`), ejecuta:

```
redocly lint openapi.yaml
```

Asegúrate de reemplazar `openapi.yaml` por la ruta real de tu especificación.

## Integración con Gradle

Puedes agregar un script en tu `build.gradle` para ejecutar la validación como parte del build:

```
task lintOpenApi(type: Exec) {
    commandLine 'npx', 'redocly', 'lint', 'src/main/resources/openapi.yaml'
}
```

Luego puedes ejecutar:

```
gradle lintOpenApi
```

## Integración en CI/CD

Agrega un paso en tu pipeline para ejecutar la validación:

```
npx redocly lint src/main/resources/openapi.yaml
```

## Recursos
- Documentación oficial: https://redocly.com/docs/cli/
