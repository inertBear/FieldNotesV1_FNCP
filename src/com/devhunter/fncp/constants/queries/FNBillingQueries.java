/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.constants.queries;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class FNBillingQueries {

    // Billing Controller Queries
    // use these default queries and SqlInterpolate to create queries

    //search for all "null" billing states
    public static final String SELECT_NULL_BILLING_QUERY = "SELECT * FROM " + DATA_TABLE + " WHERE " + BILLING_STATE_IS_NULL;

    //searchData all "created" data
    public static final String SELECT_BILLING_QUERY = "SELECT * FROM " + DATA_TABLE + " WHERE " + BILLING_COLUMN + " = '" + IS_BILLABLE + "' AND " + BILLING_STATE_COLUMN + " = '%s'";
    //searchData "created" data by user
    public static final String SELECT_BILLING_BY_USER_QUERY = SELECT_BILLING_QUERY + " AND " + USER_COLUMN + " = '%s'";
    //searchData "created" data by date range
    public static final String SELECT_BILLING_BY_RANGE_QUERY = SELECT_BILLING_QUERY + " AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //searchData "created data by project
    public static final String SELECT_BILLING_BY_PROJECT_QUERY = SELECT_BILLING_QUERY + " AND " + PROJECTNUMBER_COLUMN + " = '%s'";
    //searchData "created" data by user and date range
    public static final String SELECT_BILLING_BY_USER_AND_RANGE_QUERY = SELECT_BILLING_BY_USER_QUERY + " AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //searchData "created" data by user and project
    public static final String SELECT_BILLING_BY_USER_AND_PROJECT_QUERY = SELECT_BILLING_BY_USER_QUERY + " AND " + PROJECTNUMBER_COLUMN + " = '%s'";
    //searchData "created data by project and date range
    public static final String SELECT_BILLING_BY_PROJECT_AND_DATE_RANGE_QUERY = SELECT_BILLING_BY_PROJECT_QUERY + " AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
    //searchData "created" data by user, project, and date range
    public static final String SELECT_BILLING_BY_USER_DATE_RANGE_AND_PROJECT_QUERY = SELECT_BILLING_BY_USER_AND_PROJECT_QUERY + " AND " + DATESTART_COLUMN + " >= '%s' AND " + DATEEND_COLUMN + " <= '%s'";
}
