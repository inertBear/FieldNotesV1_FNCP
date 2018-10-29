/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.controller;

import com.fieldnotes.fncp.mvc.controller.billingStateMachine.BillingState;
import com.fieldnotes.fncp.mvc.model.FieldNote;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.fieldnotes.fncp.constants.FNPConstants.*;

/**
 * This class holds the methods for all the data changes from and into the
 * database. Using these methods users can Add, Delete, Update, and Search for
 * individual or collections of FieldNotes.
 * <p>
 * NOTE: The methods housed here were only intended to relate
 * to the actual storing, alteration, and retrieving of FieldNote data.
 */
public class FNDataController {

    private static JsonParser mJsonParser = new JsonParser();

    public FNDataController() {
    }

    /**
     * Search for FieldNotes, or for a single FieldNote (if a ticketNumber is not null)
     *
     * @param username     may be null
     * @param dateStart    should only be null if dateEnd is also empty
     * @param dateEnd      should only be null if dateStart is also empty
     * @param ticketNumber may be null and should only have a value is searching for a specific (single result) FieldNote
     * @return JSONObject that contains the search 'status' and 'message'- which contains the results
     */
    public static JSONObject searchFieldNotes(String username, String dateStart, String dateEnd, String ticketNumber) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USERNAME_TAG, username));
        params.add(new BasicNameValuePair(DATE_START_TAG, dateStart));
        params.add(new BasicNameValuePair(DATE_END_TAG, dateEnd));
        params.add(new BasicNameValuePair(TICKET_NUMBER_TAG, ticketNumber));

        // make HTTP connection
        return mJsonParser.createHttpRequest(SEARCH_NOTES_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * Search for FieldNotes by billing state
     *
     * @param state         to search for
     * @param projectNumber to search for, can be null. results in searching all Projects
     * @param username      to search for, can be null. results in searching ALL users
     * @param dateStart     to search for, can be null if dateEnd = null.
     * @param dateEnd       to search for, can be null if dateStart = null.
     *                      <p>
     *                      NOTE: if both dateStart and dateEnd are null, results in search all dates
     * @return JSONObject with search results
     */
    public static JSONObject searchFieldNotes(BillingState state, String projectNumber, String username, String dateStart, String dateEnd) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(BILLING_STATE_TAG, state.getState()));
        params.add(new BasicNameValuePair(USERNAME_TAG, username));
        params.add(new BasicNameValuePair(PROJECT_NUMBER_TAG, projectNumber));
        params.add(new BasicNameValuePair(DATE_START_TAG, dateStart));
        params.add(new BasicNameValuePair(DATE_END_TAG, dateEnd));

        // make HTTP connection
        return mJsonParser.createHttpRequest(SEARCH_BILLING_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * Add a FieldNote
     *
     * @param fieldNote that contains the data to add to the database
     * @return JSONObject that contains add 'status' and 'message'
     */
    public static JSONObject addFieldNote(FieldNote fieldNote) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USERNAME_TAG, fieldNote.getUserName()));
        params.add(new BasicNameValuePair(WELLNAME_TAG, fieldNote.getWellName()));
        params.add(new BasicNameValuePair(TIME_START_TAG, fieldNote.getTimeStart()));
        params.add(new BasicNameValuePair(TIME_END_TAG, fieldNote.getTimeEnd()));
        params.add(new BasicNameValuePair(DATE_START_TAG, fieldNote.getDateStart()));
        params.add(new BasicNameValuePair(DATE_END_TAG, fieldNote.getDateEnd()));
        params.add(new BasicNameValuePair(MILEAGE_START_TAG, fieldNote.getMileageStart()));
        params.add(new BasicNameValuePair(MILEAGE_END_TAG, fieldNote.getMileageEnd()));
        params.add(new BasicNameValuePair(DESCRIPTION_TAG, fieldNote.getDescription()));
        params.add(new BasicNameValuePair(PROJECT_NUMBER_TAG, fieldNote.getProject()));
        params.add(new BasicNameValuePair(LOCATION_TAG, fieldNote.getLocation()));
        params.add(new BasicNameValuePair(BILLING_TAG, fieldNote.getBillingType()));
        params.add(new BasicNameValuePair(GPS_TAG, fieldNote.getGPSCoords()));

        // make HTTP connection
        return mJsonParser.createHttpRequest(ADD_NOTE_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * Update a FieldNote
     *
     * @param fieldNote containing the update data and ticket number to update
     * @return JSONObject that contains update 'status' and 'message'
     */
    public static JSONObject updateFieldNote(FieldNote fieldNote) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(TICKET_NUMBER_TAG, fieldNote.getTicketNumber()));
        params.add(new BasicNameValuePair(USERNAME_TAG, fieldNote.getUserName()));
        params.add(new BasicNameValuePair(WELLNAME_TAG, fieldNote.getWellName()));
        params.add(new BasicNameValuePair(TIME_START_TAG, fieldNote.getTimeStart()));
        params.add(new BasicNameValuePair(TIME_END_TAG, fieldNote.getTimeEnd()));
        params.add(new BasicNameValuePair(DATE_START_TAG, fieldNote.getDateStart()));
        params.add(new BasicNameValuePair(DATE_END_TAG, fieldNote.getDateEnd()));
        params.add(new BasicNameValuePair(MILEAGE_START_TAG, fieldNote.getMileageStart()));
        params.add(new BasicNameValuePair(MILEAGE_END_TAG, fieldNote.getMileageEnd()));
        params.add(new BasicNameValuePair(DESCRIPTION_TAG, fieldNote.getDescription()));
        params.add(new BasicNameValuePair(PROJECT_NUMBER_TAG, fieldNote.getProject()));
        params.add(new BasicNameValuePair(LOCATION_TAG, fieldNote.getLocation()));
        params.add(new BasicNameValuePair(BILLING_TAG, fieldNote.getBillingType()));
        params.add(new BasicNameValuePair(GPS_TAG, fieldNote.getGPSCoords()));
        params.add(new BasicNameValuePair(BILLING_STATE_TAG, fieldNote.getBillingState()));

        // make HTTP connection
        return mJsonParser.createHttpRequest(UPDATE_NOTE_URL, HTTP_REQUEST_METHOD_POST, params);
    }

    /**
     * delete a fieldNote
     *
     * @param ticketNumber of the FieldNote to delete
     * @return JSONObject that contains delete 'status' and 'message'
     */
    public static JSONObject deleteFieldNote(String ticketNumber) {
        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(TICKET_NUMBER_TAG, ticketNumber));

        // make HTTP connection
        return mJsonParser.createHttpRequest(DELETE_NOTE_URL, HTTP_REQUEST_METHOD_POST, params);
    }
}