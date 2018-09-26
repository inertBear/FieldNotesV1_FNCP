package com.devhunter.fncp.mvc.controller.sql.billing.statemachine;

import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.sql.FNDataController;
import com.devhunter.fncp.mvc.controller.sql.billing.FNBillingController;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.utilities.FNUtil;

import java.util.ArrayList;

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
        FNBillingController billCon = new FNBillingController();
        ArrayList<FieldNote> fieldNotes = billCon.searchNullStates();
        if (!fieldNotes.isEmpty()) {
            for (FieldNote each : fieldNotes) {
                if (each.getBillingState() == null) {
                    each.setBillingState(FNSqlConstants.BILLING_STATE_NOT_SET);
                }
                advanceState(each);
            }
        }
    }

    /**
     * Advance the BillingState of a FieldNote
     */
    public void advanceState(FieldNote fieldNote) {
        //check current state
        switch (fieldNote.getBillingState()) {
            case FNSqlConstants.BILLING_STATE_CREATED:
                fieldNote.setBillingState(FNSqlConstants.BILLING_STATE_BILLED);
                break;
            case FNSqlConstants.BILLING_STATE_BILLED:
                fieldNote.setBillingState(FNSqlConstants.BILLING_STATE_COMPLETE);
                break;
            case FNSqlConstants.BILLING_STATE_COMPLETE:
                break;
            case FNSqlConstants.BILLING_STATE_NOT_SET:
            default:
                fieldNote.setBillingState(FNSqlConstants.BILLING_STATE_CREATED);
                break;
        }
        //update FieldNote
        fieldNote.setDescription(FNUtil.allowApostrophe(fieldNote.getDescription()));
        FNDataController dataCon = new FNDataController();
        dataCon.updateFieldNote(fieldNote);
    }

    /**
     * get the state that a FieldNote can move to
     *
     * @param currentState
     * @return BillingState
     */

    public String getNextState(String currentState) {
        switch (currentState) {
            case FNSqlConstants.BILLING_STATE_CREATED:
                return FNSqlConstants.BILLING_STATE_BILLED;
            case FNSqlConstants.BILLING_STATE_BILLED:
                return FNSqlConstants.BILLING_STATE_COMPLETE;
            case FNSqlConstants.BILLING_STATE_COMPLETE:
                return "No states available after 'Complete'";
            default:
                return "Unknown State";
        }
    }
}

