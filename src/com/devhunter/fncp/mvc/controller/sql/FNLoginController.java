/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import javax.swing.JOptionPane;

import com.devhunter.fncp.mvc.view.loginpanel.FieldNotesLogin;

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

public class FNLoginController{
	
	private SQLBridgeService mSQLBridgeService;
	
	public FNLoginController() {
		mSQLBridgeService = SQLBridgeService.getInstance();
	}

	public boolean SQLLogin(String username, String password) {
		if(mSQLBridgeService == null) {
			JOptionPane.showMessageDialog(FieldNotesLogin.getFieldNotesLoginFrame(), "Invalid username or password");
		} else {
			final String loginQuery = "SELECT * FROM rhl_login WHERE rhl_username = '" + username + "' AND rhl_password = '"
					+ password + "' ";
			return mSQLBridgeService.runLoginQuery(loginQuery);
		}
		return false;
	}
}