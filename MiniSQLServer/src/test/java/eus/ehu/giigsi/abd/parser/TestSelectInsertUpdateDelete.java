package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSelectInsertUpdateDelete {
    Database database;
    Table table;

    @BeforeEach
    public void init() {
        database = new Database("database", "admin", "admin");

        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));

        CreateTable ct = new CreateTable("tabla1", listaCP);
        ct.execute(database);

        List<String> v1 = new ArrayList<>();
        v1.add("A");
        v1.add("1.99");
        v1.add("1");

        List<String> v2 = new ArrayList<>();
        v2.add("C");
        v2.add("3.99");
        v2.add("3");

        List<String> v3 = new ArrayList<>();
        v3.add("D");
        v3.add("5.99");
        v3.add("5");

        table = database.tableByName("tabla1");
        table.insert(v1);
        table.insert(v2);
        table.insert(v3);

    }

    @Test
    public void test() {
        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column(Column.DataType.STRING, "str"));
        columnList.add(new Column(Column.DataType.DOUBLE, "dbl"));
        columnList.add(new Column(Column.DataType.INT, "int"));

        Table t1 = new Table("tabla1", columnList);

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

        t1.columns.get(0).setValues(vStr);
        t1.columns.get(1).setValues(vDbl);
        t1.columns.get(2).setValues(vInt);

        assertEquals(t1.name, table.name);
        assertEquals(t1.columns.get(1).name, table.columns.get(1).name);
        assertEquals(t1.columns.get(2).type, table.columns.get(2).type);
        assertEquals(t1.columns.get(0).getValues().get(1), table.columns.get(0).getValues().get(1));
        // assertEquals(t1, table);

        List<String> valores = new ArrayList<>();
        valores.add("E");
        valores.add("2.99");
        valores.add("2");

        table.insert(valores);

        t1.columnByName("str").values.add("E");
        t1.columnByName("dbl").values.add("2.99");
        t1.columnByName("int").values.add("2");

        assertEquals(t1.columns.get(1).getValues().get(3), table.columns.get(1).getValues().get(3));

        List<String> columnsSelect = new ArrayList<>();
        columnsSelect.add("str");
        columnsSelect.add("int");

        Condition conditionOk = new Condition("dbl", "<", "4");
        Condition conditionBad1 = new Condition("", "<", "4");
        Condition conditionBad2 = new Condition("dbl", "", "4");
        Condition conditionBad3 = new Condition("dbl", "<", "");
        Condition conditionBad4 = new Condition("", "", "");


        Select select = new Select(table.name, columnsSelect);
        Table select1 = database.select(table.name, columnsSelect, null);
        Table select2 = database.select(table.name, columnsSelect, conditionOk);
        Table select3 = database.select(table.name, columnsSelect, conditionBad1);
        Table select4 = database.select(table.name, columnsSelect, conditionBad2);
        Table select5 = database.select(table.name, columnsSelect, conditionBad3);
        Table select6 = database.select(table.name, columnsSelect, conditionBad4);

        List<Column> listOk = new ArrayList<>();

        Table ok = new Table("tabla1", null);

    }
}
