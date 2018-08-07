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
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.mvc.model.FieldNote.FieldNoteBuilder;

/**
 * This class holds the methods for all the data changes from and into the
 * database. Using these methods users can Add, Delete, Update, and Search for
 * individual FieldNotes. The methods housed here were only intended to relate
 * to the actual storing, alteration, and retrieving of FieldNote data.
 *
 */

public class SQLDataController {

	private SQLBridgeService mSQLBridgeService;
	private Statement mStatement;
	private ResultSet mResultSet;
	private static final String sep = "', '";
	
	public SQLDataController() {
		//mSQLBridgeService = SQLBridgeService.getInstance();
		mStatement = mSQLBridgeService.getInstance().getSQLBridgeStatement();
	}
	
	/**
	 * Used when searching for all FieldNotes stored in the Database
	 * 
	 * @return ArrayList<String> allDataSearchResults
	 * 
	 *         TODO: the values pulled from the database will need to be loaded into
	 *         a FieldNote Object before they should be changed
	 */

	public ArrayList<String> mySQLSearchData() {

		final String selectUserQuery = "SELECT * FROM data";
		ArrayList<String> allDataSearchResults = new ArrayList<String>();

		try {
			mResultSet = mStatement.executeQuery(selectUserQuery);

			if (!mResultSet.next()) {
				allDataSearchResults.add("No data found in database");
			} else {
				mResultSet.beforeFirst();
				while (mResultSet.next()) {
					allDataSearchResults.add("Ticket #: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.TICKET_COLUMN));
					allDataSearchResults.add("Username : ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.USER_COLUMN));
					allDataSearchResults.add("Well Name : ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.WELLNAME_COLUMN));
					allDataSearchResults.add("Date Start: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.DATESTART_COLUMN));
					allDataSearchResults.add("Time Start: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.TIMESTART_COLUMN));
					allDataSearchResults.add("Mileage Start: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.MILEAGESTART_COLUMN));
					allDataSearchResults.add("Description: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.DESCRIPTION_COLUMN));
					allDataSearchResults.add("Mileage End: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.MILEAGEEND_COLUMN));
					allDataSearchResults.add("Date End: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.DATEEND_COLUMN));
					allDataSearchResults.add("Time End: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.TIMEEND_COLUMN));
					allDataSearchResults.add("Project #: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.PROJECTNUMBER_COLUMN));
					allDataSearchResults.add("Location: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.LOCATION_COLUMN));
					allDataSearchResults.add("GPS Coords: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.GPSCOORDS_COLUMN));
					allDataSearchResults.add("Billing: ");
					allDataSearchResults.add(mResultSet.getString(SqlConstants.BILLING_COLUMN));
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
		return allDataSearchResults;
	}

	/**
	 * Used when searching for all the FieldNotes Created by a specific @param
	 * userName
	 * 
	 * @param userName
	 * @return ArrayList<String> dataSearchResults
	 * 
	 *         TODO: the values pulled from the database will need to be loaded into
	 *         a FieldNote Object
	 */

	public ArrayList<String> mySQLSearchData(String userName) {

		final String selectUserQuery = "SELECT * FROM data WHERE userName = '" + userName + "' ";
		ArrayList<String> dataSearchResults = new ArrayList<String>();

		try {
			mResultSet = mStatement.executeQuery(selectUserQuery);

			while (mResultSet.next()) {
				dataSearchResults.add("Ticket #: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.TICKET_COLUMN));
				dataSearchResults.add("Username : ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.USER_COLUMN));
				dataSearchResults.add("Well Name : ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.WELLNAME_COLUMN));
				dataSearchResults.add("Date Start: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.DATESTART_COLUMN));
				dataSearchResults.add("Time Start: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.TIMESTART_COLUMN));
				dataSearchResults.add("Mileage Start: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.MILEAGESTART_COLUMN));
				dataSearchResults.add("Description: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.DESCRIPTION_COLUMN));
				dataSearchResults.add("Mileage End: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.MILEAGEEND_COLUMN));
				dataSearchResults.add("Date End: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.DATEEND_COLUMN));
				dataSearchResults.add("Time End: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.TIMEEND_COLUMN));
				dataSearchResults.add("Project #: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.PROJECTNUMBER_COLUMN));
				dataSearchResults.add("Location: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.LOCATION_COLUMN));
				dataSearchResults.add("GPS Coords: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.GPSCOORDS_COLUMN));
				dataSearchResults.add("billing: ");
				dataSearchResults.add(mResultSet.getString(SqlConstants.BILLING_COLUMN));
			}

			mResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Search Failed");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("No user data found for that value");
		}
		return dataSearchResults;
	}

	/**
	 * Used when searching for all the FieldNotes created within a specific time
	 * frame @param dataSearchStartDate to
	 * 
	 * @param dataSearchEndDate
	 * 
	 * @param dataSearchStartDate
	 * @param dataSearchEndDate
	 * 
	 * @return ArrayList<String> dataSearchByDateResults
	 * 
	 *         TODO: the values pulled from the database will need to be loaded into
	 *         a FieldNote Object
	 */

	public ArrayList<String> mySQLSearchDataByDateRange(String dataSearchStartDate, String dataSearchEndDate) {

		final String selectUserQuery = "SELECT * FROM data WHERE dateStart >= '" + dataSearchStartDate
				+ "' AND dateEnd <= '" + dataSearchEndDate + "' ";
		ArrayList<String> dataSearchByDateResults = new ArrayList<String>();
		try {
			mResultSet = mStatement.executeQuery(selectUserQuery);

			if (!mResultSet.next()) {
				dataSearchByDateResults.add("No data found in database");
			} else {
				mResultSet.beforeFirst();
				while (mResultSet.next()) {
					dataSearchByDateResults.add("Ticket #: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.TICKET_COLUMN));
					dataSearchByDateResults.add("Username : ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.USER_COLUMN));
					dataSearchByDateResults.add("Well Name : ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.WELLNAME_COLUMN));
					dataSearchByDateResults.add("Date Start: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.DATESTART_COLUMN));
					dataSearchByDateResults.add("Time Start: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.TIMESTART_COLUMN));
					dataSearchByDateResults.add("Mileage Start: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.MILEAGESTART_COLUMN));
					dataSearchByDateResults.add("Description: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.DESCRIPTION_COLUMN));
					dataSearchByDateResults.add("Mileage End: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.MILEAGEEND_COLUMN));
					dataSearchByDateResults.add("Date End: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.DATEEND_COLUMN));
					dataSearchByDateResults.add("Time End: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.TIMEEND_COLUMN));
					dataSearchByDateResults.add("Project #: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.PROJECTNUMBER_COLUMN));
					dataSearchByDateResults.add("Location: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.LOCATION_COLUMN));
					dataSearchByDateResults.add("GPS Coords: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.GPSCOORDS_COLUMN));
					dataSearchByDateResults.add("Billing: ");
					dataSearchByDateResults.add(mResultSet.getString(SqlConstants.BILLING_COLUMN));
				}
				mResultSet.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Search Failed");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("No data found for that value");
		}
		return dataSearchByDateResults;
	}

	/**
	 * Used when searching for FieldNotes created within a specific time frame by a
	 * specific user
	 * 
	 * @param dataSearchUsername
	 * @param dataSearchStartDate
	 * @param dataSearchEndDate
	 * 
	 * @return ArrayList<String> dataSearchByDateAndUserResults
	 * 
	 *         TODO: the values pulled from the database will need to be loaded into
	 *         a FieldNote Object
	 */

	public ArrayList<String> mySQLSearchDataByUserAndDateRange(String dataSearchUsername, String dataSearchStartDate,
			String dataSearchEndDate) {
		final String selectUserQuery = "SELECT * FROM data WHERE userName = '" + dataSearchUsername
				+ "' AND dateStart >= '" + dataSearchStartDate + "' AND dateEnd <= '" + dataSearchEndDate + "' ";
		ArrayList<String> dataSearchByDateAndUserResults = new ArrayList<String>();
		try {
			mResultSet = mStatement.executeQuery(selectUserQuery);

			if (!mResultSet.next()) {
				dataSearchByDateAndUserResults.add("No data found in database");
			} else {
				mResultSet.beforeFirst();
				while (mResultSet.next()) {
					dataSearchByDateAndUserResults.add("Ticket #: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.TICKET_COLUMN));
					dataSearchByDateAndUserResults.add("Username : ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.USER_COLUMN));
					dataSearchByDateAndUserResults.add("Well Name : ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.WELLNAME_COLUMN));
					dataSearchByDateAndUserResults.add("Date Start: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.DATESTART_COLUMN));
					dataSearchByDateAndUserResults.add("Time Start: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.TIMESTART_COLUMN));
					dataSearchByDateAndUserResults.add("Mileage Start: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.MILEAGESTART_COLUMN));
					dataSearchByDateAndUserResults.add("Description: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.DESCRIPTION_COLUMN));
					dataSearchByDateAndUserResults.add("Mileage End: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.MILEAGEEND_COLUMN));
					dataSearchByDateAndUserResults.add("Date End: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.DATEEND_COLUMN));
					dataSearchByDateAndUserResults.add("Time End: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.TIMEEND_COLUMN));
					dataSearchByDateAndUserResults.add("Project #: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.PROJECTNUMBER_COLUMN));
					dataSearchByDateAndUserResults.add("Location: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.LOCATION_COLUMN));
					dataSearchByDateAndUserResults.add("GPS Coords: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.GPSCOORDS_COLUMN));
					dataSearchByDateAndUserResults.add("Billing: ");
					dataSearchByDateAndUserResults.add(mResultSet.getString(SqlConstants.BILLING_COLUMN));
				}
				mResultSet.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql exception: Search Failed");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("No data found for that value");
		}
		return dataSearchByDateAndUserResults;
	}

	/**
	 * Inserts a FieldNote into the database using an SQL query. Returns a ResponseCode to signal success or failure
	 * 
	 * @param FieldNote fieldNote
	 * @return int responseCode, confirms or rejects the added FiedlNote
	 */

	public int addFieldNote(FieldNote fieldNote) {
		int newDataResponseCode = 0;
		final String values=  fieldNote.getUserName() + sep + fieldNote.getWellName() + sep + fieldNote.getDateStart() + sep 
				+ fieldNote.getTimeStart() + sep + fieldNote.getMileageStart() + sep + fieldNote.getDescription() + sep
				+ fieldNote.getMileageEnd() + sep + fieldNote.getDateEnd() + sep + fieldNote.getTimeEnd() + sep 
				+ fieldNote.getProjectNumber() + sep + fieldNote.getLocation() + sep + fieldNote.getGPSCoords() + sep 
				+ fieldNote.getBillingType();
		final String query = "INSERT INTO data (userName, wellName, dateStart, timeStart, "
				+ "mileageStart, description, mileageEnd, dateEnd, timeEnd, projectNumber, "
				+ "location, gps, billing) VALUES ('" + values + "') ";
		
		try {
			mStatement.executeUpdate(query);
			newDataResponseCode = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			newDataResponseCode = 0;
		}
		return newDataResponseCode;
	}

	/**
	 * search for a FieldNote in the database by Ticket number
	 * 
	 * @param String editSearchTicketNumber
	 * @return FieldNote fieldNote
	 */

	public FieldNote searchFieldNote(String editSearchTicketNumber) {
		final String selectTicketQuery = "SELECT * FROM data WHERE ticketNumber = '" + editSearchTicketNumber + "' ";
		FieldNote fieldNote = null;

		try {
			mResultSet = mStatement.executeQuery(selectTicketQuery);
			if (!mResultSet.next()) {
				fieldNote = new FieldNoteBuilder().createEmptyFieldNote();
			} else {
				fieldNote = new FieldNoteBuilder()
						.setUserName(mResultSet.getString(SqlConstants.USER_COLUMN))
						.setTicketNumber(mResultSet.getString(SqlConstants.TICKET_COLUMN))
						.setWellName(mResultSet.getString(SqlConstants.WELLNAME_COLUMN))
						.setDateStart(mResultSet.getString(SqlConstants.DATESTART_COLUMN))
						.setTimeStart(mResultSet.getString(SqlConstants.TIMESTART_COLUMN))
						.setMileageStart(mResultSet.getString(SqlConstants.MILEAGESTART_COLUMN))
						.setDescription(mResultSet.getString(SqlConstants.DESCRIPTION_COLUMN))
						.setMileageEnd(mResultSet.getString(SqlConstants.MILEAGEEND_COLUMN))
						.setDateEnd(mResultSet.getString(SqlConstants.DATEEND_COLUMN))
						.setTimeEnd(mResultSet.getString(SqlConstants.TIMEEND_COLUMN))
						.setProjectNumber(mResultSet.getString(SqlConstants.PROJECTNUMBER_COLUMN))
						.setLocation(mResultSet.getString(SqlConstants.LOCATION_COLUMN))
						.setGPSCoords(mResultSet.getString(SqlConstants.GPSCOORDS_COLUMN))
						.setBillingType(mResultSet.getString(SqlConstants.BILLING_COLUMN))
						.createFieldNote();
			}
			mResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fieldNote;
	}

	/**
	 * Updates a FieldNote in the database using an SQL query. Returns a ResponseCode to signal success or failure
	 * 
	 * @param FieldNote fieldNote
	 * @return int newDataResponse Code, confirms or rejects the added FiedlNote
	 */
	
	public int updateFieldNote(FieldNote fieldNote, String ticketNumber) {
		int responseCode = 0;
		final String query = "UPDATE data SET userName = '" + fieldNote.getUserName() + "', wellName = '"
				+ fieldNote.getWellName() + "', " + "dateStart = '" + fieldNote.getDateStart() + "', timeStart = '" + fieldNote.getTimeStart()
				+ "', mileageStart = '" + fieldNote.getMileageStart() + "', " + "description = '" + fieldNote.getDescription()
				+ "', mileageEnd = '" + fieldNote.getMileageEnd() + "', dateEnd = '" + fieldNote.getDateEnd() + "'," + "timeEnd = '"
				+ fieldNote.getTimeEnd() + "', projectNumber = '" + fieldNote.getProjectNumber() + "', location = '" + fieldNote.getLocation()
				+ "', " + "gps = '" + fieldNote.getGPSCoords() + "', billing = '" + fieldNote.getBillingType()
				+ "' WHERE ticketNumber = '" + ticketNumber + "'";

		try {
			mStatement.executeUpdate(query);
			responseCode = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			responseCode = 0;
		}
		return responseCode;
	}

	/**
	 * delete a fieldNote from the database with an SQL query. Returns repsonse code to signal success or failure
	 * 
	 * @param String ticketNumber
	 * @return int reponseCode
	 */

	public int deleteFieldNote(String ticketNumber) {
		final String deleteDataQuery = "DELETE FROM data where ticketNumber = '" + ticketNumber + "' ";
		int responseCode = 0;
		try {
			mStatement.executeUpdate(deleteDataQuery);
			responseCode = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			responseCode = 0;
		}
		return responseCode;
	}

	/**
	 * This method removes the characters from the data that will case errors in the
	 * CSV exporting
	 * 
	 * @param String
	 *            preStripValue
	 * @return String postStripValue
	 */

//	public String stripCommas(String preStripValue) {
//		String postStripValue = preStripValue.replaceAll("'", " ");
//		postStripValue = postStripValue.replaceAll(",", " ");
//		return postStripValue;
//	}

}
