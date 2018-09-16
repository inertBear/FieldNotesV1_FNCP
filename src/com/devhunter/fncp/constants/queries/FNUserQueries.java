/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.constants.queries;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class FNUserQueries {
    // User Controller Queries
    // use these default queries and SqlInterpolate to create queries

    //searchData All users
    public static final String SELECT_ALL_USER_QUERY = "SELECT * FROM " + LOGIN_TABLE;
    //searchData user
    public static final String SELECT_USER_QUERY = SELECT_ALL_USER_QUERY + " WHERE " + USER_USERNAME_COLUMN + " = '%s'";
    //addData user
    public static final String ADD_USER_QUERY = "INSERT INTO " + LOGIN_TABLE + " (" + USER_USERNAME_COLUMN + ", " + USER_PASSWORD_COLUMN + ", " + USER_TYPE_COLUMN + ") VALUES ('%s', '%s', '%s')";
    //deleteData user
    public static final String DELETE_USER_QUERY = "DELETE FROM " + LOGIN_TABLE + " WHERE " + USER_USERNAME_COLUMN + " = '%s'";
    //change user password
    public static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE " + LOGIN_TABLE + " SET " + USER_PASSWORD_COLUMN + " = '%s' WHERE " + USER_USERNAME_COLUMN + " = '%s'";
}
