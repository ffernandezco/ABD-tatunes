package eus.ehu.giigsi.abd.parser;

import lombok.Getter;

public class SetValue {
    @Getter
    public String column = null;
    @Getter
    public String value = null;


    public SetValue(String column, String value)
    {
        this.column = column;
        this.value = value;
    }
}
