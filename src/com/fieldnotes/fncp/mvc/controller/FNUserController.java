/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.controller;

import com.fieldnotes.fncp.utilities.FNUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.fieldnotes.fncp.constants.FNCPConstants.ADMIN_USER;
import static com.fieldnotes.fncp.constants.FNPConstants.*;

/**
 * This class holds the methods for all the user changes from and into the
 * database. Using these methods users can Add, Delete, Search and Change
 * Passwords for FieldNotes users.
 * <p>
 * NOTE: The methods housed here were only intended to
 * relate to the actual storing, alteration, and retrieving of FNUser
 * data.
 */
public class FNUserController {

    private static JsonParser mJsonParser = new JsonParser();

    public FNUserController() {
    }

    /**
     * search for users within FieldNotes.
     *
     * @param username may be null. If non-null, a a single user will be searched
     * @return JSONObject that contains the search 'status' and 'message'- which contains the results
     */
    public static JSONObject searchUsers(String username) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USER_USERNAME_TAG, username));

        // make HTTP connection
        return mJsonParser.createHttpRequest(SEARCH_USERS_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * add a user to FieldNotes
     *
     * @param username of user to add
     * @param password of user to add
     * @param type     of user to add
     * @return JSONObject that contains the search 'status' and 'message'
     */
    public static JSONObject addUser(String username, String password, String type) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USER_USERNAME_TAG, username));
        params.add(new BasicNameValuePair(USER_PASSWORD_TAG, password));
        params.add(new BasicNameValuePair(USER_TYPE_TAG, type));

        // make HTTP connection
        return mJsonParser.createHttpRequest(ADD_USER_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * delete an user from FieldNotes
     *
     * @param username of user to delete
     * @return JSONObject that contains the delete 'status' and 'message'
     */
    public static JSONObject deleteUser(String username) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USER_USERNAME_TAG, username));

        // make HTTP connection
        return mJsonParser.createHttpRequest(DELETE_USER_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * update a user password
     *
     * @param username    user to update
     * @param newPassword password to update to
     * @return JSONObject that contains the update 'status' and 'message'
     */
    public static JSONObject updatePassword(String username, String newPassword) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USER_USERNAME_TAG, username));
        params.add(new BasicNameValuePair(USER_PASSWORD_TAG, newPassword));

        // make HTTP connection
        return mJsonParser.createHttpRequest(UPDATE_PASSWORD_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * register to FieldNotes
     *
     * @return JSONObject that contains the registration 'status' and 'message'
     */
    public static JSONObject register(String productKey) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(PRODUCT_KEY_TAG, productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(REGISTER_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * login to FieldNotes
     *
     * @param username user to login with
     * @param password password to login with
     * @return JSONObject that contains the login 'status' and 'message'
     */
    public static JSONObject login(String username, String password) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USER_USERNAME_TAG, username));
        params.add(new BasicNameValuePair(USER_PASSWORD_TAG, password));

        // make HTTP connection
        return mJsonParser.createHttpRequest(LOGIN_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * check is user has admin access
     *
     * @param username user to login with
     * @return boolean true, if user is an admin
     * false, if user is anything else
     */
    public static boolean hasAdminAccess(String username) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USER_USERNAME_TAG, username));

        // make HTTP connection
        JSONObject response = mJsonParser.createHttpRequest(CONFIRM_ADMIN_TYPE_URL, HTTP_REQUEST_METHOD_POST, params);

        //retrieve values from response
        String status = response.getString(RESPONSE_STATUS_TAG);
        String message = response.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            FNUtil.getInstance().setCurrentUserType(message);
            return message.equals(ADMIN_USER);
        }
        return false;
    }
}