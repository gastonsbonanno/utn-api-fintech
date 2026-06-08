# UTN API Fintech - README

Este README explica cómo levantar la base de datos con Docker Compose, ejecutar el script de inicialización `src/main/resources/db/init.sql`, levantar la aplicación Spring Boot y dónde encontrar la documentación OpenAPI/Swagger. También incluye ejemplos de los endpoints disponibles y comandos de prueba.

> Notas:
> - Las instrucciones están orientadas a Windows (PowerShell) y también se indican comandos alternativos para Unix (Linux/macOS) cuando aplica.
> - El proyecto ya incluye OpenAPI (springdoc) y la UI de Swagger.

---

## 1) Requisitos

- Docker y Docker Compose instalados y funcionando
- JDK 21 (o versión compatible) instalado
- Maven (opcional, el proyecto incluye `mvnw`/`mvnw.cmd`)

Comprueba Docker con:

```powershell
docker --version
docker-compose --version
```

Comprueba Java y Maven:

```powershell
java -version
./mvnw.cmd -v   # en Windows
# o
./mvnw -v       # en Unix/macOS
```

---

## 2) Levantar la base de datos con Docker Compose

El proyecto incluye un `docker-compose.yml` que levanta un servicio MySQL llamado `db` y monta el directorio `src/main/resources/db` en `/docker-entrypoint-initdb.d` dentro del contenedor. MySQL ejecutará automáticamente cualquier script `.sql` que se encuentre en ese directorio al crear el volumen por primera vez.

Desde PowerShell (en la raíz del repo):

```powershell
# Iniciar el servicio en background
docker-compose up -d

# Ver logs (opcional)
docker-compose logs -f db
```

En Unix/macOS los mismos comandos funcionan si usas `docker compose` o `docker-compose` según tu versión.

Credenciales configuradas por defecto en `docker-compose.yml` (estas mismas aparecen en `application.yaml`):

- Usuario: `utn_user`
- Password: `utn_password`
- Base: `utn_fintech`
- Puerto: `3306` (mappeado al host)

Si es la primera vez que ejecutas `docker-compose up` el archivo `src/main/resources/db/init.sql` se ejecutará automáticamente dentro del contenedor y creará las tablas y algunos registros de ejemplo.

---

## 3) Ejecutar manualmente el script `init.sql` (opcional)

Si por alguna razón necesitas ejecutar manualmente `init.sql` (por ejemplo si ya levantaste la base y quieres re-aplicar el script), puedes hacerlo mediante el cliente `mysql` dentro del contenedor:

```powershell
# Copiar el archivo al contenedor (opcional)
docker cp .\src\main\resources\db\init.sql utn-mysql:/tmp/init.sql

# Ejecutar el script usando el cliente mysql dentro del contenedor
docker exec -i utn-mysql mysql -uroot -proot_password < /tmp/init.sql

# Alternativamente, ejecutar directamente desde host (si tienes client mysql):
mysql -h 127.0.0.1 -P 3306 -u utn_user -putn_password utn_fintech < src/main/resources/db/init.sql
```

Notas:
- El contenedor en `docker-compose.yml` está configurado con `MYSQL_ROOT_PASSWORD: root_password`. Usa esa contraseña para el usuario `root` dentro del contenedor.
- Si el contenedor ya había sido iniciado y el volumen `utn_mysql_data` ya estaba creado, los scripts en `docker-entrypoint-initdb.d` no se ejecutarán de nuevo (solo se ejecutan en inicialización). En ese caso usa el `docker exec` o el cliente `mysql` para aplicar cambios.

---

## 4) Configuración de la aplicación (conexión a BD)

La conexión a la base de datos está en `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/utn_fintech?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: utn_user
    password: utn_password
```

Asegúrate de que el contenedor MySQL esté corriendo y escuchando en `localhost:3306` antes de arrancar la aplicación.

---

## 5) Levantar el servidor Spring Boot

Desde PowerShell en la raíz del repo:

```powershell
# Usando el wrapper incluido (Windows)
./mvnw.cmd spring-boot:run

# Si prefieres compilar y ejecutar el jar
./mvnw.cmd package -DskipTests=true
java -jar target/utn-api-fintech-0.0.1-SNAPSHOT.jar
```

En Unix/macOS:

```bash
./mvnw spring-boot:run
```

Si todo está correcto el servidor arrancará (por defecto en el puerto 8080). Verifica en consola los logs y espera hasta que veas "Started UtnApiFintechApplication".

---

## 6) Documentación OpenAPI / Swagger

Se agregó `springdoc-openapi` y una configuración básica. Una vez levantada la aplicación, la documentación estará disponible en:

- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Swagger UI: http://localhost:8080/swagger-ui/index.html

Usa la UI para explorar los endpoints y probarlos desde tu navegador.

---

## 7) Endpoints disponibles

Base path: `/accounts`

1) GET /accounts
- Descripción: Obtiene la lista de todas las cuentas.
- Respuesta: 200 OK
- Ejemplo (curl):

```powershell
curl http://localhost:8080/accounts
```

2) GET /accounts/{id}
- Descripción: Obtiene una cuenta por su `accountId`.
- Respuesta: 200 OK o 404 NOT FOUND
- Ejemplo:

```powershell
curl http://localhost:8080/accounts/1
```

3) POST /accounts
- Descripción: Crea una nueva cuenta para un cliente.
- Request body (JSON) — `AccountDTORequest`:

```json
{
  "clientId": 1,
  "numeroCuenta": "ACC001",
  "moneda": "ARS",    
  "saldo": 1000.0,
  "activo": true
}
```

- Respuesta: 201 Created con `AccountDTOResponse` en el body.

4) PUT /accounts/{id}
- Descripción: Actualiza una cuenta existente.
- Request body (JSON) — `AccountDTORequest` (puedes enviar solo los campos a modificar):

```json
{
  "numeroCuenta": "ACC001_UPDATED",
  "saldo": 2000.0,
  "activo": false
}
```

- Respuesta: 200 OK con `AccountDTOResponse` actualizado.

5) DELETE /accounts/{id}
- Descripción: Elimina (borra) una cuenta.
- Respuesta: 204 No Content

---

## 8) Formato de DTOs

- `AccountDTORequest` (request al crear/actualizar):
  - `clientId` (Long): id del cliente al que pertenece la cuenta (solo para creación)
  - `numeroCuenta` (String): número o código de la cuenta
  - `moneda` (String): moneda (ej. "ARS", "USD")
  - `saldo` (Double): saldo inicial o nuevo saldo
  - `activo` (Boolean): estado activo/inactivo

- `AccountDTOResponse` (respuesta que retorna la API):
  - `accountId` (Long)
  - `clientId` (Long)
  - `numeroCuenta` (String)
  - `moneda` (String)
  - `saldo` (Double)
  - `saldoEnPesos` (Double): saldo convertido a pesos (si aplica)
  - `activo` (Boolean)
  - `fechaCreacion` (DateTime)
  - `fechaModificacion` (DateTime)

---

## 9) Comandos útiles de administración

- Ver contenedores y logs:

```powershell
docker ps
docker-compose logs -f db
```

- Parar y remover contenedores/volúmenes (cuidado con datos en el volumen):

```powershell
docker-compose down
# Para eliminar el volumen asociado a MySQL
docker volume rm utn_mysql_data
```

- Ejecutar script SQL manualmente dentro del contenedor:

```powershell
docker cp .\src\main\resources\db\init.sql utn-mysql:/tmp/init.sql
docker exec -i utn-mysql mysql -uroot -proot_password < /tmp/init.sql
```

- Probar un endpoint con curl (ejemplo crear cuenta):

```powershell
curl -X POST http://localhost:8080/accounts -H "Content-Type: application/json" -d '{"clientId":1,"numeroCuenta":"ACC001","moneda":"ARS","saldo":1000}'
```

---

## 10) Troubleshooting (problemas comunes)

- Error de conexión a la DB al arrancar la app:
  - Asegúrate que Docker está corriendo y `docker-compose up -d` ya finalizó
  - Revisa que el puerto 3306 esté disponible y que el contenedor `utn-mysql` esté listo
  - Comprueba credenciales en `application.yaml`

- `init.sql` no se ejecutó:
  - Si el volumen `utn_mysql_data` ya existía, los scripts en `docker-entrypoint-initdb.d` no se vuelven a ejecutar automáticamente. Elimina el volumen (si es seguro) y recrea el contenedor, o ejecuta el script manualmente con `docker exec`.

- Swagger UI no aparece:
  - Verifica que la aplicación arrancó correctamente y que la dependencia `springdoc-openapi` está en el classpath
  - Abre: http://localhost:8080/swagger-ui/index.html

---
