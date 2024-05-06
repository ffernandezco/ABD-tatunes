package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDelete {
    private Database db;
    Condition condition;
    Table table1;

    @BeforeEach
    public void setUp(){
        db = new Database("user",  "user");

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

        String nombreTabla = "table1";
        table1 = new Table(nombreTabla, column);
        this.db.addTable(table1);
    }


    @Test
    public void TestDelete () {
        condition = new Condition("str","=", "C");
        Delete delete = new Delete(table1.name, condition);

        String resultado= delete.execute(db);

        assertEquals(2, table1.columnByName("str").values.size());
        assertEquals("A", table1.columnByName("str").getValues().get(0));
        assertEquals("D", table1.columnByName("str").getValues().get(1));
        assertEquals("1", table1.columnByName("int").getValues().get(0));
        assertEquals("5", table1.columnByName("int").getValues().get(1));
        assertEquals("1.99", table1.columnByName("dbl").getValues().get(0));
        assertEquals("5.99", table1.columnByName("dbl").getValues().get(1));



        assertEquals(Constants.DELETE_SUCCESS, resultado);

        List<Column> columns = new ArrayList<>();
        columns.add(new Column(Column.DataType.INT, "int", List.of("1", "5")));
        columns.add(new Column(Column.DataType.STRING, "str", List.of("A", "D")));
        columns.add(new Column(Column.DataType.DOUBLE, "dbl", List.of("1.99", "5.99")));

        Table t = new Table("table1", columns);

        assertEquals(t.toString(), table1.toString());
    }

    /*
    @Test
    public  void TestDeleteError () {
        Delete delete = new Delete("Table1", condition);

        String resultado = delete.execute(db) ;

        assertEquals(Constants.ERROR, resultado);
    }
    */

}
