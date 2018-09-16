/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql.billing;

import com.devhunter.fncp.constants.queries.FNBillingQueries;
import com.devhunter.fncp.mvc.controller.FNController;
import com.devhunter.fncp.mvc.controller.sql.billing.statemachine.BillingState;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.utilities.SqlInterpolate;

import java.util.ArrayList;

public class FNBillingController extends FNController {

    public FNBillingController() {
        super();
    }

    public ArrayList<FieldNote> searchNullStates() {
        final String selectQuery = FNBillingQueries.SELECT_NULL_BILLING_QUERY;
        return searchData(selectQuery);
    }

    /**
     * Used when searching for all FieldNotes stored in the Database where Billing state = "created"
     *
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchAllData(BillingState state) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_QUERY, state);
        return searchData(selectQuery);
    }

    /**
     * Used when searching for all the FieldNotes Created by a specific @param
     * userName where Billing sate = "created"
     *
     * @param userName
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchDataByUsername(BillingState state, String userName) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_BY_USER_QUERY, state, userName);
        return searchData(selectQuery);
    }

    /**
     * @param projectName
     * @return
     */
    public ArrayList<FieldNote> searchDataByProject(BillingState state, String projectName) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_BY_PROJECT_QUERY, state, projectName);
        return searchData(selectQuery);
    }

    /**
     * Used when searching for all the FieldNotes created within a specific time
     * frame @param startDate to endDate, and Billing state = "created"
     *
     * @param startDate
     * @param endDate
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchDataByDateRange(BillingState state, String startDate, String endDate) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_BY_RANGE_QUERY, state, startDate, endDate);
        return searchData(selectQuery);
    }

    /**
     * Used when searching for FieldNotes created within a specific time frame by a
     * specific user, and Billing state = "created"
     *
     * @param username  username
     * @param startDate startDate
     * @param endDate   endDate
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchDataByUsernameAndDateRange(BillingState state, String username, String startDate, String endDate) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_BY_USER_AND_RANGE_QUERY, state, username, startDate, endDate);
        return searchData(selectQuery);
    }

    /**
     * Used when searching for FieldNotes created within a specific time frame by a
     * project name, and Billing state = "created"
     *
     * @param project
     * @param startDate
     * @param endDate
     * @returnArrayList<FieldNote>
     */
    public ArrayList<FieldNote> searchDataByProjectAndDateRange(BillingState state, String project, String startDate, String endDate) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_BY_PROJECT_AND_DATE_RANGE_QUERY, state, project, startDate, endDate);
        return searchData(selectQuery);
    }

    /**
     * Used when searching for FieldNotes by a username, project name, and Billing state = "created"
     *
     * @param username
     * @param project
     * @return ArrayList<FieldNote>
     */
    public ArrayList<FieldNote> searchDataByUsernameAndProject(BillingState state, String username, String project) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_BY_USER_AND_PROJECT_QUERY, state, username, project);
        return searchData(selectQuery);
    }

    /**
     * Used when searching for FieldNotes created within a specific time frame by a
     * specific user, project name, and Billing state = "created"
     *
     * @param username
     * @param project
     * @param startDate
     * @param endDate
     * @return ArrayList<FieldNote>
     */
    public ArrayList<FieldNote> searchDataByUsernameProjectAndDateRange(BillingState state, String username, String project, String startDate, String endDate) {
        final String selectQuery = SqlInterpolate.interpolateBilling(FNBillingQueries.SELECT_BILLING_BY_USER_DATE_RANGE_AND_PROJECT_QUERY, state, username, project, startDate, endDate);
        return searchData(selectQuery);
    }
}