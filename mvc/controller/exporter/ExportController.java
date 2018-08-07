/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.controller.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class ExportController {

	public int writeUserToCSVFile(ArrayList<String> exportResults) {

		int exportResult = 0;
		PrintWriter writer = null;

		try {
			String userHomeFolder = System.getProperty("user.home");
			writer = new PrintWriter(new File(userHomeFolder + "/Desktop", "FieldNotes_Export_User.csv"));
			StringBuilder builder = new StringBuilder();

			builder.append("User_ID");
			builder.append(",");
			builder.append("User_Name");
			builder.append(",");
			builder.append("Password");
			builder.append(System.getProperty("line.separator"));

			for (int i = 1; i < exportResults.size() + 1; i++) {
				builder.append(stripCommas(exportResults.get(i - 1)));
				if (i % 3 == 0) {
					builder.append(System.getProperty("line.separator"));
				} else {
					builder.append(",");
				}
			}

			writer.write(builder.toString().trim());
			writer.close();

			exportResult = 1;
			return exportResult;

		} catch (FileNotFoundException e) {
			exportResult = 2;
			return exportResult;
		}
	}

	public int writeDataToCSVFile(ArrayList<String> exportResults) {

		int exportResult = 0;
		PrintWriter writer = null;

		try {
			int count = 0;
			String userHomeFolder = System.getProperty("user.home");
			writer = new PrintWriter(new File(userHomeFolder + "/Desktop", "FieldNotes_Export_Data.csv"));
			StringBuilder builder = new StringBuilder();

			builder.append("Ticket_Number");
			builder.append(',');
			builder.append("User_Name");
			builder.append(',');
			builder.append("Well_Name");
			builder.append(',');
			builder.append("Start_Date");
			builder.append(',');
			builder.append("Start_Time");
			builder.append(',');
			builder.append("Milage_Start");
			builder.append(',');
			builder.append("Description");
			builder.append(',');
			builder.append("Mileage_End");
			builder.append(',');
			builder.append("End_Date");
			builder.append(',');
			builder.append("Time_End");
			builder.append(',');
			builder.append("Project_Number");
			builder.append(',');
			builder.append("Location");
			builder.append(',');
			builder.append("GPS");
			builder.append(',');
			builder.append("Billing");
			builder.append(System.getProperty("line.separator"));

			for (int i = 0; i < exportResults.size() + 1; i++) {
				if(i % 2 == 1) {
					count++;
					builder.append(stripCommas(exportResults.get(i)));
					if (count % 14 == 0) {
						builder.append(System.getProperty("line.separator"));
					} else {
						builder.append(",");
					}
				}
			}

			writer.write(builder.toString().trim());
			writer.close();

			exportResult = 1;
			return exportResult;

		} catch (FileNotFoundException e) {
			exportResult = 2;
			return exportResult;
		}
	}
	
	/**
	 * last resort comma strip before CVS export. Ideally all commas will have been properly taken care of before this point
	 * @param data
	 * @return String
	 */
	
	private String stripCommas(String data) {
		if (data != null) {
			return data.replaceAll(",", " ");
		} else { 
			return data;
		}
	}
}
