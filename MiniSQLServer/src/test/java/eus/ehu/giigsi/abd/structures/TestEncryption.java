package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.security.Encryption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestEncryption {

    @Test
    public void testEncrypt() {
        String text = "FRan";
        String encriptado = "IUdq";
        String encryptedMessage = Encryption.encrypt(text);
        assertNotNull(encryptedMessage);
        assertEquals(encryptedMessage, encriptado);

    }

}
