package eus.ehu.giigsi.abd.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

        if(profile != null) {
            if (profile.name.equals(Profile.AdminProfileName)) return true;
        }
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
        if(profile != null) {
            return profile.IsGrantedPrivilege(table, privilege);
        }
        return false;
    }

    public void addProfile(Profile profile)
    {
        if(isUserAdmin()) {
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

    public void save(String databaseName) {
        String path = databaseName + File.separator + "Manager";

        for (Profile p : profiles) {
            String pathProfile = path + p.name;

            File file = new File(pathProfile);
            file.mkdirs();
            try {
                File usuarios = new File(pathProfile + File.separator + "Users.txt");
                usuarios.createNewFile();
                writeUsers(p, usuarios);

                File contrasenas = new File(pathProfile + File.separator + "Passwords.txt");
                contrasenas.createNewFile();
                writePasswords(p, contrasenas);

                File tablas = new File(pathProfile + File.separator + "Tables.txt");
                tablas.createNewFile();
                writeTables(p, tablas);

                File privilegios = new File(pathProfile + File.separator + "Privileges.txt");
                privilegios.createNewFile();
                writePrivileges(p, privilegios);

            } catch (IOException exception) {}
        }
    }

    public void writeUsers(Profile profile, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            for (int i = 0; i < profile.users.size(); i++) {

                if (i < profile.users.size() - 1) fileWriter.write(profile.users.get(i).username + "\n");
                else  fileWriter.write(profile.users.get(i).username);
            }
            fileWriter.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void writePasswords(Profile profile, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            for (int i = 0; i < profile.users.size(); i++) {

                if (i < profile.users.size() - 1) fileWriter.write(profile.users.get(i).encryptedPassword + "\n");
                else  fileWriter.write(profile.users.get(i).encryptedPassword);
            }
            fileWriter.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void writeTables(Profile profile, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            for (int i = 0; i < profile.privilegesOn.size(); i++) {

                if (i < profile.privilegesOn.size() - 1) {
                    Set nombreTablas = profile.privilegesOn.keySet();

                    Iterator iterator = nombreTablas.iterator();

                    while (iterator.hasNext()) {
                        fileWriter.write((String) iterator.next() + "\n");
                    }

                } else {
                    Set nombreTablas = profile.privilegesOn.keySet();

                    Iterator iterator = nombreTablas.iterator();

                    while (iterator.hasNext()) {
                        fileWriter.write((String) iterator.next());
                    }
                }
            }
            fileWriter.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void writePrivileges(Profile profile, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            for (int i = 0; i < profile.privilegesOn.size(); i++) {

                if (i < profile.privilegesOn.size() - 1) {
                    Set nombreTablas = profile.privilegesOn.keySet();

                    Iterator iterator = nombreTablas.iterator();

                    while (iterator.hasNext()) {

                        List<Privilege> privileges = profile.privilegesOn.get(iterator.next());

                        for (int j = 0; i < privileges.size(); j++) {

                            if (j < privileges.size() - 1)  fileWriter.write(privileges.get(j) + ", ");

                            else fileWriter.write(privileges.get(j) + "\n");
                        }
                    }

                } else {
                    Set nombreTablas = profile.privilegesOn.keySet();

                    Iterator iterator = nombreTablas.iterator();

                    while (iterator.hasNext()) {
                        fileWriter.write((String) iterator.next());
                    }
                }
            }
            fileWriter.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}