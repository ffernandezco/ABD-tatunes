# Servidor MiniSQLServer
_**Grupo Tatunes**: Asier Montero, Francisco Fernández, Julen Berbetoros, Martina Anabel Gioiello y Mauricio Bryan Gómez._

Este proyecto, diseñado para la asignatura _Administración de Bases de Datos_, contiene un pequeño **Servidor SQL basado en Java**, con Maven como gestor de proyecto. Permite, a grandes rasgos, crear bases de datos con sus respectivas tablas y columnas, así como asignar diferentes usuarios con sus respectivos perfiles según los privilegios que se les quiera dar.

## Uso del servidor
Una vez cargado, para comenzar a utilizar el servidor, es necesario instanciar un objeto `Database`, pasando como parámetro el usuario y la contraseña de administrador que se deseen. También puede utilizarse el método `load` de `Database` que, siguiendo la estructura marcada por el método `save` de la misma, permite cargar con un nombre, un usuario y una contraseña la información de la base de datos.

El proyecto también dispone de un gestor de seguridad (clase `Manager`), que permite crear perfiles con diferentes privilegios y asignarles dichos perfiles a los usuarios, de tal forma que solo si se cuenta con un privilegio determinado es posible realizar dicha acción.

Además, siempre habrá un usuario administrador que dispone de todos los permisos. Por defecto, es aquel con nombre de usuario _Admin_, si bien es cierto que se puede modificar cambiando la variable `AdminProfileName` de la clase `Profile` según resulte más relevante.

### Ejecución de instrucciones y opciones disponibles
Utilizando el método `MiniSQLParser.parse()` es posible utilizar la especificación SQL para ejecutar consultas, pasando como parámetro la consulta a realizar. Actualmente se entienden las siguientes consultas:
