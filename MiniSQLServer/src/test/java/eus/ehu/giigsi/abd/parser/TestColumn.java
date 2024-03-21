package eus.ehu.giigsi.abd.parser;
import eus.ehu.giigsi.abd.structures.Column;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
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

    }
}
