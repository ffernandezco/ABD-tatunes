package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Update implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<SetValue> columns;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Condition where;

    public Update(String table, List<SetValue> columnNames, Condition where)
    {
        this.table = table;
        this.columns = columnNames;
        this.where = where;
    }

    public String execute(Database database)
    {
        if (database.update(table, columns, where)) {
            return Constants.UPDATE_SUCCESS;
        }
        return Constants.ERROR;
    }

}
