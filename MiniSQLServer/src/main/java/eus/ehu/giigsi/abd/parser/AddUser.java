package eus.ehu.giigsi.abd.parser;


import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class AddUser implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String username;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String password;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String profileName;


    public AddUser(String username, String password, String profileName)
    {
        this.username = username;
        this.password = password;
        this.profileName= profileName;
    }
    public String execute(Database database)
    {
        return null;
    }

}
