package eus.ehu.giigsi.abd.structures;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.parser.*;
import eus.ehu.giigsi.abd.security.Encryption;
import eus.ehu.giigsi.abd.security.Manager;
import eus.ehu.giigsi.abd.security.Profile;
import eus.ehu.giigsi.abd.security.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.sql.DatabaseMetaData;
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
        securityManager = new Manager(adminUsername);
        User admin = new User(adminUsername, adminPassword);
        Profile administrador = new Profile();
        administrador.setName(Profile.AdminProfileName);
        administrador.getUsers().add(admin);
        securityManager.profiles.add(administrador);
    }

    public static Database load(String databaseName, String username, String password)
    {

        password = Encryption.encrypt(password);

        Database database = new Database(username, password);

        database.securityManager = Manager.load(databaseName, username);

        if (database.securityManager != null && database.securityManager.isPasswordCorrect(username, password)) {

            // if (database.IsUserAdmin()) {
            String path = "databases" + File.separator + databaseName;

            File file = new File(path);

            File[] listFiles = file.listFiles();

            for (File f : listFiles) {
                Table table = new Table("name", new ArrayList<Column>());

                if (table.load(f.getPath())) database.addTable(table);
            }

            return database;
            // }
        }

        // Constants.USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE
        return null;
    }
    public boolean save(String databaseName)
    {
        // Guardaremos en una ruta relativa para evitarnos problemas en caso de disponer diferentes SO
        // Falta cambiar la ruta a relativa
        String path = "databases" + File.separator + databaseName; // File.separator

        File file = new File(path);

        if (file.exists()) deleteFolder(path);

        try {
            if (! tables.isEmpty()) {
                int i = 0;
                while (i < tables.size() && tables.get(i).save(databaseName) == true) {

                    i++;
                }
            }

            securityManager.save(databaseName);

            return true;
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public Table select(String table, List<String> columns, Condition columnCondition) {
        Table t = tableByName(table);

        if(t != null) {
            List<Column> columnasSelect = new ArrayList<>();

            if (columnCondition != null) {
                Column c = t.columnByName(columnCondition.getColumn());
                List<Integer> valoresIntroducir = c.indicesWhereIsTrue(columnCondition);

                // Recorremos todas las columnas a mostrar para guardar solo los valores que cumplen de la condición
                if (columns != null && !columns.isEmpty()) {

                    for (String nombreColumna : columns) {

                        if (t.columnByName(nombreColumna) != null) {

                            Column c1 = t.columnByName(nombreColumna);

                            List<String> valoresColumna = new ArrayList<>();

                            // Guardamos los valores de las posiciones recogidas anteriormente en una lista
                            for (int j : valoresIntroducir) {
                                valoresColumna.add(c1.getValues().get(j));
                            }

                            // Insertamos la lista en la tabla
                            Column aux = new Column(c1.type, c1.getName(), valoresColumna);
                            columnasSelect.add(aux);

                        } else {
                            lastErrorMessage = Constants.COLUMN_DOES_NOT_EXIST_ERROR;
                            return null;
                        }

                    }

                    Table select = new Table(table, columnasSelect);
                    return select;

                } else if (columns == null || columns.size() == 0) {
                    return null;

                } else if (columns.get(0).equals("*")) {
                    for (Column c2 : t.columns) {

                        List<String> valoresColumna = new ArrayList<>();

                        // Guardamos los valores de las posiciones recogidas anteriormente en una lista
                        for (int j : valoresIntroducir) {
                            valoresColumna.add(c2.getValues().get(j));
                        }

                        // Insertamos la lista en la tabla
                        Column aux = new Column(c2.type, c2.getName(), valoresColumna);
                        columnasSelect.add(aux);
                    }

                    Table select = new Table(table, columnasSelect);
                    return select;
                }

            } else {
                if (columns != null && !columns.isEmpty()) {

                    for (String nombreColumna : columns) {

                        if (t.columnByName(nombreColumna) != null) {

                            Column c1 = t.columnByName(nombreColumna);

                            List<String> valoresColumna = new ArrayList<>();

                            // Guardamos los valores de las posiciones recogidas anteriormente en una lista
                            for (int j = 0; j < c1.values.size(); j++) {
                                valoresColumna.add(c1.getValues().get(j));
                            }

                            // Insertamos la lista en la tabla
                            Column aux = new Column(c1.type, c1.getName(), valoresColumna);
                            columnasSelect.add(aux);

                        } else {
                            lastErrorMessage = Constants.COLUMN_DOES_NOT_EXIST_ERROR;
                            return null;
                        }

                    }

                    Table select = new Table(table, columnasSelect);
                    return select;
                } else if (columns.get(0).equals("*")) {
                    for (Column c2 : t.columns) {

                        List<String> valoresColumna = new ArrayList<>();

                        // Guardamos los valores de las posiciones recogidas anteriormente en una lista
                        for (int j = 0; j < c2.values.size(); j++) {
                            valoresColumna.add(c2.getValues().get(j));
                        }

                        // Insertamos la lista en la tabla
                        Column aux = new Column(c2.type, c2.getName(), valoresColumna);
                        columnasSelect.add(aux);
                    }

                    Table select = new Table(table, columnasSelect);
                    return select;
                }
                return null;
            }
        }
        return null;
    }

    public boolean deleteWhere(String tableName, Condition columnCondition) {
        Table t = tableByName(tableName);

        if (t != null) {

            if (columnCondition != null) {

                t.deleteWhere(columnCondition);
                return true;
            }
        }

        return false;
    }

    public boolean update(String tableName, List<SetValue> columnNames, Condition columnCondition)
    {
        Table table = tableByName(tableName);

        if (table != null) {

            if (columnNames != null && !columnNames.isEmpty()) {

                if (columnCondition != null) {
                    int i = 0;

                    Column c = table.columnByName(columnCondition.column);

                    List<Integer> indices = c.indicesWhereIsTrue(columnCondition);

                    for (SetValue sv : columnNames) {
                        for (int k : indices) {
                            Column c1 = table.columnByName(sv.getColumn());
                            c1.SetValue(k, sv.getValue());
                        }
                    }
                    return true;

                    /*
                    for (SetValue sv : columnNames) {

                        Column c1 = table.columnByName(sv.getColumn());
                        c1.updateWhere(columnCondition, sv.getValue());
                    }
                    return true;
                    */

                }
            }
        }

        return false;
    }

    public boolean Insert(String tableName, List<String> values) {
        Table t = tableByName(tableName);

        if (t != null) {

            if (values != null && !values.isEmpty()) {

                return t.insert(values);
            }
        }

        return false;
    }

    public String executeMiniSQLQuery(String query) {
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
            if(tables.get(i).name.equals(tableName)) {
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

        if (table != null) {
            // Borramos la tabla de la lista
            return tables.remove(table);

        } else {
            return false;
        }
    }
    public void addTable(Table table)
    {
        tables.add(table);
    }
    public boolean createTable(String tableName, List<ColumnParameters> columnParameters) {
        // Recorremos el array de columnParameters para crear columnas y, posteriormente crear la tabla
        if(tableByName(tableName) == null) {
            List<Column> columns = new ArrayList<>();

            // Convertimos cada ColumnParameter en Column
            if(columnParameters != null && !columnParameters.isEmpty()) {
                for (ColumnParameters c : columnParameters) {
                    Column.DataType type = c.getType();
                    String name = c.getName();

                    Column column = new Column(type, name);

                    columns.add(column);
                }

                Table table = new Table(tableName, columns);
                addTable(table);
                lastErrorMessage = Constants.CREATE_TABLE_SUCCESS;
                return true;

            } else {
                lastErrorMessage = Constants.DATABASE_CREATED_WITHOUT_COLUMNS_ERROR;
                return false;
            }

        } else {
            lastErrorMessage = Constants.TABLE_ALREADY_EXISTS_ERROR;
            return false;
        }
    }

    public boolean IsUserAdmin() {
        if (securityManager.isPasswordCorrect(mUsername, mPassword)) return securityManager.isUserAdmin();
        else return false;

    }

    public void deleteFolder(String path){
        File file = new File(path);
        File[] files;
        File[] subDirs;

        files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        });

        subDirs = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..");
            }
        });

        if(files != null){
            for(File f : files){
                //System.out.println("Deleting "+f.getAbsolutePath());
                f.delete();
            }
        }

        if(subDirs != null){
            for(File subdir : subDirs){
                deleteFolder(subdir.getAbsolutePath());
                subdir.delete();
            }
        }
    }
}
