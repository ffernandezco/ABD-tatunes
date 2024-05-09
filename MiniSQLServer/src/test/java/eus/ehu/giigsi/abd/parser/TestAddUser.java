package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddUser {
    private Database db;
    private AddUser au,au2,au3,au4;

    @BeforeEach
    public void init(){
        db = new Database("user", "user");
        Manager mg = new Manager("manager");
        db.securityManager = mg;
        au = new AddUser("testUser", "testPass", "Admin");
        au2= new AddUser("testuser","testPass","AdminNo");
    }
    @Test
    public void prueba1(){
        Profile adminProfile = new Profile();
        adminProfile.setName(Profile.AdminProfileName);
        db.getSecurityManager().getProfiles().add(adminProfile);

        String result = au.execute(db);

        assertEquals(Constants.ADD_USER_SUCCESS, result);
        assertEquals(1, adminProfile.getUsers().size());
        assertEquals("testUser", adminProfile.getUsers().get(0).getUsername());
    }
    @Test
    public void prueba2(){
        String result = au2.execute(db);
        assertEquals(null, result);
    }
}
