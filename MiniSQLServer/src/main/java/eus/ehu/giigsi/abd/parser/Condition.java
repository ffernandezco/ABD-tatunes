package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Condition {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String column;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String operator;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String literalValue;

    public Condition(String column, String op, String literalValue)
    {
        this.column = column;
        this.operator = op;
        this.literalValue = literalValue;
    }

    public boolean ValueMeetsCondition(String value, Column.DataType type)
    {
        return false;
    }
}
