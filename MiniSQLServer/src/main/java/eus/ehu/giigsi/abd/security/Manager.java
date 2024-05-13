package eus.ehu.giigsi.abd.security;

import eus.ehu.giigsi.abd.Constants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        this.profiles = new ArrayList<>();
    }

    public boolean isUserAdmin()
    {
        Profile profile = profileByUser(uName);

       /* if(profile != null) {
            if (profile.name.equals(Profile.AdminProfileName)) return true;
        }
        return false;*/


        for (Profile p : profiles){
            if (p.getName().equals(Profile.AdminProfileName)){
                return true;
            }

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
                if(profile.getName().equals(profileName)) {
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
        String path = "databases" + File.separator + databaseName + File.separator + "Manager";

        File file = new File(path);

        Manager manager = new Manager(username);

       /* try {

            File[] listFiles = file.listFiles();

            if (listFiles != null) {

                List<Profile> listProfiles = new ArrayList<>();


                for (File f : listFiles) {

                    Profile profile = new Profile();
                    profile.setName(f.getName());

                    List<String> listUsers = new ArrayList<>();
                    List<String> listPasswords = new ArrayList<>();
                    List<String> listTables = new ArrayList<>();

                    List<List<Privilege>> listPrivileges = new ArrayList<>();

                    for (File f1 : f.listFiles()) {

                        Scanner scanner = new Scanner(f1);

                        if (f1.getName().equals("Users.txt")) {

                            while (scanner.hasNextLine()) {
                                String nombreUser = scanner.nextLine();

                                listUsers.add(nombreUser);
                            }

                        } else if (f1.getName().equals("Passwords.txt")) {

                            while (scanner.hasNextLine()) {
                                String contraUser = scanner.nextLine();

                                listPasswords.add(contraUser);
                            }

                        } else if (f1.getName().equals("Tables.txt")) {

                            while (scanner.hasNextLine()) {
                                String table = scanner.nextLine();

                                listTables.add(table);
                            }

                        } else {

                            while (scanner.hasNext()) {

                                String p = scanner.next();
                                List<Privilege> privs = new ArrayList<>();

                                while (!p.equals("$")) {

                                    privs.add(Privilege.valueOf(p));
                                    p = scanner.next();
                                }

                                listPrivileges.add(privs);
                            }
                        }

                        if (listUsers.size() == listPasswords.size()) {
                            for (int x = 0; x < listUsers.size(); x++) {
                                User user = new User(listUsers.get(x), listPasswords.get(x));
                                profile.users.add(user);
                            }
                        }

                        if (listTables.size() == listPrivileges.size()) {
                            for (int y = 0; y < listTables.size(); y++) {
                                profile.privilegesOn.put(listTables.get(y), listPrivileges.get(y));
                            }
                        }

                        manager.profiles.add(profile);

                    }
                }

                return manager;

            }

        } catch (IOException e) {

            return null;
        }

        return null;*/

        try {
            File[] listFiles = file.listFiles();

            if (listFiles != null) {
                for (File f : listFiles) {
                    Profile profile = new Profile();

                    // Establecer el nombre del perfil usando el nombre del directorio
                    profile.setName(f.getName());

                    List<String> listUsers = new ArrayList<>();
                    List<String> listPasswords = new ArrayList<>();
                    List<String> listTables = new ArrayList<>();
                    List<List<Privilege>> listPrivileges = new ArrayList<>();

                    for (File f1 : f.listFiles()) {
                        Scanner scanner = new Scanner(f1);

                        if (f1.getName().equals("Users.txt")) {
                            while (scanner.hasNextLine()) {
                                String nombreUser = scanner.nextLine();
                                listUsers.add(nombreUser);
                            }
                        } else if (f1.getName().equals("Passwords.txt")) {
                            while (scanner.hasNextLine()) {
                                String contraUser = scanner.nextLine();
                                listPasswords.add(contraUser);
                            }
                        } else if (f1.getName().equals("Tables.txt")) {
                            while (scanner.hasNextLine()) {
                                String table = scanner.nextLine();
                                listTables.add(table);
                            }
                        } else {
                            while (scanner.hasNext()) {
                                String p = scanner.next();
                                List<Privilege> privs = new ArrayList<>();
                                while (!p.equals("$")) {
                                    privs.add(Privilege.valueOf(p));
                                    p = scanner.next();
                                }
                                listPrivileges.add(privs);
                            }
                        }
                    }

                    // Asignar usuarios y privilegios al perfil
                    if (listUsers.size() == listPasswords.size()) {
                        for (int x = 0; x < listUsers.size(); x++) {
                            User user = new User(listUsers.get(x), listPasswords.get(x));
                            profile.getUsers().add(user);
                        }
                    }

                    if (listTables.size() == listPrivileges.size()) {
                        for (int y = 0; y < listTables.size(); y++) {
                            profile.getPrivilegesOn().put(listTables.get(y), listPrivileges.get(y));
                        }
                    }

                    // Agregar el perfil al manager
                    manager.getProfiles().add(profile);
                }

                return manager;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(String databaseName) {
        String path = "databases" + File.separator + databaseName + File.separator + "Manager";

        File existFile = new File(path);

      /* if (existFile.exists()) deleteFolder(path);

        existFile.mkdirs();

        for (Profile p : profiles) {
            String pathProfile = path + File.separator + p.name;

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

            } catch (IOException exception) {
            }
        }*/
        if (!existFile.exists()){
            existFile.mkdirs();
        }
        for (Profile p : profiles) {
            String pathProfile = path + File.separator + p.getName();
            try {
                FileWriter usuariosFile = new FileWriter(pathProfile + File.separator + "Users.txt");
                FileWriter passwordsFile = new FileWriter(pathProfile + File.separator + "Passwords.txt");
                FileWriter tablesFile = new FileWriter(pathProfile + File.separator + "Tables.txt");
                FileWriter privilegesFile = new FileWriter(pathProfile + File.separator + "Privileges.txt");

                for (User user : p.getUsers()) {
                    usuariosFile.write(user.getUsername() + "\n");
                    passwordsFile.write(user.getEncryptedPassword() + "\n");
                }

                for (Map.Entry<String, List<Privilege>> entry : p.getPrivilegesOn().entrySet()) {
                    tablesFile.write(entry.getKey() + "\n");
                    List<Privilege> privileges = entry.getValue();
                    for (Privilege privilege : privileges) {
                        privilegesFile.write(privilege.name() + " ");
                    }
                    privilegesFile.write("$\n");

                }
                usuariosFile.close();
                passwordsFile.close();
                tablesFile.close();
                privilegesFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                        fileWriter.write((String) iterator.next() + "\n");
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

            if (! profile.privilegesOn.isEmpty()) {

                for (int i = 0; i < profile.privilegesOn.size(); i++) {

                    if (i < profile.privilegesOn.size() - 1) {
                        Set nombreTablas = profile.privilegesOn.keySet();

                        Iterator iterator = nombreTablas.iterator();

                        while (iterator.hasNext()) {

                            List<Privilege> privileges = profile.privilegesOn.get(iterator.next());

                            if (!privileges.isEmpty()) {

                                for (int j = 0; j < privileges.size(); j++) {

                                    if (j < privileges.size() - 1) fileWriter.write(privileges.get(j) + " ");

                                    else fileWriter.write(privileges.get(j) + " $\n");
                                }
                            }
                        }
                    } else {
                        Set nombreTablas = profile.privilegesOn.keySet();

                        Iterator iterator = nombreTablas.iterator();

                        while (iterator.hasNext()) {

                            List<Privilege> privileges = profile.privilegesOn.get(iterator.next());

                            if (! privileges.isEmpty()) {

                                for (int j = 0; j < privileges.size(); j++) {

                                    if (j < privileges.size() - 1) fileWriter.write(privileges.get(j) + " ");

                                    else fileWriter.write(privileges.get(j) + " $\n");
                                }
                            }
                        }
                    }
                }
            }
            fileWriter.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void deleteFolder(String path){
        File file = new File(path);
        File[] files;
        File[] subDirs;

        files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        });

        subDirs = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..");
            }
        });

        if(files != null){
            for(File f : files){
                //System.out.println("Deleting "+f.getAbsolutePath());
                f.delete();
            }
        }

        if(subDirs != null){
            for(File subdir : subDirs){
                deleteFolder(subdir.getAbsolutePath());
                subdir.delete();
            }
        }
    }
}