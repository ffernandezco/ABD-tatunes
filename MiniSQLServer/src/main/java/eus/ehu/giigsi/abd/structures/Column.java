package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.Condition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Column {
    public enum DataType{STRING, INT, DOUBLE}

    public DataType type;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String name;

    @Getter
    @Setter
    public List<String> values;

    public Column(DataType type, String name, List<String> values){
        this.type = type;
        this.name = name;
        this.values = values;
    }

    public Column(DataType type, String name){
        this.type = type;
        this.name = name;
        this.values = new ArrayList<>();
    }

    public List<Integer> indicesWhereIsTrue(Condition condition) {
        List<Integer> indices = new ArrayList<>();

        //TODO

        return indices;
    }

    public void updateWhere(Condition condition, String value)
    {

    }

    public void SetValue(int pos,String value)
    {

    }

    public void deleteAt(int pos)
    {

    }

    public boolean Save(String directory)
    {
        return false;
    }

    public static Column Load(String file)
    {
        return null;
    }

}
