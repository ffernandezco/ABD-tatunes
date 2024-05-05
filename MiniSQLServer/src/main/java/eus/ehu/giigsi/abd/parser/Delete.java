package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Delete implements MiniSQLQuery {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String table;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Condition where;

    public Delete(String table, Condition where) {
        this.table = table;
        this.where = where;
    }

    public String execute(Database database) {
        if (table == null || where == null) {
            return Constants.ERROR + "tabla o valores estan vacios";

        } else {
            boolean resultado = database.deleteWhere(table, where);

            if (resultado == true) {
                return Constants.DELETE_SUCCESS;
            }
            return Constants.ERROR + "tabla o valores estan vacios";
        }
    }
}
