package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;

public class TestCreateTable {
    Database database;
    @BeforeEach
    void init() {
        database = new Database("database1", "admin", "admin");


    }
}
