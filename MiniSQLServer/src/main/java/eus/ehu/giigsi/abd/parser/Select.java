package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Select implements MiniSQLQuery {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<String> columns;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Condition where;

    public Select(String table, List<String> columns, Condition where) {
        this.table = table;
        this.columns = columns;
        this.where = where;
    }

    public Select(String table, List<String> columns) {
        this(table, columns, null);
    }

    public String execute(Database database) {
        Table resultado = database.tableByName(table);
        Table datosEsperados = database.select(this.table, this.columns, this.where);

        if (datosEsperados != null) {
            // Hay que preguntar sobre lo qued debería devolver
            return datosEsperados.toString();

        } else {
            if (columns == null || columns.size() == 0) {
                return Constants.COLUMN_DOES_NOT_EXIST_ERROR;

            } else if (columns != null || columns.size() == 1 && columns.get(0).equals("*")) {
                if (resultado == null) {
                    return Constants.TABLE_DOES_NOT_EXIST_ERROR;
                }

            } else {
                for (String columna : columns) {
                    boolean existeColumna = false;
                    for (Column columnaTabla : resultado.columns) {
                        if (columnaTabla.getName().equals(columna)) {
                            existeColumna = true;
                            break;
                        }
                    }
                    if (!existeColumna) {
                        return Constants.COLUMN_DOES_NOT_EXIST_ERROR;
                    }
                }
            }
        }
        return Constants.COLUMN_DOES_NOT_EXIST_ERROR;
    }
}