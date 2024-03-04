package eus.ehu.giigsi.abd.security;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter
    @Setter
    public String username;

    @Getter
    @Setter
    public String encryptedPassword;

    public User(String username, String password)
    {
        this.username = username;

        //Encrypt the password
        this.encryptedPassword = Encryption.encrypt(password);
    }

    public User() { }
}
