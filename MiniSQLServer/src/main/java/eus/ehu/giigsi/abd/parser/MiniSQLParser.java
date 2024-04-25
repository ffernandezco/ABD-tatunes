package eus.ehu.giigsi.abd.parser;

import eus.ehu.giigsi.abd.structures.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniSQLParser {
    public static final Pattern CREATE_SECURITY_PROFILE_PATTERN = Pattern.compile("CREATE\\s+SECURITY\\s+PROFILE\\s+(?<nombreSecurityProfile>[a-zA-Z]+)");
    public static final Pattern CREATE_TABLE_PATTERN = Pattern.compile("CREATE\\s+TABLE\\s+(?<table>[a-zA-Z]+)\\s*\\((?<columns>[^)]+)\\)");
    public static final Pattern DROP_TABLE_PATTERN = Pattern.compile("DROP\\s+TABLE\\s+(?<table>[a-zA-Z]+)");
    public static final Pattern SELECT_PATTERN = Pattern.compile("SELECT\\s+(?<columns>[a-zA-Z0-9]+(?:\\s*,\\s*[a-zA-Z]+)*)\\s+FROM\\s+(?<tableName>[a-zA-Z]+)\\s*(?:WHERE\\s+(?<condition>.+))?");
    public static final Pattern DELETE_PATTERN = Pattern.compile("DELETE\\s+FROM\\s+(?<table>[a-zA-Z]+)\\s+WHERE\\s+(?<condition>.+)");
    public static final Pattern INSERT_PATTERN = Pattern.compile("INSERT\\s+INTO\\s+(?<table>[a-zA-Z]+)\\s+VALUES\\s*\\((?<literalValues>[^)]+)\\)");
    public static final Pattern UPDATE_PATTERN = Pattern.compile("UPDATE\\s+(?<table>[a-zA-Z]+)\\s+SET\\s+(?<literalValues>[^\\s]+(?:\\s*,\\s*[^\\s]+)*)\\s+WHERE\\s+(?<conditions>.+)");
    public static final int CREATE_SECURITY_PROFILE_PATTERN_GROUP_COUNT = 2;
    public static final int CREATE_TABLE_PATTERN_GROUP_NAME = 1;
    public static final int CREATE_TABLE_PATTERN_GROUP_COLUMNS = 2;
    public static final int DROP_TABLE_PATTERN_GROUP_NAME = 1;
    public static final int SELECT_PATTERN_GROUP_COLUMNS = 1;
    public static final int SELECT_PATTERN_GROUP_TABLE = 2;
    public static final int SELECT_PATTERN_GROUP_CONDITION = 3;
    public static final int DELETE_PATTERN_GROUP_TABLE = 1;
    public static final int DELETE_PATTERN_GROUP_CONDITION = 2;
    public static final int INSERT_PATTERN_GROUP_TABLE = 1;
    public static final int INSERT_PATTERN_GROUP_VALUES = 2;
    public static final int UPDATE_PATTERN_GROUP_TABLE = 1;
    public static final int UPDATE_PATTERN_GROUP_VALUES = 2;
    public static final int UPDATE_PATTERN_GROUP_CONDITION = 3;
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
            List<ColumnParameters> columnsParameters = new ArrayList<>();
            List<String> columnStrings = commaSeparatedNames(columnsString);
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
            String conditionString = matcher.group(SELECT_PATTERN_GROUP_CONDITION);

            List<String> columns = commaSeparatedNames(columnsString);
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
            return new Select(table, columns, condition);
        }

        //Delete
        matcher = DELETE_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String table = matcher.group(DELETE_PATTERN_GROUP_TABLE);
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
            return new Delete(table, condition);
        }

        //Insert
        matcher = INSERT_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String table = matcher.group(INSERT_PATTERN_GROUP_TABLE);
            String literalValues = matcher.group(INSERT_PATTERN_GROUP_VALUES);
            List<String> values = commaSeparatedNames(literalValues); //Crea una lista a partir de los valores literales dados con la función dada
            return new Insert(table, values);
        }

        //Update
        matcher = UPDATE_PATTERN.matcher(miniSQLQuery);
        if (matcher.find()) {
            String table = matcher.group(UPDATE_PATTERN_GROUP_TABLE);
            String literalValues = matcher.group(UPDATE_PATTERN_GROUP_VALUES);
            String conditionData = matcher.group(UPDATE_PATTERN_GROUP_CONDITION);

            // Valores: hay que crear instancia de SetValue, no se puede pasar lista tal cual de String
            List<SetValue> columns = new ArrayList<>();
            String[] parts = literalValues.split("\\s*,\\s*");
            for (String part : parts) {
                String[] keyValue = part.split("\\s*=\\s*");
                if (keyValue.length == 2) {
                    columns.add(new SetValue(keyValue[0], keyValue[1]));
                }
            }

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

            return new Update(table, columns, condition);
        }


        //SECURITY QUERIES


        //CreateSecurityProfile

        matcher = CREATE_SECURITY_PROFILE_PATTERN.matcher(miniSQLQuery);

        if(matcher.find() && matcher.groupCount() == CREATE_SECURITY_PROFILE_PATTERN_GROUP_COUNT){
            String nombreSP = matcher.group("nombreSecurityProfile");
            return new CreateSecurityProfile(nombreSP);
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
                commaSeparator.add(textParts[i].trim());
            }
        }
        return commaSeparator;
    }

}
