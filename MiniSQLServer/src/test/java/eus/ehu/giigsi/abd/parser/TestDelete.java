package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestDelete {
    private Database db;
    Condition condition;
    String table1 = "Table1";

    @BeforeEach
    public void setUp(){

        /*db  = new Database("admin","user","password");
        Column columna1 = new Column(Column.DataType.STRING, "Columna1" , Arrays.asList("v1","v2","v3")) ;
        Table table  = new Table("Table1", Arrays.asList(columna1)) ;
        db.addTable(table);*/

        db  = new Database("admin","user","password");
        db.save("admin");
        Column columna1 = new Column(Column.DataType.STRING, "columna1" ,Arrays.asList("v1","v2","v3")) ;
        Table table  = new Table("table1", Arrays.asList(columna1)) ;

        db.addTable(table) ;
        table.save("admin");
    }


    @Test
    public void TestDelete () throws IOException {
         condition = new Condition("columna1","", "");
        Delete delete = new Delete(table1,condition);

        String resultado= delete.execute(db);
        assertEquals(Constants.DELETE_SUCCESS, resultado);


    }

    @Test
    public  void TestDeleteError () throws IOException{
        Delete delete = new Delete("Table1", condition);

        String resultado = delete.execute(db) ;

        assertEquals(Constants.ERROR, resultado);


    }


}
