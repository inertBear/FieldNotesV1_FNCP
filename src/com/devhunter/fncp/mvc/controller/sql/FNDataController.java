/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.queries.FNDataQueries;
import com.devhunter.fncp.mvc.controller.FNController;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.SqlInterpolate;

import javax.swing.*;
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

    /**
     * search for all FieldNotes
     *
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchAllData() {

        final String selectQuery = FNDataQueries.SELECT_DATA_QUERY;
        return searchData(selectQuery);
    }

    /**
     * search for all the FieldNotes with a username
     *
     * @param userName
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchDataByUsername(String userName) {

        final String selectQuery = SqlInterpolate.interpolate(FNDataQueries.SELECT_DATA_BY_USER_QUERY, userName);
        return searchData(selectQuery);
    }

    /**
     * search for all the FieldNotes within a date range
     *
     * @param startDate
     * @param endDate
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchDataByDateRange(String startDate, String endDate) {

        final String selectQuery = SqlInterpolate.interpolate(FNDataQueries.SELECT_DATA_BY_RANGE_QUERY, startDate, endDate);
        return searchData(selectQuery);
    }

    /**
     * search for FieldNotes with a username within a date range
     *
     * @param username  username
     * @param startDate startDate
     * @param endDate   endDate
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchDataByUserAndDateRange(String username, String startDate, String endDate) {

        final String selectQuery = SqlInterpolate.interpolate(FNDataQueries.SELECT_DATA_BY_RANGE_AND_USER_QUERY, username, startDate, endDate);
        return searchData(selectQuery);
    }

    /**
     * search for a FieldNote with a Ticket Number
     *
     * @param ticketNumber
     * @return FieldNote fieldNote
     */
    public FieldNote searchDataByTicketNumber(String ticketNumber) {

        final String selectQuery = SqlInterpolate.interpolate(FNDataQueries.SELECT_DATA_BY_TICKET_QUERY, ticketNumber);
        return searchData(selectQuery).get(0);
    }

    /**
     * Add a FieldNote
     *
     * @param fieldNote fieldNote
     * @return boolean
     */
    public boolean addFieldNote(FieldNote fieldNote) {

        final String addQuery = SqlInterpolate.interpolate(FNDataQueries.ADD_DATA_QUERY, fieldNote);
        return addData(addQuery);
    }

    /**
     * Update a FieldNote
     *
     * @param fieldNote
     * @return boolean
     */
    public boolean updateFieldNote(FieldNote fieldNote) {
        final String updateQuery = SqlInterpolate.interpolate(FNDataQueries.UPDATE_DATA_QUERY, fieldNote, fieldNote.getTicketNumber());
        return updateData(updateQuery);
    }

    /**
     * Update a list of FieldNotes
     *
     * @param fieldNotes
     * @return boolean
     */
    public boolean updateFieldNote(ArrayList<FieldNote> fieldNotes) {
        boolean wasSuccessful;
        for (FieldNote each : fieldNotes) {
            wasSuccessful = updateFieldNote(each);
            if (!wasSuccessful) {
                JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                        "Syntax Error in Ticket Number " + each.getTicketNumber() + ". It has not been updated");
                return false;
            }
        }
        return true;
    }

    /**
     * delete a fieldNote
     *
     * @param ticketNumber
     * @return boolean
     */
    public boolean deleteFieldNote(String ticketNumber) {
        final String deleteQuery = SqlInterpolate.interpolate(FNDataQueries.DELETE_DATA_QUERY, ticketNumber);
        return deleteData(deleteQuery);
    }
}