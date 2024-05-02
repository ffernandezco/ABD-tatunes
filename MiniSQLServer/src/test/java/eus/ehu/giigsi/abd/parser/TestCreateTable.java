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
        database = new Database("database1", "admin", "admin");
        database.save("database1");

        /*
        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));
        */

        CreateTable ct = new CreateTable("testCT", null);
        ct.execute(database);
    }

    @Test
    public void prueba1() {
        String result, expected;

        CreateTable ct1 = new CreateTable("testCT", null);

        expected = "Cannot create table";
        result = ct1.execute(database);

        assertEquals(expected, result);
    }

    @Test
    public void prueba2() {
        String result, expected;

        CreateTable ct2 = new CreateTable("prueba2", null);

        expected = Constants.CREATE_TABLE_SUCCESS;
        result = ct2.execute(database);

        assertEquals(expected, result);
    }

}
