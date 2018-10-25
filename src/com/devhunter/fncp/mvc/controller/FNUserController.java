/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller;

import com.devhunter.fncp.mvc.controller.JsonParser;
import com.devhunter.fncp.utilities.FNUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the methods for all the user changes from and into the
 * database. Using these methods users can Add, Delete, Search and Change
 * Passwords for FieldNotes users. The methods housed here were only intended to
 * relate to the actual storing, alteration, and retrieving of FNUser
 * data.
 */
public class FNUserController {

    private static JsonParser mJsonParser = new JsonParser();

    public FNUserController() {
        super();
    }

    /**
     * search for users within FieldNotes.
     *
     * @return ArrayList<FNUser>
     */
    public static JSONObject searchUsers(String username) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FN_searchUsers.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
    }

    /**
     * add a user to FieldNotes
     *
     * @param username of added user
     * @param password of added user
     * @param type     of added user
     * @return JSONObject that contains the response
     */
    public static JSONObject addUser(String username, String password, String type) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FN_addUser.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
    }

    /**
     * delete a registered user from FieldNotes
     *
     * @param username of user to delete
     * @return JSONObject with response from server
     */
    public static JSONObject deleteUser(String username) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FN_deleteUser.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
    }

    /**
     * update a user's password
     *
     * @param username    user to udpate
     * @param newPassword password to update to
     * @return JSON with update results
     */
    public static JSONObject updatePassword(String username, String newPassword) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FN_changePassword.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", newPassword));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
    }

    /**
     * register to FieldNotes
     *
     * @param productKey to register with
     * @return JSON with registration results
     */
    public static JSONObject register(String productKey) {
        final String REGISTER_URL = "http://www.fieldnotesfn.com/FNA_test/FNA_register.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(REGISTER_URL, "POST", params);
    }

    /**
     * login to FieldNotes
     *
     * @param username user to login with
     * @param password password to login with
     * @return JSON with login results
     */
    public static JSONObject login(String username, String password) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FNA_login.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
    }

    /**
     * check is user has admin access
     *
     * @param username user to login with
     * @return JSON with login results
     */
    public static boolean hasAdminAccess(String username) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String searchUrl = "http://www.fieldnotesfn.com/FNA_test/FN_confirmAdmin.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        JSONObject response = mJsonParser.createHttpRequest(searchUrl, "POST", params);

        //retrieve values from response
        String status = response.getString("status");
        String message = response.getString("message");

        if (status.equals("success")) {
            FNUtil.getInstance().setCurrentUserType(message);
            return message.equals("admin");
        }
        return false;
    }
}