package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.*;
import eus.ehu.giigsi.abd.security.*;
import eus.ehu.giigsi.abd.structures.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /*
    @Test
    public void testSaveLoad() {
        database.tables.get(0).columns.get(0).setValues(List.of("c", "Badfaf", "Franchupapito"));
        database.tables.get(1).columns.get(0).setValues(List.of("D", "E", "F"));

        database.tables.get(0).columns.get(1).setValues(List.of("1.11", "2.22", "3.33"));
        database.tables.get(1).columns.get(1).setValues(List.of("4.44", "5.55", "6.66"));

        database.tables.get(0).columns.get(2).setValues(List.of("10", "20", "30"));
        database.tables.get(1).columns.get(2).setValues(List.of("40", "50", "60"));

        database.save(database.name);

        Database database1 = Database.load(database.name, "admin", "admin");

        for (int i = 0; i < database.tables.size(); i++) {
            String nameTabla = database.tables.get(i).name;
            Table t = database1.tableByName(nameTabla);

            assertNotNull(t);


            for (Column c : database.tables.get(i).columns) {
                for (int j = 0; j < database.tables.get(i).columns.size(); j++) {
                    assertEquals(t.columnByName(c.name).getValues().get(j), c.values.get(j));
                }
            }

        }
    }

     */


}
