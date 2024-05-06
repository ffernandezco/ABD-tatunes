package eus.ehu.giigsi.abd;


public class Constants {
    public static final String ERROR = "ERROR: ";
    public static final String MSG = "MSG: ";
    public static final String CREATE_TABLE_SUCCESS = MSG+"Table created";
    public static final String INSERT_SUCCESS = MSG+"Tuple added";
    public static final String DELETE_SUCCESS = MSG+"Tuple(s) deleted";
    public static final String UPDATE_SUCCESS = MSG+"Tuple(s) updated";
    public static final String DROP_TABLE_SUCCESS = MSG+"Table dropped";
    public static final String SYNTAX_ERROR = ERROR + "Syntactical error";
    public static final String DATABASE_DOES_NOT_EXIST_ERROR = ERROR + "Database does not exist";
    public static final String DATABASE_CREATED_WITHOUT_COLUMNS_ERROR = ERROR + "Cannot create table without columns";
    public static final String TABLE_DOES_NOT_EXIST_ERROR = ERROR + "Table does not exist";
    public static final String TABLE_ALREADY_EXISTS_ERROR = ERROR + "Table exists already";
    public static final String COLUMN_DOES_NOT_EXIST_ERROR = ERROR + "Column does not exist";

    public static final String CREATE_SECURITY_PROFILE_SUCCESS = MSG+"Security profile created";
    public static final String DROP_SECURITY_PROFILE_SUCCESS = MSG+"Security profile dropped";
    public static final String GRANT_PRIVILEGE_SUCCESS = MSG+"Security privilege granted";
    public static final String REVOKE_PRIVILEGE_SUCCESS = MSG+"Security privilege revoked";
    public static final String ADD_USER_SUCCESS = MSG+"User added";
    public static final String DELETE_USER_SUCCESS = MSG+"User deleted";
    public static final String SECURITY_PROFILE_DOES_NOT_EXIST_ERROR = ERROR + "Security profile does not exist";
    public static final String USER_DOES_NOT_EXIST_ERROR = ERROR + "User does not exist or does not match with password";
    public static final String USER_DOES_NOT_HAVE_PRIVILEGE_ERROR = ERROR + "Privilege cannot be revoked because the profile has not been granted it";
    public static final String PRIVILEGE_DOES_NOT_EXIST_ERROR = ERROR + "Privilege does not exist";
    public static final String PROFILE_ALREADY_HAS_PRIVILEGE = ERROR + "Profile already has privilege";
    public static final String USERS_PROFILE_IS_NOT_GRANTED_REQUIRED_PRIVILEGE = ERROR + "The security profile of the user does not have the required privilege to perform the operation";
}
