package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDropTable {
    Database database;
    @BeforeEach
    void init() {
        database = new Database("admin", "admin");
        database.save("database1");

        /*
        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));
        */

        // Inicio 15/04
        CreateTable ct = new CreateTable("testDT", null);
        ct.execute(database);
    }

    @Test
    public void prueba1() {
        String result, expected;

        DropTable dt1 = new DropTable("prueba1");

        expected = "Cannot delete table";
        result = dt1.execute(database);

        assertEquals(expected, result);
    }

    @Test
    public void prueba2() {
        String result, expected;

        DropTable dt2 = new DropTable("testDT");

        expected = Constants.DROP_TABLE_SUCCESS;
        result = dt2.execute(database);

        assertEquals(expected, result);
    }

}
