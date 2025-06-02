# Control 2 - Taller de Base de Datos

📌 **Aplicación Web para la Gestión de Tareas**  
Este sistema permite la administración de tareas asociadas a ubicaciones geográficas mediante el uso de **PostGIS**, una extensión espacial para PostgreSQL.

---

## 👨‍💻 Integrantes - Grupo 1 (Ejecución)

- [@EmirSilva](https://github.com/EmirSilva) - Emir Silva  
- [@Bastian444](https://github.com/Bastian444) - Bastián Olea  
- [@IsaacEspinoza91](https://github.com/IsaacEspinoza91) - Isaac Espinoza  
- [@OmarSaez](https://github.com/OmarSaez) - Omar Sáez  
- [@willer0](https://github.com/willer0) - Williams Jiménez  

---

## 🛠️ Tecnologías Utilizadas

- **Backend:** Java 17 + Spring Boot 3.4.5  
- **Frontend:** Vue.js con Vite + Node.js 22.14.0  
- **Base de Datos:** PostgreSQL 17 + PostGIS  
- **ORM:** SQL2O 1.9.1  
- **Autenticación:** JSON Web Token (JWT) 0.11.5  

---

## 🚀 Cómo Ejecutar el Proyecto

### Metodo 1: Uso de Docker Compose

1. Clonar este repositorio o descargar el proyecto en formato ZIP y descomprimirlo.
2. Abrir una terminal en la raíz del proyecto (`/Control-2-TBD-Grupo1`).
3. Ejecutar el siguiente comando:

```bash
docker-compose up --build
```

> ℹ️ **Notas**:
>
> * La primera vez que se ejecuta el entorno, puede tardar alrededor de **1 minuto y 30 segundos**.
> * Si el log se detiene un tiempo considerable en:
>   `db-1 | 2025-06-02 00:40:01.861 UTC [1] LOG:  database system is ready to accept connections`, es un comportamiento **normal**.
> * Una vez que aparezca el mensaje **"✅ Todo listo y funcionando!"** en la consola, el sistema estará completamente operativo y listo para pruebas.

#### Comandos útiles:

* `docker-compose up --build`: Levanta el entorno completo (construyendo imágenes si es necesario).
* `docker-compose down`: Detiene los contenedores activos (mantiene los datos).
* `docker-compose down -v --rmi all`: Elimina contenedores, volúmenes y las imágenes creadas.

---

### Metodo 2: Ejecucion manual

### 1. Clonar el repositorio

```bash
git clone https://github.com/IsaacEspinoza91/Control-2-TBD-Grupo1.git
cd Control-2-TBD-Grupo1
```

---

### 2. Configurar la Base de Datos

1. PostgreSQL 17 y PostGIS instalados.
2. Crear la base de datos con `Tablas.sql` (puede hacerse desde `psql` o una interfaz gráfica como pgAdmin):

---

### 3. Ejecutar el Backend

1. Abrir la carpeta **`Backend GestorTareas`** desde el IDE (por ejemplo, IntelliJ).
2. Configura el archivo `application.properties` con las credenciales correctas de la base de datos.
3. Ejecutar la aplicación desde el IDE

> ⚠️ El backend se ejecuta por defecto en el puerto **8080**.

---

### 4. Poblar la Base de Datos

Una vez que el backend esté corriendo, puedes poblar la base de datos utilizando los archivos SQL disponibles en la carpeta del proyecto:

- `poblado.sql`: Contiene datos de prueba para poblar las tablas.

---

### 5. Ejecutar el Frontend

```bash
cd 'Frontend GestorTareas'
npm install
npm run dev
```

Luego, acceder desde el navegador a:

[http://localhost:5173](http://localhost:5173)

> ⚠️ Asegurarse de que el backend esté corriendo antes de iniciar el frontend.

---

## ⚙️ Requisitos

- Los puertos **8080** (backend) y **5173** (frontend) deben estar disponibles.
- PostgreSQL versión **17** con extensión **PostGIS** habilitada.

---