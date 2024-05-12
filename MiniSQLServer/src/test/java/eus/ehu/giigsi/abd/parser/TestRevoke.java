package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Privilege;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.structures.Column;
import eus.ehu.giigsi.abd.structures.Database;
import eus.ehu.giigsi.abd.structures.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRevoke {
    private Database db;
    private Profile pf;
    private List<Privilege> priv,priv2;
    private String tb,tb2;

    @BeforeEach
    public void init(){
        db = new Database("admin", "admin");
        Manager sm = new Manager("manager");
        db.securityManager = sm;

        List<ColumnParameters> listaCP = new ArrayList<>();

        listaCP.add(new ColumnParameters("str", Column.DataType.STRING));
        listaCP.add(new ColumnParameters("dbl", Column.DataType.DOUBLE));
        listaCP.add(new ColumnParameters("int", Column.DataType.INT));
        db.createTable("empleado",listaCP);

        pf = new Profile();
        pf.setName(Profile.AdminProfileName);
        db.getSecurityManager().getProfiles().add(pf);

        priv = new ArrayList<>();
        priv.add(Privilege.INSERT);
        priv.add(Privilege.DELETE);

        priv2 = new ArrayList<>();
        priv2.add(Privilege.UPDATE);
        priv2.add(Privilege.INSERT);

        tb = "empleado";
        tb2 = "departamento";

        pf.getPrivilegesOn().put(tb,priv);
        pf.getPrivilegesOn().put(tb2,priv2);
        pf.getPrivilegesOn();


    }
    @Test
    public void prueba1(){
        Revoke rv = new Revoke("INSERT","empleado",Profile.AdminProfileName);
        String result = rv.execute(db);

        assertEquals(Constants.REVOKE_PRIVILEGE_SUCCESS,result);
    }
}
