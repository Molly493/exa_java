# Examen Java: Login y gestión de usuarios con JavaFX y PostgreSQL

Aplicación de escritorio desarrollada en **Java** con **JavaFX** que se conecta a una base de datos **PostgreSQL**. 
Cuenta con una ventana de **Login** y, tras autenticarse, una ventana principal con tres pestañas para **leer**, **insertar** y **eliminar** usuarios.

**Repositorio:** https://github.com/Molly493/exa_java.git

---

## Requisitos del proyecto

1. Preparar las sentencias SQL de creación de la tabla `usuario` (campos: `id`, `nomUsuario`, `password`, `nombres`, `apellidos`, `direccion`, `telefono`) y el llenado de al menos 5 registros, en un archivo de texto plano.
2. Realizar una aplicación Java con una ventana de **Login** que lea los usuarios de la base de datos al iniciarse. Si las credenciales son correctas, se abre una segunda ventana con 3 pestañas:
   - **Tab 1 - Lectura:** un botón que lee la base de datos y muestra todos los registros.
   - **Tab 2 - Inserción:** un formulario con nombre de usuario, password (oculto), nombres, apellidos, dirección y teléfono, y un botón para registrar al usuario.
   - **Tab 3 - Eliminación:** un campo para ingresar el `id` del usuario y un botón para eliminarlo.

---

## Tecnologías

| Componente | Detalle |
|---|---|
| Lenguaje | Java (JDK 17 o superior) |
| Interfaz gráfica | JavaFX 21 (FXML) |
| Base de datos | PostgreSQL |
| Driver | `org.postgresql:postgresql:42.7.3` |
| Gestor de dependencias | Maven |

---

## Base de datos

- **Host:** `localhost`
- **Puerto:** `5235`
- **Base de datos:** `examen`
- **Tabla:** `usuario`

### Script SQL

El script se ejecuta en dos partes porque PostgreSQL no permite cambiar de base de datos con `USE`.

**Parte 1.** Conectado a la base `postgres`, crear la base de datos:

```sql
CREATE DATABASE examen;
```

**Parte 2.** Conectado a la base `examen`, crear la tabla y cargar los datos:

```sql
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id          BIGSERIAL PRIMARY KEY,
    nomUsuario  VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    nombres     VARCHAR(100) NOT NULL,
    apellidos   VARCHAR(100) NOT NULL,
    direccion   VARCHAR(150),
    telefono    VARCHAR(20)
);

INSERT INTO usuario (nomUsuario, password, nombres, apellidos, direccion, telefono) VALUES
('admin',   'admin123', 'Carlos',    'Mamani Quispe', 'Av. 6 de Agosto 123',  '70112233'),
('jperez',  'clave1',   'Juan Jose', 'Perez Rojas',   'Calle Murillo 45',     '71223344'),
('mlopez',  'clave2',   'Maria',     'Lopez Condori', 'Zona Sur, Calle 8',    '72334455'),
('rgarcia', 'clave3',   'Rosa',      'Garcia Flores', 'Av. Buenos Aires 560', '73445566'),
('lramos',  'clave4',   'Luis',      'Ramos Choque',  'Calle Comercio 210',   '74556677');
```

### Usuarios de prueba

| Usuario | Contraseña |
|---|---|
| `admin` | `admin123` |
| `jperez` | `clave1` |
| `mlopez` | `clave2` |
| `rgarcia` | `clave3` |
| `lramos` | `clave4` |

---

## Estructura del proyecto

```
exa_java/
├── pom.xml
└── src/main/
    ├── java/
    │   ├── module-info.java
    │   └── com/java/examen/
    │       ├── Launcher.java              # Punto de entrada
    │       ├── MainApp.java               # Carga la ventana de Login
    │       ├── ConexionBD.java            # Conexión JDBC a PostgreSQL
    │       ├── Usuario.java               # Modelo
    │       ├── UsuarioDAO.java            # Listar, insertar y eliminar
    │       ├── LoginController.java       # Lógica del Login
    │       └── PrincipalController.java   # Lógica de las 3 pestañas
    └── resources/com/java/examen/
        ├── login.fxml                     # Vista del Login
        └── principal.fxml                 # Vista con las 3 pestañas
```

---
## Funcionamiento

### Ventana de Login
- Al iniciarse, la ventana **lee de la base de datos** la lista de usuarios, verificando que exista conexion con la base de datos.
- El usuario ingresa su nombre de usuario y contraseña (el campo de contraseña está oculto).
- Si los datos coinciden con algún registro, se cierra el Login y se abre la ventana principal, caso contrario muestra un mensaje de error.

### Ventana principal (3 pestañas)

**Tab 1 - Lectura**
Un botón **Mostrar registros** consulta la tabla `usuario` y muestra todos los registros. Los datos se presentan en una grilla (`TableView`) con una columna por campo.

**Tab 2 - Inserción**
Formulario con los campos nombre de usuario, password, nombres, apellidos, dirección y teléfono. El password se ingresa en un campo oculto (`PasswordField`). El botón **Registrar usuario** inserta el registro en la tabla. Se validan los campos obligatorios y se informa el resultado.

**Tab 3 - Eliminación**
Se ingresa el `id` del usuario en un campo de texto y el botón **Eliminar** borra el registro. Se valida que el `id` sea numérico y muestra un mensaje de alerta si no existe un usuario con ese `id`.
