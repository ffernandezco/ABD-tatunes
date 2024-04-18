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
    public static final int CREATE_SECURITY_PROFILE_PATTERN_GROUP_COUNT = 2;
    //TODO todos los demás patrones
    public static final int CREATE_TABLE_PATTERN_GROUP_NAME = 1;
    public static final int CREATE_TABLE_PATTERN_GROUP_COLUMNS = 2;
    public static final int DROP_TABLE_PATTERN_GROUP_NAME = 1;
    public static MiniSQLQuery parse(String miniSQLQuery)
    {
        Matcher matcher;

        //REGULAR MINISQL QUERIES
        //TODO

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
