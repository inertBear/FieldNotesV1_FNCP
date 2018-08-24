/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.utilities;

/**
 * This class combines the variables used in SQL queries with the static queries for easy reuse.
 */

public class SqlInterpolate {

    public static String interpolate(String queryTemplate, String variable) {
        return String.format(queryTemplate, variable);
    }

    public static String interpolate(String queryTemplate, String variable, String variable2) {
        return String.format(queryTemplate, variable, variable2);
    }

    public static String interpolate(String queryTemplate, String variable, String variable2, String variable3) {
        return String.format(queryTemplate, variable, variable2, variable3);
    }
}
