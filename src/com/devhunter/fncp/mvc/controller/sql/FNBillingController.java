/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.FNQueries;
import com.devhunter.fncp.mvc.controller.FNController;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.utilities.SqlInterpolate;

import java.util.ArrayList;

public class FNBillingController extends FNController {

    //TODO: [FNCP-007] add commenting
    //TODO: [FNCP-007] add controller methods for Billed and Completed states (
        // Q: will that go here, or should I make separate classes for them?)

    public FNBillingController() {
        super();
    }

    /**
     * Used when searching for all FieldNotes stored in the Database where Billing state = "created"
     *
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchAllCreatedData() {

        final String selectQuery = FNQueries.SELECT_CREATED_DATA_QUERY;
        return search(selectQuery);
    }

    /**
     * Used when searching for all the FieldNotes Created by a specific @param
     * userName where Billing sate = "created"
     *
     * @param userName
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchCreatedDataByUsername(String userName) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_CREATED_DATA_BY_USER_QUERY, userName);
        return search(selectQuery);
    }

    /**
     * @param projectName
     * @return
     */
    public ArrayList<FieldNote> searchCreatedDataByProject(String projectName) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_CREATED_DATA_BY_PROJECT_QUERY, projectName);
        return search(selectQuery);
    }

    /**
     * Used when searching for all the FieldNotes created within a specific time
     * frame @param startDate to endDate, and Billing state = "created"
     *
     * @param startDate
     * @param endDate
     * @return ArrayList<FieldNote> fieldNotes
     */
    public ArrayList<FieldNote> searchCreatedDataByDateRange(String startDate, String endDate) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_CREATED_DATA_BY_RANGE_QUERY, startDate, endDate);
        return search(selectQuery);
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
    public ArrayList<FieldNote> searchCreatedDataByUsernameAndDateRange(String username, String startDate, String endDate) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_CREATED_DATA_BY_RANGE_AND_USER_QUERY, username, startDate, endDate);
        return search(selectQuery);
    }

    public ArrayList<FieldNote> searchCreatedDataByUsernameProjectAndDateRange(String username, String project, String startDate, String endDate) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_CREATED_DATA_BY_USER_DATE_RANGE_AND_PROJECT_QUERY, username, startDate, endDate, project);
        return search(selectQuery);
    }

    public ArrayList<FieldNote> searchCreatedDataByProjectAndDateRange(String project, String startDate, String endDate) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_CREATED_DATA_BY_PROJECT_AND_DATE_RANGE_QUERY, project, startDate, endDate);
        return search(selectQuery);
    }

    public ArrayList<FieldNote> searchCreatedDataByUsernameAndProject(String username, String project) {

        final String selectQuery = SqlInterpolate.interpolate(FNQueries.SELECT_CREATED_DATA_BY_USER_AND_PROJECT_QUERY, username, project);
        return search(selectQuery);
    }
}