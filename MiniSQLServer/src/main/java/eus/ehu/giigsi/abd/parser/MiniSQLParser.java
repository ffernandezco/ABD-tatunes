package eus.ehu.giigsi.abd.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniSQLParser {
    public static final Pattern CREATE_SECURITY_PROFILE_PATTERN = Pattern.compile("CREATE\\s+SECURITY\\s+PROFILE\\s+(?<nombreSecurityProfile>[a-zA-Z]+)");
    public static final int CREATE_SECURITY_PROFILE_PATTERN_GROUP_COUNT = 2;
    //TODO todos los demás patrones
    public static final Pattern CREATE_TABLE_PATTERN = Pattern.compile("CREATE\\s+TABLE\\s+(?<nombre>[a-zA-Z]+)\\s*\\((?<columnas>[^)]+)\\)");
    public static final int CREATE_TABLE_PATTERN_GROUP_NAME = 1;
    public static final int CREATE_TABLE_PATTERN_GROUP_COLUMNS = 2;

    public static MiniSQLQuery parse(String miniSQLQuery)
    {
        Matcher matcher;


        //REGULAR MINISQL QUERIES
        //TODO

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
