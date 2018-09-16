package com.devhunter.fncp.mvc.controller.sql.billing.statemachine;

import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.sql.FNDataController;
import com.devhunter.fncp.mvc.controller.sql.billing.FNBillingController;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.utilities.FNUtil;

import java.util.ArrayList;

public class FNBillingStateMachine {

    //TODO: implement FNBillingStateMachine

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
    public boolean initializeStates() {
        FNBillingController billCon = new FNBillingController();
        ArrayList<FieldNote> fieldNotes = billCon.searchNullStates();
        if (!fieldNotes.isEmpty()) {
            for (FieldNote each : fieldNotes) {
                each.setDescription(FNUtil.allowApostrophe(each.getDescription()));
                each.setBillingState(FNSqlConstants.BILLING_STATE_CREATED);
            }
            FNDataController dataCon = new FNDataController();
            return dataCon.updateFieldNote(fieldNotes);
        }
        return true;
    }

    /**
     * Advance the BillingState of a FieldNote
     */
    public boolean advanceState(FieldNote fieldNote) {
        //check current state
        switch (fieldNote.getBillingState()) {
            case FNSqlConstants.BILLING_STATE_CREATED:
                fieldNote.setBillingState(FNSqlConstants.BILLING_STATE_BILLED);
                break;
            case FNSqlConstants.BILLING_STATE_BILLED:
                fieldNote.setBillingState(FNSqlConstants.BILLING_STATE_COMPLETE);
                break;
            default:
                //if not set or completed
                break;
        }
        //update FieldNote
        FNDataController dataCon = new FNDataController();
        return dataCon.updateFieldNote(fieldNote);
    }
}
