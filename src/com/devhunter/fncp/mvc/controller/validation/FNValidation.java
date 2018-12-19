/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.validation;

import com.devhunter.fncp.mvc.model.FNNote;
import com.devhunter.fncp.mvc.view.FNControlPanel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class handles the validation of the FNNote objects. It is responsible for ensuring that no
 * incomplete or incorrect values get from the User-end and into the database. FNValidation will
 * throw an IllegalArgumentException if the data is incomplete.
 */

public class FNValidation {

    public FNValidation() {
    }

    public static boolean validate(FNNote fn) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String error = "";
        try {
            if (fn.getUserName().equals("")) {
                error = "You are not logged in!";
                throw new IllegalArgumentException();
            }

            if (fn.getWellName().equals("")) {
                error = "Please enter a WellName";
                throw new IllegalArgumentException();
            } else if (fn.getWellName().contains(",")) {
                error = "Please do not use ',' in WellNames.";
                throw new IllegalArgumentException();
            }

            if (fn.getProject().equals("")) {
                error = "Please enter a Project Number";
                throw new IllegalArgumentException();
            } else if (fn.getProject().contains(",")) {
                error = "Please do not use ',' in Project Numbers.";
                throw new IllegalArgumentException();
            }

            if (fn.getDescription().equals("")) {
                error = "Please enter a Description";
                throw new IllegalArgumentException();
            } else if (fn.getDescription().length() > 255) {
                error = "description is too long";
                throw new IllegalArgumentException();
            } else if (fn.getDescription().contains(",")) {
                error = "Please do not use ',' in descriptions.";
                throw new IllegalArgumentException();
            }

            if (fn.getLocation().equals("")) {
                error = "Please select a Location";
                throw new IllegalArgumentException();
            } else if (fn.getLocation().equals("here") || fn.getLocation().equals("there") || fn.getLocation().equals("anywhere")) {
                //"v0.0.1 pre-Legacy", hand entered (here, there, anywhere) locations for test
                //FIXME: user FNValidationUpdater to set location to ""
                error = "Legacy data detected - please updateData Location";
                throw new IllegalArgumentException();
            } else if (fn.getLocation().equals("office") || fn.getLocation().equals("field") || fn.getLocation().equals("shop")) {
                //"v0.1.0 Legacy", hand entered locations that may not match case sensitive formatting
                error = "Legacy formatting error detected - please updateData location to" + fn.getLocation().toUpperCase();
                throw new IllegalArgumentException();
            }

            if (fn.getGPSCoords().equals("")) {
                error = "No GPS provided";
                //FIXME: updateData gps location to "no provided" using FNValidationUpdater
            } else if (fn.getGPSCoords().contains(",")) {
                //FIXME: currently filtered out by CVS export blanket comma stripper, need to implement gps formatting here
            }

            if (fn.getBillingType().equals("")) {
                error = "Please select a Billing Code";
                throw new IllegalArgumentException();
            } else if (fn.getLocation().equals("turnkey") || fn.getLocation().equals("billable") || fn.getLocation().equals("nonbillable")) {
                //"v0.1.0 Legacy", hand entered locations that may not match case sensitive formatting
                error = "Formatting error detected - please updateData billing to" + fn.getBillingType().toUpperCase();
                throw new IllegalArgumentException();
            }

            if (fn.getMileageStart().equals("") || fn.getMileageEnd().equals("")) {
                error = "Please make sure Start and End Mileages have been entered **Enter '0' if no travel";
                throw new IllegalArgumentException();
            } else if (!fn.getMileageStart().matches(".*\\d+.*") || !fn.getMileageEnd().matches(".*\\d+.*")) {
                error = "Please make sure the Start and End Mileage only contain numbers **[0-9]";
                throw new IllegalArgumentException();
            } else if (Integer.parseInt(fn.getMileageEnd()) < Integer.parseInt(fn.getMileageStart())) {
                error = "Please verify Start and End Mileage. **ENSURE: Start Mileage < End Mileage";
                throw new IllegalArgumentException();
            }

            if (fn.getDateStart().equals("") || fn.getDateEnd().equals("")) {
                error = "Please make sure Start and End Dates have been selected.";
                throw new IllegalArgumentException();
            } else if (!fn.getDateStart().matches("\\d{4}-\\d{2}-\\d{2}") || !fn.getDateEnd().matches("\\d{4}-\\d{2}-\\d{2}")) {
                error = "Date Format is incorrect **FORMAT: YYYY-MM-DD";
                throw new IllegalArgumentException();
            } else if (!dateFormat.parse(fn.getDateStart()).before(dateFormat.parse(fn.getDateEnd()))) {
                if (!fn.getDateStart().equals(fn.getDateEnd())) {
                    error = "Please verify Start and End Date. **Ensure Start Date is before End Date";
                    throw new IllegalArgumentException();
                }
            }

            if (fn.getTimeStart().equals("") || fn.getTimeEnd().equals("")) {
                error = "Please make sure Start and End Times have been selected.";
                throw new IllegalArgumentException();
            } else if (!fn.getTimeStart().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]") || !fn.getTimeEnd().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                error = "Time format is incorrect **FORMAT (24-hr): HH:mm";
                throw new IllegalArgumentException();
            } else if (!timeFormat.parse(fn.getTimeStart()).before(timeFormat.parse(fn.getTimeEnd()))) {
                if (!fn.getTimeStart().equals(fn.getTimeEnd())) {
                    error = "Please verify Start and End Time. **Ensure Start Time is before End Time";
                    throw new IllegalArgumentException();
                }
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), error);
            return false;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Date Parse Error "
                    + "- please make sure it is in the correct format.");
            return false;
        }
        return true;
    }

    /**
     * Corrects a Date (date) so it can be displayed
     * <p>
     * NOTE: this is intended to allow older FieldNotes which may not have the correct formatting to pass validation
     *
     * @param date
     * @return String
     */
    public static String validateDate(String date) {
        final String legacyPattern = "\\d{1,2}-\\d{1,2}-\\d{2}";
        final String correctPattern = "\\d{4}-\\d{2}-\\d{2}";
        final SimpleDateFormat legacyFormat = new SimpleDateFormat("MM/dd/yy");
        final SimpleDateFormat correctFormat = new SimpleDateFormat("yyyy-MM-dd");

        String validDate = null;
        if (date.matches(correctPattern)) {
            return date;
        } else {
            try {
                validDate = correctFormat.format(legacyFormat.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return validDate;
    }

    /**
     * Corrects a Date (time) so it can be displayed
     * <p>
     * NOTE: this is intended to allow older FieldNotes which may not have the correct formatting to pass validation
     *
     * @param time
     * @return String
     */
    public static String validateTime(String time) {
        final String correctPattern = "\\d{1,2}:\\d{2}";
        final SimpleDateFormat legacyFormat = new SimpleDateFormat("HHmm");
        final SimpleDateFormat correctFormat = new SimpleDateFormat("HH:mm");

        String validTime = null;
        if (time.matches(correctPattern)) {
            return time;
        } else {
            try {
                validTime = correctFormat.format(legacyFormat.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return validTime;
    }
}
