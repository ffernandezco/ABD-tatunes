package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Insert implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table ;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<String> values;
    public Insert(String table, List<String> values)
    {
        this.table = table;
        this.values = values;
    }

    public String execute(Database database)
    {
        if (database.Insert(this.table, this.values)) {
            return Constants.INSERT_SUCCESS;

        } else {
            if (database.tableByName(table) == null) {
                return Constants.TABLE_DOES_NOT_EXIST_ERROR;

            }

            return Constants.ERROR;
        }
    }

}
