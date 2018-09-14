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

    private Statement mStatement;

    public FNController() {
        mStatement = FNBridgeService.getInstance().getSQLBridgeStatement();
    }

    public ArrayList<FieldNote> search(String query) {

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

    public boolean add(String query) {

        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String query) {

        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String query) {

        try {
            mStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
