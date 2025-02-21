package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.Condition;
import eus.ehu.giigsi.abd.parser.SetValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
    public void testColumnByName() {
        String name = "Test";
        List<Column> columns = new ArrayList<>();
        List<String> valores = List.of("Valor1","Valor2","Valor3");
        columns.add(new Column(Column.DataType.STRING, "Columna", valores));
        Table table = new Table(name, columns);
        assertEquals(columns.get(0), table.columnByName("Columna"));
        assertEquals(null, table.columnByName("demo"));
    }

    @Test
    public void testToString() {
        String name = "Test";
        List<Column> columns = new ArrayList<>();
        List<String> valores = List.of("Valor1","Valor2","Valor3");
        columns.add(new Column(Column.DataType.STRING, "Columna1", valores));
        columns.add(new Column(Column.DataType.STRING, "Columna2", valores));
        Table table = new Table(name, columns);
        assertEquals("['Columna1','Columna2']{'Valor1','Valor1'}{'Valor2','Valor2'}{'Valor3','Valor3'}", table.toString());
    }

    @Test
    public void testDeleteWhere() {
        String name = "Test";
        List<Column> columns = new ArrayList<>();
        List<String> valores = new ArrayList<>(List.of("Valor1", "Valor2", "Valor3"));
        columns.add(new Column(Column.DataType.STRING, "Columna", valores));
        Table table = new Table(name, columns);
        Condition condition = new Condition("Columna", "=", "Valor3");
        table.deleteWhere(condition);
        assertEquals("Valor1", table.columns.get(0).getValues().get(0));
        assertEquals("Valor2", table.columns.get(0).getValues().get(1));
    }


    @Test
    public void testInsert() {
        List<Column> columns = new ArrayList<>();
        List<String> valores = new ArrayList<>(List.of("Valor1"));
        List<String> inicio = new ArrayList<>();
        columns.add(new Column(Column.DataType.STRING, "Columna", inicio));
        Table table = new Table("Tabla", columns);
        table.insert(valores);
        assertEquals("Valor1", table.columns.get(0).getValues().get(0));
    }

    @Test
    public void testUpdate() {
        List<Column> columns = new ArrayList<>();
        List<String> valoresIniciales = new ArrayList<>(List.of("Valor1"));
        columns.add(new Column(Column.DataType.STRING, "Columna", valoresIniciales));
        Table table = new Table("Tabla", columns);
        Condition condition = new Condition("Columna", "=", "Valor1");
        List<SetValue> setValues = new ArrayList<>();
        setValues.add(new SetValue("Columna", "nuevoValor1"));
        String errorMessage = table.update(setValues, condition);

        // Comprobar salida del mensaje
        assertEquals(null, errorMessage);

        // Comprobar que se ha actualizado por el nuevo valoe especificado
        assertEquals("nuevoValor1", table.columns.get(0).getValues().get(0));
    }

}
