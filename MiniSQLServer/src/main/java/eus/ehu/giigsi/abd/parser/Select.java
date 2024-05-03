package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
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
        if (table == null || columns == null) {
            return Constants.ERROR + "tabla o valores estan vacios";

        } else {
            if (database.select(this.table, this.columns, this.where) != null) {
                // Hay que preguntar sobre lo qued debería devolver
                return Constants.CREATE_TABLE_SUCCESS;
            }

            return Constants.SYNTAX_ERROR;
        }
    }
}
