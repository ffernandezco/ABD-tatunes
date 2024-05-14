package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DeleteUser implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String username;

    public DeleteUser(String username)
    {
        this.username = username;
    }
    public String execute(Database database) {
        Manager sm = database.getSecurityManager();

        if (! sm.isUserAdmin()) {
            return Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE;

        } else if (sm.profileByUser(username) == null) {
            return Constants.USER_DOES_NOT_EXIST_ERROR;

        } else {
            List<User> userList = sm.profileByUser(username).users;

            for (User u : userList) {
                if (u.username.equals(username)) {

                    userList.remove(u);
                    return Constants.DELETE_USER_SUCCESS;
                }
            }

            return Constants.ERROR;
        }
    }
}
