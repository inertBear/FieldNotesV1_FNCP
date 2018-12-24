/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.controller.validation;

import com.fieldnotes.fncp.mvc.view.FNControlPanel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CrudSearchValidation {

    public CrudSearchValidation() {
    }

    /**
     * confirms if a ticket number is present
     *
     * @param ticketNumber
     * @return
     */
    public static boolean validate(String ticketNumber) {
        String error = "";
        try {
            if (ticketNumber.equals("")) {
                error = "Please enter a Ticket Number";
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), error);
            return false;
        }
        return true;
    }

    /**
     * confirms if a date range has been supplied
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean hasDateRange(String start, String end) {
        return !start.isEmpty() && !end.isEmpty();
    }

    /**
     * confirms if a date range is valid
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isDateRangeValid(String startDate, String endDate) {

        //if they are the same, then its empty or only one day is selected
        if (!startDate.equals(endDate)) {
            //if we have both dates, check for validity
            if (!startDate.isEmpty() && !endDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    //if start date is before end date, it is valid
                    System.out.println(sdf.parse(startDate).before(sdf.parse(endDate)));
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Error Parsing date for validation");
                    return false;
                }
                return true;
            } else {
                //missing one of the dates
                return false;
            }
        } else {
            if (hasDateRange(startDate, endDate)) {
                //there are values and they span one day
                return true;
            } else {
                //neither date has been entered
                return true;
            }
        }
    }
}