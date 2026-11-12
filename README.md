# Biblioteca – CRUD de Autores y Libros

Proyecto Maven en Java con persistencia en **SQLite** (JDBC), interfaces **DAO** implementadas con `implements`, capa de **servicio** con reglas de negocio y presentación en **Swing**.

- **Nombre:** Ingrid Julissa Cribas
- **Número de cuenta:** 202510040208

## Requisitos

- JDK 26 (o cambie `maven.compiler.release` en `pom.xml` a su versión, por ejemplo 21)
- Maven 3.9+ (o NetBeans / IntelliJ, que ya lo incluyen)

## Cómo ejecutar

```bash
mvn clean compile exec:java
```

En NetBeans: abrir el proyecto y presionar **Run** (F6). La clase principal es `hn.uth.poo.biblioteca.app.Main`.

Al iniciar, el programa crea el archivo `biblioteca.db` (si no existe) con las tablas de `biblioteca.sql`. Cada conexión ejecuta `PRAGMA foreign_keys = ON`.

## Estructura de paquetes

| Paquete | Contenido |
|---|---|
| `hn.uth.poo.biblioteca.modelo` | `Autor`, `Libro` |
| `hn.uth.poo.biblioteca.excepciones` | `DatosException`, `DatoInvalidoException` |
| `hn.uth.poo.biblioteca.dao` | `Conexion`, `CrudDAO`, `AutorDAO`, `LibroDAO` |
| `hn.uth.poo.biblioteca.dao.impl` | `AutorDAOImpl`, `LibroDAOImpl` |
| `hn.uth.poo.biblioteca.servicio` | `ServicioAutores`, `ServicioLibros` |
| `hn.uth.poo.biblioteca.app` | `Main`, `VentanaPrincipal`, `PanelAutores`, `PanelLibros`, `Mensajes` |

## Reglas de negocio

- Nombre del autor y título del libro obligatorios.
- ISBN obligatorio y no repetido.
- Año entre 1450 y el año actual.
- Precio mayor o igual a 0.
- Un libro solo se registra si su autor existe.
- No se puede eliminar un autor que tiene libros.

Cualquier regla incumplida lanza `DatoInvalidoException` y se muestra en un mensaje sin cerrar el programa.

## Evidencias

Las capturas están en la carpeta [`evidencias`](evidencias/).

### Autores
- Crear: ![](evidencias/autor_crear.png)
- Listar: ![](evidencias/autor_listar.png)
- Buscar por ID: ![](evidencias/autor_buscar.png)
- Actualizar: ![](evidencias/autor_actualizar.png)
- Eliminar: ![](evidencias/autor_eliminar.png)

### Libros
- Crear: ![](evidencias/libro_crear.png)
- Listar: ![](evidencias/libro_listar.png)
- Buscar por ID: ![](evidencias/libro_buscar.png)
- Actualizar: ![](evidencias/libro_actualizar.png)
- Eliminar: ![](evidencias/libro_eliminar.png)
- Libros de un autor: ![](evidencias/libro_por_autor.png)

### Reglas de negocio rechazando datos
- ISBN repetido: ![](evidencias/regla_isbn_repetido.png)
- Eliminar autor con libros: ![](evidencias/regla_autor_con_libros.png)
