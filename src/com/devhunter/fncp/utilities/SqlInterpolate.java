/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.utilities;

import com.devhunter.fncp.mvc.controller.sql.billing.statemachine.BillingState;
import com.devhunter.fncp.mvc.model.FieldNote;

/**
 * This class combines the variables used in SQL queries with the static queries for easy reuse.
 */
public class SqlInterpolate {

    //TODO: [FNCP-007[ take a list of Strings format until end of list
    //TODO: [FNCP-007] add comments

    public static String interpolate(String queryTemplate, String variable) {
        return String.format(queryTemplate, variable);
    }

    public static String interpolate(String queryTemplate, String variable, String variable2) {
        return String.format(queryTemplate, variable, variable2);
    }

    public static String interpolate(String queryTemplate, String variable, String variable2, String variable3) {
        return String.format(queryTemplate, variable, variable2, variable3);
    }

    public static String interpolate(String queryTemplate, String variable, String variable2, String variable3, String variable4) {
        return String.format(queryTemplate, variable, variable2, variable3, variable4);
    }

    public static String interpolate(String queryTemplate, FieldNote fieldNote) {
        return String.format(queryTemplate, fieldNote.getUserName(), fieldNote.getWellName(), fieldNote.getDateStart(), fieldNote.getTimeStart(), fieldNote.getMileageStart(), fieldNote.getDescription(),
                fieldNote.getMileageEnd(), fieldNote.getDateEnd(), fieldNote.getTimeEnd(), fieldNote.getProject(), fieldNote.getLocation(), fieldNote.getGPSCoords(), fieldNote.getBillingType());
    }

    public static String interpolate(String queryTemplate, FieldNote fieldNote, String ticketNumber) {
        return String.format(queryTemplate, fieldNote.getUserName(), fieldNote.getWellName(), fieldNote.getDateStart(), fieldNote.getTimeStart(), fieldNote.getMileageStart(), fieldNote.getDescription(),
                fieldNote.getMileageEnd(), fieldNote.getDateEnd(), fieldNote.getTimeEnd(), fieldNote.getProject(), fieldNote.getLocation(), fieldNote.getGPSCoords(), fieldNote.getBillingType(),
                fieldNote.getBillingState(), ticketNumber);
    }

    public static String interpolateBilling(String queryTemplate, BillingState state) {
        return String.format(queryTemplate, state.getState());
    }

    public static String interpolateBilling(String queryTemplate, BillingState state, String variable1) {
        return String.format(queryTemplate, state.getState(), variable1);
    }

    public static String interpolateBilling(String queryTemplate, BillingState state, String variable1, String variable2) {
        return String.format(queryTemplate, state.getState(), variable1, variable2);
    }

    public static String interpolateBilling(String queryTemplate, BillingState state, String variable1, String variable2, String variable3) {
        return String.format(queryTemplate, state.getState(), variable1, variable2, variable3);
    }

    public static String interpolateBilling(String queryTemplate, BillingState state, String variable1, String variable2, String variable3, String variable4) {
        return String.format(queryTemplate, state.getState(), variable1, variable2, variable3, variable4);
    }
}
