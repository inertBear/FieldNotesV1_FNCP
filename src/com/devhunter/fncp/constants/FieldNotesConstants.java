/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.constants;

/**
 * This class attempts to encapsulate everything that aligns this Version to FieldNotes with a specific entity.
 * The idea is: is FieldNotes needs to be customized for a new entity, the only changes that will need to be made
 * will be in these "CONSTANT" classes.
 *
 * This class specifically holds the database connection data and the approved drop down values for that entity.
 *
 */

public class FieldNotesConstants {
    // entity data
    public static final String ENTITY_NAME = "RHL";
    public static final int ENTITY_ID = 1;
    // Database connection information
    public static final String AWS_DATABASE_ENDPOINT = "fieldnotes-rhl-restore.ckzbugsctcko.us-west-2.rds.amazonaws.com:3306";
    public static final String FIELDNOTES_DATABASE_NAME = "FieldNotes_RHL";
    public static final String FIELDNOTES_DATABASE_USERNAME = "FNAdmin";
    public static final String FIELDNOTES_DATABASE_PASSWORD = "Nashv1ll3$";
    // approved work locations
    public static String[] APPROVED_BILLING_LOCATIONS = {"", "Office", "Field", "Shop"};
    // approved billing codes
    public static String[] APPROVED_BILLING_CODES = {"", "Turnkey", "Billable", "Not Billable"};
    // Login window size Constants
    public static final int lOGIN_PANEL_X_AXIS = 300;
    public static final int lOGIN_PANEL_Y_AXIS = 300;
    // Main Control Panel size Constants
    public static final int MAIN_CONTROL_PANEL_X_AXIS = 700;
    public static final int MAIN_CONTROL_PANEL_Y_AXIS = 850;
    // Control Panel string
    public static String APPLICATION_LOGO_FOLDER = "/images/logo.png";
    public static String APPLICATION_ICON_FOLDER = "/images/icon.png";
    public static String APPLICATION_NAME = "FieldNotes";
    public static String APPLICATION_SUB_NAME = "Control Panel";
    // Login Panel strings
    public static String CRED_USERNAME_LABEL = "Username:";
    public static String CRED_PASSWORD_LABEL = "Password:";
    // User Panel strings
    public static String USER_CONTROLS_BUTTON = "User Controls";
    public static String USER_SEARCH_BUTTON = "Search User";
    public static String USER_ADD_BUTTON = "Add User";
    public static String USER_DELETE_BUTTON = "Delete User";
    public static String USER_EDIT_PASSWORD_BUTTON = "Change Password";
    // Data Panel strings
    public static String DATA_CONTROLS_BUTTON = "Data Controls";
    public static String DATA_SEARCH_BUTTON = "Search Data";
    public static String DATA_ADD_BUTTON = "Add Data";
    public static String DATA_DELETE_BUTTON = "Delete Data";
    public static String DATA_EDIT_BUTTON = "Edit Data";
    // CRUD Search Panel Strings
    public static String CRUD_SEARCH_TICKET_NUMBER = "Ticket Number:";
    // User Maintenance Strings
    public static String USER_NEW_USERNAME_LABEL = "New Username:";
    public static String USER_NEW_PASSWORD_LABEL = "New Password:";
    // FieldNote field strings
    public static String FN_USERNAME_LABEL = "Name:";
    public static String FN_WELLNAME_LABEL = "Well Name:";
    public static String FN_DATE_START_LABEL = "Date Start:";
    public static String FN_DATE_END_LABEL = "Date End:";
    public static String FN_TIME_START_LABEL = "Time Start:";
    public static String FN_TIME_END_LABEL = "Time End:";
    public static String FN_MILEAGE_START_LABEL = "Mileage Start:";
    public static String FN_MILEAGE_END_LABEL = "Mileage End:";
    public static String FN_DESCRIPTION_LABEL = "Description:";
    public static String FN_PROJECT_LABEL = "Project:";
    public static String FN_LOCATION_LABEL = "Location Code:";
    public static String FN_BILLING_LABEL = "Billing Code:";
    public static String FN_GPS_LABEL = "GPS: ";
    // Button Strings
    public static String BUTTON_LOGIN = "Login";
    public static String BUTTON_SUBMIT = "Submit";
    public static String BUTTON_UPDATE = "Update";
    public static String BUTTON_DELETE = "Delete";
    public static String BUTTON_SEARCH = "Search";
    public static String BUTTON_ADD = "Add";
    public static String BUTTON_EXPORT = "Export to Excel";
    // Button File Locations
    public static String BUTTON_SUBMIT_LOCATION = "/images/submitbutton.png";
    public static String BUTTON_SEARCH_LOCATION = "/images/searchbutton.png";
}
