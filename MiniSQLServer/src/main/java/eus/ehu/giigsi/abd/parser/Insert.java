package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Insert implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table;

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
        if (table == null || values == null)
        {
            return "Error: tabla o valores estan vacios";
        }

       StringBuilder insert = new StringBuilder();
        insert.append("INSERT INTO").append(table).append("VALUES(") ;
        for (int i =0; i < values.size(); i++)
        {
            insert.append(" (' ").append(values.get(i)).append(" ') ");

            if (i < values.size() - 1)
            {
               insert.append(", ");
            }
        }
        insert.append(")");

        try{
           database.executeMiniSQLQuery(insert.toString());
           return  "Inserción satisfactoria";

        } catch(Exception e){
            return "Error en la insercion"+ e.getMessage();
        }
    }
}
