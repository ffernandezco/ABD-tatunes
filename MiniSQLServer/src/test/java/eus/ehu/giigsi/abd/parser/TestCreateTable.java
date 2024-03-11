package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCreateTable {

    @Test
    public void prueba1() {
        String result, expected;

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column(Column.DataType.STRING, "Str"));
        columns.add(new Column(Column.DataType.DOUBLE, "Dbl"));
        columns.add(new Column(Column.DataType.INT, "Int"));

        List<ColumnParameters> columnParameters = new ArrayList<ColumnParameters>();

        Table tablePrueba1 = new Table("Prueba1", columns);
        //CreateTable ct = new CreateTable(tablePrueba1, columnParameters);

        //Clase 11/03
        //result = ct.execute(null);
    }

}
