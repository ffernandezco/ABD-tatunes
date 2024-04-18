package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
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

    public String execute(Database database) throws IOException {
        Table tb = database.select(this.table, this.columns, this.where);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<tb.columns.size();i++){
            for(String elemento : tb.columns.get(i).values){
                sb.append(elemento).append(",");
            }
            if(sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
            sb.append("\n");
        }
        String val = sb.toString();
        return val;
    }
}
