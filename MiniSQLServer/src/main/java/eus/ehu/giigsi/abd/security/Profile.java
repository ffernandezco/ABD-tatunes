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
        return false;
    }

    public boolean revokePrivilege(String table, Privilege privilege)
    {
        return false;
    }

    public boolean IsGrantedPrivilege(String table, Privilege privilege)
    {
        return false;
    }
}
