/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.SqlConstants;
import com.devhunter.fncp.mvc.model.FNUser.FNEntity;
import com.devhunter.fncp.utilities.SqlInterpolate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class holds the methods for all the user changes from and into the
 * database. Using these methods users can Add, Delete, Search and Change
 * Passwords for FieldNotes users. The methods housed here were only intended to
 * relate to the actual storing, alteration, and retrieving of FNEntity
 * data.
 */
public class SQLUserController {

    private Statement mStatement;
    private ResultSet mResultSet;

    public SQLUserController() {
        mStatement = SQLBridgeService.getInstance().getSQLBridgeStatement();
    }

    /**
     * Used when searching for a specific user within FieldNotes. Returns an
     * FNEntity of the user searched
     *
     * @param username
     * @return ArrayList<FnEntity> searchResults
     */
    public FNEntity mySQLSearchUser(String username) {

        //TODO: [FNCP-022] create an Entity factory to create the entity type by the String "type" passed in
        FNEntity entity = new FNEntity();
        String query = SqlInterpolate.interpolate(SqlConstants.SELECT_USER_QUERY, username);

        try {
            mResultSet = mStatement.executeQuery(query);

            if (mResultSet != null) {
                if (mResultSet.next()) {
                    // move iterator back to beginning of result set
                    mResultSet.beforeFirst();
                    while (mResultSet.next()) {
                        entity.setID(Integer.parseInt(mResultSet.getString(SqlConstants.USER_ID_COLUMN)));
                        entity.setUsername(mResultSet.getString(SqlConstants.USER_USERNAME_COLUMN));
                        entity.setPassword(mResultSet.getString(SqlConstants.USER_PASSWORD_COLUMN));
                        entity.setType(mResultSet.getString((SqlConstants.USER_TYPE_COLUMN)));
                    }
                }
                mResultSet.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Search Failed");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("No user data found for that value");
        }
        return entity;
    }

    /**
     * Used when search for all users within FieldNotes. The users are returned in
     * an ArrayList containing all the FNEntities
     *
     * @return ArrayList<FNEntity> allUsers
     */
    public ArrayList<FNEntity> mySQLSearchUser() {

        ArrayList<FNEntity> allUsers = new ArrayList<>();
        String query = SqlConstants.SELECT_ALL_USER_QUERY;

        try {
            mResultSet = mStatement.executeQuery(query);

            if (mResultSet != null) {
                while (mResultSet.next()) {
                    // create a new FNUser
                    FNEntity user = new FNEntity();
                    // assign the user data from the result row
                    user.setID(Integer.parseInt(mResultSet.getString(SqlConstants.USER_ID_COLUMN)));
                    user.setUsername(mResultSet.getString(SqlConstants.USER_USERNAME_COLUMN));
                    user.setPassword(mResultSet.getString(SqlConstants.USER_PASSWORD_COLUMN));
                    user.setType(mResultSet.getString(SqlConstants.USER_TYPE_COLUMN));
                    //add this user to the list of users
                    allUsers.add(user);
                }
                mResultSet.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Search Failed");
        }
        return allUsers;
    }


    /**
     * Adds a User to the Database. validates and rejects duplicate UserNames
     *
     * @param user
     * @return int responseCode, (2 if the UserName already exists, 1 for success, 0
     * for error)
     */
    public int addUser(FNEntity user) {

        String searchQuery = SqlInterpolate.interpolate(SqlConstants.SELECT_USER_QUERY, user.getUsername());
        String addQuery = SqlInterpolate.interpolate(SqlConstants.ADD_USER_QUERY, user.getUsername(), user.getPassword(), user.getType());

        int responseCode;
        try {
            mResultSet = mStatement.executeQuery(searchQuery);
            if (mResultSet.next()) {
                responseCode = 2;
            } else {
                mStatement.executeUpdate(addQuery);
                responseCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Add User Failed");
            responseCode = 0;
        }
        return responseCode;
    }

    /**
     * Deletes a FNEntity from the Database. Validates user existence
     *
     * @param user
     * @return int deleteUserResponseCode, (1 for success, 2 if the user did not
     * exist, 0 for error)
     */
    public int deleteUser(FNEntity user) {

        String searchQuery = SqlInterpolate.interpolate(SqlConstants.SELECT_USER_QUERY, user.getUsername());
        String deleteQuery = SqlInterpolate.interpolate(SqlConstants.DELETE_USER_QUERY, user.getUsername());

        int deleteUserResponseCode;
        try {
            mResultSet = mStatement.executeQuery(searchQuery);
            if (mResultSet.next()) {
                mStatement.executeUpdate(deleteQuery);
                deleteUserResponseCode = 1;
            } else {
                deleteUserResponseCode = 2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            deleteUserResponseCode = 0;
        }
        return deleteUserResponseCode;
    }

    /**
     * Change the password of a user in the database. validates and rejects nonexistent UserNames
     *
     * @param user (contains the CURRENT user and NEW password)
     * @return int changePassResponseCode, (1 if success, 2 if UserName does not
     * exist, 0 if error)
     */
    public int updatePassword(FNEntity user) {

        String searchQuery = SqlInterpolate.interpolate(SqlConstants.SELECT_USER_QUERY, user.getUsername());
        String updatePassworQuery = SqlInterpolate.interpolate(SqlConstants.UPDATE_USER_PASSWORD_QUERY, user.getPassword(), user.getUsername());

        int changePassResponseCode;
        try {
            mResultSet = mStatement.executeQuery(searchQuery);
            if (mResultSet.next()) {
                mStatement.executeUpdate(updatePassworQuery);
                changePassResponseCode = 1;
            } else {
                changePassResponseCode = 2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            changePassResponseCode = 0;
        }
        return changePassResponseCode;
    }
}