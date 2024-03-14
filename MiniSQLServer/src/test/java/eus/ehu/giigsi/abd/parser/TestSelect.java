package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSelect {

    Database database;
    @BeforeEach
    public void createDatabase() {
        database = new Database("admin", "admin");
        //database.createTable("Tabla1", )

        //Commit 14/03
    }

    @Test
    public void TestPrueba1() {

    }

}
