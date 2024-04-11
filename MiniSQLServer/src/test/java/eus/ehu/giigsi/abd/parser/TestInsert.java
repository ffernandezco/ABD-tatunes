package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInsert {


    private Database db;



    @BeforeEach
    public void setUp(){

        db  = new Database("admin","user","password");
        Column columna1 = new Column(Column.DataType.STRING, "Columna1" ,Arrays.asList("v1","v2","v3")) ;
        Table table  = new Table("Table1", Arrays.asList(columna1)) ;

        db.addTable(table);
    }

    @Test
    public void TestInsert (Database db) throws IOException {
        this.db = db;
        Insert insert = new Insert("Table1", Arrays.asList("valor1", "valor2"));

        String resultado= insert.execute(db);
        assertEquals(Constants.INSERT_SUCCESS, resultado);

    }

    @Test
    public  void TestInsertError () throws IOException{
        Insert insert = new Insert("Table1", Arrays.asList("valor1", "valor2"));

        String resultado = insert.execute(db);

        assertEquals(Constants.ERROR, resultado);


    }





}
