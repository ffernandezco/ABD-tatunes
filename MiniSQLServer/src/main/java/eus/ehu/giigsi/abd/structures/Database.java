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
        boolean exist = false;

        // Guardaremos en una ruta relativa para evitarnos problemas en caso de disponer diferentes SO
        // Falta cambiar la ruta a relativa
        String path = "databases" + File.separator + databaseName; // File.separator

        // Recuperamos los ficheros que encontramos en esa ruta
        File[] databases = new File(path).listFiles();

        // Comparamos los ficheros con el nombre de nuestra BD para evitarnos guardar una BD ya creada anteriormente
        if(databases != null) {
            for (File db : databases) {
                if (db.getName().equals(databaseName)) {
                    exist = true;
                    break;
                }
            }
        }

        // Solo creamos el directorio en caso de no encontrar ficheros con ese nombre
        if(exist) {
            System.out.print("Ya existe una base de datos con ese nombre");
            return false;
        }
        else {
            File f = new File(path);
            return f.mkdirs();
        }
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

    public boolean Insert(String tableName, List<String> values) {
        /*String user = null;
        String password = null;
        FileReader fr = load(  this.name,user, password);
        int numLine = findTable(tableName);
        if(numLine != -1){
            BufferedReader reader = new BufferedReader(fr);
            String line;
            int currentLine=0;
            while(true) {
                try {
                    if (!((line = reader.readLine()) != null && currentLine < numLine - 1)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                currentLine++;
            }
            int i=0;
            while(i< values.size()){
                FileWriter fw = null;
                try {
                    fw = new FileWriter("/archives/"+this.name+".txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter writer = new BufferedWriter(fw);
                try {
                    writer.write(values.get(i) + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                i++;
                currentLine++;
            }
        }
        return false;*/
        Table table = tableByName(tableName);
        return table.insert(values);
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
        int i = 0;

        // Buscamos en cada vector del array tables y si coinciden se devuelve el item
        while(i < tables.size()) {
            if(tables.get(i).name == tableName) {
                return tables.get(i);
            }
            else {
                i++;
            }
        }

        // En caso de no existir se devuelve null
        return null;
    }
    public boolean dropTable(String tableName)
    {
        return false;
    }
    public void addTable(Table table)
    {
        tables.add(table);
    }
    public boolean createTable(String database,String tableName, List<ColumnParameters> columnParameters) throws IOException {

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
                writer.write(columnParameters.get(i).type.name()+", ");
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
    public int findTable( String tableName) {
          /*  BufferedReader reader = new BufferedReader(fr);
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
                return -1;*/
        return 0;
        }
}
