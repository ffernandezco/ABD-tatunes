package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class DropSecurityProfile implements MiniSQLQuery{
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String profileName;

    public DropSecurityProfile(String profileName)
    {
        this.profileName = profileName;
    }
    public String execute(Database database)
    {
        return null;
    }
}
