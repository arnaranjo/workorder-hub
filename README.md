# WorkOrder Hub - Manual de ejecución

Aplicación de escritorio JavaFX + Maven + MySQL.

## Descripcion del proyecto

WorkOrder Hub es una aplicación de escritorio para gestionar ordenes de trabajo, usuarios, procedimientos, permisos de trabajo y elementos de planta. Este repositorio se desarrolla como **proyecto final y de aprendizaje** para la etapa final de los estudios de **DAM (Desarrollo de Aplicaciones Multiplataforma)**.

## 1) Prerequisitos

- Java JDK **22** (el proyecto compila con source/target 22, ver `pom.xml`).
- Git (opcional, para clonar).
- MySQL de una de estas formas:
  - **MySQL Server** instalado localmente, o
  - **XAMPP** (usando el modulo MySQL/MariaDB).
- Un gestor SQL (elige uno):
  - **HeidiSQL**, o
  - **MySQL Workbench**.

## 2) Clonar o abrir el proyecto
```powershell
git clone <URL_DEL_REPO>
Set-Location "workorder-hub"
```

## 3) Levantar la base de datos
### Opción A: MySQL Server
1. Iniciar el servicio MySQL.
2. Verificar que escucha en `localhost:3306`.
### Opción B: XAMPP
1. Abrir XAMPP Control Panel.
2. Iniciar **MySQL**.
3. Verificar puerto (normalmente `3306`).

## 4) Crear BD y cargar scripts (con HeidiSQL o MySQL Workbench)
> El proyecto espera la BD `order_hub` (ver `src/main/java/com/workorderhub/infrastructure/config/database.properties`).

1. Conectar al servidor local (`localhost`, puerto `3306`).
2. Crear la base de datos:
```sql
CREATE DATABASE IF NOT EXISTS order_hub;
USE order_hub;
```

3. Ejecutar primero el esquema:
   - `database/01_schema.sql`
4. Ejecutar después los datos semilla:
   - `database/02_data.sql`

Orden importante: **primero `01_schema.sql`, luego `02_data.sql`**.

## 5) Configurar credenciales de conexion
Editar el archivo:
- `src/main/java/com/workorderhub/infrastructure/config/database.properties`

Valores esperados:
```properties
database.URL = jdbc:mysql://localhost:3306/order_hub
database.username = root
database.password = admin
database.connector = com.mysql.cj.jdbc.Driver
```

Ajustar `username` y `password` según la instalación.
## 6) Ejecutar la aplicación
### Recomendado (Windows, con Maven Wrapper)
```powershell
.\mvnw.cmd clean javafx:run
```
### Alternativa (si tienes Maven global)
```powershell
mvn clean javafx:run
```

## 8) Problemas comunes
- **Access denied for user**
  - Usuario/password incorrectos en `database.properties`.
- **Unknown database 'order_hub'**
  - No se creo la BD o no corriste `01_schema.sql`.
- **Table ... does not exist**
  - Corriste `02_data.sql` antes de `01_schema.sql`.
- **Communications link failure**
  - MySQL/XAMPP no esta iniciado o puerto incorrecto.
- **Unsupported class file major version / errores de compilacion Java**
  - Verifica que estas usando JDK 22.

## 9) Notas útiles
- La conexión lee `database.properties` desde una ruta de archivo local en `DBConnection`:
  - `src/main/java/com/workorderhub/infrastructure/common/DBConnection.java`
- Si cambias host o puerto, actualizar `database.URL`.

---

## English guide

## Project description

WorkOrder Hub is a desktop application to manage work orders, users, procedures, work permits, and plant elements. This repository is developed as a **final and learning project** for the final stage of **DAM studies (Cross-Platform Application Development)**.
## 1) Prerequisites
- Java JDK **22** (the project compiles with source/target 22, see `pom.xml`).
- Git (optional, for cloning).
- MySQL in one of these options:
  - **MySQL Server** installed locally, or
  - **XAMPP** (using the MySQL/MariaDB module).
- A SQL client (choose one):
  - **HeidiSQL**, or
  - **MySQL Workbench**.

## 2) Clone or open the project
```powershell
git clone <REPOSITORY_URL>
Set-Location "workorder-hub"
```

## 3) Start the database
### Option A: MySQL Server
1. Start the MySQL service.
2. Verify it is listening on `localhost:3306`.
### Option B: XAMPP
1. Open XAMPP Control Panel.
2. Start **MySQL**.
3. Verify the port (usually `3306`).

## 4) Create DB and load scripts (with HeidiSQL or MySQL Workbench)
> The project expects the `order_hub` database (see `src/main/java/com/workorderhub/infrastructure/config/database.properties`).

1. Connect to the local server (`localhost`, port `3306`).
2. Create the database:
```sql
CREATE DATABASE IF NOT EXISTS order_hub;
USE order_hub;
```

3. Run schema first:
   - `database/01_schema.sql`
4. Then run seed data:
   - `database/02_data.sql`

Important order: **first `01_schema.sql`, then `02_data.sql`**.

## 5) Configure connection credentials
Edit this file:
- `src/main/java/com/workorderhub/infrastructure/config/database.properties`

Expected values:
```properties
database.URL = jdbc:mysql://localhost:3306/order_hub
database.username = root
database.password = admin
database.connector = com.mysql.cj.jdbc.Driver
```

`username` and `password` according to your local setup.

## 6) Run the application
### Recommended (Windows, using Maven Wrapper)
```powershell
.\mvnw.cmd clean javafx:run
```
### Alternative (if you have global Maven installed)
```powershell
mvn clean javafx:run
```

## 8) Common issues
- **Access denied for user**
  - Wrong username/password in `database.properties`.
- **Unknown database 'order_hub'**
  - The DB was not created or you did not run `01_schema.sql`.
- **Table ... does not exist**
  - You ran `02_data.sql` before `01_schema.sql`.
- **Communications link failure**
  - MySQL/XAMPP is not started or port is wrong.
- **Unsupported class file major version / Java compilation errors**
  - Verify you are using JDK 22.

## 9) Useful notes
- The connection reads `database.properties` from a local file path in `DBConnection`:
  - `src/main/java/com/workorderhub/infrastructure/common/DBConnection.java`
- If you change host or port, update `database.URL`.
