/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.exporter;

import com.devhunter.fncp.mvc.model.FNUser.FNEntity;
import com.devhunter.fncp.mvc.model.FieldNote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExportController {

    public boolean writeUserToCSVFile(ArrayList<FNEntity> users) {

        PrintWriter writer;
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

        try {
            String userHomeFolder = System.getProperty("user.home");
            writer = new PrintWriter(new File(userHomeFolder + "/Desktop", "FN_User_" + dateTime + ".csv"));
        } catch (FileNotFoundException e) {
            return false;
        }
        StringBuilder builder = new StringBuilder();

        builder.append("User_ID");
        builder.append(",");
        builder.append("User_Name");
        builder.append(",");
        builder.append("Password");
        builder.append(System.getProperty("line.separator"));

        for (FNEntity each : users) {
            builder.append(each.getId());
            builder.append(",");
            builder.append(stripCommas(each.getUsername()));
            builder.append(",");
            builder.append(stripCommas(each.getPassword()));
        }
        writer.write(builder.toString().trim());
        writer.close();
        return true;
    }

    public boolean writeDataToCSVFile(ArrayList<FieldNote> fieldNotes) {

        PrintWriter writer;
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

        try {
            String userHomeFolder = System.getProperty("user.home");
            writer = new PrintWriter(new File(userHomeFolder + "/Desktop", "FN_Data_" + dateTime + ".csv"));
        } catch (FileNotFoundException e) {
            return false;
        }
        StringBuilder builder = new StringBuilder();

        builder.append("Ticket_Number");
        builder.append(',');
        builder.append("User_Name");
        builder.append(',');
        builder.append("Project_Name");
        builder.append(',');
        builder.append("Well_Name");
        builder.append(',');
        builder.append("Start_Date");
        builder.append(',');
        builder.append("End_Date");
        builder.append(',');
        builder.append("Time_Start");
        builder.append(',');
        builder.append("Time_End");
        builder.append(',');
        builder.append("Mileage_Start");
        builder.append(',');
        builder.append("Mileage_End");
        builder.append(',');
        builder.append("Description");
        builder.append(',');
        builder.append("Location");
        builder.append(',');
        builder.append("Billing");
        builder.append(',');
        builder.append("GPS_Coords");
        builder.append(System.getProperty("line.separator"));

        for (FieldNote each : fieldNotes) {
            builder.append(each.getTicketNumber());
            builder.append(",");
            builder.append(stripCommas(each.getUserName()));
            builder.append(",");
            builder.append(stripCommas(each.getProjectNumber()));
            builder.append(",");
            builder.append(stripCommas(each.getWellName()));
            builder.append(",");
            builder.append(stripCommas(each.getDateStart()));
            builder.append(",");
            builder.append(stripCommas(each.getDateEnd()));
            builder.append(",");
            builder.append(stripCommas(each.getTimeStart()));
            builder.append(",");
            builder.append(stripCommas(each.getTimeEnd()));
            builder.append(",");
            builder.append(stripCommas(each.getMileageStart()));
            builder.append(",");
            builder.append(stripCommas(each.getMileageEnd()));
            builder.append(",");
            builder.append(stripCommas(each.getDescription()));
            builder.append(",");
            builder.append(stripCommas(each.getLocation()));
            builder.append(",");
            builder.append(stripCommas(each.getBillingType()));
            builder.append(",");
            builder.append(stripCommas(each.getGPSCoords()));
            builder.append(System.getProperty("line.separator"));
        }
        writer.write(builder.toString().trim());
        writer.close();
        return true;
    }

    /**
     * last resort comma strip before CVS export. Ideally all commas will have been properly taken care of before this point
     *
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
