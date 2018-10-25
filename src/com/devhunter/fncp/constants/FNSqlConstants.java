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
public class FNSqlConstants {

    // database table: data
    public static final String DATA_TABLE = "data";
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
    public static final String BILLING_STATE_COLUMN = "state";

    //database table: date: state column
    public static final String BILLING_STATE_IS_NULL = BILLING_STATE_COLUMN + " IS NULL";
    public static final String BILLING_STATE_NOT_SET = "not set";
    public static final String BILLING_STATE_CREATED = "created";
    public static final String BILLING_STATE_BILLED = "billed";
    public static final String BILLING_STATE_COMPLETE = "complete";

    public static final String IS_BILLABLE = "Billable";
}
