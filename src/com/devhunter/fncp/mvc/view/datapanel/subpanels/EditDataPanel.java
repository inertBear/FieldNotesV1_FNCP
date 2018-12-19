/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import com.devhunter.fncp.constants.FNCPConstants;
import com.devhunter.fncp.mvc.controller.FNDataController;
import com.devhunter.fncp.mvc.controller.validation.CrudSearchValidation;
import com.devhunter.fncp.mvc.controller.validation.FNValidation;
import com.devhunter.fncp.mvc.model.FNNote;
import com.devhunter.fncp.mvc.model.FNNote.FieldNoteBuilder;
import com.devhunter.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;
import lu.tudor.santec.jtimechooser.JTimeChooser;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import static com.devhunter.fncp.constants.FNPConstants.*;

public class EditDataPanel extends FNPanel {

    private static FNPanel mCrudSearchPanel;
    private FNPanel mCrudSearchTextFieldPanel;
    private static EditDataPanel sInstance;
    private static FNPanel mEditFNDataPanel;
    private FNPanel mEditFNButtonPanel;
    private FNTextField mEditTicketNumber;
    private FNTextField mTextEditDataName;
    private FNTextField mTextEditDataWellName;
    private FNTextField mTextEditDataMileageStart;
    private FNTextField mTextEditDataDescription;
    private FNTextField mTextEditDataMileageEnd;
    private FNTextField mTextEditDataProject;
    private FNTextField mTextEditDataGPS;
    private JSpinner mSpinnerEditDataLocation;
    private JSpinner mSpinnerEditDataBillable;
    private final JTimeChooser mEditTicketTimeStart;
    private final JTimeChooser mEditTicketTimeEnd;
    private UtilDateModel mEditTicketStartModel;
    private Properties mEditTicketStartProperties;
    private JDatePickerImpl mEditTicketStartDatePicker;
    private UtilDateModel mEditTicketEndModel;
    private Properties mEditTicketEndProperties;
    private JDatePickerImpl mEditTicketEndDatePicker;
    private FNButton buttonEdit;
    private String mEditTicketNumberString;

    private EditDataPanel() {
        mCrudSearchPanel = new FNPanel();
        mCrudSearchTextFieldPanel = new FNPanel();
        mEditFNDataPanel = new FNPanel();
        mEditFNButtonPanel = new FNPanel();
        mEditTicketNumber = new FNTextField();
        mTextEditDataName = new FNTextField();
        mTextEditDataWellName = new FNTextField();
        mTextEditDataMileageStart = new FNTextField();
        mTextEditDataDescription = new FNTextField();
        mTextEditDataMileageEnd = new FNTextField();
        mTextEditDataProject = new FNTextField();
        mTextEditDataGPS = new FNTextField();
        mSpinnerEditDataLocation = new JSpinner(FNUtil.getInstance().getLocations());
        mSpinnerEditDataBillable = new JSpinner(FNUtil.getInstance().getBillable());
        mEditTicketTimeStart = new JTimeChooser(new Date());
        mEditTicketTimeEnd = new JTimeChooser(new Date());
        mEditTicketStartModel = new UtilDateModel();
        mEditTicketStartProperties = new Properties();
        JDatePanelImpl mEditTicketStartDatePanel = new JDatePanelImpl(mEditTicketStartModel, mEditTicketStartProperties);
        mEditTicketStartDatePicker = new JDatePickerImpl(mEditTicketStartDatePanel, new DateLabelFormatter());
        mEditTicketEndModel = new UtilDateModel();
        mEditTicketEndProperties = new Properties();
        JDatePanelImpl mEditTicketEndDatePanel = new JDatePanelImpl(mEditTicketEndModel, mEditTicketEndProperties);
        mEditTicketEndDatePicker = new JDatePickerImpl(mEditTicketEndDatePanel, new DateLabelFormatter());
        buttonEdit = new FNButton(FNCPConstants.BUTTON_UPDATE);
        mEditTicketNumberString = "";

        init();
    }

    public static EditDataPanel getInstance() {
        if (sInstance == null) {
            sInstance = new EditDataPanel();
        }
        return sInstance;
    }

    private void init() {
        // CRUD Search Panel Layouts
        BorderLayout crudSearchLayout = new BorderLayout();
        mCrudSearchPanel.setLayout(crudSearchLayout);
        GridLayout crudSearchTextFieldPanelLayout = new GridLayout(0, 2);
        mCrudSearchTextFieldPanel.setLayout(crudSearchTextFieldPanelLayout);

        FNLabel crudTicketLabel = new FNLabel(FNCPConstants.CRUD_SEARCH_NOTE_NUMBER);
        FNButton buttonCrudSearch = new FNButton(FNCPConstants.BUTTON_SEARCH);

        GridLayout editNDataPanelLayout = new GridLayout(0, 2);
        mEditFNDataPanel.setLayout(editNDataPanelLayout);
        FlowLayout editDataPanelLayout = new FlowLayout();
        mEditFNButtonPanel.setLayout(editDataPanelLayout);

        FNLabel lblEditDataName = new FNLabel(FNCPConstants.FN_USERNAME_LABEL);
        FNLabel lblEditDataWellName = new FNLabel(FNCPConstants.FN_WELLNAME_LABEL);
        FNLabel lblEditDataDateStart = new FNLabel(FNCPConstants.FN_DATE_START_LABEL);
        FNLabel lblEditDataTimeStart = new FNLabel(FNCPConstants.FN_TIME_START_LABEL);
        FNLabel lblEditDataMileageStart = new FNLabel(FNCPConstants.FN_MILEAGE_START_LABEL);
        FNLabel lblEditDataDescription = new FNLabel(FNCPConstants.FN_DESCRIPTION_LABEL);
        FNLabel lblEditDataMileageEnd = new FNLabel(FNCPConstants.FN_MILEAGE_END_LABEL);
        FNLabel lblEditDataDateEnd = new FNLabel(FNCPConstants.FN_DATE_END_LABEL);
        FNLabel lblEditDataTimeEnd = new FNLabel(FNCPConstants.FN_TIME_END_LABEL);
        FNLabel lblEditDataProject = new FNLabel(FNCPConstants.FN_PROJECT_LABEL);
        FNLabel lblEditDataLocation = new FNLabel(FNCPConstants.FN_LOCATION_LABEL);
        FNLabel lblEditDataGPS = new FNLabel(FNCPConstants.FN_GPS_LABEL);
        FNLabel lblEditDataBillable = new FNLabel(FNCPConstants.FN_BILLING_LABEL);

        // Customize Spinner properties
        mSpinnerEditDataLocation.setPreferredSize(FNUtil.getInstance().getLargeTextFieldDimen());
        mSpinnerEditDataLocation.setEditor(new JSpinner.DefaultEditor(mSpinnerEditDataLocation));
        mSpinnerEditDataBillable.setPreferredSize(FNUtil.getInstance().getLargeTextFieldDimen());
        mSpinnerEditDataBillable.setEditor(new JSpinner.DefaultEditor(mSpinnerEditDataBillable));

        // set DatePickers properties
        mEditTicketStartProperties.put("text.today", "Today");
        mEditTicketStartProperties.put("text.month", "Month");
        mEditTicketStartProperties.put("text.year", "Year");
        mEditTicketEndProperties.put("text.today", "Today");
        mEditTicketEndProperties.put("text.month", "Month");
        mEditTicketEndProperties.put("text.year", "Year");

        // Add Views to panel
        mEditFNDataPanel.add(lblEditDataName);
        mEditFNDataPanel.add(mTextEditDataName);
        mEditFNDataPanel.add(lblEditDataWellName);
        mEditFNDataPanel.add(mTextEditDataWellName);
        mEditFNDataPanel.add(lblEditDataDateStart);
        mEditFNDataPanel.add(mEditTicketStartDatePicker);
        mEditFNDataPanel.add(lblEditDataTimeStart);
        mEditFNDataPanel.add(mEditTicketTimeStart);
        mEditFNDataPanel.add(lblEditDataMileageStart);
        mEditFNDataPanel.add(mTextEditDataMileageStart);
        mEditFNDataPanel.add(lblEditDataDescription);
        mEditFNDataPanel.add(mTextEditDataDescription);
        mEditFNDataPanel.add(lblEditDataMileageEnd);
        mEditFNDataPanel.add(mTextEditDataMileageEnd);
        mEditFNDataPanel.add(lblEditDataDateEnd);
        mEditFNDataPanel.add(mEditTicketEndDatePicker);
        mEditFNDataPanel.add(lblEditDataTimeEnd);
        mEditFNDataPanel.add(mEditTicketTimeEnd);
        mEditFNDataPanel.add(lblEditDataProject);
        mEditFNDataPanel.add(mTextEditDataProject);
        mEditFNDataPanel.add(lblEditDataLocation);
        mEditFNDataPanel.add(mSpinnerEditDataLocation);
        mEditFNDataPanel.add(lblEditDataGPS);
        mEditFNDataPanel.add(mTextEditDataGPS);
        mEditFNDataPanel.add(lblEditDataBillable);
        mEditFNDataPanel.add(mSpinnerEditDataBillable);
        mEditFNDataPanel.add(new FNLabel(""));
        mEditFNDataPanel.add(buttonEdit);
        //Add CRUD Search Views to CRUD Search TextField Panel
        mCrudSearchTextFieldPanel.add(crudTicketLabel);
        mCrudSearchTextFieldPanel.add(mEditTicketNumber);
        mCrudSearchTextFieldPanel.add(new FNLabel());
        mCrudSearchTextFieldPanel.add(buttonCrudSearch);
        // Add CRUD Search Views to Main Panel
        mCrudSearchPanel.add(mCrudSearchTextFieldPanel, BorderLayout.NORTH);
        mCrudSearchPanel.add(mEditFNDataPanel, BorderLayout.CENTER);

        // set initial CRUD Search View settings
        mCrudSearchPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        buttonCrudSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // CRUDSearch Validation
                mEditTicketNumberString = mEditTicketNumber.getText();
                if (CrudSearchValidation.validate(mEditTicketNumberString)) {
                    searchFieldNotes(mEditTicketNumberString);
                }
            }
        });

        buttonEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Build FNNote
                FNNote note = new FieldNoteBuilder()
                        .setNoteId(mEditTicketNumberString)
                        .setUserName(mTextEditDataName.getText())
                        .setWellName(mTextEditDataWellName.getText())
                        .setTimeStart(formatTime(mEditTicketTimeStart.getFormatedTime()))
                        .setTimeEnd(formatTime(mEditTicketTimeEnd.getFormatedTime()))
                        .setDateStart(mEditTicketStartDatePicker.getJFormattedTextField().getText())
                        .setDateEnd(mEditTicketEndDatePicker.getJFormattedTextField().getText())
                        .setMileageStart(mTextEditDataMileageStart.getText())
                        .setMileageEnd(mTextEditDataMileageEnd.getText())
                        .setDescription(mTextEditDataDescription.getText())
                        .setWellName(mTextEditDataWellName.getText())
                        .setProjectNumber(mTextEditDataProject.getText())
                        .setLocation((String) mSpinnerEditDataLocation.getValue())
                        .setGPSCoords(mTextEditDataGPS.getText())
                        .setBillingType((String) mSpinnerEditDataBillable.getValue())
                        .build();
                // Validate FNNote
                if (FNValidation.validate(note)) {
                    updateFieldNote(note);
                }
            }
        });


    }

    /**
     * update an existing FNNote
     *
     * @param note
     */
    private void updateFieldNote(FNNote note) {
        JSONObject updateResult = FNDataController.updateFieldNote(note);
        String status = updateResult.getString(RESPONSE_STATUS_TAG);
        String message = updateResult.getString(RESPONSE_MESSAGE_TAG);

        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), message);
        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            resetGui();
            mCrudSearchPanel.setVisible(true);
        }
    }

    private void searchFieldNotes(String ticketNumber) {
        JSONObject searchResult = FNDataController.searchFieldNotes(null, null, null, ticketNumber);
        String status = searchResult.getString(RESPONSE_STATUS_TAG);
        String messageString = searchResult.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            JSONArray messageArray = new JSONArray(messageString);
            JSONObject message = messageArray.getJSONObject(0);

            mEditFNDataPanel.setVisible(true);

            // show searchData bar and field note data
            mEditTicketNumber.setText(mEditTicketNumberString);
            mTextEditDataName.setText(message.getString(USERNAME_TAG));
            mTextEditDataWellName.setText(message.getString(WELLNAME_TAG));
            mEditTicketStartDatePicker.getJFormattedTextField().setText(message.getString(DATE_START_TAG));
            mEditTicketEndDatePicker.getJFormattedTextField().setText(message.getString(DATE_END_TAG));
            mEditTicketTimeStart.setTime(parseTime(message.getString(TIME_START_TAG)));
            mEditTicketTimeEnd.setTime(parseTime(message.getString(TIME_END_TAG)));
            mTextEditDataMileageStart.setText(message.getString(MILEAGE_START_TAG));
            mTextEditDataDescription.setText(message.getString(DESCRIPTION_TAG));
            mTextEditDataMileageEnd.setText(message.getString(MILEAGE_END_TAG));
            mTextEditDataProject.setText(message.getString(PROJECT_NUMBER_TAG));
            mSpinnerEditDataLocation.setValue(message.getString(LOCATION_TAG));
            mTextEditDataGPS.setText(message.getString(GPS_TAG));
            mSpinnerEditDataBillable.setValue(message.getString(BILLING_TAG));

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();

            // show searchData bar and field note data
            mEditFNDataPanel.setVisible(true);
        } else {
            mEditFNDataPanel.setVisible(false);
            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();

            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
        }
    }

    private String formatTime(String time) {
        return time.substring(0, 5);
    }

    /**
     * updates the older entries for billing and location to match the "approved" codes
     **/
    private String matchCase(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * parse time string for entry into TimePicker
     *
     * @param string
     * @return
     */
    private Time parseTime(String string) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time timeValue = null;
        try {
            timeValue = new Time(formatter.parse(string).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeValue;
    }

    public static JPanel getView() {
        return mCrudSearchPanel;
    }

    public static void showView() {
        mCrudSearchPanel.setVisible(true);
    }

    public static void hideView() {
        mCrudSearchPanel.setVisible(false);
        sInstance.resetGui();
    }

    private void resetGui() {
        // return to empty view
        mEditFNDataPanel.setVisible(false);
        mCrudSearchPanel.setVisible(false);
        // clear all values in the views
        mEditTicketTimeStart.setTime(FNUtil.getInstance().getZeroHour());
        mEditTicketTimeEnd.setTime(FNUtil.getInstance().getZeroHour());
        mEditTicketNumber.setText(null);
        mTextEditDataName.setText(null);
        mTextEditDataWellName.setText(null);
        mTextEditDataMileageStart.setText(null);
        mTextEditDataDescription.setText(null);
        mTextEditDataMileageEnd.setText(null);
        mTextEditDataProject.setText(null);
        mSpinnerEditDataLocation.setValue("");
        mTextEditDataGPS.setText(null);
        mSpinnerEditDataBillable.setValue("");

        // Reset TimePickers to current Time
        LocalDate now = LocalDate.now();
        mEditTicketStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mEditTicketStartDatePicker.getJFormattedTextField().setText(null);
        mEditTicketEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mEditTicketEndDatePicker.getJFormattedTextField().setText(null);
    }
}
