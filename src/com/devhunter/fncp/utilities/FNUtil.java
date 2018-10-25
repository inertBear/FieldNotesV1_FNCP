/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.utilities;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.sql.billing.FNBillingController;
import com.devhunter.fncp.mvc.model.FieldNote;

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
        mLocationModel = new SpinnerListModel(FNConstants.APPROVED_BILLING_LOCATIONS);
        mBillableModel = new SpinnerListModel(FNConstants.APPROVED_BILLING_CODES);
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
     * Retreive the FieldNotes from within a ResultSet
     *
     * @param resultSet
     * @return ArrayList<FieldNote>
     * @throws SQLException
     */
    public static ArrayList<FieldNote> retrieveFieldNotes(ResultSet resultSet) throws SQLException {

        ArrayList<FieldNote> fieldNotes = new ArrayList<>();

        if (resultSet != null) {
            while (resultSet.next()) {
                FieldNote fieldNote = new FieldNote.FieldNoteBuilder()
                        .setUserName(resultSet.getString(FNSqlConstants.USER_COLUMN))
                        .setTicketNumber(resultSet.getString(FNSqlConstants.TICKET_COLUMN))
                        .setWellName(resultSet.getString(FNSqlConstants.WELLNAME_COLUMN))
                        .setDateStart(resultSet.getString(FNSqlConstants.DATESTART_COLUMN))
                        .setTimeStart(resultSet.getString(FNSqlConstants.TIMESTART_COLUMN))
                        .setMileageStart(resultSet.getString(FNSqlConstants.MILEAGESTART_COLUMN))
                        .setDescription(resultSet.getString(FNSqlConstants.DESCRIPTION_COLUMN))
                        .setMileageEnd(resultSet.getString(FNSqlConstants.MILEAGEEND_COLUMN))
                        .setDateEnd(resultSet.getString(FNSqlConstants.DATEEND_COLUMN))
                        .setTimeEnd(resultSet.getString(FNSqlConstants.TIMEEND_COLUMN))
                        .setProjectNumber(resultSet.getString(FNSqlConstants.PROJECTNUMBER_COLUMN))
                        .setLocation(resultSet.getString(FNSqlConstants.LOCATION_COLUMN))
                        .setGPSCoords(resultSet.getString(FNSqlConstants.GPSCOORDS_COLUMN))
                        .setBillingType(resultSet.getString(FNSqlConstants.BILLING_COLUMN))
                        .setBillingState(resultSet.getString(FNSqlConstants.BILLING_STATE_COLUMN))
                        .build();

                fieldNotes.add(fieldNote);
            }
            resultSet.close();
        }
        return fieldNotes;
    }

    /**
     * Print an array of FieldNotes to the JTextArea
     *
     * @param fieldNotes
     * @param textArea
     */
    public static void printFieldNotesToJTextArea(ArrayList<FieldNote> fieldNotes, JTextArea textArea) {
        for (FieldNote each : fieldNotes) {
            textArea.append(FNConstants.CRUD_SEARCH_TICKET_NUMBER + " " + each.getTicketNumber() + "\n");
            textArea.append(FNConstants.FN_USERNAME_LABEL + " " + each.getUserName() + "\n");
            textArea.append(FNConstants.FN_PROJECT_LABEL + " " + each.getProject() + "\n");
            textArea.append(FNConstants.FN_WELLNAME_LABEL + " " + each.getWellName() + "\n");
            textArea.append(FNConstants.FN_LOCATION_LABEL + " " + each.getLocation() + "\n");
            textArea.append(FNConstants.FN_BILLING_LABEL + " " + each.getBillingType() + "\n");
            textArea.append(FNConstants.FN_DATE_START_LABEL + " " + each.getDateStart() + "\n");
            textArea.append(FNConstants.FN_DATE_END_LABEL + " " + each.getDateEnd() + "\n");
            textArea.append(FNConstants.FN_TIME_START_LABEL + " " + each.getTimeStart() + "\n");
            textArea.append(FNConstants.FN_TIME_END_LABEL + " " + each.getTimeEnd() + "\n");
            textArea.append(FNConstants.FN_MILEAGE_START_LABEL + " " + each.getMileageStart() + "\n");
            textArea.append(FNConstants.FN_MILEAGE_END_LABEL + " " + each.getMileageEnd() + "\n");
            textArea.append(FNConstants.FN_DESCRIPTION_LABEL + " " + each.getDescription() + "\n");
            textArea.append(FNConstants.FN_GPS_LABEL + " " + each.getGPSCoords() + "\n\n");
        }
    }

    /**
     * return the contents of a FieldNote as a String value
     *
     * @param fieldNote
     * @return String
     */
    public static String getFieldNoteAsString(FieldNote fieldNote) {
        FNBillingController con = new FNBillingController();

        return (FNConstants.CRUD_SEARCH_TICKET_NUMBER + " " + fieldNote.getTicketNumber() + "\n") +
                FNConstants.FN_USERNAME_LABEL + " " + fieldNote.getUserName() + "\n" +
                FNConstants.FN_PROJECT_LABEL + " " + fieldNote.getProject() + "\n" +
                FNConstants.FN_WELLNAME_LABEL + " " + fieldNote.getWellName() + "\n" +
                FNConstants.FN_LOCATION_LABEL + " " + fieldNote.getLocation() + "\n" +
                FNConstants.FN_BILLING_LABEL + " " + fieldNote.getBillingType() + "\n" +
                FNConstants.FN_DATE_START_LABEL + " " + fieldNote.getDateStart() + "\n" +
                FNConstants.FN_DATE_END_LABEL + " " + fieldNote.getDateEnd() + "\n" +
                FNConstants.FN_TIME_START_LABEL + " " + fieldNote.getTimeStart() + "\n" +
                FNConstants.FN_TIME_END_LABEL + " " + fieldNote.getTimeEnd() + "\n" +
                FNConstants.FN_MILEAGE_START_LABEL + " " + fieldNote.getMileageStart() + "\n" +
                FNConstants.FN_MILEAGE_END_LABEL + " " + fieldNote.getMileageEnd() + "\n" +
                FNConstants.FN_DESCRIPTION_LABEL + " " + fieldNote.getDescription() + "\n" +
                FNConstants.FN_GPS_LABEL + " " + fieldNote.getGPSCoords() + "\n\n" +
                FNConstants.FN_BILLING_STATE_LABEL + " " + fieldNote.getBillingState() + "\n\n" +
                "Total hours: " + con.calculateHours(fieldNote) + "\n" +
                "Total mileage: " + con.calculateMilage(fieldNote) + "\n";
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
     * get large TextField dimension, used for the TextFields holding FieldNote Values
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
