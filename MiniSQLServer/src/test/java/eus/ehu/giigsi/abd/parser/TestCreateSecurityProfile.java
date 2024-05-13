package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.structures.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCreateSecurityProfile {
    private Database database;


     @BeforeEach
      public  void setUp(){
         this.database = new Database("admin", "admin");
     }

     /*
     @Test
    public void TestCreateSecurityProfile(){

         String profileName = "Name";
         CreateSecurityProfile createSecurityProfile = new CreateSecurityProfile(profileName);

         String resultado = createSecurityProfile.execute(database);
         assertEquals(Constants.CREATE_SECURITY_PROFILE_SUCCESS, resultado);


     }

      */

     @Test
     public void TestCreateSecurityProfileError(){
         String profileName = null;
         CreateSecurityProfile createSecurityProfile = new CreateSecurityProfile(profileName);

         String resultado = createSecurityProfile.execute(database);

         assertEquals(Constants.SECURITY_PROFILE_DOES_NOT_EXIST_ERROR, resultado);

     }






}






