package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class DeleteUser implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String username;

    public DeleteUser(String username)
    {
        this.username = username;
    }
    public String execute(Database database)
    {
        return null;
    }
}
