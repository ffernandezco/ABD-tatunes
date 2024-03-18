package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CreateTable implements MiniSQLQuery{
    public String Table;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<ColumnParameters> columnsParameters;

    public CreateTable(String pTable, List<ColumnParameters> columns)
    {
        Table = pTable;
        columnsParameters = columns;
    }
    public String execute(Database database)
    {
        String result = "Create table " + Table + "(";

        for(int i = 0; i < columnsParameters.size(); i++) {
            result += columnsParameters.get(i).getName() + " " + columnsParameters.get(i).getType();

            if(i+1 == columnsParameters.size()) {
                result += ",";
            }
        }

        result += ")";

        database.executeMiniSQLQuery(result);

        return result;
    }
}
