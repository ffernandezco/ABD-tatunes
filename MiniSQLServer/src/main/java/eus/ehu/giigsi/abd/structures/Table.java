package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.parser.Condition;
import eus.ehu.giigsi.abd.parser.SetValue;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Table {
    public List<Column> columns;
    @Getter
    public String name = null;

    public Table(String name, List<Column> columns)
    {
        this.name = name;
        this.columns = columns;
    }

    public Table() {

    }

    public boolean load(String path)
    {
        File f = new File(path);

        return false;
    }

    public boolean save(String databaseName)
    {
        String path = "databases" + File.separator + databaseName + File.separator + this.name;

        File f = new File(path);
        boolean b = f.mkdirs();

        if (b) {
            for (Column c : columns) {
                c.Save(path);
            }

            return true;
        }

        return false;
    }

    public Column columnByName(String columnName) {
        int i = 0;

        // Buscamos en cada vector del array tables y si coinciden se devuelve el item
        while (i < columns.size()) {
            if (columns.get(i).name.equals(columnName)) {
                return columns.get(i);
            } else {
                i++;
            }
        }
        // En caso de no existir se devuelve null
        return null;
    }

    public void deleteColumnByName(String name)
    {
        for(Column columna : columns){
            if(columna.getName().equals(name)){
                columns.remove(columna);
            }

            else{
                System.out.println("No hay ninguna columna con el nombre introducido.");
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Colocar columnas entre corchetes
        builder.append("[");
        for (int i = 0; i < columns.size(); i++) {
            builder.append("'").append(columns.get(i).getName()).append("'");
            // Gestión de múltiples columnas
            if (i < columns.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");

        // Colocar respuestas en llaves
        for (int i = 0; i < columns.get(0).getValues().size(); i++) {
            builder.append("{");
            for (int j = 0; j < columns.size(); j++) {
                builder.append("'").append(columns.get(j).getValues().get(i)).append("'");
                // Gestión de múltiples resultados
                if (j < columns.size() - 1) {
                    builder.append(",");
                }
            }
            builder.append("}");
        }
        return builder.toString();
    }

    public void deleteWhere(Condition condition) {
        List<Integer> indices = columnByName(condition.column).indicesWhereIsTrue(condition);

        for (Column columna : columns) {

            for (int i = indices.size()-1; i>=0; i--) {

                int index = indices.get(i);
                columna.deleteAt(index);
            }
        }
    }

    public boolean insert(List<String> values)
    {
        // En caso de que no haya suficientes datos para cada columna devuelve error
        if (values.size() != columns.size()) {
            return false;
        }

        // Introduce los datos en las columnas en función del orden en el que estén
        for (int i = 0; i < columns.size(); i++) {
            Column columna = columns.get(i);
            String value = values.get(i);

            columna.values.add(value);
        }

        return true;
    }

    public String update(List<SetValue> setValues, Condition condition)
    {
        String errorMessage = null;
        for(SetValue value : setValues) {
            for (Column columna : columns) {
                if (columna.name.equals(value.getColumn())) {
                    columna.updateWhere(condition, value.getValue());
                }
                else {
                    errorMessage = "Columna no encontrada.";
                }
            }
        }
        return errorMessage;
    }
}

