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
        switch (type) {
            case INT:
                int intValue = Integer.parseInt(value);
                int literalIntValue = Integer.parseInt(literalValue);
                if (operator.equals("=")) {
                    return intValue == literalIntValue;
                } else if (operator.equals("<")) {
                    return intValue < literalIntValue;
                } else if (operator.equals(">")) {
                    return intValue > literalIntValue;
                }
            case DOUBLE:
                double dValue = Double.parseDouble(value);
                double literalDValue = Double.parseDouble(literalValue);
                if (operator.equals("=")) {
                    return dValue == literalDValue;
                } else if (operator.equals("<")) {
                    return dValue < literalDValue;
                } else if (operator.equals(">")) {
                    return dValue > literalDValue;
                }
            case STRING:
                if (operator.equals("=")) {
                    return value.equals(literalValue);
                } else if (operator.equals("<")) {
                    return value.compareTo(literalValue) < 0;
                } else if (operator.equals(">")) {
                    return value.compareTo(literalValue) > 0;
                }
        }
        return false;
    }

    public String getColumn() {
        return column;
    }
}