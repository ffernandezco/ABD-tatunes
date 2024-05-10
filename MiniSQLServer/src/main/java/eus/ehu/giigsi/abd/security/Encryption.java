package eus.ehu.giigsi.abd.security;


public class Encryption {

    public static String encrypt(String value) {

        // Basado en el método Caesar Cipher, altera la secuencia de letras
        // https://www.geeksforgeeks.org/videos/caesar-cipher/

        // No es demasiado seguro, convendría utilizar algún algoritmo tipo MD5
        // No tiene (aún) en cuenta números ni símbolos, mejor usar solo letras

        StringBuilder sb = new StringBuilder();
        char letra;
        int letras;
        int alter = 3;

        // Separamos contraseña en todos sus caracteres
        for (int i = 0; i < value.length(); i++) {
            letra = value.charAt(i);
            if (Character.isLetter(letra)) {
                // Sumamos alteración o Shift
                letras = letra + alter;

                // Modificamos letras. Hay que tener en cuenta mayúsculas y minúsculas
                if (Character.isUpperCase(letra) && letras > 'Z') {
                    letras = letras - 'Z' + 'A' - 1;
                } else if (Character.isLowerCase(letra) && letras > 'z') {
                    letras = letras - 'z' + 'a' - 1;
                }

                // Agrupamos todos los caracteres en la nueva contraseña encriptada
                sb.append((char) letras);
            } else {
                sb.append(letra);
            }
        }

        return sb.toString(); //eliminamos objeto StringBuilder y convertimos en String

    }
}
