package eus.ehu.giigsi.abd.security;

import eus.ehu.giigsi.abd.structures.Column;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestProfile {

    @Test
    public void testGrantPrivilege() {
        Profile profile = new Profile();
        profile.setName(Profile.AdminProfileName);
        String table = "table";
        Privilege privilege = Privilege.SELECT;

        boolean granted = profile.grantPrivilege(table, privilege);
        assertTrue(granted);
        List<Privilege> privileges = profile.privilegesOn.get(table);
        assertNotNull(privileges);
        assertEquals(1, privileges.size());
        assertEquals(privilege, privileges.get(0));
    }

    @Test
    public void testGrantPrivilegeNoAdmin() {
        Profile profile = new Profile();
        profile.setName("monterito");
        String table = "table";
        Privilege privilege = Privilege.SELECT;

        boolean granted = profile.grantPrivilege(table, privilege);
        assertFalse(granted);
        List<Privilege> privileges = profile.privilegesOn.get(table);
        assertNull(privileges);
    }
}
