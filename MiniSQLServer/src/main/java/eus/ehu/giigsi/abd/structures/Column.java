package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.Condition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        for(int i = 0; i < values.size(); i++) {
            if (condition.ValueMeetsCondition(values.get(i), type)) {
                indices.add(i);
            }
        }
        return indices;
    }

    public void updateWhere(Condition condition, String value)
    {
        List<Integer> indicesToUpdate = indicesWhereIsTrue(condition);

        for (int index : indicesToUpdate) {
            SetValue(index, value);
        }
    }

    public void SetValue(int pos,String value)
    {
        if (pos >= 0 && pos < values.size()) {
            values.set(pos, value);
        }
    }

    public void deleteAt(int pos)
    {
        if (pos >= 0 && pos < values.size()) {
            values.remove(pos);
        }
    }

    public boolean Save(String directory)
    {
        File file = new File(directory + File.separator + name + ".txt");

        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(name + "\n");
            fileWriter.write(type.name() + "\n");

            for (int i = 0; i < values.size(); i++) {

                if (i < values.size() - 1) fileWriter.write(values.get(i) + "\n");
                else  fileWriter.write(values.get(i));
            }
            fileWriter.close();
            return true;

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
    }

    public static Column Load(String file)
    {

        return null;
    }
}
