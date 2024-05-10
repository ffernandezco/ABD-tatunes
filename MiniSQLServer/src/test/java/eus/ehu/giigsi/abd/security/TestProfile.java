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
    public void testRevokePrivilege() {
        Profile profile = new Profile();
        profile.setName(Profile.AdminProfileName);
        String table = "table";
        Privilege privilege = Privilege.DELETE;

        //Otorgar privilegio
        profile.grantPrivilege(table, privilege);
        assertTrue(profile.privilegesOn.containsKey(table));
        List<Privilege> privileges = profile.privilegesOn.get(table);
        assertNotNull(privileges);
        assertTrue(privileges.contains(privilege));

        // Revocar privilegio
        boolean revoked = profile.revokePrivilege(table, privilege);
        assertTrue(revoked);
        assertFalse(profile.privilegesOn.containsKey(table));
    }
    @Test
    public void testRevokePrivilegeNotAdmin() {
        Profile profile = new Profile();
        profile.setName("fran");
        String table = "table";

        // Revocar privilegio
        boolean revoked = profile.revokePrivilege(table, Privilege.UPDATE);
        assertFalse(revoked);
    }

    @Test
    public void isGrantedAdmin() {
        Profile profile = new Profile();
        profile.setName("Admin");
        String table = "Table";
        assertTrue(profile.IsGrantedPrivilege(table, Privilege.DELETE));
        assertTrue(profile.IsGrantedPrivilege(table, Privilege.SELECT));
        assertTrue(profile.IsGrantedPrivilege(table, Privilege.INSERT));
        assertTrue(profile.IsGrantedPrivilege(table, Privilege.UPDATE));
    }

    @Test
    public void testIsGrantedPrivilege() {
        Profile profile = new Profile();
        profile.setName("monterolachupa");
        String table = "Table";
        profile.grantPrivilege(table, Privilege.UPDATE);
        assertTrue(profile.IsGrantedPrivilege(table, Privilege.UPDATE));
    }


}
