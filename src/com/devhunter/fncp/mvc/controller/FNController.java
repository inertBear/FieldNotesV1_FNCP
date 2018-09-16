/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller;

import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.mvc.model.fnuser.FNEntity;
import com.devhunter.fncp.utilities.FNUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FNController {

    protected Statement mStatement;

    protected FNController() {
        mStatement = FNBridgeService.getInstance().getSQLBridgeStatement();
    }

    /**
     * Search for FieldNotes
     *
     * @param query
     * @return ArrayList<FieldNotes>
     */
    protected ArrayList<FieldNote> searchData(String query) {

        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        try {
            ResultSet resultsSet = mStatement.executeQuery(query);
            fieldNotes = FNUtil.retrieveFieldNotes(resultsSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Search Failed");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("No data found for that value");
        }
        return fieldNotes;
    }

    /**
     * Add a FieldNote
     *
     * @param query
     * @return true is success, false if failure
     */
    protected boolean addData(String query) {
        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update a FieldNote
     *
     * @param query
     * @return true is success, false if failure
     */
    protected boolean updateData(String query) {
        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a FieldNote
     *
     * @param query
     * @return
     */
    protected boolean deleteData(String query) {
        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * search for users.
     *
     * @param query
     * @return ArrayList<FNEntity> allUsers
     */
    protected ArrayList<FNEntity> searchUser(String query) {

        ArrayList<FNEntity> entities = new ArrayList<>();

        try {
            ResultSet resultSet = mStatement.executeQuery(query);
            entities = FNUtil.retrieveUsers(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Search Failed");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("No user data found for that value");
        }
        return entities;
    }

    /**
     * Add a User. Fails if username already exists
     *
     * @param query
     * @return int responseCode, (2 if the UserName already exists, 1 for success, 0
     * for error)
     */
    protected int addUser(String query) {
        //TODO: [FNCP-024] substitute responseCode for Tuple<boolean, message>
        int responseCode;
        try {
            mStatement.executeUpdate(query);
            responseCode = 1;
        } catch (SQLException e) {
            System.out.println("sql exception: Add User Failed");
            responseCode = 0;
        }
        return responseCode;
    }

    /**
     * Deletes a User. Fails if username doesn't exist
     *
     * @param query
     * @return int deleteUserResponseCode, (1 for success, 2 if the user did not
     * exist, 0 for error)
     */
    protected int deleteUser(String query) {
        //TODO: [FNCP-024] substitute responseCode for Tuple<boolean, message>
        int responseCode;
        try {
            mStatement.executeUpdate(query);
            responseCode = 1;
        } catch (SQLException e) {
            e.printStackTrace();
            responseCode = 0;
        }
        return responseCode;
    }

    /**
     * Change the password of a user. Fails if username doesn't exist
     *
     * @param query
     * @return int changePassResponseCode, (1 if success, 2 if UserName does not
     * exist, 0 if error)
     */
    protected int updatePassword(String query) {
        //TODO: [FNCP-024] substitute responseCode for Tuple<boolean, message>
        int responseCode;
        try {
            mStatement.executeUpdate(query);
            responseCode = 1;
        } catch (SQLException e) {
            e.printStackTrace();
            responseCode = 0;
        }
        return responseCode;
    }
}