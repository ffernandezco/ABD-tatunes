package eus.ehu.giigsi.abd.security;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public static String encrypt(String value)
    {
        String encryptedPassword = null;

        //Encripta contraseñas a partir de java.security en MD5
        //Basado en el código de https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/

        try{

            //Configurar MessageDigest y separar en bytes contraseña
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            byte[] bytes = md.digest();

            //Crear String con la contraseña encriptada para devolver
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<bytes.length;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            encryptedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }
}