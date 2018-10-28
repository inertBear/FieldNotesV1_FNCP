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

    // JSON Data tags
    public static final String PRODUCT_KEY_TAG = "customerKey";
    public static final String TICKET_NUMBER_TAG = "ticketNumber";
    public static final String USERNAME_TAG = "userName";
    public static final String WELLNAME_TAG = "wellName";
    public static final String DATE_START_TAG = "dateStart";
    public static final String TIME_START_TAG = "timeStart";
    public static final String MILEAGE_START_TAG = "mileageStart";
    public static final String DESCRIPTION_TAG = "description";
    public static final String MILEAGE_END_TAG = "mileageEnd";
    public static final String DATE_END_TAG = "dateEnd";
    public static final String TIME_END_TAG = "timeEnd";
    public static final String PROJECT_NUMBER_TAG = "projectNumber";
    public static final String LOCATION_TAG = "location";
    public static final String GPS_TAG = "gps";
    public static final String BILLING_TAG = "billing";
    public static final String BILLING_STATE_TAG = "state";

    // JSON User tags
    public static final String USER_USER_ID_TAG = "userId";
    public static final String USER_USERNAME_TAG = "userUserName";
    public static final String USER_PASSWORD_TAG = "userPassword";
    public static final String USER_TYPE_TAG = "userType";

    // JSON response tags
    public static final String RESPONSE_STATUS_TAG = "status";
    public static final String RESPONSE_STATUS_SUCCESS = "success";
    public static final String RESPONSE_STATUS_FAILURE = "failure";
    public static final String RESPONSE_MESSAGE_TAG = "message";

    // Web Service URLs
    public static final String REGISTER_URL = "http://www.fieldnotesfn.com/FNA_test/FNA_register.php";
    public static final String LOGIN_URL = "http://www.fieldnotesfn.com/FNA_test/FNA_login.php";
    public static final String CONFIRM_ADMIN_TYPE_URL = "http://www.fieldnotesfn.com/FNA_test/FN_confirmAdmin.php";
    public static final String ADD_NOTE_URL = "http://www.fieldnotesfn.com/FNA_test/FNA_addNote.php";
    public static final String SEARCH_NOTES_URL = "http://www.fieldnotesfn.com/FNA_test/FN_searchNotes.php";
    public static final String UPDATE_NOTE_URL = "http://www.fieldnotesfn.com/FNA_test/FNA_updateNote.php";
    public static final String DELETE_NOTE_URL = "http://www.fieldnotesfn.com/FNA_test/FN_deleteNote.php";
    public static final String ADD_USER_URL = "http://www.fieldnotesfn.com/FNA_test/FN_addUser.php";
    public static final String SEARCH_USERS_URL = "http://www.fieldnotesfn.com/FNA_test/FN_searchUsers.php";
    public static final String UPDATE_PASSWORD_URL = "http://www.fieldnotesfn.com/FNA_test/FN_changePassword.php";
    public static final String DELETE_USER_URL = "http://www.fieldnotesfn.com/FNA_test/FN_deleteUser.php";

    // HTTP Request Methods
    public static final String HTTP_REQUEST_METHOD_POST = "POST";
    public static final String HTTP_REQUEST_METHOD_GET = "GET";

    // Database tables
    public static final String USER_TABLE = "User";
    public static final String DATA_TABLE = "Data";

    // Database table: state column
    public static final String BILLING_STATE_IS_NULL = BILLING_STATE_TAG + " IS NULL";
    public static final String BILLING_STATE_NOT_SET = "not set";
    public static final String BILLING_STATE_CREATED = "created";
    public static final String BILLING_STATE_BILLED = "billed";
    public static final String BILLING_STATE_COMPLETE = "complete";

    public static final String IS_BILLABLE = "Billable";
}
