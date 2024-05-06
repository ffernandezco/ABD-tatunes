package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class ColumnParameters {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Column.DataType type;

    public ColumnParameters(String name, Column.DataType type)
    {
        this.name = name;
        this.type = type;
    }

    public Column.DataType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
