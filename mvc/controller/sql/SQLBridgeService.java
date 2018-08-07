/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.controller.sql;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.devhunter.fieldnotes.constants.FieldNotesConstants;
import com.mysql.jdbc.CommunicationsException;

/**
 * gets an instance of the connection between the MySQL database and FieldNotes.
 * A statement is created and return for immediate reuse by the client
 *
 * @return Statement
 *
 * 
 *
 *  creating a singleton instance will keep the jdbc.Driver from having
 *  to be loaded every time a call is made. The same instance of the
 *  connection can be called every time. This will increase efficiency
 *  for all connection activities except the initial call
 */

public class SQLBridgeService {

	private static SQLBridgeService sInstance;
	private static Connection mConnection = null;
	private static Statement mStatement;
	private static ResultSet mResultSet = null;

	private SQLBridgeService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = DriverManager.getConnection(
					"jdbc:mysql://" + FieldNotesConstants.AWS_DATABASE_ENDPOINT + "/"
							+ FieldNotesConstants.FIELDNOTES_DATABASE_NAME,
					FieldNotesConstants.FIELDNOTES_DATABASE_USERNAME, FieldNotesConstants.FIELDNOTES_DATABASE_PASSWORD);
			mStatement = mConnection.createStatement();
		} catch (CommunicationsException e) {
			e.printStackTrace();
			System.out.println("Connection Failed: Could not log in");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Login Failed");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("JBDC class not found: Login Failed");
		} 
	}

	public static SQLBridgeService getInstance() {
		if (sInstance == null) {
			sInstance = new SQLBridgeService();
		}
		return sInstance;
	}
	
	public Statement getSQLBridgeStatement() {
		if(mStatement == null) {
			throw new IllegalStateException("SQLBridgeStatment is NULL");
		}
		return mStatement;
	}

	/**
	 * THis method is called when the client attempts to login to the database.
	 *
	 * @param loginQuery
	 * @return boolean isLoggedIn
	 */

	public boolean runLoginQuery(String loginQuery) {
		boolean isLoggedIn = false;
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(loginQuery);

			if (mResultSet.next())
				isLoggedIn = true;
			else
				isLoggedIn = false;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Login Failed");
		}
		return isLoggedIn;

	}
}
