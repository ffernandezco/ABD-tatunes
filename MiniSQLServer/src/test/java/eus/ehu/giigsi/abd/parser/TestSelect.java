package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSelect {

    @Test
    public void TestSelect() throws IOException {

        Database db = new Database("user", "user");
        db.name = "empresa";
        String table = "empleado";
        List<String> col = new ArrayList<>();
        col.add("dni");
        col.add("nombre");

        String condCol = "dni";
        String condOp = "=";
        String condLitVal = "12345678X";
        Condition cond = new Condition(condCol,condOp,condLitVal);

        Select sel = new Select(table,col,cond);
        String resultado = sel.execute(db);

        assertEquals("12345678X,Asier \n", resultado);
        System.out.println(resultado);

    }

}
