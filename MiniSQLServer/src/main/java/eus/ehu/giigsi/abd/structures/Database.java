package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.parser.*;
import eus.ehu.giigsi.abd.security.Manager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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

    public Database(String adminUsername, String adminPassword)
    {
        mUsername = adminUsername;
        mPassword = adminPassword;
    }

    public static Database load(String databaseName, String username, String password) {
        Database db = new Database(username,password);
        File file = new File("/archives/"+databaseName);
        if (file.exists() && file.isDirectory()) {
            System.out.println("Database loaded successfully");
            return db;
        } else {
            System.out.println(Constants.DATABASE_DOES_NOT_EXIST_ERROR);
            return null;
        }
    }

    public boolean save(String databaseName)
    {
        return false;
    }
    public Table select(String table, List<String> columns, Condition columnCondition) {
        List<Integer> index = new ArrayList<>();
        List<Column> colList = new ArrayList<>();
        Column.DataType type = null;
        String file1 = "/eus/ehu/giigsi/abd/archives/"+this.name+"/"+table+"/"+columnCondition.column+".txt";
        try(FileReader fr = new FileReader(file1)) {
            BufferedReader reader = new BufferedReader(fr);
            String line;
            if (columnCondition.operator.equalsIgnoreCase("=")) {
                int i = 1;
                while ((line = reader.readLine()) != null) {//Recojo las líneas en las que coincide el dato
                    if (line.equalsIgnoreCase(columnCondition.literalValue)) {
                        index.add(i);
                    }
                    i++;
                }
            } else if (columnCondition.operator.equalsIgnoreCase("<")) {
                reader.readLine();
                int i=2;
                while ((line = reader.readLine()) != null) {//Recojo la línea en la que esta el dato
                    int val = Integer.parseInt(line);
                    int litVal = Integer.parseInt(columnCondition.literalValue);
                    if (val < litVal) {
                        index.add(i);
                    }
                    i++;
                }
            } else if (columnCondition.operator.equalsIgnoreCase(">")) {
                int i=2;
                reader.readLine();
                while ((line = reader.readLine()) != null) {//Recojo la línea en la que esta el dato
                    int val = Integer.parseInt(line);
                    int litVal = Integer.parseInt(columnCondition.literalValue);
                    if (val > litVal) {
                        index.add(i);
                    }
                    i++;
                }
            }
        }
        catch (IOException e){
            System.out.println(Constants.TABLE_DOES_NOT_EXIST_ERROR);
            return null;
        }
        for(int j=0;j<columns.size();j++){
            List<String> colValues = new ArrayList<>();
            try(FileReader file = new FileReader("/archives/"+this.name+"/"+table+"/"+columns.get(j)+".txt")) {
                BufferedReader br = new BufferedReader(file);
                String primeraLinea = br.readLine();
                type = Column.DataType.valueOf(primeraLinea);
                String linea;
                int valorCorrecto;
                for(int k=0;k<index.size();k++){
                    valorCorrecto =2;
                    int p = index.get(k);
                    while((linea = br.readLine())!= null){
                        if(valorCorrecto == p){
                            colValues.add(linea);
                        }
                        valorCorrecto++;
                    }
                }
                Column col = new Column(type, columns.get(j),colValues);
                colList.add(col);
            }
                catch (IOException e){
                    System.out.println(Constants.COLUMN_DOES_NOT_EXIST_ERROR);
                }
        }
        Table tb = new Table(table, colList);
        return tb;
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
        /*String user = null;
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
        }*/
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
        return false;
    }
    public void addTable(Table table)
    {

    }
    public boolean createTable(String database,String tableName, List<ColumnParameters> columnParameters) throws IOException {
        return false;
        /*try{
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
                writer.write(columnParameters.get(i).type.name()+", ");
            }
            System.out.println(Constants.CREATE_TABLE_SUCCESS);
            return true;
        }catch (IOException e){
            System.out.println(Constants.ERROR);
            return false;
        }*/
    }

    public boolean IsUserAdmin() throws IOException {
        FileReader fr = new FileReader("/archives/profiles/admins.txt");
        BufferedReader reader = new BufferedReader(fr);
        String line;
        while((line = reader.readLine())!=null){
            if(line.contains(mUsername)){
                return true;
            }
        }
        return false;
    }
}
