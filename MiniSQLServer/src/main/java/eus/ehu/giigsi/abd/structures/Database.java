package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.parser.*;
import eus.ehu.giigsi.abd.security.Manager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DEFAULT_RESULT_TABLE_NAME = "Result";

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public List<Table> tables = new ArrayList<>();
    public String name = null;
    private String mUsername;
    private String mPassword;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String lastErrorMessage;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public Manager securityManager;

    //This constructor is only used from Load (without needing to set a password for the user). It cannot be used from any other class
    private Database()
    {

    }

    public Database(String adminUsername, String adminPassword)
    {
        mUsername = adminUsername;
        mPassword = adminPassword;
    }


    public static Database load(String databaseName, String username, String password)
    {
        return null;
    }



    public boolean save(String databaseName)
    {
        return false;
    }
    public Table select(String table, List<String> columns, Condition columnCondition)
    {
        return null;
    }

    public boolean deleteWhere(String tableName, Condition columnCondition)
    {
        Table table = findTable(tableName);

        if (table == null){
            System.out.println(Constants.TABLE_DOES_NOT_EXIST_ERROR);
            return false;
        }
        table.deleteWhere(columnCondition);
        return true;
    }
    public void findeTable(Table pTable){
        return null;
    }

    public boolean update(String tableName, List<SetValue> columnNames, Condition columnCondition)
    {
        return false;
    }

    public boolean Insert(String tableName, List<String> values)
    {
        return false;
    }

    public String executeMiniSQLQuery(String query)
    {
        //Parse the query
        MiniSQLQuery miniSQLQuery = MiniSQLParser.parse(query);

        if (miniSQLQuery == null)
            return Constants.SYNTAX_ERROR;

        return miniSQLQuery.execute(this);
    }

    public Table tableByName(String tableName)
    {
        return null;
    }
    public boolean dropTable(String tableName)
    {

        return false;
    }
    public void addTable(Table table)
    {

    }

    public boolean createTable(String tableName, List<ColumnParameters> columnParameters)
    {
        return false;
    }

    public boolean IsUserAdmin()
    {
        return true;
    }
}
