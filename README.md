
# Servidor MiniSQLServer

Este proyecto, diseñado para la asignatura _Administración de Bases de Datos_, contiene un pequeño **Servidor SQL basado en Java**, con Maven como gestor de proyecto. Permite, a grandes rasgos, crear bases de datos con sus respectivas tablas y columnas, así como asignar diferentes usuarios con sus respectivos perfiles según los privilegios que se les quiera dar.

## Uso del servidor
Una vez cargado, para comenzar a utilizar el servidor, es necesario **instanciar un objeto `Database`**, pasando como parámetro el usuario y la contraseña de administrador que se deseen. **También puede utilizarse el método `load`** de `Database` que, siguiendo la estructura marcada por el método `save` de la misma, permite cargar con un nombre, un usuario y una contraseña la información de la base de datos.

El proyecto también dispone de un **gestor de seguridad** (clase `Manager`), que permite **crear perfiles con diferentes privilegios** y asignar dichos perfiles a los usuarios, de tal forma que solo si el usuario cuenta con un perfil de seguridad que permita en su lista de privilegios realizar una acción se podrá completar.

Además, **siempre habrá un usuario administrador** que dispone de todos los permisos. Por defecto, es aquel con **nombre de usuario _Admin_**, si bien es cierto que se puede modificar cambiando la variable `AdminProfileName` de la clase `Profile` según resulte más relevante.

### Ejecución de instrucciones y opciones disponibles
Utilizando el método `MiniSQLParser.parse()` es posible **utilizar la especificación SQL para ejecutar consultas**, pasando como parámetro la consulta a realizar. Actualmente se entienden las siguientes consultas:

| Instrucción             | Descripción                                                                                                                                                     | Ejemplo                                                     |
|-------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------|
| _CREATE TABLE_            | Crea una tabla con las columnas especificadas. Se deben especificar entre paréntesis los tipos de datos. Si hay más de una columna, se puede separar con comas. | `CREATE TABLE TableName (ColumnName INT,ColumnName DOUBLE)` |
| _DROP TABLE_              | Permite eliminar una tabla a partir de su nombre.                                                                                                               | `DROP TABLE TableName`                                      |
| _SELECT_                  | Permite seleccionar las columnas de una tabla. Opcionalmente se puede especificar una condición con WHERE.                                                      | `SELECT ColumnName,ColumnName1 FROM Table WHERE id=1`       |
| _DELETE_                  | Permite borrar los valores de una tabla que cumplan una determinada condición.                                                                                  | `DELETE FROM TableName WHERE edad<18`                       |
| _INSERT_                  | Permite insertar uno o varios valores en una tabla. En caso de ser varios valores, deberan separarse con una coma.                                              |  `INSERT INTO TableName VALUES (12,11,10)`                  |
| _UPDATE_                  | Modifica los valores de una tabla que cumplan una determinada condición.                                                                                        |  `UPDATE TableName SET ColumnName='NewName' WHERE id=1`     |
| _CREATE SECURITY PROFILE_ | Dado un nombre, permite crear un perfil de seguridad.                                                                                                           | `CREATE SECURITY PROFILE ProfileName`                         |
| _DROP SECURITY PROFILE_   | Dado el nombre de un perfil de seguridad, permite borrarlo.                                                                                                     | `DROP SECURITY PROFILE ProfileName`                           |
| _GRANT_                   | Otorga a un determinado perfil de seguridad un privilegio sobre una tabla.                                                                                      | `GRANT SELECT ON TableName TO ProfileName`                    |
| _REVOKE_                  | Elimina a un determinado perfil de seguridad un privilegio sobre una tabla.                                                                                     | `REVOKE DELETE ON TableName TO ProfileName`                   |
| _ADD USER_                | Permite añadir un usuario. Se debe especificar el nombre de usuario, la contraseña deseada y el perfil de seguridad a asignar.                                  | `ADD USER (User,P4ssw0rd,ProfileName)`                        |
| _DELETE USER_             | A partir de un nombre de usuario, permite eliminarlo.                                                                                                           | `DROP USER Username`                                          |

## Autores e información
Este proyecto ha sido realizado por el _Grupo “Tatunes”_, formado por los alumnos **Asier Montero, Francisco Fernández, Julen Berbetoros, Martina Anabel Gioiello y Mauricio Bryan Gómez** de la Escuela de Ingeniería de Vitoria-Gasteiz como parte de la asignatura _Administración de Bases de Datos_ del Grado en Ingeniería Informática de Gestión y Sistemas de Información en el curso 2023-24.

_[Universidad del País Vasco / Euskal Herriko Unibertsitatea (UPV/EHU)](https://www.ehu.eus/es/)_
