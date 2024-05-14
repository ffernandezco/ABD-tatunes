package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.security.Encryption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestEncryption {

    @Test
    public void testEncrypt() {

        //Contraseñas encriptadas a partir de https://www.md5hashgenerator.com

        String pass = Encryption.encrypt("fran");
        assertEquals("2c20cb5558626540a1704b1fe524ea9a", pass);

        String passconletrasynumeros = Encryption.encrypt("P4ssw0rd");
        assertEquals("8efe310f9ab3efeae8d410a8e0166eb2", passconletrasynumeros);
    }

}
