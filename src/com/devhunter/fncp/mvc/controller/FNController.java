/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller;

import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.utilities.FNUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FNController {

    protected Statement mStatement;

    protected FNController() {
        mStatement = FNBridgeService.getInstance().getSQLBridgeStatement();
    }

    /**
     * Search for FieldNotes
     *
     * @param query
     * @return ArrayList<FieldNotes>
     */
    protected ArrayList<FieldNote> searchData(String query) {

        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        try {
            ResultSet resultsSet = mStatement.executeQuery(query);
            fieldNotes = FNUtil.retrieveFieldNotes(resultsSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Search Failed");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("No data found for that value");
        }
        return fieldNotes;
    }
}