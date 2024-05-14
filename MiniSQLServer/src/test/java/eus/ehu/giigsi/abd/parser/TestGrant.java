package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.security.Privilege;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGrant {
    Database database;
    Database cargadaDatabase;
    User user;

    @BeforeEach
    public void init () {
        database = new Database("admin", "admin");
        List<Column> listaCP = new ArrayList<>();
        listaCP.add(new Column(Column.DataType.INT,"id"));
        Table table = new Table("table", listaCP);

        database.addTable(table);

        Profile profile = new Profile();
        profile.setName("Profile");

        user = new User("Fran", "rubiasaltas");

        profile.users.add(new User("Asier", "morenasbajitas"));
        profile.users.add(user);

        database.getSecurityManager().addProfile(profile);

        database.save("testeo");
    }


    @Test
    public void testGrant(){
        cargadaDatabase = Database.load("testeo", "admin", "admin");
        Grant grant = new Grant("DELETE", "table", "Profile");
        grant.execute(cargadaDatabase);
        Profile profile = cargadaDatabase.getSecurityManager().profileByName("Profile");

        assertTrue(profile.IsGrantedPrivilege("table", Privilege.DELETE));
        assertFalse(profile.IsGrantedPrivilege("table", Privilege.SELECT));
        assertFalse(profile.IsGrantedPrivilege("table", Privilege.INSERT));

    }


}
