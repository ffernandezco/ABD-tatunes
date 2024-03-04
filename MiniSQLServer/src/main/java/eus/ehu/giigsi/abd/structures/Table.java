package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.parser.Condition;
import eus.ehu.giigsi.abd.parser.SetValue;

import java.util.ArrayList;
import java.util.List;

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
        return false;
    }

    public boolean save(String databaseName)
    {
        return false;
    }

    public Column columnByName(String column)
    {
        return null;
    }
    public void deleteColumnByName(String name)
    {

    }


    @Override
    public String toString()
    {

        return null;
    }

    public void deleteWhere(Condition condition)
    {

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
