package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;

public class DataTypeUtils {
    public static Column.DataType fromMiniSQLName(String typeName)
    {
        try{
            return Column.DataType.valueOf(typeName);
        }
        catch (IllegalArgumentException ex){
            throw new RuntimeException(String.format("Unknown MiniSQL data type: %s",typeName));
        }
    }

    public static Column.DataType fromMiniTypeName(String typeName)
    {
        if (typeName.equals("Int"))
            return Column.DataType.INT;
        else if (typeName.equals("Double"))
            return Column.DataType.DOUBLE;
        else if (typeName.equals("String"))
            return Column.DataType.STRING;
        throw new RuntimeException(String.format("Unknown MiniSQL data type: %s",typeName));
    }
}
