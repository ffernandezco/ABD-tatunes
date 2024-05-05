package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreateTable {
    Database database;
    @BeforeEach
    public void init() {
        database = new Database("admin", "admin");
        database.tables.clear();

        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));

        CreateTable ct2 = new CreateTable("prueba2", listaCP);
        CreateTable ct3 = new CreateTable("prueba3", null);
        CreateTable ct4 = new CreateTable("", listaCP);

        CreateTable ct = new CreateTable("testCT", listaCP);
        ct.execute(database);

    }

    @Test
    public void prueba1() {
        String result, expected;

        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));

        CreateTable ct2 = new CreateTable("prueba2", listaCP);
        CreateTable ct3 = new CreateTable("prueba3", null);
        CreateTable ct4 = new CreateTable("", listaCP);

        CreateTable ct1 = new CreateTable("testCT", listaCP);

        expected = "Ya existe una tabla con ese nombre";
        result = ct1.execute(database);

        assertEquals(expected, result);
    }

    @Test
    public void prueba2() {
        String result, expected;

        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));

        CreateTable ct2 = new CreateTable("prueba2", listaCP);
        CreateTable ct3 = new CreateTable("prueba3", null);
        CreateTable ct4 = new CreateTable("", listaCP);

        expected = Constants.CREATE_TABLE_SUCCESS;
        result = ct2.execute(database);
        String r2 = ct3.execute(database);
        String r3 = ct4.execute(database);

        List<Column> columnList = new ArrayList<>();
        columnList.add(new Column(Column.DataType.STRING, "str"));
        columnList.add(new Column(Column.DataType.DOUBLE, "dbl"));
        columnList.add(new Column(Column.DataType.INT, "int"));

        Table prueba2 = new Table("prueba2", columnList);

        assertEquals(prueba2.name, database.tables.get(1).name);
        assertEquals(prueba2.columns.get(1).name, database.tables.get(1).columns.get(1).name);
        assertEquals(prueba2.columns.get(2).type, database.tables.get(1).columns.get(2).type);

        assertEquals("No se puede crear una tabla sin columnas", r2);
        assertEquals("No se ha creado la tabla, falta el nombre", r3);

        assertEquals(expected, result);
    }

}
