package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestUpdate {

    Database database;
    Table table;

    @BeforeEach
    void init() {
        database = new Database("database", "admin", "admin");

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

        table = new Table("tabla1", column);
        database.addTable(table);
    }

    @Test
    void test1() {
        String expected, result;

        List<SetValue> sv = new ArrayList<>();

        sv.add(new SetValue("str", "hola"));
        sv.add(new SetValue("int", "4"));
        sv.add(new SetValue("dbl", "4.99"));

        Condition c = new Condition("str", "<", "D");


        result = new Update(table.name, sv, c).execute(database);

        expected = Constants.UPDATE_SUCCESS;

        assertEquals(expected, result);
    }
}
