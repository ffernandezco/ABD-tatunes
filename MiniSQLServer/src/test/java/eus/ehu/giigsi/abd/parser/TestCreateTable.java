package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCreateTable {

    @Test
    public void prueba1() {
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column(Column.DataType.STRING, "Str"));
        columns.add(new Column(Column.DataType.DOUBLE, "Dbl"));
        columns.add(new Column(Column.DataType.INT, "Int"));

        //Clase 11/03
    }

}
