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
            return "No se ha creado la tabla, falta el nombre";

        } else if (columnsParameters == null || columnsParameters.isEmpty()) {
            return "No se puede crear una tabla sin columnas";
        }

        else {
            boolean b = database.createTable(Table, columnsParameters);
            if (b) {
                return Constants.CREATE_TABLE_SUCCESS;
            } else {
                return "Ya existe una tabla con ese nombre";
            }
        }
    }
}