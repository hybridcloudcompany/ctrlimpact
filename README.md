# clientes-mock

Proyecto Java 17 + Maven (Spring Boot) con **un solo endpoint mockeado**: `GET /clientes`.
La respuesta se toma del `example` definido en el contrato OpenAPI configurado.

## Ejecutar
```bat
cd D:\ctrlimpact\clientes-mock
mvn spring-boot:run
curl http://localhost:8080/clientes
```

## Cambiar el contrato
Edita `src/main/resources/application.yml`:
```yaml
contract:
  path: file:D:/ctrlimpact/contratos/clientes-api.yaml   # o classpath:contract/clientes-api.yaml
  endpoint: /clientes
  status: "200"
```
También por variable de entorno: `CONTRACT_PATH=file:D:/ruta/contrato.yaml`.
Si el contrato no define el endpoint o su `example`, la aplicación falla al iniciar con un mensaje claro.

## Estructura
- `contract/clientes-api.yaml`: contrato OpenAPI de ejemplo.
- `ContractLoader`: lee y valida el contrato.
- `ClienteController`: expone `GET /clientes`.
