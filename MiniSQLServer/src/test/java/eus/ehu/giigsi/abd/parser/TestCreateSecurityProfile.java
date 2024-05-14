package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCreateSecurityProfile {
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
    public void TestCreateSecurityProfile(){
        cargadaDatabase = Database.load("testeo", "admin", "admin");

         String profileName = "Name";
         CreateSecurityProfile createSecurityProfile = new CreateSecurityProfile(profileName);

         String resultado = createSecurityProfile.execute(cargadaDatabase);
         assertEquals(Constants.CREATE_SECURITY_PROFILE_SUCCESS, resultado);

         assertNotNull(cargadaDatabase.securityManager.profileByName("Name"));

         assertEquals("Name", cargadaDatabase.securityManager.profiles.get(cargadaDatabase.securityManager.profiles.size()-1).name);

    }

     @Test
     public void TestCreateSecurityProfileError(){
         cargadaDatabase = Database.load("testeo", "Fran", "rubiasaltas");

         String profileName = "Name";
         CreateSecurityProfile createSecurityProfile = new CreateSecurityProfile(profileName);

         String resultado = createSecurityProfile.execute(cargadaDatabase);
         assertEquals(Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE, resultado);

         assertNull(cargadaDatabase.securityManager.profileByName("Name"));

         assertEquals("Profile", cargadaDatabase.securityManager.profiles.get(cargadaDatabase.securityManager.profiles.size()-1).name);

     }
}






