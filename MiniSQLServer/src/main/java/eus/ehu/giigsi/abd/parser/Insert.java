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
        if (table == null || values == null) {
            return Constants.ERROR + "tabla o valores estan vacios";

        } else {
            if (database.Insert(this.table, this.values)) {
                return Constants.INSERT_SUCCESS;
            }

            return Constants.ERROR + "tabla o valores estan vacios";
        }
    }

    public String getTable() {
        return table;
    }

    public List<String> getValues() {
        return values;
    }
}
