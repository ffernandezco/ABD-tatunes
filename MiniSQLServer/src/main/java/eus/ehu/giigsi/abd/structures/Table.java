package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.parser.Condition;
import eus.ehu.giigsi.abd.parser.SetValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Table {
    public List<Column> columns;
    public String name = null;

    public Table(String name, List<Column> columns)
    {
        this.name = name;
        this.columns = columns;
    }

    public boolean load(String path)
    {
        File f = new File(path);

        return false;
    }

    public boolean save(String databaseName)
    {
        String path = "databases" + File.separator + databaseName + File.separator + this.name;

        try {
            File f = new File(path);
            return f.mkdirs();

        } catch (Exception e) {
            System.out.println(Constants.ERROR + e.getMessage());
            return false;
        }
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
    public String toString()
    {
        String salida = "Tabla " + name + "\n" + "Contenido: \n";
        for (Column columna : columns) {
            salida = salida + "\t Columna: " + columna.getName() + "\n";
            for (String valor : columna.getValues()) {
                salida = salida + "\t \t" + valor + "\n";
            }
        }
        return salida;
    }

    public void deleteWhere(Condition condition) {
        for (Column columna : columns) {
            if (columna.name.equals(condition.column)) {
                List<Integer> indices = columna.indicesWhereIsTrue(condition);
                for (int i = indices.size()-1; i>=0; i--) {
                    int index = indices.get(i);
                    columna.deleteAt(index);
                }
            }
        }
    }


    public boolean insert(List<String> values)
    {
        if (values.size() > columns.size()) {
            return false;
        }
        for (int i = 0; i < columns.size(); i++) {
            Column columna = columns.get(i);
            String value = values.get(i);
           // columna.values.add(value);

            try {
                FileWriter fileWriter = new FileWriter(this.name + ".txt");
                String texto = value;
                fileWriter.write(texto);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

