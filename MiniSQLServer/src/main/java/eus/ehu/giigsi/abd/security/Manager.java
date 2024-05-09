package eus.ehu.giigsi.abd.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<Profile> profiles = new ArrayList<>();


    public Manager(String username)
    {

    }

    public boolean isUserAdmin()
    {
        return true;
    }

    public boolean isPasswordCorrect(String username, String password)
    {

        return true;
    }

    public void grantPrivilege(String profileName, String table, Privilege privilege)
    {

    }

    public void revokePrivilege(String profileName, String table, Privilege privilege)
    {

    }

    public boolean isGrantedPrivilege(String username, String table, Privilege privilege)
    {

        return true;
    }

    public void addProfile(Profile profile)
    {

        return;
    }

    public User userByName(String username)
    {

        return null;
    }

    public Profile profileByName(String profileName)
    {

        return null;
    }

    public Profile profileByUser(String username)
    {

        return null;
    }

    public boolean removeProfile(String profileName)
    {

        return true;
    }

    public static Manager load(String databaseName, String username)
    {

        return null;
    }

    public void save(String databaseName)
    {

    }
}
