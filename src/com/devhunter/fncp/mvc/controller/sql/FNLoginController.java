/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import com.devhunter.fncp.constants.FNQueries;
import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.FNBridgeService;
import com.devhunter.fncp.mvc.view.loginpanel.FNLogin;
import com.devhunter.fncp.utilities.SqlInterpolate;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FNLoginController {

    private Statement mStatement;

    public FNLoginController() {
        mStatement = FNBridgeService.getInstance().getSQLBridgeStatement();
    }

    //TODO: extend FNController class

    /**
     * This method is called when a user clicks on the login button from the
     * FieldNotes login page. Returning true confirms that the users credentials are
     * in the database and they are allowed access to the rest of FieldNotes.
     * Returning false can mean that either the user name or password were
     * incorrect, or a SQLException occurred.
     *
     * @param username
     * @param password
     * @return boolean isLoggedIn
     */
    public boolean SQLLogin(String username, String password) {
        boolean isLoggedIn = false;
        if (mStatement != null) {
            String query = SqlInterpolate.interpolate(FNQueries.LOGIN_QUERY, username, password);
            try {
                ResultSet resultSet = mStatement.executeQuery(query);
                isLoggedIn = resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("sql exception: Login Failed");
            }
        } else {
            JOptionPane.showMessageDialog(FNLogin.getFieldNotesLoginFrame(), "Invalid username or password");
            return isLoggedIn;
        }
        return isLoggedIn;
    }
}