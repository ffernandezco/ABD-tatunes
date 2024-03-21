package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.parser.*;
import eus.ehu.giigsi.abd.security.Manager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

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

    public Database(String pName,String adminUsername, String adminPassword)
    {
        name = pName;
        mUsername = adminUsername;
        mPassword = adminPassword;
    }

    public static FileReader load(String databaseName, String username, String password) {
        File file = new File("/archives/" + databaseName + ".txt");
        try (FileReader fr = new FileReader(file)) {
            System.out.println("Database loaded successfully");
            return fr;
        } catch (IOException e) {
            System.out.println(Constants.DATABASE_DOES_NOT_EXIST_ERROR);
            return null;
        }


    }

    public boolean save(String databaseName)
    {
        return false;
    }
    public Table select(String databaseName,String table, List<String> columns, Condition columnCondition) throws IOException {
        String user = this.mUsername;
        String password = this.mPassword;
        FileReader fr = load(databaseName, user, password);
        return null;
    }

    public boolean deleteWhere(String tableName, Condition columnCondition) {
        return false;
    }

    public boolean update(String tableName, List<SetValue> columnNames, Condition columnCondition)
    {
        return false;
    }

    public boolean Insert(String databaseName,String tableName, List<String> values) throws IOException
    {
        String user = null;
        String password = null;
        FileReader fr = load(databaseName, user, password);
        int numLine = findTable(fr, tableName);
        if(numLine != -1){
            BufferedReader reader = new BufferedReader(fr);
            String line;
            int currentLine=0;
            while((line = reader.readLine()) != null && currentLine < numLine - 1) {
                currentLine++;
            }
            int i=0;
            while(i< values.size()){
                FileWriter fw = new FileWriter("/archives/"+databaseName+".txt");
                BufferedWriter writer = new BufferedWriter(fw);
                writer.write(values.get(i) + "\n");
                i++;
                currentLine++;
            }
        }
        return false;
    }

    public String executeMiniSQLQuery(String query) throws IOException {
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
        Table table = tableByName(tableName);

        return false;
    }
    public void addTable(Table table)
    {
        tables.add(table);
    }
    public boolean createTable(String database,String tableName, List<ColumnParameters> columnParameters) throws IOException {

        List<Column> columns = new ArrayList<>();

        for(ColumnParameters c : columnParameters) {
            Column.DataType type = c.getType();
            String name = c.getName();

            Column column = new Column(type, name);

            columns.add(column);
        }

        Table table = new Table(tableName, columns);

        addTable(table);


        try{
            FileReader fr = load(database,mUsername,mPassword);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while((line = reader.readLine())!=null){
                if(line.contains(tableName)){
                 System.out.println(Constants.TABLE_ALREADY_EXISTS_ERROR);
                 return false;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("/archives/"+database+".txt",true));
            writer.write(tableName+"\n");
            for(int i=0; i<columnParameters.size();i++){
                writer.write(columnParameters.get(i).name+" ");
                writer.write(columnParameters.get(i).type.name());

                if(i+1 < columnParameters.size()) {
                    writer.write(", ");
                }
            }
            System.out.println(Constants.CREATE_TABLE_SUCCESS);
            return true;
        }catch (IOException e){
            System.out.println(Constants.ERROR);
            return false;
        }
    }

    public boolean IsUserAdmin() throws IOException {
        FileReader fr = new FileReader("/archives/admin.txt");
        BufferedReader reader = new BufferedReader(fr);
        String line;
        while((line = reader.readLine())!=null){
            if(line.contains(mUsername)){
                return true;
            }
        }
        return false;
    }
    public int findTable(FileReader fr, String tableName) throws IOException{
            BufferedReader reader = new BufferedReader(fr);
            String line;
            int lineNum=0;
            while((line = reader.readLine()) != null){
                lineNum++;
                if(line.contains(tableName)){
                    reader.close();
                    return lineNum;
                }
            }
                reader.close();
                return -1;
        }
}
