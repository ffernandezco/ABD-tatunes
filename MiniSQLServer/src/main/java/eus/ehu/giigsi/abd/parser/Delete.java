package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Delete implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Condition where;

    public Delete(String table, Condition where)
    {
        this.table = table ;
        this.where = where;
    }

    public String execute(Database database)
    {
        if (table == null || where == null)
        {
            System.out.println("Error : tabla o condición esta vacia") ;
        }
        StringBuilder delete = new StringBuilder();
        delete.append("DELETE").append(table).append("WHERE(");
        for (int i =0 ;i < where.column.length(); i++)
        {

        }
      return null;
    }
}
