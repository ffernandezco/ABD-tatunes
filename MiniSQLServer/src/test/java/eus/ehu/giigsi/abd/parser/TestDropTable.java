package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDropTable {
    Database database;
    @BeforeEach
    void init() {
        database = new Database("database1", "admin", "admin");
        database.save("database1");

        /*
        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));
        */

        DropTable dt = new DropTable("testDT");
        dt.execute(database);
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
