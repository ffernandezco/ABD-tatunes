package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.security.Privilege;

public class PrivilegeUtils {
    public static Privilege fromPrivilegeName(String name)
    {
        switch(name)
        {
            case "DELETE": return Privilege.DELETE;
            case "INSERT": return Privilege.INSERT;
            case "UPDATE": return Privilege.UPDATE;
            case "SELECT": return Privilege.SELECT;
            default:
                throw new RuntimeException("Unknown Privilege name in PrivilegeUtils.FromPrivilegeName()");
        }
    }
}
