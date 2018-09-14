package com.devhunter.fncp.constants;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class FNQueries {

    // Login Controller Queries
    // use these default queries a SqlInterpolate to create queries
    //login
    public static final String LOGIN_QUERY = "SELECT * FROM " + LOGIN_TABLE + " WHERE " + USER_USERNAME_COLUMN + " = '%s' AND " + USER_PASSWORD_COLUMN + " = '%s'";

    // User Controller Queries
    // use these default queries and SqlInterpolate to create queries
    //search All users
    public static final String SELECT_ALL_USER_QUERY = "SELECT * FROM " + LOGIN_TABLE;
    //search user
    public static final String SELECT_USER_QUERY = SELECT_ALL_USER_QUERY + " WHERE " + USER_USERNAME_COLUMN + " = '%s'";
    //add user
    public static final String ADD_USER_QUERY = "INSERT INTO " + LOGIN_TABLE + " (" + USER_USERNAME_COLUMN + ", " + USER_PASSWORD_COLUMN + ", " + USER_TYPE_COLUMN + ") VALUES ('%s', '%s', '%s')";
    //delete user
    public static final String DELETE_USER_QUERY = "DELETE FROM " + LOGIN_TABLE + " WHERE " + USER_USERNAME_COLUMN + " = '%s'";
    //change user password
    public static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE " + LOGIN_TABLE + " SET " + USER_PASSWORD_COLUMN + " = '%s' WHERE " + USER_USERNAME_COLUMN + " = '%s'";

    // Data Controller Queries
    // use these default queries and SqlInterpolate to create queries
    //search all data
    public static final String SELECT_DATA_QUERY = "SELECT * FROM " + DATA_TABLE;
    //search data by ticket number
    public static final String SELECT_DATA_BY_TICKET_QUERY = SELECT_DATA_QUERY + " WHERE " + TICKET_COLUMN + " = '%s'";
    //search data by user
    public static final String SELECT_DATA_BY_USER_QUERY = SELECT_DATA_QUERY + " WHERE " + USER_COLUMN + " = '%s'";
    //search data by date range
    public static final String SELECT_DATA_BY_RANGE_QUERY = SELECT_DATA_QUERY + " WHERE " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //search data by username and date range
    public static final String SELECT_DATA_BY_RANGE_AND_USER_QUERY = SELECT_DATA_BY_USER_QUERY + " AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //add data
    public static final String ADD_DATA_QUERY = "INSERT INTO " + DATA_TABLE + " (" + USER_COLUMN + ", " + WELLNAME_COLUMN + ", " + DATESTART_COLUMN + ", " + TIMESTART_COLUMN + "," + MILEAGESTART_COLUMN + ", " +
            DESCRIPTION_COLUMN + ", " + MILEAGEEND_COLUMN + ", " + DATEEND_COLUMN + ", " + TIMEEND_COLUMN + ", " + PROJECTNUMBER_COLUMN + ", " + LOCATION_COLUMN + "," + GPSCOORDS_COLUMN + ", " +
            BILLING_COLUMN + ") VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
    //update data
    public static final String UPDATE_DATA_QUERY = "UPDATE " + DATA_TABLE + " SET " + USER_COLUMN + " = '%s', " + WELLNAME_COLUMN + " = '%s', " + DATESTART_COLUMN + " = '%s', " + TIMESTART_COLUMN + " = '%s', " +
            MILEAGESTART_COLUMN + " = '%s', " + DESCRIPTION_COLUMN + " = '%s', " + MILEAGEEND_COLUMN + " = '%s', " + DATEEND_COLUMN + " = '%s', " + TIMEEND_COLUMN + " = '%s', " + PROJECTNUMBER_COLUMN + " = '%s', " +
            LOCATION_COLUMN + " = '%s', " + GPSCOORDS_COLUMN + " = '%s', " + BILLING_COLUMN + " = '%s' WHERE " + TICKET_COLUMN + " = '%s'";
    //delete data
    public static final String DELETE_DATA_QUERY = "DELETE FROM " + DATA_TABLE + " WHERE " + TICKET_COLUMN + " = '%s'";

    // Billing Controller Queries
    // use these default queries and SqlInterpolate to create queries
    //search all "created" data
    public static final String SELECT_CREATED_DATA_QUERY = "SELECT * FROM " + DATA_TABLE + " WHERE " + BILLING_STATE_COLUMN + " = '" + BILLING_STATE_CREATED + "' OR " + BILLING_STATE_NOT_SET;
    //search "created" data by user
    public static final String SELECT_CREATED_DATA_BY_USER_QUERY = SELECT_CREATED_DATA_QUERY + " AND " + USER_COLUMN + " = '%s'";
    //search "created" data by date range
    public static final String SELECT_CREATED_DATA_BY_RANGE_QUERY = SELECT_CREATED_DATA_QUERY + " AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //search "created" data by user and date range
    public static final String SELECT_CREATED_DATA_BY_RANGE_AND_USER_QUERY = SELECT_CREATED_DATA_QUERY + " AND " + USER_COLUMN + "  = '%s' AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //search "created data by project
    public static final String SELECT_CREATED_DATA_BY_PROJECT_QUERY = SELECT_CREATED_DATA_QUERY + " AND " + PROJECTNUMBER_COLUMN + " = '%s'";
    //search "created" data by user and project
    public static final String SELECT_CREATED_DATA_BY_USER_AND_PROJECT_QUERY = SELECT_CREATED_DATA_BY_USER_QUERY + " AND " + PROJECTNUMBER_COLUMN + " = '%s'";
    //search "created data by project and date range
    public static final String SELECT_CREATED_DATA_BY_PROJECT_AND_DATE_RANGE_QUERY = SELECT_CREATED_DATA_BY_PROJECT_QUERY + "AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //search "created" data by user, project, and date range
    public static final String SELECT_CREATED_DATA_BY_USER_DATE_RANGE_AND_PROJECT_QUERY = SELECT_CREATED_DATA_BY_RANGE_AND_USER_QUERY + " AND " + PROJECTNUMBER_COLUMN + " = '%s'";
}
