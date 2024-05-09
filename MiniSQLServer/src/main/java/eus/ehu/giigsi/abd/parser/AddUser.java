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
    public String execute(Database database)
    {
        Manager sm = database.getSecurityManager();
            for (int i = 0; i < sm.profiles.size(); i++) {
                if(sm.getProfiles().get(i).getName().equalsIgnoreCase(profileName)) {
                   User us = new User(username,password);
                   sm.getProfiles().get(i).getUsers().add(us);
                   return Constants.ADD_USER_SUCCESS;
                }
            }
            return null;
    }

}
