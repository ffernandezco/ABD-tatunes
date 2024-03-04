package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;

public interface MiniSQLQuery {
    String execute(Database database);
}
