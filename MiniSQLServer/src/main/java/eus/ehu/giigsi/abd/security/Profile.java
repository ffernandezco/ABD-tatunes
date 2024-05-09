package eus.ehu.giigsi.abd.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {
    public static final String AdminProfileName = "Admin";

    @Getter
    @Setter
    public String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<User> users = new ArrayList<>();

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Map<String, List<Privilege>> privilegesOn  = new HashMap<>();

    public boolean grantPrivilege(String table, Privilege privilege)
    {
        //Comprobar usuario admin
        if (name.equals(AdminProfileName)){
            //Diferentes casos según exista o no map de privilegios
            List<Privilege> privilegies = privilegesOn.get(table);
            if(privilegies != null) {
                privilegies.add(privilege);
                privilegesOn.put(table, privilegies);
                return true;
            }else{
                privilegies = new ArrayList<>();
                privilegies.add(privilege);
                privilegesOn.put(table, privilegies);
                return true;
            }
        }

        return false;
    }

    public boolean revokePrivilege(String table, Privilege privilege)
    {
        //Comprobar usuario admin
        if (name.equals(AdminProfileName)){
            List<Privilege> privilegies = privilegesOn.get(table);
            if(privilegies != null) {
                boolean removed = privilegies.remove(privilege);
                if (removed == true){
                    if(privilegies.isEmpty()){
                        privilegesOn.remove(table);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean IsGrantedPrivilege(String table, Privilege privilege)
    {
        return false;
    }
}
