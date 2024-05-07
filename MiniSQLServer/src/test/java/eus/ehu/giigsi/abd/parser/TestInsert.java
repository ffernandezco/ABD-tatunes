package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class TestInsert {
    Database database;
    Table table;

    @BeforeEach
    public void init () {
        database = new Database("user",  "user");

        List<String> vInt = new ArrayList<>();
        vInt.add("1");
        vInt.add("3");
        vInt.add("5");

        List<String> vStr = new ArrayList<>();
        vStr.add("A");
        vStr.add("C");
        vStr.add("D");

        List<String> vDbl = new ArrayList<>();
        vDbl.add("1.99");
        vDbl.add("3.99");
        vDbl.add("5.99");

        List<Column> column = new ArrayList<>();
        column.add(new Column(Column.DataType.INT, "int", vInt));
        column.add(new Column(Column.DataType.STRING, "str", vStr));
        column.add(new Column(Column.DataType.DOUBLE, "dbl", vDbl));

        String nombreTabla = "usuarios";
        table = new Table(nombreTabla, column);
        this.database.addTable(table);
    }

    @Test
    public void TestInsert (){

        List<String> values = Arrays.asList("20", "Julen", "1.88");
        Insert insert = new Insert(table.name, values);

        String resultado = insert.execute(database);

        assertEquals("A", table.columnByName("str").getValues().get(0));

        assertEquals("20", table.columnByName("int").getValues().get(3));
        assertEquals("Julen", table.columnByName("str").getValues().get(3));
        assertEquals("1.88", table.columnByName("dbl").getValues().get(3));

        assertEquals(Constants.INSERT_SUCCESS, resultado);

        insert = new Insert(table.name, null);
        resultado = insert.execute(database);
        assertEquals("ERROR: ", resultado);

        insert = new Insert(null, values);
        resultado = insert.execute(database);
        assertEquals(Constants.TABLE_DOES_NOT_EXIST_ERROR, resultado);

    }

    @Test
    public void TestInsertTablaVacia (){


        List<String> values = Arrays.asList("20", "Julen", "1.88");
        Insert insert = new Insert(null, values);


        String resultado = insert.execute(database);

        assertEquals(Constants.TABLE_DOES_NOT_EXIST_ERROR, resultado);
    }
    @Test
    public void TestInsertValoresVacios (){

        Insert insert = new Insert(table.name,null);

        String resultado = insert.execute(database);

        assertEquals(Constants.ERROR, resultado);
    }

    @Test
    public void SelectColumnaNoExiste() {
        Database database = new Database("admin", "adminPassword");
        assertEquals("MSG: Table created", database.executeMiniSQLQuery("CREATE TABLE MyTable (Name TEXT,Age INT,Address TEXT)"));
        assertEquals("ERROR: Column does not exist", database.executeMiniSQLQuery("SELECT Salary FROM MyTable"));
    }
}
