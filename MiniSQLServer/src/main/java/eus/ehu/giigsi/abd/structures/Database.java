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
        boolean exist = false;

        String path = "C:\\MiniSqlServer" + File.separator + databaseName; // File.separator

        File[] databases = new File(path).listFiles();

        for(File db : databases) {
            if(db.getName().equals(databaseName)) {
                exist = true;
                break;
            }
        }

        if(exist) {
            System.out.print("Ya existe una base de datos con ese nombre");
        }
        else {
            File f = new File(path);
            return f.mkdir();
        }

        return false;
    }

    public Table select(String table, List<String> columns, Condition columnCondition) {
        String user = this.mUsername;
        String password = this.mPassword;
        FileReader fr = load(this.name, user, password);
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
        boolean enc = false;
        int i = 0;

        while(i < tables.size() && enc == false) {
            if(tables.get(i).name == tableName) {
                return tables.get(i);
            }
        }

        return null;
    }
    public boolean dropTable(String tableName) {
        Table table = tableByName(tableName);

        if (table != null) {
            tables.remove(table);

            // FALTA BORRARLO EN LOS DIRECTORIOS
        }

        return false;
    }
    public void addTable(Table table)
    {
        tables.add(table);
    }
    public boolean createTable(String tableName, List<ColumnParameters> columnParameters) {

        // Verificamos si existe la tabla
        // Recorremos el array de columnParameters para crear columnas y, posteriormente crear la tabla
        if(tableByName(tableName) == null) {
            List<Column> columns = new ArrayList<>();

            // Convertimos cada ColumnParameter en Column
            for (ColumnParameters c : columnParameters) {
                Column.DataType type = c.getType();
                String name = c.getName();

                Column column = new Column(type, name);

                columns.add(column);
            }

            // Creamos la tabla y la añadimos a la lista de la base de datos
            Table table = new Table(tableName, columns);

            addTable(table);

            // Guardamos la tabla en nuestros ficheros
            return table.save(this.name);

        }

        return false;
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
