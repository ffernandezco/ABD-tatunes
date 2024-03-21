package eus.ehu.giigsi.abd.structures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class TestTable {
    @Test
    public void testInicio() {
        String name = "Test";
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<String> valores = List.of("Valor" + i);
            columns.add(new Column(Column.DataType.STRING, "Columna" + i, valores));
        }
        Table table = new Table(name, columns);

        assertEquals(name, table.name);
        assertEquals(columns, table.columns);
    }

    @Test
    public void testInicioVacio() {
        String name = "Test";
        Table table = new Table(name, null);

        assertEquals(name, table.name);
        assertEquals(null, table.columns);
    }

    @Test
    public void testInicioSinNombre() {
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> valores = List.of("Valor" + i);
            columns.add(new Column(Column.DataType.STRING, "Columna" + i, valores));
        }
        Table table = new Table(null, columns);
        assertEquals(null, table.name);
        assertEquals(columns, table.columns);
    }


    //TODO: tests load + save

    @Test
    public void testColumnByType() {
        String name = "Test";
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<String> valores = List.of("Valor" + i);
            columns.add(new Column(Column.DataType.STRING, "Columna" + i, valores));
        }
        Table table = new Table(name, columns);

        //TODO: probar clase
    }

}
