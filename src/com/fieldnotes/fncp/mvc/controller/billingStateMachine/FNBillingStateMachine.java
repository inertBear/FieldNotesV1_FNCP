/**
 * ? 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.controller.billingStateMachine;

import com.fieldnotes.fncp.constants.FNPConstants;
import com.fieldnotes.fncp.mvc.controller.FNDataService;
import com.fieldnotes.fncp.mvc.model.FNNote;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
import com.fieldnotes.fncp.mvc.controller.services.FNSessionService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

import static com.fieldnotes.fncp.constants.FNPConstants.*;

public class FNBillingStateMachine {

    public static FNBillingStateMachine sInstance;

    private FNBillingStateMachine() {
    }

    public static FNBillingStateMachine getInstance() {
        if (sInstance == null) {
            sInstance = new FNBillingStateMachine();
        }
        return sInstance;
    }

    /**
     * Updates the billing state of all uninitialized FieldNotes to "created".
     */
    public void initializeStates() {
        JSONObject searchResponse = FNDataService.searchFieldNotes(null, null, null, null);
        String status = searchResponse.getString(RESPONSE_STATUS_TAG);
        String messageString = searchResponse.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            JSONArray messageArray = new JSONArray(messageString);

            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject message = messageArray.getJSONObject(i);

                // get current state
                Object state = message.get(BILLING_STATE_TAG);
                // if no state
                if (state == JSONObject.NULL || state.equals("")) {
                    message.put(BILLING_STATE_TAG, BILLING_STATE_NOT_SET);
                    // create update state
                    FNNote note = FNSessionService.buildNote(message);
                    // advance state
                    advanceState(note);
                }
            }
        }
    }

    /**
     * Advance the BillingState of a FNNote
     */
    public void advanceState(FNNote note) {
        //check current state
        switch (note.getBillingState()) {
            case FNPConstants.BILLING_STATE_CREATED:
                note.setBillingState(FNPConstants.BILLING_STATE_BILLED);
                break;
            case FNPConstants.BILLING_STATE_BILLED:
                note.setBillingState(FNPConstants.BILLING_STATE_COMPLETE);
                break;
            case FNPConstants.BILLING_STATE_COMPLETE:
                break;
            case FNPConstants.BILLING_STATE_NOT_SET:
            default:
                note.setBillingState(FNPConstants.BILLING_STATE_CREATED);
                break;
        }
        note.setDescription(FNSessionService.allowApostrophe(note.getDescription()));

        JSONObject updateResponse = FNDataService.updateFieldNote(note);
        String status = updateResponse.getString(RESPONSE_STATUS_TAG);
        String messageString = updateResponse.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_FAILURE)) {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
        }
    }

    /**
     * get the state that a FNNote can move to
     *
     * @param currentState
     * @return BillingState
     */
    public String getNextState(String currentState) {
        switch (currentState) {
            case FNPConstants.BILLING_STATE_CREATED:
                return FNPConstants.BILLING_STATE_BILLED;
            case FNPConstants.BILLING_STATE_BILLED:
                return FNPConstants.BILLING_STATE_COMPLETE;
            case FNPConstants.BILLING_STATE_COMPLETE:
                return "No states available after 'Complete'";
            default:
                return "Unknown State";
        }
    }
}