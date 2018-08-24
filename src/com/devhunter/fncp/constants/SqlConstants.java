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
    public static final String ID_COLUMN = "rhl_login_id";
    public static final String USERNAME_COLUMN = "rhl_username";
    public static final String PASSWORD_COLUMN = "rhl_password";
    public static final String USER_TYPE_COLUMN = "rhl_user_type";

    // User Controller Queries
    // use these default queries and SqlInterpolate to create queries
    //search user
    public static final String SELECT_QUERY = "SELECT * FROM " + LOGIN_TABLE + " WHERE " + USERNAME_COLUMN + " = '%s'";
    //search All users
    public static final String SELECT_ALL_QUERY = "SELECT * FROM " + LOGIN_TABLE;
    //add user
    public static final String ADD_USER_QUERY = "INSERT INTO " + LOGIN_TABLE + " (" + USERNAME_COLUMN + ", " + PASSWORD_COLUMN + ", " + USER_TYPE_COLUMN + ") VALUES ('%s', '%s', '%s')";
    //delete user
    public static final String DELETE_USER_QUERY = "DELETE FROM " + LOGIN_TABLE + " WHERE " + USERNAME_COLUMN + " = '%s'";
    //change user password
    public static final String UPDATE_PASSWORD_QUERY = "UPDATE " + LOGIN_TABLE + " SET " + PASSWORD_COLUMN + " = '%s' WHERE " + USERNAME_COLUMN + " = '%s'";
}
