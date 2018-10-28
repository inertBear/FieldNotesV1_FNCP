/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.constants.queries;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class FNDataQueries {
    // Data Controller Queries
    // use these default queries and SqlInterpolate to create queries

    //searchData all data
    public static final String SELECT_DATA_QUERY = "SELECT * FROM " + DATA_TABLE;
    //searchData data by ticket number
    public static final String SELECT_DATA_BY_TICKET_QUERY = SELECT_DATA_QUERY + " WHERE " + TICKET_NUMBER_TAG + " = '%s'";
}
