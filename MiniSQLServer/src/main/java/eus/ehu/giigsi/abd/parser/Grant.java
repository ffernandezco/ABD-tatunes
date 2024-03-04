package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Grant implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String privilegeName;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String tableName;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String profileName;

    public Grant(String privilegeName, String tableName, String profileName)
    {
        this.privilegeName = privilegeName;
        this.tableName = tableName;
        this.profileName = profileName;
    }
    public String execute(Database database)
    {
        return null;
    }
}
