package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Privilege;
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
        if (database.tableByName(tableName) == null) {
            return Constants.TABLE_DOES_NOT_EXIST_ERROR;
        }

        Manager manager = database.securityManager;

        if (manager.profileByName(profileName) == null) {
            return Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR;
        }
        if (!manager.isUserAdmin()) {
            return Constants.USER_DOES_NOT_HAVE_PRIVILEGE_ERROR;
        } else {
            manager.grantPrivilege(profileName, tableName, Privilege.valueOf(privilegeName));
        }
        if (!manager.isGrantedPrivilege(profileName, tableName, Privilege.valueOf(privilegeName))){
            return Constants.ERROR + "Failed to grant privilege";
        }

        return Constants.GRANT_PRIVILEGE_SUCCESS;
    }
}

