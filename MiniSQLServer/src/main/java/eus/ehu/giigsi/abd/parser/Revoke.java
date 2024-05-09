package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.Getter;
import lombok.Setter;

public class Revoke implements MiniSQLQuery{
    @Getter
    @Setter
    public String privilegeName;
    @Getter
    @Setter
    public String tableName;
    @Getter
    @Setter
    public String profileName;

    public Revoke(String privilegeName, String tableName, String profileName)
    {
        this.privilegeName = privilegeName;
        this.tableName = tableName;
        this.profileName = profileName;
    }
    public String execute(Database database)
    {
        Manager sm = database.getSecurityManager();
        sm.revokePrivilege();
        return null;
    }

}
