package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Select implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<String> columns;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Condition where;

    public Select(String table, List<String> columns, Condition where)
    {
        this.table = table;
        this.columns = columns;
        this.where = where;
    }

    public Select(String table, List<String> columns)
    {
        this(table,columns,null);
    }

    public String execute(Database database)
    {
        String result = "Select ";

        for(int i = 0; i < columns.size(); i++) {
            result += columns.get(i);
            if(i+1 < columns.size()) {
                result += ", ";
            }
        }

        result += " from" + table;

        if(where != null) {
            result += " " + where;
        }

        return database.executeMiniSQLQuery(result);

    }
}
