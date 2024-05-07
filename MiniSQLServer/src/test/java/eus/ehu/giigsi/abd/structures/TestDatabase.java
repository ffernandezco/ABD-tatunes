package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.ColumnParameters;
import eus.ehu.giigsi.abd.parser.CreateTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestDatabase {

    Database database;

    @BeforeEach
    public void init() {
        database = new Database("admin", "admin");
        database.tables.clear();

        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));

        CreateTable ct1 = new CreateTable("prueba1", listaCP);
        CreateTable ct2 = new CreateTable("prueba2", listaCP);
        ct1.execute(database);
        ct2.execute(database);
    }

    @Test
    public void test1() {
        Table table = database.tableByName("prueba2");
        assertEquals(table.toString(), database.tables.get(1).toString());

        Column c1 = new Column(Column.DataType.STRING, "str");
        Column c2 = new Column(Column.DataType.DOUBLE, "dbl");
        Column c3 = new Column(Column.DataType.INT, "int");

        List<Column> columns = new ArrayList<>();
        columns.add(c1);
        columns.add(c2);
        columns.add(c3);

        Table t3 = new Table("table3", columns);
        database.addTable(t3);

        assertEquals("table3", database.tables.get(2).name);

        database.dropTable("prueba1");

        Table table1 = new Table("prueba2", columns);
        assertEquals(table1.toString(), database.tableByName("prueba2").toString());
    }

}
