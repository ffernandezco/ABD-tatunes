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
    public String execute(Database database)
    {
        Manager sm = database.getSecurityManager();
        if(!database.IsUserAdmin()){
            return Constants.ERROR;
        }
        List<Profile> pf = sm.getProfiles();
            for(int i=0; i<pf.size();i++){
                for(int j=0;j<pf.get(i).getUsers().size();j++){
                    if(pf.get(i).getUsers().get(j).getUsername().equalsIgnoreCase(username)){
                        pf.get(i).getUsers().remove(j);
                        return Constants.DELETE_USER_SUCCESS;
                    }
                }
            }
            return Constants.USER_DOES_NOT_EXIST_ERROR;
    }
}
