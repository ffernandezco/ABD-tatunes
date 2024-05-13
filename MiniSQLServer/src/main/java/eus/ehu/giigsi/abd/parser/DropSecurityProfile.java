package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class DropSecurityProfile implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String profileName;

    public DropSecurityProfile(String profileName)
    {
        this.profileName = profileName;
    }
    public String execute(Database database)
    {

        if (profileName == null) {
            return Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR;
        } else {
            Manager securityManager = database.getSecurityManager();
            if (securityManager != null) {
                securityManager.removeProfile(profileName);
                return Constants.DROP_SECURITY_PROFILE_SUCCESS;
            } else {
                return Constants.ERROR;
            }
        }

    }
}
