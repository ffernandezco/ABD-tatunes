package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
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
        String result;

        //Table t = database.tableByName(table);
        //t.update(columns, where);

        return null;
    }

}
