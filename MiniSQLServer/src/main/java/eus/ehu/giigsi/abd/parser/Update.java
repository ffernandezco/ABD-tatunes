package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
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
        String result;

        result = "Update from " + table + " set ";

        for(int i = 0; i < columns.size(); i++) {
            result += columns.get(i).getColumn() + " = " + columns.get(i).getValue();

            if(i+1 < columns.size()) {
                result += ",";
            }
        }

        result += " where " + where.column + " " + where.operator + " " + where.literalValue;

        database.executeMiniSQLQuery(result);

        return result;
    }

}
