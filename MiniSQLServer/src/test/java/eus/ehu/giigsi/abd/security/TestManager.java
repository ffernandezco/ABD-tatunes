package eus.ehu.giigsi.abd.security;

import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestManager {

    @Test
    public void testIsPasswordCorrect() {
        Manager manager = new Manager("admin");
        User user1 = new User("fran", "passfranzwq123");
        String encryptedPassword = Encryption.encrypt(user1.getEncryptedPassword());

        Profile userProfile = new Profile();
        userProfile.setName("Fran");
        userProfile.users = new ArrayList(List.of((Object) user1));
        manager.getProfiles().add(userProfile);

        assertTrue(manager.isPasswordCorrect(user1.getUsername(), "passfranzwq123"));
    }

    @Test
    public void testIsPasswordCorrect2() {
        Manager manager = new Manager("admin");
        User user1 = new User("fran", "passfran123");
        String encryptedPassword = Encryption.encrypt(user1.getEncryptedPassword());

        Profile userProfile = new Profile();
        userProfile.setName("Fran");
        userProfile.users = new ArrayList(List.of((Object) user1));
        manager.getProfiles().add(userProfile);

        assertFalse(manager.isPasswordCorrect(user1.getUsername(), "passasier456"));
    }

    @Test
    public void testIsGrantedPrivilege() {
        Manager manager = new Manager("admin");
        User user1 = new User("asier", "mont123");
        Profile userProfile = new Profile();
        userProfile.setName("Deleter");
        userProfile.users.add(user1);
        manager.getProfiles().add(userProfile);
        String table1 = "Contenidos";
        userProfile.grantPrivilege(table1, Privilege.DELETE);

        assertTrue(manager.isGrantedPrivilege(user1.getUsername(), table1, Privilege.DELETE));
    }

    @Test
    public void testUserByName() {
        Manager manager = new Manager("admin");
        User user = new User("francisco", "pas123");
        Profile userProfile = new Profile();
        userProfile.setName("Deleter");
        userProfile.users.add(user);
        manager.getProfiles().add(userProfile);
        User u = manager.userByName(user.getUsername());
        User notfound = manager.userByName("faked");

        assertEquals(user, u);
        assertNull(notfound);
    }

    @Test
    public void testProfileByName() {
        Manager manager = new Manager("admin");
        Profile userProfile = new Profile();
        userProfile.setName("miPerfil");
        manager.getProfiles().add(userProfile);
        Profile p = manager.profileByName(userProfile.getName());
        Profile notfound = manager.profileByName("faked");

        assertEquals(userProfile, p);
        assertNull(notfound);
    }

    @Test
    public void testProfileByUser() {
        Manager manager = new Manager("admin");
        User user = new User("francisco", "fern4nd3z");
        Profile userProfile = new Profile();
        userProfile.setName("Fran");
        userProfile.users.add(user);
        manager.getProfiles().add(userProfile);
        Profile p = manager.profileByUser(user.getUsername());

        assertEquals(userProfile, p);
    }

    @Test
    public void testGrant(){
        Manager manager = new Manager("admin");
        User user = new User("francisco", "fern4nd3z");
        Profile profile = new Profile();
        profile.setName("Admin");
        profile.users.add(user);
        manager.getProfiles().add(profile);
        manager.grantPrivilege("francisco", "Table", Privilege.DELETE);
        Assertions.assertTrue(manager.isGrantedPrivilege("francisco", "Table", Privilege.DELETE));
    }

}