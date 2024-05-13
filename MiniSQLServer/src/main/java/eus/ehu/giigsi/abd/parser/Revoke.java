package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Privilege;
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


        if (!sm.isUserAdmin()) {
            return Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE;

        } if (sm.profileByName(profileName) == null) {
            return Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR;

        } else if (sm.profileByName(profileName).privilegesOn.get(tableName) == null) {
            return Constants.ERROR;

        } else {
            sm.revokePrivilege(profileName, tableName, Privilege.valueOf(privilegeName));

            if (sm.isGrantedPrivilege(profileName, tableName, Privilege.valueOf(privilegeName))){
                return Constants.ERROR;

            } else {
                return Constants.REVOKE_PRIVILEGE_SUCCESS;
            }
        }
    }
}
