/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.utilities;

import com.devhunter.fncp.mvc.controller.billing.statemachine.BillingState;

/**
 * This class combines the variables used in SQL queries with the static queries for easy reuse.
 */
public class SqlInterpolate {

    public static String interpolate(String queryTemplate, String variable) {
        return String.format(queryTemplate, variable);
    }

    public static String interpolate(String queryTemplate, BillingState state) {
        return String.format(queryTemplate, state.getState());
    }

    public static String interpolate(String queryTemplate, BillingState state, String variable1) {
        return String.format(queryTemplate, state.getState(), variable1);
    }

    public static String interpolate(String queryTemplate, BillingState state, String variable1, String variable2) {
        return String.format(queryTemplate, state.getState(), variable1, variable2);
    }

    public static String interpolate(String queryTemplate, BillingState state, String variable1, String variable2, String variable3) {
        return String.format(queryTemplate, state.getState(), variable1, variable2, variable3);
    }

    public static String interpolate(String queryTemplate, BillingState state, String variable1, String variable2, String variable3, String variable4) {
        return String.format(queryTemplate, state.getState(), variable1, variable2, variable3, variable4);
    }
}
