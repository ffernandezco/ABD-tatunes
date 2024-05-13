package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.Constants;
import eus.ehu.giigsi.abd.structures.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniSQLParser {
    public static final Pattern CREATE_SECURITY_PROFILE_PATTERN = Pattern.compile("CREATE\\s+SECURITY\\s+PROFILE\\s+(?<nombreSecurityProfile>[a-zA-Z]+)");
    public static final Pattern CREATE_TABLE_PATTERN = Pattern.compile("CREATE\\s+TABLE\\s+(?<table>[a-zA-Z]+)\\s*\\((?<columns>[^)]+)\\)$");
    public static final Pattern DROP_TABLE_PATTERN = Pattern.compile("DROP\\s+TABLE\\s+(?<table>[a-zA-Z]+)$");
    //public static final Pattern SELECT_PATTERN = Pattern.compile("SELECT\\s+(?<columns>[a-zA-Z0-9]+(?:\\s*,\\s*[a-zA-Z0-9]+)*)\\s+FROM\\s+(?<tableName>[a-zA-Z]+)\\s*(?:WHERE\\s+(?<condition>.+))?");
    //public static final Pattern SELECT_PATTERN = Pattern.compile("SELECT\\s+(?<columns>[a-zA-Z0-9]+(?:\\s*,\\s*[a-zA-Z0-9]+)*)\\s+FROM\\s+(?<tableName>[a-zA-Z]+)\\s*(?:WHERE\\s+(?<column>[a-zA-Z]+)\\s*(?<operator>[=<>])\\s*(?<literalValue>(-?\\d+|-?\\d+\\.\\d+|'(.*)')))?$");
    public static final Pattern SELECT_PATTERN = Pattern.compile("SELECT\\s+(?<columns>[a-zA-Z0-9,]+)\\s+FROM\\s+(?<tableName>[a-zA-Z]+)\\s*(?:WHERE\\s+(?<column>[a-zA-Z]+)\\s*(?<operator>[=<>])\\s*(?<literalValue>(-?\\d+|-?\\d+\\.\\d+|'(.*)')))?$");
    //public static final Pattern DELETE_PATTERN = Pattern.compile("DELETE\\s+FROM\\s+(?<table>[a-zA-Z]+)\\s+WHERE\\s+(?<condition>.+)");
    //public static final Pattern DELETE_PATTERN = Pattern.compile("DELETE\\s+FROM\\s+(?<table>[a-zA-Z]+)\\s+WHERE\\s+(?<column>[a-zA-Z0-9]+)(?<operator>[=<>])'?(?<literalValue>.*)'?");
    public static final Pattern DELETE_PATTERN = Pattern.compile("DELETE\\s+FROM\\s+(?<table>[a-zA-Z]+)\\s+WHERE\\s+(?<column>[a-zA-Z0-9]+)(?<operator>[=<>])(?<literalValue>(-?\\d+|-?\\d+\\.\\d+|'(.*)'))$");
    //public static final Pattern DELETE_PATTERN = Pattern.compile("DELETE\\s+FROM\\s+(?<table>[a-zA-Z]+)\\s+WHERE\\s+(?<column>[a-zA-Z]+)\\s*(?<operator>[=<>])\\s*(?:'(?<literalValueString>(?:[^'\\\\]|\\\\.)+)'|(?<literalValueNumeric>-?\\d*\\.?\\d+))");
    public static final Pattern INSERT_PATTERN = Pattern.compile("INSERT\\s+INTO\\s+(?<table>[a-zA-Z0-9]+)\\s+VALUES\\s+\\((?<literalValues>(.*))\\)$");
    //public static final Pattern UPDATE_PATTERN = Pattern.compile("UPDATE\\s+(?<table>[a-zA-Z]+)\\s+SET\\s+(?<literalValues>[^\\s]+(?:\\s*,\\s*[^\\s]+)*)\\s+WHERE\\s+(?<conditions>.+)");
    public static final Pattern UPDATE_PATTERN = Pattern.compile("UPDATE\\s+(?<table>[a-zA-Z]+)\\s+SET\\s+(?<literalValues>[^\\s]+(?:,[^\\s]+)*)\\s+WHERE\\s+(?<column>[a-zA-Z]+)\\s*(?<operator>[=<>])\\s*(?<literalValue>(-?\\d+|-?\\d+\\.\\d+|'(.*)'))$");
    public static final Pattern DROP_SECURITY_PROFILE_PATTERN = Pattern.compile("DROP\\s+SECURITY\\s+PROFILE\\s+(?<nombreSecurityProfile>[a-zA-Z]+)");
    public static final Pattern GRANT_PATTERN = Pattern.compile("GRANT\\s+(?<privilege>[a-zA-Z0-9]+)\\s+ON\\s+(?<tableName>[a-zA-Z0-9]+)\\s+TO\\s+(?<securityProfile>[a-zA-Z]+)");
    public static final Pattern REVOKE_PATTERN = Pattern.compile("REVOKE\\s+(?<privilege>[a-zA-Z0-9]+)\\s+ON\\s+(?<tableName>[a-zA-Z0-9]+)\\s+TO\\s+(?<securityProfile>[a-zA-Z]+)");
    public static final Pattern ADD_USER_PATTERN = Pattern.compile("ADD\\s+USER\\s+\\((?<username>[a-zA-Z]+),\\s*(?<password>[^,]+),\\s*(?<securityProfile>[a-zA-Z]+)\\)");
    public static final Pattern DELETE_USER_PATTERN = Pattern.compile("DELETE\\s+USER\\s+(?<username>[a-zA-Z]+)");

    public static final int CREATE_SECURITY_PROFILE_PATTERN_GROUP_COUNT = 2;
    public static final int CREATE_TABLE_PATTERN_GROUP_NAME = 1;
    public static final int CREATE_TABLE_PATTERN_GROUP_COLUMNS = 2;
    public static final int DROP_TABLE_PATTERN_GROUP_NAME = 1;
    public static final int SELECT_PATTERN_GROUP_COLUMNS = 1;
    public static final int SELECT_PATTERN_GROUP_TABLE = 2;
    public static final int SELECT_PATTERN_GROUP_COLUMN = 3;
    public static final int SELECT_PATTERN_GROUP_OPERATOR = 4;
    public static final int SELECT_PATTERN_GROUP_LITERAL_VALUE = 5;

    //public static final int SELECT_PATTERN_GROUP_CONDITION = 3;
    //public static final int DELETE_PATTERN_GROUP_TABLE = 1;
    //public static final int DELETE_PATTERN_GROUP_CONDITION = 2;
    public static final int DELETE_PATTERN_GROUP_TABLE = 1;
    public static final int DELETE_PATTERN_GROUP_COLUMN = 2;
    public static final int DELETE_PATTERN_GROUP_OPERATOR = 3;
    public static final int DELETE_PATTERN_GROUP_LITERAL_VALUE = 4;

    public static final int INSERT_PATTERN_GROUP_TABLE = 1;
    public static final int INSERT_PATTERN_GROUP_VALUES = 2;
    public static final int UPDATE_PATTERN_GROUP_TABLE = 1;
    public static final int UPDATE_PATTERN_GROUP_VALUES = 2;
    public static final int UPDATE_PATTERN_GROUP_COLUMN = 3;
    public static final int UPDATE_PATTERN_GROUP_OPERATOR = 4;
    public static final int UPDATE_PATTERN_GROUP_LITERAL_VALUE = 5;
    public static final int DROP_SECURITY_PROFILE_PATTERN_GROUP_COUNT = 2;
    public static final int GRANT_PATTERN_GROUP_PRIVILEGE = 1;
    public static final int GRANT_PATTERN_GROUP_TABLE = 2;
    public static final int GRANT_PATTERN_GROUP_PROFILE = 3;
    public static final int REVOKE_PATTERN_GROUP_PRIVILEGE = 1;
    public static final int REVOKE_PATTERN_GROUP_TABLE = 2;
    public static final int REVOKE_PATTERN_GROUP_PROFILE = 3;
    public static final int ADD_USER_PATTERN_GROUP_USERNAME = 1;
    public static final int ADD_USER_PATTERN_GROUP_PASSWORD = 2;
    public static final int ADD_USER_PATTERN_GROUP_PROFILE = 3;
    public static final int DELETE_USER_GROUP_USERNAME = 1;

    public static MiniSQLQuery parse(String miniSQLQuery)
    {
        // System.out.println("Ejecutando " + miniSQLQuery);
        Matcher matcher;

        //REGULAR MINISQL QUERIES
        //CreateTable
        matcher = CREATE_TABLE_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String tableName = matcher.group(CREATE_TABLE_PATTERN_GROUP_NAME);
            String columnsString = matcher.group(CREATE_TABLE_PATTERN_GROUP_COLUMNS);
            if (columnsString != null && !columnsString.isEmpty()) {
                if (!columnsString.matches("TEXT")) { // Se introduce TEXT como tipo de dato
                    columnsString = columnsString.replaceAll("TEXT", "STRING"); // Sustituye TEXT por STRING para ser compatible con el tipo de dato
                }
            }
            List<ColumnParameters> columnsParameters = new ArrayList<>();
            List<String> columnStrings = commaSeparatedNames(columnsString);
            if(columnStrings == null){
                return null;
            }
            for (String column : columnStrings) {
                String[] parts = column.trim().split("\\s+");
                if (parts.length == 2) {
                    String columnName = parts[0];
                    String dataTypeName = parts[1];
                    Column.DataType dataType = DataTypeUtils.fromMiniSQLName(dataTypeName); // Convierte al tipo de dato automáticamente, sustituye al switch
                    columnsParameters.add(new ColumnParameters(columnName, dataType));
                }
            }
            return new CreateTable(tableName, columnsParameters);
        }

        //DropTable
        matcher = DROP_TABLE_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String tableName = matcher.group(DROP_TABLE_PATTERN_GROUP_NAME);
            return new DropTable(tableName);
        }

        //Select
        matcher = SELECT_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String table = matcher.group(SELECT_PATTERN_GROUP_TABLE);
            String columnsString = matcher.group(SELECT_PATTERN_GROUP_COLUMNS);
            String column = matcher.group(SELECT_PATTERN_GROUP_COLUMN);
            String operator = matcher.group(SELECT_PATTERN_GROUP_OPERATOR);
            String literalValue = matcher.group(SELECT_PATTERN_GROUP_LITERAL_VALUE);

            //String conditionString = matcher.group(SELECT_PATTERN_GROUP_CONDITION);

            Condition condition = null;
            List<String> columns = commaSeparatedNames(columnsString);
            if (literalValue != null && !literalValue.isEmpty()) {
                if (!literalValue.matches("-?\\d+(\\.\\d+)?")) { // No es DOUBLE ni INT
                    literalValue = literalValue.replaceAll("'", ""); // Quitar el símbolo ' de la consulta
                }
                condition = new Condition(column, operator, literalValue);
            }


            /*
            Condition condition = null; // Podría no tener condición según el enunciado
            if (conditionString != null && !conditionString.isEmpty()) {
                String[] conditionData = conditionString.trim().split("\\s+");
                //Creamos la Condition a partir de los valores solo si se incluye en el WHERE
                if (conditionData.length == 3) {
                    String column = conditionData[0];
                    String operator = conditionData[1];
                    String literalValue = conditionData[2];
                    condition = new Condition(column, operator, literalValue);
                }
            }

             */
            return new Select(table, columns, condition);
        }

        //Delete
        matcher = DELETE_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String table = matcher.group(DELETE_PATTERN_GROUP_TABLE);
            String column = matcher.group(DELETE_PATTERN_GROUP_COLUMN);
            String operator = matcher.group(DELETE_PATTERN_GROUP_OPERATOR);
            String literalValue = matcher.group(DELETE_PATTERN_GROUP_LITERAL_VALUE);


            // Comprobar si es Int, Double o String
            if (literalValue != null && !literalValue.isEmpty()) {
                if (!literalValue.matches("-?\\d+(\\.\\d+)?")) { // No es DOUBLE ni INT
                    literalValue = literalValue.replaceAll("'", ""); // Quitar el símbolo ' de la consulta
                }
            }
            Condition condition = new Condition(column, operator, literalValue);

            /*
            String conditionData = matcher.group(DELETE_PATTERN_GROUP_CONDITION);
            Condition condition = null;
            if (conditionData != null && !conditionData.isEmpty()) {
                String[] conditionConverted = conditionData.trim().split("\\s+"); //Separa la cadena en los 3 valores para crear un objeto Condition
                if (conditionConverted.length == 3) {
                    String column = conditionConverted[0];
                    String operator = conditionConverted[1];
                    String literalValue = conditionConverted[2];
                    condition = new Condition(column, operator, literalValue);
                }
            }
             */

            return new Delete(table, condition);
        }

        //Insert
        matcher = INSERT_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String table = matcher.group(INSERT_PATTERN_GROUP_TABLE);
            String literalValues = matcher.group(INSERT_PATTERN_GROUP_VALUES);

            List<String> values = commaSeparatedNames(literalValues); //Crea una lista a partir de los valores literales dados con la función dada

            if(values == null){
                return null;
            }

            List<String> valuesUpdated = new ArrayList<>();
            for(String value : values){
                if(value.matches("'(.*)'")) {
                    value = value.replaceAll("'","");
                }
                valuesUpdated.add(value);
            }
            return new Insert(table, valuesUpdated);
        }

        //Update
        matcher = UPDATE_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String table = matcher.group(UPDATE_PATTERN_GROUP_TABLE);
            String literalValues = matcher.group(UPDATE_PATTERN_GROUP_VALUES);
            String columna = matcher.group(UPDATE_PATTERN_GROUP_COLUMN);
            String operator = matcher.group(UPDATE_PATTERN_GROUP_OPERATOR);
            String literalValue = matcher.group(UPDATE_PATTERN_GROUP_LITERAL_VALUE);

            // Valores: hay que crear instancia de SetValue, no se puede pasar lista tal cual de String

            /*
            List<SetValue> columns = new ArrayList<>();
            String[] parts = literalValues.split("\\s*,\\s*");
            for (String part : parts) {
                String[] keyValue = part.split("\\s*=\\s*");
                if (keyValue.length == 2) {
                    columns.add(new SetValue(keyValue[0], keyValue[1]));
                }
            }

             */
            List<SetValue> columns = new ArrayList<>();
            String[] parts = literalValues.split("\\s*,\\s*");
            for (String part : parts) {
                String[] keyValue = part.split("\\s*=\\s*");
                if (keyValue.length == 2) {
                    String column = keyValue[0];
                    String value = keyValue[1].replaceAll("'", ""); // Eliminar comillas simples
                    columns.add(new SetValue(column, value));
                }
            }

            // Condiciones
            // Comprobar si es Int, Double o Text
            if (literalValue != null && !literalValue.isEmpty()) {
                if (!literalValue.matches("-?\\d+(\\.\\d+)?")) { // No es DOUBLE ni INT
                    literalValue = literalValue.replaceAll("'", ""); // Quitar el símbolo ' de la consulta
                }
            }
            Condition condition = new Condition(columna, operator, literalValue);

            /*
            // Condiciones
            Condition condition = null;
            if (conditionData != null && !conditionData.isEmpty()) {
                Pattern conditionPattern = Pattern.compile("(\\w+)\\s*([<>=])\\s*(\\w+)");
                Matcher conditionMatcher = conditionPattern.matcher(conditionData);
                if (conditionMatcher.matches() && conditionMatcher.groupCount() == 3) {
                    String column = conditionMatcher.group(1);
                    String operator = conditionMatcher.group(2);
                    String literalValue = conditionMatcher.group(3);
                    condition = new Condition(column, operator, literalValue);
                }
            }

             */

            return new Update(table, columns, condition);
        }


        //SECURITY QUERIES


        //CreateSecurityProfile

        matcher = CREATE_SECURITY_PROFILE_PATTERN.matcher(miniSQLQuery);

        if(matcher.find() && matcher.groupCount() == CREATE_SECURITY_PROFILE_PATTERN_GROUP_COUNT){
            String nombreSP = matcher.group("nombreSecurityProfile");
            return new CreateSecurityProfile(nombreSP);
        }

        matcher = DROP_SECURITY_PROFILE_PATTERN.matcher(miniSQLQuery);

        if(matcher.find() && matcher.groupCount() == DROP_SECURITY_PROFILE_PATTERN_GROUP_COUNT){
            String nombreSP = matcher.group("nombreSecurityProfile");
            return new DropSecurityProfile(nombreSP);
        }

        matcher = GRANT_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String privilege = matcher.group(GRANT_PATTERN_GROUP_PRIVILEGE);
            String table = matcher.group(GRANT_PATTERN_GROUP_TABLE);
            String profile = matcher.group(GRANT_PATTERN_GROUP_PROFILE);

            return new Grant(privilege, table, profile);
        }

        matcher = REVOKE_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String privilege = matcher.group(REVOKE_PATTERN_GROUP_PRIVILEGE);
            String table = matcher.group(REVOKE_PATTERN_GROUP_TABLE);
            String profile = matcher.group(REVOKE_PATTERN_GROUP_PROFILE);

            return new Revoke(privilege, table, profile);
        }

        matcher = ADD_USER_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String username = matcher.group(ADD_USER_PATTERN_GROUP_USERNAME);
            String password = matcher.group(ADD_USER_PATTERN_GROUP_PASSWORD);
            String profile = matcher.group(ADD_USER_PATTERN_GROUP_PROFILE);
            return new AddUser(username, password, profile);
        }

        matcher = DELETE_USER_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String username = matcher.group(DELETE_USER_GROUP_USERNAME);
            return new DeleteUser(username);
        }



        if(false /* Comprobar las demás sentencias */){
            return null;
        }

        return null;
    }

    static List<String> commaSeparatedNames(String text)
    {
        String[] textParts = text.split(",");
        List<String> commaSeparator = new ArrayList<>();
        for(int i=0; i < textParts.length; i++) {
            if(textParts[i] != null && !textParts[i].trim().isEmpty()){

                commaSeparator.add(textParts[i]);
            }
        }
        for(String value : commaSeparator) {
            if(value.startsWith(" ") || value.endsWith(" ")){
                return null;
            }
        }
        return commaSeparator;
    }

}