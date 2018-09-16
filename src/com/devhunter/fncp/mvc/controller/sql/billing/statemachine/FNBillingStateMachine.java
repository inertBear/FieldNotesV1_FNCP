package com.devhunter.fncp.mvc.controller.sql.billing.statemachine;

import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.sql.FNDataController;
import com.devhunter.fncp.mvc.controller.sql.billing.FNBillingController;
import com.devhunter.fncp.mvc.model.FieldNote;

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
        FNBillingController conn = new FNBillingController();
        ArrayList<FieldNote> fieldNotes = conn.searchNullStates();
        for (FieldNote each : fieldNotes) {
            each.setBillingState(FNSqlConstants.BILLING_STATE_CREATED);
        }
    }

    public void advanceState(FieldNote fieldNote) {
        //search for ticket by ticket number
        String state = fieldNote.getBillingState();

        if (state.equals(FNSqlConstants.BILLING_STATE_CREATED)) {
            fieldNote.setBillingState(FNSqlConstants.BILLING_STATE_BILLED);
        } else if (state.equals(FNSqlConstants.BILLING_STATE_BILLED)) {
            fieldNote.setBillingState(FNSqlConstants.BILLING_STATE_COMPLETE);
        }
    }
}
