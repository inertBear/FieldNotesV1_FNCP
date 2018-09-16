/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.constants.queries;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class FNLoginQueries {

    // Login Controller Queries
    // use these default queries a SqlInterpolate to create queries

    public static final String LOGIN_QUERY = "SELECT * FROM " + LOGIN_TABLE + " WHERE " + USER_USERNAME_COLUMN + " = '%s' AND " + USER_PASSWORD_COLUMN + " = '%s'";
}
