/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.constants;

/**
 * This class attempts to encapsulate everything that aligns this Version to FieldNotes with a specific entity.
 * The idea is: if FieldNotes needs to be customized for a new entity, the only changes that will need to be made
 * will be in these "CONSTANT" classes.
 * <p>
 * This class specifically holds the entity-specific database table names and columns..
 */
public class SqlConstants {

    // database table: data
    public static final String TICKET_COLUMN = "ticketNumber";
    public static final String USER_COLUMN = "userName";
    public static final String WELLNAME_COLUMN = "wellName";
    public static final String DATESTART_COLUMN = "dateStart";
    public static final String TIMESTART_COLUMN = "timeStart";
    public static final String MILEAGESTART_COLUMN = "mileageStart";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String MILEAGEEND_COLUMN = "mileageEnd";
    public static final String DATEEND_COLUMN = "dateEnd";
    public static final String TIMEEND_COLUMN = "timeEnd";
    public static final String PROJECTNUMBER_COLUMN = "projectNumber";
    public static final String LOCATION_COLUMN = "location";
    public static final String GPSCOORDS_COLUMN = "gps";
    public static final String BILLING_COLUMN = "billing";

    // database table: user
    private static final String LOGIN_TABLE = "rhl_login";
    public static final String USER_ID_COLUMN = "rhl_login_id";
    public static final String USER_USERNAME_COLUMN = "rhl_username";
    public static final String USER_PASSWORD_COLUMN = "rhl_password";
    public static final String USER_TYPE_COLUMN = "rhl_user_type";
    //database table: data
    private static final String DATA_TABLE = "data";

    // User Controller Queries
    // use these default queries and SqlInterpolate to create queries
    //search user
    public static final String SELECT_USER_QUERY = "SELECT * FROM " + LOGIN_TABLE + " WHERE " + USER_USERNAME_COLUMN + " = '%s'";
    //search All users
    public static final String SELECT_ALL_USER_QUERY = "SELECT * FROM " + LOGIN_TABLE;
    //add user
    public static final String ADD_USER_QUERY = "INSERT INTO " + LOGIN_TABLE + " (" + USER_USERNAME_COLUMN + ", " + USER_PASSWORD_COLUMN + ", " + USER_TYPE_COLUMN + ") VALUES ('%s', '%s', '%s')";
    //delete user
    public static final String DELETE_USER_QUERY = "DELETE FROM " + LOGIN_TABLE + " WHERE " + USER_USERNAME_COLUMN + " = '%s'";
    //change user password
    public static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE " + LOGIN_TABLE + " SET " + USER_PASSWORD_COLUMN + " = '%s' WHERE " + USER_USERNAME_COLUMN + " = '%s'";

    //Data Controller Queries
    //use these default queries and SqlInterpolate to create queries
    //search data
    public static final String SELECT_ALL_DATA_QUERY = "SELECT * FROM " + DATA_TABLE;
    //seatch data by ticket number
    public static final String SELECT_DATA_BY_TICKET = "SELECT * FROM " + DATA_TABLE + " WHERE " + TICKET_COLUMN + " = '%s'";
    //search data by user
    public static final String SELECT_DATA_BY_USER_QUERY = "SELECT * FROM " + DATA_TABLE + " WHERE " + USER_COLUMN + " = '%s'";
    //search data by date range
    public static final String SELECT_DATA_BY_RANGE_QUERY = "SELECT * FROM " + DATA_TABLE + " WHERE " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //search data by username and date range
    public static final String SELECT_DATA_BY_RANGE_AND_USER_QUERY = "SELECT * FROM " + DATA_TABLE + " WHERE " + USER_COLUMN + "  = '%s' AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
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
}
