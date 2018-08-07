/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import com.devhunter.fieldnotes.mvc.model.FieldNote;
import com.devhunter.fieldnotes.mvc.view.FieldNotesControlPanel;

/**
 * This class handles the validation of the FieldNote objects. It is responsible for ensuring that no
 * incomplete or incorrect values get from the User-end and into the database. FieldNoteValidation will
 * throw an IllegalArgumentException if the data is incomplete.
 *
 */

public class FieldNoteValidation {
	
	private static SimpleDateFormat dateFormat;
	private static SimpleDateFormat timeFormat;
	
	public FieldNoteValidation() {
	}
	
	public static boolean validate(FieldNote fn) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeFormat = new SimpleDateFormat("HH:mm");
		String error = "";
		try {
			if(fn.getUserName().equals("")) {
				error = "You are not logged in!";
				throw new IllegalArgumentException();
			}
			
			if(fn.getWellName().equals("")) {
				error = "Please enter a WellName";
				throw new IllegalArgumentException();
			} else if (fn.getWellName().contains(",")) {
				error = "Please do not use ',' in WellNames.";
				throw new IllegalArgumentException();
			}
			
			if(fn.getProjectNumber().equals("")) {
				error = "Please enter a Project Number";
				throw new IllegalArgumentException();
			}else if (fn.getProjectNumber().contains(",")) {
				error = "Please do not use ',' in Project Numbers.";
				throw new IllegalArgumentException();
			}
			
			if(fn.getDescription().equals("")) {
				error = "Please enter a Description";
				throw new IllegalArgumentException();
			} else if (fn.getDescription().length() > 255) {
				error = "description is too long";
				throw new IllegalArgumentException();
			} else if (fn.getDescription().contains(",")) {
				error = "Please do not use ',' in descriptions.";
				throw new IllegalArgumentException();
			}
			
			if(fn.getLocation().equals("")) {
				error = "Please select a Location";
				throw new IllegalArgumentException();
			} else if(fn.getLocation().equals("here") || fn.getLocation().equals("there") || fn.getLocation().equals("anywhere")) {
				//"v0.0.1 pre-Legacy", hand entered (here, there, anywhere) locations for test
				//FIXME: user FieldNoteValidationUpdater to set location to ""
				error = "Legacy data detected - please update Location";
				throw new IllegalArgumentException();
			} else if(fn.getLocation().equals("office") || fn.getLocation().equals("field") || fn.getLocation().equals("shop")) {
				//"v0.1.0 Legacy", hand entered locations that may not match case sensitive formatting
				error = "Legacy formatting error detected - please update location to" + fn.getLocation().toUpperCase();
				throw new IllegalArgumentException();
			}
			
			if(fn.getGPSCoords().equals("")) {
				error = "No GPS provided";
				//FIXME: update gps location to "no provided" using FNValidationUpdater
			} else if (fn.getGPSCoords().contains(",")) {
				//FIXME: currently filtered out by CVS export blanket comma stripper, need to implement gps formatting here
			}
			
			if(fn.getBillingType().equals("")) {
				error = "Please select a Billing Code";
				throw new IllegalArgumentException();
			} else if(fn.getLocation().equals("turnkey") || fn.getLocation().equals("billable") || fn.getLocation().equals("nonbillable")) {
				//"v0.1.0 Legacy", hand entered locations that may not match case sensitive formatting
				error = "Formatting error detected - please update billing to" + fn.getBillingType().toUpperCase();
				throw new IllegalArgumentException();
			}
			
			if(fn.getMileageStart().equals("") || fn.getMileageEnd().equals("") ) {
				error = "Please make sure Start and End Mileages have been entered **Enter '0' if no travel";
				throw new IllegalArgumentException();
			} else if(!fn.getMileageStart().matches(".*\\d+.*") || !fn.getMileageEnd().matches(".*\\d+.*")) {
				error = "Please make sure the Start and End Mileage only contain numbers **[0-9]";
				throw new IllegalArgumentException();
			}else if (Integer.parseInt(fn.getMileageEnd()) < Integer.parseInt(fn.getMileageStart())) {
				error = "Please verify Start and End Mileage. **ENSURE: Start Mileage < End Mileage";
				throw new IllegalArgumentException();
			}
			
			if(fn.getDateStart().equals("") || fn.getDateEnd().equals("")) {
				error = "Please make sure Start and End Dates have been selected.";
				throw new IllegalArgumentException();
			} else if (!fn.getDateStart().matches("\\d{4}-\\d{2}-\\d{2}") || !fn.getDateEnd().matches("\\d{4}-\\d{2}-\\d{2}")) {
				error = "Date Format is incorrect **FORMAT: YYYY-MM-DD";
				throw new IllegalArgumentException();
			} else if(!dateFormat.parse(fn.getDateStart()).before(dateFormat.parse(fn.getDateEnd()))) {
				if(!fn.getDateStart().equals(fn.getDateEnd())) {
					error = "Please verify Start and End Date. **Ensure Start Date is before End Date";
					throw new IllegalArgumentException();
				}
			}

			if(fn.getTimeStart().equals("") || fn.getTimeEnd().equals("")) {
				error = "Please make sure Start and End Times have been selected.";
				throw new IllegalArgumentException();
			} else if (!fn.getTimeStart().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]") || !fn.getTimeEnd().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
				error = "Time format is incorrect **FORMAT (24-hr): HH:mm";
				throw new IllegalArgumentException();
			} else if(!timeFormat.parse(fn.getTimeStart()).before(timeFormat.parse(fn.getTimeEnd()))) {
				if(!fn.getTimeStart().equals(fn.getTimeEnd())) {
					error = "Please verify Start and End Time. **Ensure Start Time is before End Time";
					throw new IllegalArgumentException();
				}
			}
			
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(), error);
			return false;
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(), "Date Parse Error "
					+ "- please make sure it is in the correct format.");
			return false;
		}
		return true;
	}
}
