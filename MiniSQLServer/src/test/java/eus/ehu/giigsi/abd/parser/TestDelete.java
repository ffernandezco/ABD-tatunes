package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDelete {

    @Test
    public void TestDelete()
    {
        Database db = new Database("user", "user");
        String table = "usuarios";
        String column = "Salario";
        String op = ">";
        String literalValue = "5000";
        Condition where= new Condition(column,op,literalValue);
        Delete delete = new Delete(table,where);

        String resultado = delete.execute(db);

        assertEquals("Eliminacion satisfactoria", resultado);
        System.out.println(resultado);


    }

}
