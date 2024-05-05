package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMiniSQLParser {

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

}
