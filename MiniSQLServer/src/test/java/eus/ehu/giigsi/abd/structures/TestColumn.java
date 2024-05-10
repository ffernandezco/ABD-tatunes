package eus.ehu.giigsi.abd.structures;
import eus.ehu.giigsi.abd.parser.Condition;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestColumn {

    @Test
    void testCreacionInicial() {
        //Creamos una instancia column con unos valores puestos por nosotros, verificamos que la inicialización de
        //la lista de valores se hace correctamente cuando proporcionamos los valores en la creación de la columna.
        List<String> initialValues = Arrays.asList("asier", "francisco", "martina", "julen", "mauricio");
        Column columnaInteg = new Column(Column.DataType.STRING, "Integrantes", initialValues);
        assertNotNull(columnaInteg);
        assertEquals(initialValues, columnaInteg.values);

        // Creamos una instancia column sin darle valores para verificar que al crearla, se crea vacía
        Column columnaEdad = new Column(Column.DataType.INT, "Edad");
        assertEquals(Column.DataType.INT, columnaEdad.type);
        assertEquals("Edad", columnaEdad.name);
        assertTrue(columnaEdad.values.isEmpty());
    }

    @Test
    void testIndicesWhereIsTrue() {
        //Creamos una columna nueva con valores iniciales y elegimos una condicion que validar
        // Devolviendo así los indices de los valores que cumplan la condición

        // INT
        List<String> initialValues = Arrays.asList("1", "2", "3", "4", "5");
        Column columnaIndices = new Column(Column.DataType.INT, "IDs", initialValues);

        Condition condition = new Condition("IDs", "=", "3");
        List<Integer> indices = columnaIndices.indicesWhereIsTrue(condition);

        assertEquals(1, indices.size());
        assertEquals(2, indices.get(0));

        // DOUBLES
        List<String> initialValues1 = Arrays.asList("3.7", "4.0", "2.1", "6.5", "8.6");
        Column columnaIndices1 = new Column(Column.DataType.DOUBLE, "Notas", initialValues1);

        Condition condition1 = new Condition("Notas", ">", "5.0");
        List<Integer> indices1 = columnaIndices1.indicesWhereIsTrue(condition1);

        assertEquals(2, indices1.size());
        assertTrue(indices1.contains(3));
        assertTrue(indices1.contains(4));


        // STRING
        List<String> initialValues2 = Arrays.asList("Investigación Operativa", "Diseño de Bases de Datos", "Cálculo", "EDA", "Administración de Base de Datos");
        Column columnaIndices2 = new Column(Column.DataType.STRING, "Asignaturas", initialValues2);

        Condition condition2 = new Condition("Asignaturas", "=", "Administración de Base de Datos");
        List<Integer> indices2 = columnaIndices2.indicesWhereIsTrue(condition2);

        assertEquals(1, indices2.size());
        assertEquals(4, indices2.get(0));
    }

    @Test
    void updateWhereTest() {

        // INT
        List<String> initialValues = Arrays.asList("20", "32", "47", "25", "16");
        Column column = new Column(Column.DataType.INT, "Edades", initialValues);

        Condition condition = new Condition("Edades", ">", "30");
        column.updateWhere(condition, "100");

        assertEquals(Arrays.asList("20", "100", "100", "25", "16"), column.values);

        // DOUBLE
        List<String> initialValues1 = Arrays.asList("35.65", "19.99", "9.99", "40.25", "4.95");
        Column column1 = new Column(Column.DataType.DOUBLE, "Precios", initialValues1);

        Condition condition1 = new Condition("Precios", "<", "20.00");
        column1.updateWhere(condition1, "14.99");

        assertEquals(Arrays.asList("35.65", "14.99", "14.99", "40.25", "14.99"), column1.values);

        // STRING
        List<String> initialValues2 = Arrays.asList("Francisco", "Julen", "Asier", "Mauricio", "Iñaki");
        Column column2 = new Column(Column.DataType.STRING, "Integrantes", initialValues2);

        Condition condition2 = new Condition("Integrantes", "=", "Iñaki");
        column2.updateWhere(condition2, "Martina");

        assertEquals(Arrays.asList("Francisco", "Julen", "Asier", "Mauricio", "Martina"), column2.values);
    }

    @Test
    void setValue() {
        // INT
        List<String> initialValues = Arrays.asList("1", "2", "3");
        Column column = new Column(Column.DataType.INT, "Numeros", initialValues);

        column.SetValue(0, "10");
        column.SetValue(2, "15");

        assertEquals(Arrays.asList("10", "2", "15"), column.values);

        // DOUBLE
        List<String> initialValues1 = Arrays.asList("4.9", "7.2", "9.3", "6.85");
        Column column1 = new Column(Column.DataType.DOUBLE, "Notas finales", initialValues1);

        column1.SetValue(0, "5.0");
        column1.SetValue(3, "7.0");

        assertEquals(Arrays.asList("5.0", "7.2", "9.3", "7.0"), column1.values);

        // STRING
        List<String> initialValues2 = Arrays.asList("Francisco", "Julen", "Asier", "Mauricio", "Eneko");
        Column column2 = new Column(Column.DataType.STRING, "Alumnos", initialValues2);

        column2.SetValue(4, "Martina");

        assertEquals(Arrays.asList("Francisco", "Julen", "Asier", "Mauricio", "Martina"), column2.values);
    }

    @Test
    void deleteAt() {
        // INT
        List<String> initialValues = new ArrayList<>();
        initialValues.addAll(Arrays.asList("1", "2", "3", "4", "5"));
        Column column = new Column(Column.DataType.INT, "Portales", initialValues);

        column.deleteAt(5);
        assertEquals(5, column.values.size());
        assertEquals("5", column.values.get(4));

        column.deleteAt(2);

        assertEquals(4, column.values.size());
        assertEquals("4", column.values.get(2));
        assertEquals("5", column.values.get(3));

        // DOUBLE
        List<String> initialValues1 = new ArrayList<>();
        initialValues1.addAll(Arrays.asList("4.1", "1.2", "3.3"));
        Column column1 = new Column(Column.DataType.DOUBLE, "Piso y puerta", initialValues1); // Agregar initialValues1 aquí
        column1.deleteAt(0);

        assertEquals(2, column1.values.size());
        assertEquals("1.2", column1.values.get(0));
        assertEquals("3.3", column1.values.get(1));

        // STRING
        List<String> initialValues2 = new ArrayList<>();
        initialValues2.addAll(Arrays.asList("Postas", "Manuel Iradier", "Dulzaina"));
        Column column2 = new Column(Column.DataType.STRING, "Calles", initialValues2); // Agregar initialValues2 aquí
        column2.deleteAt(2);

        assertEquals(2, column2.values.size());
        assertEquals("Postas", column2.values.get(0));
        assertEquals("Manuel Iradier", column2.values.get(1));
    }
}
