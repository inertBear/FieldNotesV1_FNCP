/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.SqlConstants;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.mvc.model.FieldNote.FieldNoteBuilder;
import com.devhunter.fncp.utilities.SqlInterpolate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class holds the methods for all the data changes from and into the
 * database. Using these methods users can Add, Delete, Update, and Search for
 * individual FieldNotes. The methods housed here were only intended to relate
 * to the actual storing, alteration, and retrieving of FieldNote data.
 */
public class SQLDataController {

    private Statement mStatement;

    public SQLDataController() {
        mStatement = SQLBridgeService.getInstance().getSQLBridgeStatement();
    }

    /**
     * Used when searching for all FieldNotes stored in the Database
     *
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> mySQLSearchData() {

        final String selectDataQuery = SqlConstants.SELECT_ALL_DATA_QUERY;
        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        try {
            ResultSet resultSet = mStatement.executeQuery(selectDataQuery);
            fieldNotes = retrieveFieldNotes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Search Failed");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("No user data found for that value");
        }
        return fieldNotes;
    }

    /**
     * Used when searching for all the FieldNotes Created by a specific @param
     * userName
     *
     * @param userName
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> mySQLSearchData(String userName) {

        final String selectDataQuery = SqlInterpolate.interpolate(SqlConstants.SELECT_DATA_BY_USER_QUERY, userName);
        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        try {
            ResultSet resultSet = mStatement.executeQuery(selectDataQuery);
            fieldNotes = retrieveFieldNotes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Search Failed");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("No user data found for that value");
        }
        return fieldNotes;
    }

    /**
     * Used when searching for all the FieldNotes created within a specific time
     * frame @param dataSearchStartDate to
     *
     * @param startDate
     * @param endDate
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> mySQLSearchDataByDateRange(String startDate, String endDate) {

        final String selectUserQuery = SqlInterpolate.interpolate(SqlConstants.SELECT_DATA_BY_RANGE_QUERY, startDate, endDate);
        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        try {
            ResultSet resultSet = mStatement.executeQuery(selectUserQuery);
            fieldNotes = retrieveFieldNotes(resultSet);

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
     * Used when searching for FieldNotes created within a specific time frame by a
     * specific user
     *
     * @param username  username
     * @param startDate startDate
     * @param endDate   endDate
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> mySQLSearchDataByUserAndDateRange(String username, String startDate, String endDate) {

        final String selectUserQuery = SqlInterpolate.interpolate(SqlConstants.SELECT_DATA_BY_RANGE_AND_USER_QUERY, username, startDate, endDate);
        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        try {
            ResultSet resultsSet = mStatement.executeQuery(selectUserQuery);
            fieldNotes = retrieveFieldNotes(resultsSet);
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
     * Inserts a FieldNote into the database using an SQL query. Returns a ResponseCode to signal success or failure
     *
     * @param fieldNote fieldNote
     * @return int responseCode, confirms or rejects the added FieldNote
     */
    public boolean addFieldNote(FieldNote fieldNote) {

        final String query = SqlInterpolate.interpolate(SqlConstants.ADD_DATA_QUERY, fieldNote);

        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * search for a FieldNote in the database by Ticket number
     *
     * @param ticketNumber
     * @return FieldNote fieldNote
     */
    public FieldNote mySQLSearchDataByTicketNumber(String ticketNumber) {

        final String selectTicketQuery = SqlInterpolate.interpolate(SqlConstants.SELECT_DATA_BY_TICKET, ticketNumber);
        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        try {
            ResultSet resultSet = mStatement.executeQuery(selectTicketQuery);
            fieldNotes = retrieveFieldNotes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fieldNotes.get(0);
    }

    /**
     * Updates a FieldNote in the database using an SQL query. Returns a ResponseCode to signal success or failure
     *
     * @param fieldNote
     * @return int newDataResponse Code, confirms or rejects the added FiedlNote
     */
    public boolean updateFieldNote(FieldNote fieldNote, String ticketNumber) {

        final String query = SqlInterpolate.interpolate(SqlConstants.UPDATE_DATA_QUERY, fieldNote, ticketNumber);

        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * delete a fieldNote from the database with an SQL query. Returns repsonse code to signal success or failure
     *
     * @param ticketNumber
     * @return int reponseCode
     */
    public boolean deleteFieldNote(String ticketNumber) {

        final String deleteDataQuery = SqlInterpolate.interpolate(SqlConstants.DELETE_DATA_QUERY, ticketNumber);

        try {
            mStatement.executeUpdate(deleteDataQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retreive the FieldNotes from within a ResultSet
     *
     * @param resultSet
     * @return ArrayList<FieldNote>
     * @throws SQLException
     */
    private ArrayList<FieldNote> retrieveFieldNotes(ResultSet resultSet) throws SQLException {

        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        if (resultSet != null) {
            while (resultSet.next()) {
                FieldNote fieldNote = new FieldNoteBuilder()
                        .setUserName(resultSet.getString(SqlConstants.USER_COLUMN))
                        .setTicketNumber(resultSet.getString(SqlConstants.TICKET_COLUMN))
                        .setWellName(resultSet.getString(SqlConstants.WELLNAME_COLUMN))
                        .setDateStart(resultSet.getString(SqlConstants.DATESTART_COLUMN))
                        .setTimeStart(resultSet.getString(SqlConstants.TIMESTART_COLUMN))
                        .setMileageStart(resultSet.getString(SqlConstants.MILEAGESTART_COLUMN))
                        .setDescription(resultSet.getString(SqlConstants.DESCRIPTION_COLUMN))
                        .setMileageEnd(resultSet.getString(SqlConstants.MILEAGEEND_COLUMN))
                        .setDateEnd(resultSet.getString(SqlConstants.DATEEND_COLUMN))
                        .setTimeEnd(resultSet.getString(SqlConstants.TIMEEND_COLUMN))
                        .setProjectNumber(resultSet.getString(SqlConstants.PROJECTNUMBER_COLUMN))
                        .setLocation(resultSet.getString(SqlConstants.LOCATION_COLUMN))
                        .setGPSCoords(resultSet.getString(SqlConstants.GPSCOORDS_COLUMN))
                        .setBillingType(resultSet.getString(SqlConstants.BILLING_COLUMN))
                        .build();

                fieldNotes.add(fieldNote);
            }
            resultSet.close();
        } else {
            FieldNote fieldNote = new FieldNote.FieldNoteBuilder().buildEmptyFieldNote();
            fieldNotes.add(fieldNote);
        }
        return fieldNotes;
    }
}