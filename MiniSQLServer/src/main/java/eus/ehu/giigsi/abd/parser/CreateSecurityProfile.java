package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Database;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class CreateSecurityProfile implements MiniSQLQuery {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String profileName;

    public CreateSecurityProfile(String profileName)
    {
        this.profileName = profileName;
    }
    public String execute(Database database)
    {
        return null;
    }

}
