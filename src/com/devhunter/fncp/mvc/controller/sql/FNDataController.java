/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.queries.FNDataQueries;
import com.devhunter.fncp.mvc.controller.FNController;
import com.devhunter.fncp.mvc.controller.JsonParser;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;
import com.devhunter.fncp.utilities.SqlInterpolate;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the methods for all the data changes from and into the
 * database. Using these methods users can Add, Delete, Update, and Search for
 * individual FieldNotes. The methods housed here were only intended to relate
 * to the actual storing, alteration, and retrieving of FieldNote data.
 */
public class FNDataController extends FNController {

    private static JsonParser mJsonParser = new JsonParser();

    public FNDataController() {
        super();
    }

    /**
     * Search for FieldNotes
     *
     * @param username  may be empty
     * @param dateStart may only be empty if dateEnd is also empty
     * @param dateEnd   may only be empty if dateStart is also empty
     * @return JSONObject that contains the search results
     */

    public static JSONObject searchFieldNotes(String username, String dateStart, String dateEnd) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FN_searchNotes.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("userName", username));
        params.add(new BasicNameValuePair("dateStart", dateStart));
        params.add(new BasicNameValuePair("dateEnd", dateEnd));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
    }

    /**
     * Add a FieldNote
     *
     * @param fieldNote fieldNote
     * @return boolean
     */
    public static JSONObject addFieldNote(FieldNote fieldNote) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FNA_search.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("userName", fieldNote.getUserName()));
        params.add(new BasicNameValuePair("wellName", fieldNote.getWellName()));
        params.add(new BasicNameValuePair("timeStart", fieldNote.getTimeStart()));
        params.add(new BasicNameValuePair("timeEnd", fieldNote.getTimeEnd()));
        params.add(new BasicNameValuePair("dateStart", fieldNote.getDateStart()));
        params.add(new BasicNameValuePair("dateEnd", fieldNote.getDateEnd()));
        params.add(new BasicNameValuePair("mileageStart", fieldNote.getMileageStart()));
        params.add(new BasicNameValuePair("mileageEnd", fieldNote.getMileageEnd()));
        params.add(new BasicNameValuePair("description", fieldNote.getDescription()));
        params.add(new BasicNameValuePair("projectNumber", fieldNote.getProject()));
        params.add(new BasicNameValuePair("location", fieldNote.getLocation()));
        params.add(new BasicNameValuePair("billing", fieldNote.getBillingType()));
        params.add(new BasicNameValuePair("gps", fieldNote.getGPSCoords()));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
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