/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.constants;

import java.io.File;

/**
 * constant values relevant to FNCP
 */
public class FNCPConstants {
    // approved work locations
    public static final String[] APPROVED_BILLING_LOCATIONS = {"", "Office", "Field", "Shop"};
    // approved billing codes
    public static final String[] APPROVED_BILLING_CODES = {"", "Turn-key", "Billable", "Not Billable"};
    // Login window size Constants
    public static final int lOGIN_PANEL_X_AXIS = 300;
    public static final int lOGIN_PANEL_Y_AXIS = 300;
    // Main Control Panel size Constants
    public static final int MAIN_CONTROL_PANEL_X_AXIS = 700;
    public static final int MAIN_CONTROL_PANEL_Y_AXIS = 850;
    // Control Panel string
    public static final String APPLICATION_LOGO_FOLDER = "/images/logo.png";
    public static final String APPLICATION_ICON_FOLDER = "/images/icon.png";
    public static final String APPLICATION_NAME = "FieldNotes";
    public static final String APPLICATION_SUB_NAME = "Control Panel";
    // User Panel strings
    public static final String USER_CONTROLS_BUTTON = "User Controls";
    public static final String USER_SEARCH_BUTTON = "Search User";
    public static final String USER_ADD_BUTTON = "Add User";
    public static final String USER_DELETE_BUTTON = "Delete User";
    public static final String USER_EDIT_PASSWORD_BUTTON = "Change Password";
    // Data Panel strings
    public static final String DATA_CONTROLS_BUTTON = "Data Controls";
    public static final String DATA_SEARCH_BUTTON = "Search Data";
    public static final String DATA_ADD_BUTTON = "Add Data";
    public static final String DATA_DELETE_BUTTON = "Delete Data";
    public static final String DATA_EDIT_BUTTON = "Edit Data";
    // Billing Panel Strings
    public static final String BILLING_CONTROLS_BUTTON = "Billing Controls";
    public static final String UNBILLED_DATA = "Created";
    public static final String BILLED_DATA = "Billed";
    public static final String COMPLETED_DATA = "Completed";
    // CRUD Search Panel Strings
    public static final String CRUD_SEARCH_NOTE_NUMBER = "Note Number:";
    // User Maintenance Strings
    public static final String USER_NEW_USERNAME_LABEL = "New Username:";
    public static final String USER_CURRENT_PASSWORD_LABEL = "Current Password:";
    public static final String USER_NEW_PASSWORD_LABEL = "New Password:";
    public static final String USER_NEW_TOKEN_LABEL = "User Token:";
    public static final String USER_USERNAME_LABEL = "Username:";
    public static final String USER_PASSWORD_LABEL = "Password:";
    public static final String USER_ID_LABEL = "User ID:";
    public static final String USER_USER_TYPE_LABEL = "User Type:";
    public static final String USER_TOKEN_LABEL = "User Token:";
    public static final String ADMIN_USER = "admin";
    public static final String REGULAR_USER = "user";
    public static final String TEST_USER = "test";
    // FNNote field strings
    public static final String FN_NOTE_NUMBER_LABEL = "Note Number:";
    public static final String FN_USERNAME_LABEL = "Name:";
    public static final String FN_WELLNAME_LABEL = "Well Name:";
    public static final String FN_DATE_START_LABEL = "Date Start:";
    public static final String FN_DATE_END_LABEL = "Date End:";
    public static final String FN_TIME_START_LABEL = "Time Start:";
    public static final String FN_TIME_END_LABEL = "Time End:";
    public static final String FN_MILEAGE_START_LABEL = "Mileage Start:";
    public static final String FN_MILEAGE_END_LABEL = "Mileage End:";
    public static final String FN_DESCRIPTION_LABEL = "Description:";
    public static final String FN_PROJECT_LABEL = "Project:";
    public static final String FN_LOCATION_LABEL = "Location Code:";
    public static final String FN_BILLING_LABEL = "Billing Code:";
    public static final String FN_GPS_LABEL = "GPS:";
    public static final String FN_BILLING_STATE_LABEL = "State:";
    // Button Strings
    public static final String BUTTON_LOGIN = "Login";
    public static final String BUTTON_SUBMIT = "Submit";
    public static final String BUTTON_UPDATE = "Update";
    public static final String BUTTON_DELETE = "Delete";
    public static final String BUTTON_SEARCH = "Search";
    public static final String BUTTON_ADD = "Add";
    public static final String BUTTON_REGISTER = "Register";
    public static final String BUTTON_EXPORT = "Export to Excel";
    public static final String BUTTON_CANCEL = "Cancel";
    public static final String BUTTON_CLOSE = "Close";
    // Label Strings
    public static final String AS_USER_TYPE_USER_LABEL = "as User:";
    public static final String AS_USER_TYPE_TEST_LABEL = "as Test:";
    public static final String AS_USER_TYPE_ADMIN_LABEL = "as Admin:";
    public static final String REGISTRATION_LABEL = "Enter your Product key";
    // Billing Strings
    public static final String FN_BILLING_TOTAL_HOURS = "Total Hours:";
    public static final String FN_BILLING_TOTAL_MILEAGE = "Total Mileage:";
    // Button File Locations
    public static final String BUTTON_SUBMIT_LOCATION = "/images/submitbutton.png";
    public static final String BUTTON_SEARCH_LOCATION = "/images/searchbutton.png";
    // Registration File Locations
    private static final String SYSTEM_FILE_PATH = System.getProperty("user.home") + File.separator + "Documents";
    public static final String REGISTRATION_FILE_PATH = SYSTEM_FILE_PATH + "FNCP_product_key.txt";
}