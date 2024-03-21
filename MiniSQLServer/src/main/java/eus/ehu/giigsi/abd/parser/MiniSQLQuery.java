package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;

import java.io.IOException;

public interface MiniSQLQuery {
    String execute(Database database) throws IOException;
}
