package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class DropTable implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table;
    public DropTable(String table)
    {
        this.table = table;
    }

    public String execute(Database database)
    {
        if(database.dropTable(table)) {
            return Constants.DROP_TABLE_SUCCESS;
        }

        return "Cannot delete table";
    }
}
