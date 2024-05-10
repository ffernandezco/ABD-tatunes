package eus.ehu.giigsi.abd.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestManager {

    public Manager manager;
    public String username;



    /*
    @Test
    public void testIsUserAdmin() {
        Manager manager = new Manager("usuario");
        Profile profile = new Profile();
        profile.setName("Usuario");
        manager.getProfiles().add(profile);
        assertFalse(manager.isUserAdmin());
    }

     */


    @Test
    public void testAddProfile() {
        Manager manager = new Manager("Admin");
        Profile profile = new Profile();
        profile.setName("fran");
        manager.addProfile(profile);
        assertTrue(manager.profiles.contains(profile));
    }


}
