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
import java.util.ArrayList;

public class ExportController {

    public boolean writeUserToCSVFile(ArrayList<FNEntity> users) {

        PrintWriter writer;

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

            for (FNEntity each : users) {
                builder.append(each.getId());
                builder.append(",");
                builder.append(each.getUsername());
                builder.append(",");
                builder.append(each.getPassword());
            }

            writer.write(builder.toString().trim());
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean writeDataToCSVFile(ArrayList<FieldNote> fieldNotes) {

        PrintWriter writer;

        try {
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
            builder.append("Mileage_Start");
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

            for (FieldNote each : fieldNotes) {
                builder.append(stripCommas(each.getTicketNumber()));
                builder.append(",");
                builder.append(stripCommas(each.getUserName()));
                builder.append(",");
                builder.append(stripCommas(each.getWellName()));
                builder.append(",");
                builder.append(each.getLocation());
                builder.append(",");
                builder.append(each.getBillingType());
                builder.append(",");
                builder.append(each.getDateStart());
                builder.append(",");
                builder.append(each.getDateEnd());
                builder.append(",");
                builder.append(each.getTimeStart());
                builder.append(",");
                builder.append(each.getTimeEnd());
                builder.append(",");
                builder.append(each.getMileageStart());
                builder.append(",");
                builder.append(each.getMileageEnd());
                builder.append(",");
                builder.append(each.getDescription());
                builder.append(",");
                builder.append(each.getGPSCoords());
                builder.append(System.getProperty("line.separator"));
            }

            writer.write(builder.toString().trim());
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
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
