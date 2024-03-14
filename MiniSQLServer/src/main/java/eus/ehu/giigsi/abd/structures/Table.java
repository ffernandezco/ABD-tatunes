package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.Condition;
import eus.ehu.giigsi.abd.parser.SetValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Table {
    //TODO
    public List<Column> columns;
    public String name = null;

    public Table(String name, List<Column> columns)
    {
        this.name = name;
        this.columns = columns;
    }

    public boolean load(String path)
    {
        try ( Scanner entrada = new Scanner(new File(path))){
            while (entrada.hasNext()){

            }
        } catch ( FileNotFoundException e ) {
            System.err.println("Fichero no encontrado");
        }
        return false;
    }

    public boolean save(String databaseName)
    {
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
    public String toString()
    {
        String salida = "Tabla " + name + "\n" + "Contenido: \n";
        for (Column column : columns) {
            salida = salida + "- " + column.toString() + "\n";
        }
        return salida;
    }

    public void deleteWhere(Condition condition)
    {

        //Depende de Column
    }

    public boolean insert(List<String> values)
    {

        return false;
    }

    public String update(List<SetValue> setValues, Condition condition)
    {
        String errorMessage = null;
        return errorMessage;
    }
}
