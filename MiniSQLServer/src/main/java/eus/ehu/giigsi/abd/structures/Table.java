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
        File file = new File(path);

        int indNombre = path.lastIndexOf(File.separator);
        name = path.substring(indNombre + 1);

        File[] listFiles = file.listFiles();

        for (File f : listFiles) {
            Column column = Column.Load(f.getPath());

            if(column == null) {
                return false;

            } else columns.add(column);
        }

        return true;
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

    public Column columnByName(String column)
    {

        for(Column columna : columns) {
            if (columna.getName().equals(column)) {
                return columna;
            }
        }
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

        // Obtener nombres de columnas y colocarlos entre corchetes
        builder.append("[");
        for (int i = 0; i < columns.size(); i++) {
            builder.append("'").append(columns.get(i).getName()).append("'");
            // Gestionar varios valores, separados con coma
            if (i < columns.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");

        // Obtener resultados y colocar entre llaves
        for (int i = 0; i < columns.get(0).getValues().size(); i++) {
            builder.append("{");
            for (int j = 0; j < columns.size(); j++) {
                builder.append("'").append(columns.get(j).getValues().get(i)).append("'");
                // Gestionar comas
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

