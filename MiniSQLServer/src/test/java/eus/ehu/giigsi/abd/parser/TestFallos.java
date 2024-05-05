package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFallos {
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
   public void CreateInsertSelect() {

        Insert insert = new Insert("table1", List.of("3", "F", "4.1"));
        insert.execute(db);

        Condition condition = new Condition("int", "=", "3");

        Select select = new Select("table1", List.of("int", "dbl"), condition);
        String result = select.execute(db);

        Table expected = new Table("table1", List.of(new Column(Column.DataType.INT, "int", List.of("3", "3")), new Column(Column.DataType.DOUBLE, "dbl", List.of("3.99", "4.1"))));

        assertEquals(expected.toString(), result);

   }

    @Test
    public void CreateInsertDeleteSelect() {

        Insert insert = new Insert("table1", List.of("3", "F", "4.1"));
        insert.execute(db);

        Table expected1 = new Table("table1", List.of(new Column(Column.DataType.INT, "int", List.of("1", "3", "5", "3")),
                new Column(Column.DataType.STRING, "str", List.of("A", "C", "D", "F")),
                new Column(Column.DataType.DOUBLE, "dbl", List.of("1.99", "3.99", "5.99", "4.1"))));

        assertEquals(expected1.toString(), table1.toString());



        Condition condition1 = new Condition("dbl", ">", "4");

        Delete delete = new Delete("table1", condition1);
        delete.execute(db);

        Table expected2 = new Table("table1", List.of(new Column(Column.DataType.INT, "int", List.of("1", "3")),
                new Column(Column.DataType.STRING, "str", List.of("A", "C")),
                new Column(Column.DataType.DOUBLE, "dbl", List.of("1.99", "3.99"))));

        assertEquals(expected2.toString(), table1.toString());



        Condition condition2 = new Condition("int", "=", "3");

        Select select = new Select("table1", List.of("int", "dbl"), condition2);
        String result3 = select.execute(db);

        Table expected3 = new Table("table1", List.of(new Column(Column.DataType.INT, "int", List.of("3")), new Column(Column.DataType.DOUBLE, "dbl", List.of("3.99"))));

        assertEquals(expected3.toString(), result3);

    }
}
