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

        // Guardaremos en una ruta relativa para evitarnos problemas en caso de disponer diferentes SO
        // Falta cambiar la ruta a relativa
        String path = "C:\\MiniSqlServer" + File.separator + databaseName; // File.separator

        // Recuperamos los ficheros que encontramos en esa ruta
        File[] databases = new File(path).listFiles();

        // Comparamos los ficheros con el nombre de nuestra BD para evitarnos guardar una BD ya creada anteriormente
        for(File db : databases) {
            if(db.getName().equals(databaseName)) {
                exist = true;
                break;
            }
        }

        // Solo creamos el directorio en caso de no encontrar ficheros con ese nombre
        if(exist) {
            System.out.print("Ya existe una base de datos con ese nombre");
            return false;
        }
        else {
            File f = new File(path);
            return f.mkdir();
        }
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
    public boolean dropTable(String tableName) {
        // Buscamos si existe la tabla que quiere borrarse
        Table table = tableByName(tableName);

        // Falta cambiar la ruta a relativa
        String path = this.name + File.pathSeparator + tableName;

        if (table != null) {
            // Borramos la tabla de la lista
            tables.remove(table);

            // Borramos el directorio del equipo
            File directorio = new File(path);
            return directorio.delete();
        }

        System.out.print("No existe tabla con ese nombre");
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

        // En caso de existir no se crea la tabla
        System.out.print("Ya existe una tabla con ese nombre");
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
