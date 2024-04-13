package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInsert {

    @Test
    public void TestInsert (){
         Database db = new Database("database", "user",  "user");
         String table = "usuarios";
         List<String> values = Arrays.asList("Julen", "20");
         Insert insert = new Insert(table,values);


         String resultado = insert.execute(db);

         assertEquals("Inserción satisfactoria", resultado);
         System.out.println(resultado);
    }

    @Test
    public void TestInsertTablaVacia (){
        Database db = new Database("user",  "user");

        List<String> values = Arrays.asList("Julen", "20");
        Insert insert = new Insert(null,values);


        String resultado = insert.execute(db);

        assertEquals("Error: tabla o valores estan vacios", resultado);
        System.out.println(resultado);
    }
    @Test
    public void TestInsertValoresVacios (){
        Database db = new Database("user",  "user");

        String table = "usuarios";
        Insert insert = new Insert(null,null);

        String resultado = insert.execute(db);

        assertEquals("Error: tabla o valores estan vacios", resultado);
        System.out.println(resultado);
    }



}
