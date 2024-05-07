package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateTable implements MiniSQLQuery {
    public String Table;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<ColumnParameters> columnsParameters;

    public CreateTable(String pTable, List<ColumnParameters> columns) {
        Table = pTable;
        columnsParameters = columns;
    }

    public String execute(Database database) {
        if (Table == null || Table.trim() == "") {
            return Constants.SYNTAX_ERROR;

        } else if (columnsParameters == null || columnsParameters.isEmpty()) {
            return Constants.DATABASE_CREATED_WITHOUT_COLUMNS_ERROR;
        }

        else {
            boolean created = database.createTable(Table, columnsParameters);
            if (created) {
                return Constants.CREATE_TABLE_SUCCESS;
            } else {
                return Constants.TABLE_ALREADY_EXISTS_ERROR;  // Use specific error message
            }
        }
    }
}