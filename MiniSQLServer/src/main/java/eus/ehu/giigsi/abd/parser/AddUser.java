package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class AddUser implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String username;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String password;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String profileName;


    public AddUser(String username, String password, String profileName)
    {
        this.username = username;
        this.password = password;
        this.profileName= profileName;
    }
    public String execute(Database database) {
        Manager sm = database.getSecurityManager();

        if (! sm.isUserAdmin()) {
            return Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE;

        } else if (sm.profileByName(profileName) == null) {
            return Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR;

        } else {
            User user = new User(username, password);
            sm.profileByName(profileName).users.add(user);

            return Constants.ADD_USER_SUCCESS;
        }
    }
}
