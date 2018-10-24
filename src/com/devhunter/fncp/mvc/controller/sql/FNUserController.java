/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.queries.FNUserQueries;
import com.devhunter.fncp.mvc.controller.FNController;
import com.devhunter.fncp.mvc.controller.JsonParser;
import com.devhunter.fncp.mvc.model.FNUser;
import com.devhunter.fncp.utilities.FNUtil;
import com.devhunter.fncp.utilities.SqlInterpolate;
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
public class FNUserController extends FNController {

    private static JsonParser mJsonParser = new JsonParser();

    public FNUserController() {
        super();
    }

    /**
     * search for all users within FieldNotes.
     *
     * @return ArrayList<FNUser>
     */
    public ArrayList<FNUser> searchAllUsers() {

        String selectQuery = FNUserQueries.SELECT_ALL_USER_QUERY;
        return searchUser(selectQuery);
    }

    /**
     * search for a specific user within FieldNotes.
     *
     * @param username to search for
     * @return ArrayList<FnEntity>
     */
    public FNUser searchUsersByUsername(String username) {

        String selectQuery = SqlInterpolate.interpolate(FNUserQueries.SELECT_USER_QUERY, username);
        return searchUser(selectQuery).get(0);
    }

    /**
     * add a user to FieldNotes
     *
     * @param user
     * @return
     */
    public int addUser(FNUser user) {
        //TODO: [FNCP-024] substitute responseCode for Tuple<boolean, message>
        FNUser entity = searchUsersByUsername(user.getUsername());
        int responseCode;

        if (entity.getUsername() != null) {
            //then the user already exists
            responseCode = 2;
        } else {
            String addQuery = SqlInterpolate.interpolate(FNUserQueries.ADD_USER_QUERY, user.getUsername(), user.getPassword(), user.getType());
            responseCode = addUser(addQuery);
        }
        return responseCode;
    }

    /**
     * delete a registered user from FieldNotes
     *
     * @param user
     * @return
     */
    public int deleteUser(FNUser user) {
        //TODO: [FNCP-024] substitute responseCode for Tuple<boolean, message>
        FNUser entity = searchUsersByUsername(user.getUsername());
        int responseCode;

        if (entity.getUsername() != null) {
            String deleteQuery = SqlInterpolate.interpolate(FNUserQueries.DELETE_USER_QUERY, user.getUsername());
            responseCode = deleteUser(deleteQuery);
        } else {
            //then the user doesn't exist
            responseCode = 2;
        }
        return responseCode;
    }

    /**
     * update a user's password
     *
     * @param username user to udpate
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
        final String searchUrl = "http://www.fieldnotesfn.com/FNA_test/FN_searchUser.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        JSONObject response = mJsonParser.createHttpRequest(searchUrl, "POST", params);

        //retrieve values from response
        String status = response.getString("status");
        String message = response.getString("message");

        if(status.equals("success")){
            FNUtil.getInstance().setCurrentUserType(message);
            return message.equals("admin");
        }
        return false;
    }
}