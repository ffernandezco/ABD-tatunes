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
        List<Column> listColumns = new ArrayList<Column>();

        String result = "Table: " + Table + "\n";

        for(ColumnParameters c : columnsParameters) {
            Column column = new Column(c.getType(), c.getName());
            listColumns.add(column);
            result += column.getName() + "\n";
        }

        Table t = new Table(this.Table, listColumns);

        return result;
    }
}
