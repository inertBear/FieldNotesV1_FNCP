/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.queries.FNUserQueries;
import com.devhunter.fncp.mvc.controller.FNController;
import com.devhunter.fncp.mvc.model.FNUser;
import com.devhunter.fncp.utilities.SqlInterpolate;

import java.util.ArrayList;

/**
 * This class holds the methods for all the user changes from and into the
 * database. Using these methods users can Add, Delete, Search and Change
 * Passwords for FieldNotes users. The methods housed here were only intended to
 * relate to the actual storing, alteration, and retrieving of FNUser
 * data.
 */
public class FNUserController extends FNController {

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
     * update a registered user's password
     *
     * @param user (contains the CURRENT user and NEW password)
     * @return
     */
    public int updatePassword(FNUser user) {
        //TODO: [FNCP-024] substitute responseCode for Tuple<boolean, message>
        FNUser entity = searchUsersByUsername(user.getUsername());
        int responseCode;

        if (entity.getUsername() != null) {
            String updateQuery = SqlInterpolate.interpolate(FNUserQueries.UPDATE_USER_PASSWORD_QUERY, user.getPassword(), user.getUsername());
            responseCode = updatePassword(updateQuery);
        } else {
            //then the user doesn't exist
            responseCode = 2;
        }
        return responseCode;
    }
}