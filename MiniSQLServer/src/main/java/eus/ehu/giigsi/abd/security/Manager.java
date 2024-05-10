package eus.ehu.giigsi.abd.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    @Getter
    @Setter
    public String uName;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<Profile> profiles = new ArrayList<>();


    public Manager(String username)
    {
        this.uName = username;
    }

    public boolean isUserAdmin()
    {
        Profile profile = profileByUser(uName);

        if (profile.name.equals(Profile.AdminProfileName)) return true;
        return false;
    }

    public boolean isPasswordCorrect(String username, String password)
    {
        for (Profile p : profiles) {
            for (User u : p.users) {
                if (u.username.equals(username)) {
                    if (Encryption.encrypt(password).equals(u.encryptedPassword)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void grantPrivilege(String profileName, String table, Privilege privilege)
    {
        if (isUserAdmin()){
            for (Profile profile : profiles){
                if(profile.getName() == profileName) {
                    profile.grantPrivilege(table, privilege);
                }
            }
        }
    }

    public void revokePrivilege(String profileName, String table, Privilege privilege)
    {
        if (isUserAdmin()){
            for (Profile profile : profiles){
                if(profile.getName() == profileName) {
                    profile.revokePrivilege(table, privilege);
                }
            }
        }
    }

    public boolean isGrantedPrivilege(String username, String table, Privilege privilege)
    {
        Profile profile = profileByUser(username);
        return profile.IsGrantedPrivilege(table, privilege);
    }

    public void addProfile(Profile profile)
    {
        if(isUserAdmin()){
            profiles.add(profile);
        }
    }

    public User userByName(String username)
    {
        for (Profile p : profiles) {
            for (User u : p.users) {
                if (u.username.equals(username)) return u;
            }
        }
        return null;
    }

    public Profile profileByName(String profileName)
    {
        for (Profile p : profiles) {
            if (p.name.equals(profileName)) return p;
        }
        return null;
    }

    public Profile profileByUser(String username)
    {
        for (Profile p : profiles) {
            for (User u : p.users) {
                if (u.username.equals(username)) {
                    return p;
                }
            }
        }
        return null;
    }

    public boolean removeProfile(String profileName)
    {
        if(isUserAdmin()) {
            Profile p = profileByName(profileName);
            return profiles.remove(p);
        }
        return false;
    }

    public static Manager load(String databaseName, String username)
    {

        return null;
    }

    public void save(String databaseName)
    {

    }
}