package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.security.Encryption;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAddUser {
    Database database;
    Database cargadaDatabase;

    @BeforeEach
    public void init () {
        database = new Database("admin", "admin");

        Profile profile = new Profile();
        profile.setName("Profile");

        profile.users.add(new User("Asier", "morenasbajitas"));
        profile.users.add(new User("Fran", "rubiasaltas"));

        database.getSecurityManager().addProfile(profile);

        database.save("testeo");
    }


    @Test
    public void testAddUser(){
        cargadaDatabase = Database.load("testeo", "admin", "admin");
        AddUser add = new AddUser("Antonio", "fr4n", "Profile");
        add.execute(cargadaDatabase);
        Profile profile = cargadaDatabase.getSecurityManager().profileByName("Profile");
        User user = profile.getUsers().get(profile.getUsers().size() - 1);

        assertNotNull(user);
        assertTrue(profile.getUsers().contains(user));
        assertEquals("Antonio", user.getUsername());
        assertEquals(Encryption.encrypt("fr4n"), user.getEncryptedPassword());
    }


}
