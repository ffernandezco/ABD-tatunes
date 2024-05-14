package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Privilege;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMiniSQLParser {

    Database database;
    Database cargadaDatabase;

    @BeforeEach
    public void init () {
        database = new Database("admin", "admin");

        Profile profile = new Profile();
        profile.setName("Profile");

        profile.users.add(new User("Asier", "morenasbajitas"));
        profile.users.add(new User("Fran", "rubiasaltas"));

        database.getSecurityManager().addProfile(profile);

        database.save("testeo");
    }


    //TEST PARSER
    @Test
    public void testCreateTableParse() {
        String query = "CREATE TABLE TableName (dato1 INT,dato2 TEXT,dato3 DOUBLE)";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);
        assertTrue(parsedQuery instanceof CreateTable);
        CreateTable createTableQuery = (CreateTable) parsedQuery;

        assertEquals("TableName", createTableQuery.Table);

        List<ColumnParameters> columnParameters = createTableQuery.getColumnsParameters();
        assertEquals(3, columnParameters.size());

        assertEquals("dato1", columnParameters.get(0).getName());
        assertEquals(Column.DataType.INT, columnParameters.get(0).getType());

        assertEquals("dato2", columnParameters.get(1).getName());
        assertEquals(Column.DataType.STRING, columnParameters.get(1).getType());

        assertEquals("dato3", columnParameters.get(2).getName());
        assertEquals(Column.DataType.DOUBLE, columnParameters.get(2).getType());
    }

    @Test
    public void testDeleteParse() {
        String query = "DELETE FROM Table    WHERE id>-3.2";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);
        assertTrue(parsedQuery instanceof Delete);
        Delete deleteQuery = (Delete) parsedQuery;

        assertEquals("Table", deleteQuery.getTable());

        Condition whereCondition = deleteQuery.getWhere();
        assertNotNull(whereCondition);
        assertEquals("id", whereCondition.getColumn());
        assertEquals(">", whereCondition.getOperator());
        assertEquals("-3.2", whereCondition.getLiteralValue());
    }

    @Test
    public void testDeleteParseInt() {
        String query = "DELETE FROM Table WHERE id<17";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);
        assertTrue(parsedQuery instanceof Delete);
        Delete deleteQuery = (Delete) parsedQuery;

        assertEquals("Table", deleteQuery.getTable());

        Condition whereCondition = deleteQuery.getWhere();
        assertNotNull(whereCondition);
        assertEquals("id", whereCondition.getColumn());
        assertEquals("<", whereCondition.getOperator());
        assertEquals("17", whereCondition.getLiteralValue());
    }

    @Test
    public void testDeleteParseText() {
        String query = "DELETE     FROM Table WHERE nombre='abc def'";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);
        assertTrue(parsedQuery instanceof Delete);
        Delete deleteQuery = (Delete) parsedQuery;

        assertEquals("Table", deleteQuery.getTable());

        Condition whereCondition = deleteQuery.getWhere();
        assertNotNull(whereCondition);
        assertEquals("nombre", whereCondition.getColumn());
        assertEquals("=", whereCondition.getOperator());
        assertEquals("abc def", whereCondition.getLiteralValue());
    }

    @Test
    public void testInsertParse() {
        String query = "INSERT INTO TableName VALUES (1,'Prueba 2',3)"; //Comillas
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);
        assertTrue(parsedQuery instanceof Insert);
        Insert insertQuery = (Insert) parsedQuery;

        assertEquals("TableName", insertQuery.getTable());

        List<String> values = insertQuery.getValues();
        assertNotNull(values);
        assertEquals(3, values.size());
        assertEquals("1", values.get(0));
        assertEquals("Prueba 2", values.get(1));
        assertEquals("3", values.get(2));
    }


    @Test
    public void testInsertParseConEspacios() {
        String query = "INSERT INTO TableName VALUES ( 7 ,  'Espacios'   )";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);
        assertFalse(parsedQuery instanceof Insert);
    }



    @Test
    public void testUpdateParse() {
        String query = "UPDATE TableName SET     dato='nombre1',dato1=30 WHERE id = 12.1";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof Update);
        Update updateQuery = (Update) parsedQuery;

        assertEquals("TableName", updateQuery.getTable());

        List<SetValue> columns = updateQuery.getColumns();
        assertNotNull(columns);
        assertEquals(2, columns.size());
        assertEquals("dato", columns.get(0).getColumn());
        assertEquals("nombre1", columns.get(0).getValue());
        assertEquals("dato1", columns.get(1).getColumn());
        assertEquals("30", columns.get(1).getValue());


        Condition whereCondition = updateQuery.getWhere();
        assertNotNull(whereCondition);
        assertEquals("id", whereCondition.getColumn());
        assertEquals("=", whereCondition.getOperator());
        assertEquals("12.1", whereCondition.getLiteralValue());
    }

    @Test
    public void testUpdateParseEspacios() {
        String query = "UPDATE TableName SET     dato='nombre1' , dato1=30 WHERE id = 12";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertFalse(parsedQuery instanceof Update);
    }

    @Test
    public void testSelectParse() {
        String query = "SELECT columnName1 FROM Table WHERE id > 'Fran'";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof Select);
        Select selectQuery = (Select) parsedQuery;

        assertEquals("Table", selectQuery.getTable());

        List<String> columns = selectQuery.getColumns();
        assertEquals(1, columns.size());
        assertTrue(columns.contains("columnName1"));

        Condition whereCondition = selectQuery.getWhere();
        assertNotNull(whereCondition);
        assertEquals("id", whereCondition.getColumn());
        assertEquals(">", whereCondition.getOperator());
        assertEquals("Fran", whereCondition.getLiteralValue());
    }

    @Test
    public void testSelectParseSinWhere() {
        String query = "SELECT columnName1 FROM Table";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof Select);
        Select selectQuery = (Select) parsedQuery;

        assertEquals("Table", selectQuery.getTable());

        List<String> columns = selectQuery.getColumns();
        assertEquals(1, columns.size());
        assertTrue(columns.contains("columnName1"));

    }

    @Test
    public void testSelectParseMultiple() {
        String query = "SELECT      columnName1, columnName2 FROM Table WHERE id < 3.62";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertFalse(parsedQuery instanceof Select);

    }


    @Test
    public void testDropTableParse() {
        String query = "DROP TABLE TableName";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof DropTable);
        assertEquals("TableName", ((DropTable) parsedQuery).getTable());
    }

    //TEST PARSER - Security
    @Test
    public void testCreateSecurityProfileParse() {
        String query = "CREATE SECURITY PROFILE ProfileDemo";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof CreateSecurityProfile);
        assertEquals("ProfileDemo", ((CreateSecurityProfile) parsedQuery).getProfileName());
    }

    @Test
    public void testCreateSecurityProfileSinLetrasParse() {
        String query = "CREATE SECURITY PROFILE ProfileDemo1";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertNull(parsedQuery);
    }

    @Test
    public void testDropSecurityProfileParse() {
        String query = "DROP SECURITY PROFILE ProfileDemo";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof DropSecurityProfile);
        assertEquals("ProfileDemo", ((DropSecurityProfile) parsedQuery).getProfileName());
    }

    @Test
    public void testGrantParse() {
        String query = "GRANT DELETE ON tabla TO Profile";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof Grant);
        assertEquals("DELETE", ((Grant) parsedQuery).getPrivilegeName());
        assertEquals("tabla", ((Grant) parsedQuery).getTableName());
        assertEquals("Profile", ((Grant) parsedQuery).getProfileName());
    }

    @Test
    public void testRevokeParse() {
        String query = "REVOKE SELECT ON tabla TO Profile";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof Revoke);
        assertEquals("SELECT", ((Revoke) parsedQuery).getPrivilegeName());
        assertEquals("tabla", ((Revoke) parsedQuery).getTableName());
        assertEquals("Profile", ((Revoke) parsedQuery).getProfileName());
    }

    @Test
    public void testAddUser() {
        String query = "ADD USER (Usuario,Pass,Perfil)";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof AddUser);
        assertEquals("Usuario", ((AddUser) parsedQuery).getUsername());
        assertEquals("Pass", ((AddUser) parsedQuery).getPassword());
        assertEquals("Perfil", ((AddUser) parsedQuery).getProfileName());
    }

    @Test
    public void testAddUserSinLetras() {
        String query = "ADD USER (Usuario1,Pass,Perfil1)";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertNull(parsedQuery);
    }
    @Test
    public void testAddUserConEspacios() {
        String query = "ADD USER (Usuario, Pass, Perfil)";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertNull(parsedQuery);
    }

    @Test
    public void testDeleteUserParse() {
        String query = "DELETE USER Fran";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertTrue(parsedQuery instanceof DeleteUser);
        assertEquals("Fran", ((DeleteUser) parsedQuery).getUsername());
    }

    @Test
    public void testDeleteUserSinLetrasParse() {
        String query = "DELETE USER Asier1";
        MiniSQLQuery parsedQuery = MiniSQLParser.parse(query);

        assertNull(parsedQuery);
    }

    //TEST MÉTODOS
    @Test
    public void testCommaSeparatedNames() {
        String input = "Asier,Fran, ,Martina,Julito, ,";
        List<String> output = List.of("Asier", "Fran", "Martina", "Julito");
        List<String> result = MiniSQLParser.commaSeparatedNames(input);
        assertNotNull(result);
        assertEquals(output.size(), result.size());
        for (int i = 0; i < output.size(); i++) {
            assertEquals(output.get(i), result.get(i));
        }
    }

    @Test
    public void peticionesSinSerAdmin () {
        cargadaDatabase = Database.load("testeo", "Asier", "morenasbajitas");

        AddUser addUser = new AddUser("Pablo", "pelirrojastambien", "Profile");
        assertEquals(Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE, addUser.execute(cargadaDatabase));

        DeleteUser deleteUser = new DeleteUser("Usuario");
        assertEquals(Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE, deleteUser.execute(cargadaDatabase));


        DropSecurityProfile dropSecurityProfile = new DropSecurityProfile("Profile");
        assertEquals(Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE, dropSecurityProfile.execute(cargadaDatabase));

        CreateSecurityProfile createSecurityProfile = new CreateSecurityProfile("Profile");
        assertEquals(Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE, createSecurityProfile.execute(cargadaDatabase));

        Revoke revoke = new Revoke(Privilege.INSERT.name(), "Tabla", "Profile");
        assertEquals(Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE, revoke.execute(cargadaDatabase));

        Grant grant = new Grant(Privilege.INSERT.name(), "Tabla", "Profile");
        assertEquals(Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE, grant.execute(cargadaDatabase));
    }

    @Test
    public void perfilNoExiste () {
        cargadaDatabase = Database.load("testeo", "admin", "admin");

        AddUser addUser = new AddUser("Lander", "Zubillaga", "Ulia");
        assertEquals(Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR, addUser.execute(cargadaDatabase));

        DropSecurityProfile dropSecurityProfile = new DropSecurityProfile("Vitoria");
        assertEquals(Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR, dropSecurityProfile.execute(cargadaDatabase));
    }

    @Test
    public void usuarioInexistente () {
        cargadaDatabase = Database.load("testeo", "admin", "admin");

        DeleteUser deleteUser = new DeleteUser("Ander");
        assertEquals(Constants.USER_DOES_NOT_EXIST_ERROR, deleteUser.execute(cargadaDatabase));
    }

    /*
    @Test
    public void borrarUsuarioExistente() {
        cargadaDatabase = Database.load("testeo", "admin", "admin");

        Profile profile = cargadaDatabase.securityManager.profileByName("Profile");

        AddUser addUser = new AddUser("Ander", "Bidebieta", "Profile");

        assertEquals(Constants.ADD_USER_SUCCESS, addUser.execute(cargadaDatabase));
        assertEquals(3, profile.users.size());
        assertEquals("Ander", profile.users.get(profile.users.size()-1).username);

        DeleteUser deleteUser = new DeleteUser("Ander");

        assertEquals(Constants.DELETE_USER_SUCCESS, deleteUser.execute(cargadaDatabase));
        assertEquals(2, profile.users.size());
        assertEquals("Fran", profile.users.get(profile.users.size()-1).username);
    }

     */

    /*
    @Test
    public void grantRevoke () {
        cargadaDatabase = Database.load("testeo", "admin", "admin");
        cargadaDatabase.securityManager.profileByName("Profile").privilegesOn.put("Tabla1", List.of(Privilege.SELECT));

        Grant grant = new Grant(Privilege.UPDATE.name(), "Tabla1", "Profile");

        assertEquals(Constants.GRANT_PRIVILEGE_SUCCESS, grant.execute(cargadaDatabase));
        assertEquals(List.of(Privilege.SELECT, Privilege.UPDATE), cargadaDatabase.securityManager.profileByName("Profile").privilegesOn.get("Tabla1"));
    }
     */
}
