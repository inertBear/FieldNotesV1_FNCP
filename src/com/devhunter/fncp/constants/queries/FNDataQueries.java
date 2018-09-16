/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.constants.queries;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class FNDataQueries {
    // Data Controller Queries
    // use these default queries and SqlInterpolate to create queries

    //searchData all data
    public static final String SELECT_DATA_QUERY = "SELECT * FROM " + DATA_TABLE;
    //searchData data by ticket number
    public static final String SELECT_DATA_BY_TICKET_QUERY = SELECT_DATA_QUERY + " WHERE " + TICKET_COLUMN + " = '%s'";
    //searchData data by user
    public static final String SELECT_DATA_BY_USER_QUERY = SELECT_DATA_QUERY + " WHERE " + USER_COLUMN + " = '%s'";
    //searchData data by date range
    public static final String SELECT_DATA_BY_RANGE_QUERY = SELECT_DATA_QUERY + " WHERE " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //searchData data by username and date range
    public static final String SELECT_DATA_BY_RANGE_AND_USER_QUERY = SELECT_DATA_BY_USER_QUERY + " AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //addData data
    public static final String ADD_DATA_QUERY = "INSERT INTO " + DATA_TABLE + " (" + USER_COLUMN + ", " + WELLNAME_COLUMN + ", " + DATESTART_COLUMN + ", " + TIMESTART_COLUMN + "," + MILEAGESTART_COLUMN + ", " +
            DESCRIPTION_COLUMN + ", " + MILEAGEEND_COLUMN + ", " + DATEEND_COLUMN + ", " + TIMEEND_COLUMN + ", " + PROJECTNUMBER_COLUMN + ", " + LOCATION_COLUMN + "," + GPSCOORDS_COLUMN + ", " +
            BILLING_COLUMN + ") VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
    //updateData data
    public static final String UPDATE_DATA_QUERY = "UPDATE " + DATA_TABLE + " SET " + USER_COLUMN + " = '%s', " + WELLNAME_COLUMN + " = '%s', " + DATESTART_COLUMN + " = '%s', " + TIMESTART_COLUMN + " = '%s', " +
            MILEAGESTART_COLUMN + " = '%s', " + DESCRIPTION_COLUMN + " = '%s', " + MILEAGEEND_COLUMN + " = '%s', " + DATEEND_COLUMN + " = '%s', " + TIMEEND_COLUMN + " = '%s', " + PROJECTNUMBER_COLUMN + " = '%s', " +
            LOCATION_COLUMN + " = '%s', " + GPSCOORDS_COLUMN + " = '%s', " + BILLING_COLUMN + " = '%s', " + BILLING_STATE_COLUMN + " = '%s' WHERE " + TICKET_COLUMN + " = '%s'";
    //deleteData data
    public static final String DELETE_DATA_QUERY = "DELETE FROM " + DATA_TABLE + " WHERE " + TICKET_COLUMN + " = '%s'";
}
