package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeleteUser {
    private Database db;
    private DeleteUser du,du2;

    @BeforeEach
    public void init() {
        db = new Database("user", "user");
        Manager mg = new Manager("manager");
        db.securityManager = mg;
        du = new DeleteUser("user1");
        du2 = new DeleteUser("user10");
        Profile adminProfile = new Profile();
        adminProfile.setName("adminProfileName");
        Profile pf2 = new Profile();
        pf2.setName("pf2");
        User us = new User("user1","user1Pass");
        User us2 = new User("user2", "user2pass");
        adminProfile.getUsers().add(us);
        adminProfile.getUsers().add(us2);
        db.getSecurityManager().getProfiles().add(adminProfile);
        db.getSecurityManager().getProfiles().add(pf2);
    }

    /*
    @Test
    public void prueba1(){
        String result = du.execute(db);
        assertEquals(Constants.DELETE_USER_SUCCESS,result);
    }
    @Test
    public void prueba2(){
        String result = du2.execute(db);
        assertEquals(Constants.USER_DOES_NOT_EXIST_ERROR,result);
    }
    */
}
