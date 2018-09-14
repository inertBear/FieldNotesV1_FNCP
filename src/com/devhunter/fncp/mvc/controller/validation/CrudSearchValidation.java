/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.validation;

import javax.swing.JOptionPane;

import com.devhunter.fncp.mvc.view.FNControlPanel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CrudSearchValidation {

    public CrudSearchValidation() {
    }

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

    public static boolean hasDateRange(String start, String end) {
        return !start.isEmpty() && !end.isEmpty();
    }

    public static boolean isDateRangeValid(String startDate, String endDate) {
        //no start date or end date, it is valid
        if (hasDateRange(startDate, endDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                //if start date is before end date, it is valid
                System.out.println(sdf.parse(startDate).before(sdf.parse(endDate)));
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Error Parsing date for validation");
                return false;
            }
        }
        return true;
    }
}
