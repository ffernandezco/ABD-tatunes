package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreateTable {

    Database database;
    @BeforeEach
    public void createDatabase() {
        database = new Database("admin", "admin");


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

        List<Column> columns= new ArrayList<>();
        columns.add(new Column(Column.DataType.INT, "int", vInt));
        columns.add(new Column(Column.DataType.STRING, "str", vStr));
        columns.add(new Column(Column.DataType.DOUBLE, "dbl", vDbl));
        Table table = new Table("Tabla1", columns);

        database.addTable(table);
    }

    @Test
    public void prueba1() {
        String result, expected;

        List<ColumnParameters> columnParameters = new ArrayList<>();
        columnParameters.add(new ColumnParameters("int", Column.DataType.INT));
        columnParameters.add(new ColumnParameters("str", Column.DataType.STRING));
        columnParameters.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));

        CreateTable ct = new CreateTable("Tabla1",columnParameters);

        result = ct.execute(database);

        expected = "Table: " + ct.Table + "\n";

        for(ColumnParameters c : ct.columnsParameters) {
            expected += c.getName() + "\n";
        }

        assertEquals(expected, result);
    }

}
