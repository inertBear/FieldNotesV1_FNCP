/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.devhunter.fncp.constants.SqlConstants;
import com.devhunter.fncp.mvc.model.FNUser.FNEntity;

/**
 * This class holds the methods for all the user changes from and into the
 * database. Using these methods users can Add, Delete, Search and Change
 * Passwords for FieldNotes users. The methods housed here were only intended to
 * relate to the actual storing, alteration, and retrieving of FieldNote user
 * data.
 */

public class SQLUserController {

	private SQLBridgeService mSQLBridgeService;
	private Statement mStatement;
	private ResultSet mResultSet;

	public SQLUserController() {
		//mSQLBridgeService = SQLBridgeService.getInstance();
		mStatement = mSQLBridgeService.getInstance().getSQLBridgeStatement();
	}
	
	/**
	 * Used when searching for a specific user within FieldNotes. Returns an
	 * ArrayList containing the data of the search for user
	 * 
	 * @param String user
	 * @return ArrayList<String> searchResults
	 * 
	 *         TODO: Users needs to be a Class that holds the attributes: id, user
	 *         name, and password. These user objects can then be stripped of their
	 *         data for conversing with the database
	 */

	public ArrayList<String> mySQLSearchUser(String user) {

		final String selectQuery = "SELECT * FROM rhl_login WHERE rhl_username = '" + user + "' ";
		ArrayList<String> searchResults = new ArrayList<String>();

		try {
			mResultSet = mStatement.executeQuery(selectQuery);

			if (!mResultSet.next()) {
				searchResults.add(0, "Username does not exist");
				searchResults.add(1, "");
				searchResults.add(2, "");
			} else {
				mResultSet.beforeFirst();
				while (mResultSet.next()) {
					String idValue = mResultSet.getString(SqlConstants.ID_COLUMN);
					String userValue = mResultSet.getString(SqlConstants.USERNAME_COLUMN);
					String passValue = mResultSet.getString(SqlConstants.PASSWORD_COLUMN);

					searchResults.add(idValue);
					searchResults.add(userValue);
					searchResults.add(passValue);
				}
			}
			mResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Search Failed");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("No user data found for that value");
		}
		return searchResults;
	}

	/**
	 * Used when search for all users within FieldNotes. The users are returned in
	 * an ArrayList containing all the values of the (Future)'FieldNotesUser' class
	 * 
	 * @return ArrayList<String> allSearchResults
	 */

	public ArrayList<String> mySQLSearchUser() {

		final String selectAllQuery = "SELECT * FROM rhl_login ";
		ArrayList<String> allSearchResults = new ArrayList<String>();

		try {
			mResultSet = mStatement.executeQuery(selectAllQuery);

			while (mResultSet.next()) {
				allSearchResults.add(mResultSet.getString(SqlConstants.ID_COLUMN));
				allSearchResults.add(mResultSet.getString(SqlConstants.USERNAME_COLUMN));
				allSearchResults.add(mResultSet.getString(SqlConstants.PASSWORD_COLUMN));
			}

			mResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Search Failed");
		}
		return allSearchResults;
	}

	/**
	 * Adds a User to the Database. validates and rejects duplicate UserNames
	 * 
	 * @param FNEntity user
	 * @return int responseCode, (2 if the UserName already exists, 1 for success, 0
	 *         for error)
	 */

	public int addUser(FNEntity user) {
		final String selectQuery = "SELECT * FROM rhl_login WHERE rhl_username = '" + user.getUsername() + "' ";
		final String addUserQuery = "INSERT INTO rhl_login (rhl_username, rhl_password, rhl_user_type) " + "VALUES ( '" + user.getUsername()
				+ "', '" + user.getPassword() + "', '" + user.getType() + "' )";
		int responseCode = 0;
		try {
			mResultSet = mStatement.executeQuery(selectQuery);
			if (mResultSet.next()) {
				responseCode = 2;
			} else {
				mStatement.executeUpdate(addUserQuery);
				responseCode = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Add User Failed");
			responseCode = 0;
		}
		return responseCode;
	}

	/**
	 * Deletes a FieldNotesUser from the Database. Validates user existence
	 * 
	 * @param FNUser user
	 * @return int deleteUserResponseCode, (1 for success, 2 if the user did not
	 *         exist, 0 for error)
	 */

	public int deleteUser(FNEntity user) {

		final String selectQuery = "SELECT * FROM rhl_login WHERE rhl_username = '" + user.getUsername() + "' ";
		final String deleteUserQuery = "DELETE FROM rhl_login where rhl_username = '" + user.getUsername() + "' ";
		int deleteUserResponseCode = 0;
		try {
			mResultSet = mStatement.executeQuery(selectQuery);
			if (mResultSet.next()) {
				mStatement.executeUpdate(deleteUserQuery);
				deleteUserResponseCode = 1;
			} else {
				deleteUserResponseCode = 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			deleteUserResponseCode = 0;
		}
		return deleteUserResponseCode;
	}

	/**
	 * Change the password of a user in the database. validates and rejects nonexistent UserNames
	 * 
	 * @param FNUser (contains the CURRENT user and NEW password)
	 * @return int changePassResponseCode, (1 if success, 2 if UserName does not
	 *         exist, 0 if error)
	 */

	public int updatePassword(FNEntity user) {
		final String selectQuery = "SELECT * FROM rhl_login WHERE rhl_username = '" + user.getUsername() + "' ";
		final String updatePassQuery = "UPDATE rhl_login SET rhl_password = '" + user.getPassword()
				+ "' WHERE rhl_username = '" + user.getUsername() + "'";
		int changePassResponseCode = 0;
		try {
			mResultSet = mStatement.executeQuery(selectQuery);
			if (mResultSet.next()) {
				mStatement.executeUpdate(updatePassQuery);
				changePassResponseCode = 1;
			} else {
				changePassResponseCode = 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			changePassResponseCode = 0;
		}
		return changePassResponseCode;
	}
}