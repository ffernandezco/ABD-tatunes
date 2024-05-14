package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestDeleteUser {
    Database database;
    Database cargadaDatabase;
    User user;

    @BeforeEach
    public void init () {
        database = new Database("admin", "admin");

        Profile profile = new Profile();
        profile.setName("Profile");

        user = new User("Fran", "rubiasaltas");

        profile.users.add(new User("Asier", "morenasbajitas"));
        profile.users.add(user);

        database.getSecurityManager().addProfile(profile);

        database.save("testeo");
    }


    @Test
    public void testDeleteUser(){
        cargadaDatabase = Database.load("testeo", "admin", "admin");
        DeleteUser delete = new DeleteUser("Fran");
        delete.execute(cargadaDatabase);
        Profile profile = cargadaDatabase.getSecurityManager().profileByName("Profile");

        assertFalse(profile.getUsers().contains(user));
    }


}
