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

public class TestSelect {
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
        List<String> columns = new ArrayList<>();
        columns.add("int");

        Condition condition = new Condition("int", "<", "5");
        Select s = new Select("tabla1", columns, condition);

        String expected = Constants.CREATE_TABLE_SUCCESS;
        String result = s.execute(database);

        assertEquals(expected, result);
    }

    // El test que vale es este
    @Test
    void test2() {
        List<String> columnsSelect = new ArrayList<>();
        columnsSelect.add("str");
        columnsSelect.add("dbl");

        Condition condition = new Condition("int", "<", "5");

        List<String> vStr = new ArrayList<>();
        vStr.add("A");
        vStr.add("C");

        List<String> vDbl = new ArrayList<>();
        vDbl.add("1.99");
        vDbl.add("3.99");

        List<Column> columnTabla2 = new ArrayList<>();
        columnTabla2.add(new Column(Column.DataType.STRING, "str", vStr));
        columnTabla2.add(new Column(Column.DataType.DOUBLE, "dbl", vDbl));

        Table t2 = new Table("Resultado", columnTabla2);

        Table result = database.select("tabla1", columnsSelect, condition);

        assertEquals(t2, result);
    }
}
