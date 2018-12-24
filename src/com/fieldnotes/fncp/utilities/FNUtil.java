/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.utilities;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.constants.FNPConstants;
import com.fieldnotes.fncp.mvc.controller.validation.FNValidation;
import com.fieldnotes.fncp.mvc.model.FNNote;
import com.fieldnotes.fncp.mvc.model.FNUser;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.fieldnotes.fncp.constants.FNPConstants.*;

/**
 * This class holds all of the common View characteristics including colors, borders, and Dimensions.
 * These can be accessed from anywhere in FieldNotes and ensures that all dimensions, colors, and
 * borders stay the same throughout the entire project.
 */

public class FNUtil {

    private static FNUtil sInstance;
    private static String mCurrentUsername;
    private static String mCurrentPassword;
    private static String mCurrentUserType;
    private static String mCurrentProductKey;
    private static boolean mCurrentAdminAccess;
    private SpinnerListModel mLocationModel;
    private SpinnerListModel mBillableModel;
    private SimpleDateFormat mFormatter;
    private Dimension mSmallLabelDimension;
    private Dimension mStandardLabelDimension;
    private Dimension mLargeLabelDimension;
    private Dimension mStandardTextFieldDimensions;
    private Dimension mLargeTextFieldDimension;
    private Dimension mLoginPanelDimension;
    private Dimension mStandardButtonDimension;
    private Color mPrimaryColor;

    /**
     * This class has been established as a singleton to disallow the creation of multiple Utility objects.
     * <p>
     * TODO: include utilities for Thread control- i.e. UI VS background threading
     */
    private FNUtil() {
        mLocationModel = new SpinnerListModel(FNCPConstants.APPROVED_BILLING_LOCATIONS);
        mBillableModel = new SpinnerListModel(FNCPConstants.APPROVED_BILLING_CODES);
        mFormatter = new SimpleDateFormat("HHmmss");

        mPrimaryColor = new Color(100, 149, 237);
        mSmallLabelDimension = new Dimension(50, 24);
        mStandardLabelDimension = new Dimension(100, 24);
        mLargeLabelDimension = new Dimension(200, 24);
        mStandardTextFieldDimensions = new Dimension(200, 24);
        mLargeTextFieldDimension = new Dimension(500, 24);
        mLoginPanelDimension = new Dimension(325, 40);
    }

    public static FNUtil getInstance() {
        if (sInstance == null) {
            sInstance = new FNUtil();
        }
        return sInstance;
    }

    /**
     * set logged in user
     *
     * @param username
     */
    public void setCurrentUsername(String username) {
        mCurrentUsername = username;
    }

    /**
     * get the UserName of the currently logged in User.
     *
     * @return String, user name
     */
    public String getCurrentUsername() {
        return mCurrentUsername;
    }

    /**
     * set logged in password
     *
     * @param password
     */
    public void setCurrentPassword(String password) {
        mCurrentPassword = password;
    }

    /**
     * get the password of the currently logged in User.
     *
     * @return String, password
     */
    public String getCurrentPassword() {
        return mCurrentPassword;
    }

    /**
     * set product key for session
     *
     * @param productKey
     */

    public void setCurrentProductKey(String productKey) {
        mCurrentProductKey = productKey;
    }

    /**
     * get product key for session
     *
     * @return
     */
    public String getCurrentProductKey() {
        return mCurrentProductKey;
    }

    /**
     * set user type for session
     *
     * @param userType
     */

    public void setCurrentUserType(String userType) {
        mCurrentUserType = userType;
    }

    /**
     * get user type for session
     *
     * @return
     */
    public String getCurrentUserType() {
        return mCurrentUserType;
    }

    /**
     * set admin access for session
     *
     * @return
     */
    public void setAdminAccess(boolean hasAccess) {
        mCurrentAdminAccess = hasAccess;
    }

    /**
     * get admin access for session
     *
     * @return
     */
    public boolean hasAdminAccess() {
        return mCurrentAdminAccess;
    }

    /**
     * create a FNNote from a JSONObject
     *
     * @param message
     * @return FNNote
     */
    public static FNNote buildNote(JSONObject message) {
        return new FNNote.FieldNoteBuilder()
                .setNoteId(message.getString(TICKET_NUMBER_TAG))
                .setUserName(message.getString(USERNAME_TAG))
                .setWellName(message.getString(WELLNAME_TAG))
                .setTimeStart(message.getString(TIME_START_TAG))
                .setTimeEnd(message.getString(TIME_END_TAG))
                .setDateStart(message.getString(DATE_START_TAG))
                .setDateEnd(message.getString(DATE_END_TAG))
                .setMileageStart(message.getString(MILEAGE_START_TAG))
                .setMileageEnd(message.getString(MILEAGE_END_TAG))
                .setDescription(message.getString(DESCRIPTION_TAG))
                .setProjectNumber(message.getString(PROJECT_NUMBER_TAG))
                .setLocation(message.getString(LOCATION_TAG))
                .setBillingType(message.getString(BILLING_TAG))
                .setGPSCoords(message.getString(GPS_TAG))
                .setBillingState(message.getString(BILLING_STATE_TAG))
                .build();
    }

    /**
     * create a FNNote from a JSONObject for Readback
     *
     * @param message
     * @return FNNote
     */
    public static FNNote buildNoteForReadback(JSONObject message) {
        return new FNNote.FieldNoteBuilder()
                .setNoteId(message.getString(TICKET_NUMBER_TAG))
                .setUserName(message.getString(USERNAME_TAG))
                .setWellName(message.getString(WELLNAME_TAG))
                .setTimeStart(message.getString(TIME_START_TAG))
                .setTimeEnd(message.getString(TIME_END_TAG))
                .setDateStart(message.getString(DATE_START_TAG))
                .setDateEnd(message.getString(DATE_END_TAG))
                .setMileageStart(message.getString(MILEAGE_START_TAG))
                .setMileageEnd(message.getString(MILEAGE_END_TAG))
                .setDescription(message.getString(DESCRIPTION_TAG))
                .setProjectNumber(message.getString(PROJECT_NUMBER_TAG))
                .setLocation(message.getString(LOCATION_TAG))
                .setBillingType(message.getString(BILLING_TAG))
                .setGPSCoords(message.getString(GPS_TAG))
                .build();
    }

    /**
     * create a FNUser from a JSONObject
     *
     * @param message
     * @return FNUser
     */
    public static FNUser buildUser(JSONObject message) {
        return new FNUser.FNUserBuilder()
                .setId(message.getString(USER_USER_ID_TAG))
                .setUsername(message.getString(USER_USERNAME_TAG))
                .setPassword(message.getString(USER_PASSWORD_TAG))
                .setType(message.getString(USER_TYPE_TAG))
                .build();
    }

    /**
     * Retreive the FieldNotes from within a ResultSet
     *
     * @param resultSet
     * @return ArrayList<FNNote>
     * @throws SQLException
     */
    public static ArrayList<FNNote> retrieveFieldNotes(ResultSet resultSet) throws SQLException {

        ArrayList<FNNote> notes = new ArrayList<>();

        if (resultSet != null) {
            while (resultSet.next()) {
                FNNote note = new FNNote.FieldNoteBuilder()
                        .setUserName(resultSet.getString(FNPConstants.USERNAME_TAG))
                        .setNoteId(resultSet.getString(FNPConstants.TICKET_NUMBER_TAG))
                        .setWellName(resultSet.getString(FNPConstants.WELLNAME_TAG))
                        .setDateStart(resultSet.getString(FNPConstants.DATE_START_TAG))
                        .setTimeStart(resultSet.getString(FNPConstants.TIME_START_TAG))
                        .setMileageStart(resultSet.getString(FNPConstants.MILEAGE_START_TAG))
                        .setDescription(resultSet.getString(FNPConstants.DESCRIPTION_TAG))
                        .setMileageEnd(resultSet.getString(FNPConstants.MILEAGE_END_TAG))
                        .setDateEnd(resultSet.getString(FNPConstants.DATE_END_TAG))
                        .setTimeEnd(resultSet.getString(FNPConstants.TIME_END_TAG))
                        .setProjectNumber(resultSet.getString(FNPConstants.PROJECT_NUMBER_TAG))
                        .setLocation(resultSet.getString(FNPConstants.LOCATION_TAG))
                        .setGPSCoords(resultSet.getString(FNPConstants.GPS_TAG))
                        .setBillingType(resultSet.getString(FNPConstants.BILLING_TAG))
                        .setBillingState(resultSet.getString(FNPConstants.BILLING_STATE_TAG))
                        .build();

                notes.add(note);
            }
            resultSet.close();
        }
        return notes;
    }

    /**
     * Print an array of FieldNotes to the JTextArea
     *
     * @param notes
     * @param textArea
     */
    public static void printFieldNotesToJTextArea(ArrayList<FNNote> notes, JTextArea textArea) {
        for (FNNote each : notes) {
            textArea.append(FNCPConstants.CRUD_SEARCH_NOTE_NUMBER + " " + each.getTicketNumber() + "\n");
            textArea.append(FNCPConstants.FN_USERNAME_LABEL + " " + each.getUserName() + "\n");
            textArea.append(FNCPConstants.FN_PROJECT_LABEL + " " + each.getProject() + "\n");
            textArea.append(FNCPConstants.FN_WELLNAME_LABEL + " " + each.getWellName() + "\n");
            textArea.append(FNCPConstants.FN_LOCATION_LABEL + " " + each.getLocation() + "\n");
            textArea.append(FNCPConstants.FN_BILLING_LABEL + " " + each.getBillingType() + "\n");
            textArea.append(FNCPConstants.FN_DATE_START_LABEL + " " + each.getDateStart() + "\n");
            textArea.append(FNCPConstants.FN_DATE_END_LABEL + " " + each.getDateEnd() + "\n");
            textArea.append(FNCPConstants.FN_TIME_START_LABEL + " " + each.getTimeStart() + "\n");
            textArea.append(FNCPConstants.FN_TIME_END_LABEL + " " + each.getTimeEnd() + "\n");
            textArea.append(FNCPConstants.FN_MILEAGE_START_LABEL + " " + each.getMileageStart() + "\n");
            textArea.append(FNCPConstants.FN_MILEAGE_END_LABEL + " " + each.getMileageEnd() + "\n");
            textArea.append(FNCPConstants.FN_DESCRIPTION_LABEL + " " + each.getDescription() + "\n");
            textArea.append(FNCPConstants.FN_GPS_LABEL + " " + each.getGPSCoords() + "\n\n");
        }
    }

    /**
     * return the contents of a FNNote as a String value
     *
     * @param note
     * @return String
     */
    public static String getNoteAsString(FNNote note) {

        return (FNCPConstants.CRUD_SEARCH_NOTE_NUMBER + " " + note.getTicketNumber() + "\n") +
                FNCPConstants.FN_USERNAME_LABEL + " " + note.getUserName() + "\n" +
                FNCPConstants.FN_PROJECT_LABEL + " " + note.getProject() + "\n" +
                FNCPConstants.FN_WELLNAME_LABEL + " " + note.getWellName() + "\n" +
                FNCPConstants.FN_LOCATION_LABEL + " " + note.getLocation() + "\n" +
                FNCPConstants.FN_BILLING_LABEL + " " + note.getBillingType() + "\n" +
                FNCPConstants.FN_DATE_START_LABEL + " " + note.getDateStart() + "\n" +
                FNCPConstants.FN_DATE_END_LABEL + " " + note.getDateEnd() + "\n" +
                FNCPConstants.FN_TIME_START_LABEL + " " + note.getTimeStart() + "\n" +
                FNCPConstants.FN_TIME_END_LABEL + " " + note.getTimeEnd() + "\n" +
                FNCPConstants.FN_MILEAGE_START_LABEL + " " + note.getMileageStart() + "\n" +
                FNCPConstants.FN_MILEAGE_END_LABEL + " " + note.getMileageEnd() + "\n" +
                FNCPConstants.FN_DESCRIPTION_LABEL + " " + note.getDescription() + "\n" +
                FNCPConstants.FN_GPS_LABEL + " " + note.getGPSCoords() + "\n\n" +
                FNCPConstants.FN_BILLING_STATE_LABEL + " " + note.getBillingState() + "\n\n" +
                FNCPConstants.FN_BILLING_TOTAL_HOURS + " " + calculateHours(note) + "\n" +
                FNCPConstants.FN_BILLING_TOTAL_MILEAGE + " " + calculateMileage(note) + "\n";
    }

    /**
     * return the contents of a FNNote as a String value for Readback
     *
     * @param note
     * @return String
     */
    public static String getNoteAsStringForReadback(FNNote note) {

        return (FNCPConstants.CRUD_SEARCH_NOTE_NUMBER + " " + note.getTicketNumber() + "\n") +
                FNCPConstants.FN_USERNAME_LABEL + " " + note.getUserName() + "\n" +
                FNCPConstants.FN_PROJECT_LABEL + " " + note.getProject() + "\n" +
                FNCPConstants.FN_WELLNAME_LABEL + " " + note.getWellName() + "\n" +
                FNCPConstants.FN_LOCATION_LABEL + " " + note.getLocation() + "\n" +
                FNCPConstants.FN_BILLING_LABEL + " " + note.getBillingType() + "\n" +
                FNCPConstants.FN_DATE_START_LABEL + " " + note.getDateStart() + "\n" +
                FNCPConstants.FN_DATE_END_LABEL + " " + note.getDateEnd() + "\n" +
                FNCPConstants.FN_TIME_START_LABEL + " " + note.getTimeStart() + "\n" +
                FNCPConstants.FN_TIME_END_LABEL + " " + note.getTimeEnd() + "\n" +
                FNCPConstants.FN_MILEAGE_START_LABEL + " " + note.getMileageStart() + "\n" +
                FNCPConstants.FN_MILEAGE_END_LABEL + " " + note.getMileageEnd() + "\n" +
                FNCPConstants.FN_DESCRIPTION_LABEL + " " + note.getDescription() + "\n" +
                FNCPConstants.FN_GPS_LABEL + " " + note.getGPSCoords() + "\n\n";
    }

    /**
     * return the contents of a FNUser as a String value for readback
     *
     * @param user
     * @return String
     */
    public static String getUserAsString(FNUser user) {

        return (FNCPConstants.USER_ID_LABEL + " " + user.getId() + "\n") +
                FNCPConstants.USER_USERNAME_LABEL + " " + user.getName() + "\n" +
                FNCPConstants.USER_PASSWORD_LABEL + " " + user.getPassword() + "\n" +
                FNCPConstants.USER_USER_TYPE_LABEL + " " + user.getType() + "\n";
    }

    /**
     * calculate the number of miles included in a FNNote
     *
     * @param note
     * @return String
     */
    private static String calculateMileage(FNNote note) {
        return String.valueOf(Integer.parseInt(note.getMileageEnd()) - Integer.parseInt(note.getMileageStart()));
    }

    /**
     * calculate the number of billable hours from a provided date/time range
     *
     * @param note
     * @return String
     */
    private static String calculateHours(FNNote note) {
        final String dateFormat = "yyyy-MM-dd";
        final String timeFormat = "HH:mm";
        final int MILLI_TO_MINUTE = 1000 * 60;
        final int MILLI_TO_HOUR = MILLI_TO_MINUTE * 60;
        String hourString = null;

        try {
            Date dateStart = new SimpleDateFormat(dateFormat).parse(FNValidation.validateDate(note.getDateStart()));
            Date dateEnd = new SimpleDateFormat(dateFormat).parse(FNValidation.validateDate(note.getDateEnd()));
            Date timeStart = new SimpleDateFormat(timeFormat).parse(FNValidation.validateTime(note.getTimeStart()));
            Date timeEnd = new SimpleDateFormat(timeFormat).parse(FNValidation.validateTime(note.getTimeEnd()));

            Date fullStartDate = combine(dateStart, timeStart);
            Date fullEndDate = combine(dateEnd, timeEnd);

            // get hours
            int hours = (int) (fullEndDate.getTime() - fullStartDate.getTime()) / MILLI_TO_HOUR;
            //get minutes out of what is left over
            int mins = (int) ((fullEndDate.getTime() - fullStartDate.getTime()) % MILLI_TO_HOUR) / MILLI_TO_MINUTE;

            if (mins < 10) {
                hourString = hours + ":0" + mins;
            } else {
                hourString = hours + ":" + mins;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hourString;
    }

    /**
     * combine two Dates into one
     *
     * @param date
     * @param time
     * @return Date
     */
    private static Date combine(Date date, Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        return cal.getTime();
    }

    /**
     * allow apostrophes in descriptions
     *
     * @return string
     */
    public static String allowApostrophe(String string) {
        if (string.contains("'")) {
            return string.replace("'", "''");
        }
        return string;
    }

    /**
     * get the approved FieldNotes billing locations
     *
     * @return SpinnerModel of approved Locations
     */
    public SpinnerModel getLocations() {
        return mLocationModel;
    }

    /**
     * get the approved FieldNotes billing codes
     *
     * @return SpinnerModel of approved billing codes
     */
    public SpinnerModel getBillable() {
        return mBillableModel;
    }

    /**
     * get the current time
     *
     * @return current Date
     */
    public Date getZeroHour() {
        String timeStamp = mFormatter.format(Calendar.getInstance().getTime());
        Date now = null;
        try {
            now = mFormatter.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * get small label dimension
     *
     * @return small label dimension
     */
    public Dimension getSmallLabelDimension() {
        return mSmallLabelDimension;
    }

    /**
     * get standard label dimension
     *
     * @return standard label Dimension
     */
    public Dimension getStandardLabelDimension() {
        return mStandardLabelDimension;
    }

    /**
     * get large label dimension, used in login panel (4/23/2018)
     *
     * @return large label Dimension
     */
    public Dimension getLargeLabelDimension() {
        return mLargeLabelDimension;
    }

    /**
     * get standard TextField dimension, used for the TextFields holding Login Information
     * for example: login panel
     *
     * @return standard TextField Dimension
     */
    public Dimension getStandardTextFieldDimension() {
        return mStandardTextFieldDimensions;
    }

    /**
     * get large TextField dimension, used for the TextFields holding FNNote Values
     * for example: in the addData, edit, deleteData panels
     *
     * @return large TextField Dimension
     */
    public Dimension getLargeTextFieldDimen() {
        return mLargeTextFieldDimension;
    }

    /**
     * get login panel Dimension
     *
     * @return login panel Dimension
     */
    public Dimension getLoginPanelDimension() {
        return mLoginPanelDimension;
    }

    /**
     * get a standard button dimension
     *
     * @return standard button Dimension
     */
    public Dimension getStandardButtonDimension() {
        return mStandardButtonDimension;
    }

    /**
     * get primary FieldNotes blue color
     *
     * @return Color (blue)
     */
    public Color getPrimaryColor() {
        return mPrimaryColor;
    }

    /**
     * get the standard FieldNotes window border
     *
     * @return standard LineBorder (blue)
     */
    public LineBorder getLineBorder() {
        return new LineBorder(getPrimaryColor(), 1, true);
    }

    /**
     * get the standard rigid area for padding
     *
     * @return standard rigid area
     */
    public Dimension getStandardRigidArea() {
        return new Dimension(0, 75);
    }

    /**
     * reset ALL GUI elements within FieldNotes
     */
    public void resetAllGui() {

    }

}