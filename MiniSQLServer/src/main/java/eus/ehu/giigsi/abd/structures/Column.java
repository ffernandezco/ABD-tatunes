package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.Condition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

        for (int index : indicesToUpdate) { //sugerencia del intellij
            values.set(index, value);
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
        try(PrintWriter writer = new PrintWriter(new FileWriter(directory))) {
            writer.println(type.toString());
            writer.println(name);

            for (String value : values) {
                writer.println(value);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Column Load(String file)
    {
        try(Scanner scanner = new Scanner(new File(file))){
            DataType type = DataType.valueOf(scanner.nextLine());
            String name = scanner.nextLine();
            List<String> values = new ArrayList<>();

            while (scanner.hasNextLine()) {
                values.add(scanner.nextLine());
            }
            return new Column(type, name, values);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
