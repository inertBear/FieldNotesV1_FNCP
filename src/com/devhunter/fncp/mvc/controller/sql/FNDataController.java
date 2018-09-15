/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.FNQueries;
import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.FNBridgeService;
import com.devhunter.fncp.mvc.controller.FNController;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.utilities.FNUtil;
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
public class FNDataController extends FNController {

    public FNDataController() {
        super();
    }

   //TODO: [FNCP-007] update comments

    /**
     * Used when searching for all FieldNotes stored in the Database
     *
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> mySQLSearchData() {

        final String selectQuery = FNQueries.SELECT_DATA_QUERY;
        return search(selectQuery);
    }

    /**
     * Used when searching for all the FieldNotes Created by a specific @param
     * userName
     *
     * @param userName
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> mySQLSearchData(String userName) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_DATA_BY_USER_QUERY, userName);
        return search(selectQuery);
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

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_DATA_BY_RANGE_QUERY, startDate, endDate);
        return search(selectQuery);
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

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_DATA_BY_RANGE_AND_USER_QUERY, username, startDate, endDate);
        return search(selectQuery);
    }

    /**
     * Inserts a FieldNote into the database using an SQL query. Returns a ResponseCode to signal success or failure
     *
     * @param fieldNote fieldNote
     * @return int responseCode, confirms or rejects the added FieldNote
     */
    public boolean addFieldNote(FieldNote fieldNote) {

        final String addQuery = SqlInterpolate.interpolate(FNQueries.ADD_DATA_QUERY, fieldNote);
        return add(addQuery);
    }

    /**
     * search for a FieldNote in the database by Ticket number
     *
     * @param ticketNumber
     * @return FieldNote fieldNote
     */
    public FieldNote mySQLSearchDataByTicketNumber(String ticketNumber) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_DATA_BY_TICKET_QUERY, ticketNumber);
        return search(selectQuery).get(0);
    }

    /**
     * Updates a FieldNote in the database using an SQL query. Returns a ResponseCode to signal success or failure
     *
     * @param fieldNote
     * @return int newDataResponse Code, confirms or rejects the added FiedlNote
     */
    public boolean updateFieldNote(FieldNote fieldNote, String ticketNumber) {

        final String updateQuery = SqlInterpolate.interpolate(FNQueries.UPDATE_DATA_QUERY, fieldNote, ticketNumber);
        return update(updateQuery);
    }

    /**
     * delete a fieldNote from the database with an SQL query. Returns repsonse code to signal success or failure
     *
     * @param ticketNumber
     * @return int reponseCode
     */
    public boolean deleteFieldNote(String ticketNumber) {

        final String deleteQuery = SqlInterpolate.interpolate(FNQueries.DELETE_DATA_QUERY, ticketNumber);
        return delete(deleteQuery);
    }
}