package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class CreateSecurityProfile implements MiniSQLQuery {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String profileName;

    public CreateSecurityProfile(String profileName)
    {
        this.profileName = profileName;
    }
    public String execute(Database database)
    {

        if (profileName == null) {
            return Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR;

        } else {
            Manager securityManager = database.getSecurityManager();
            Profile profile = new Profile();
            profile.setName(profileName);

            if (! securityManager.isUserAdmin()) {
                return Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE;
            } else {
                securityManager.addProfile(profile);
                return Constants.CREATE_SECURITY_PROFILE_SUCCESS;
            }
        }

    }

}
