/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.validation.CrudSearchValidation;
import com.devhunter.fncp.mvc.controller.validation.FNValidation;
import com.devhunter.fncp.mvc.controller.sql.FNDataController;
import com.devhunter.fncp.mvc.model.*;
import com.devhunter.fncp.mvc.model.FieldNote.FieldNoteBuilder;
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

public class EditDataPanel extends FNPanel {
    // CRUD Panels
    private static FNPanel mCrudSearchPanel;
    private FNPanel mCrudSearchTextFieldPanel;
    // Panels
    private static EditDataPanel sInstance;
    private static FNPanel mEditFNDataPanel;
    private FNPanel mEditFNButtonPanel;
    // TextFields
    private FNTextField mCRUDSearch;
    private FNTextField mTextEditDataName;
    private FNTextField mTextEditDataWellName;
    private FNTextField mTextEditDataMileageStart;
    private FNTextField mTextEditDataDescription;
    private FNTextField mTextEditDataMileageEnd;
    private FNTextField mTextEditDataProject;
    private FNTextField mTextEditDataGPS;
    // Spinners
    private JSpinner mSpinnerEditDataLocation;
    private JSpinner mSpinnerEditDataBillable;
    // TimeChoosers
    private final JTimeChooser mEditTicketTimeStart;
    private final JTimeChooser mEditTicketTimeEnd;
    // DatePickers
    private UtilDateModel mEditTicketStartModel;
    private Properties mEditTicketStartProperties;
    private JDatePickerImpl mEditTicketStartDatePicker;
    private UtilDateModel mEditTicketEndModel;
    private Properties mEditTicketEndProperties;
    private JDatePickerImpl mEditTicketEndDatePicker;
    // Buttons
    private FNButton buttonEdit;
    // Strings
    private String mFlexTicketNumber;

    private EditDataPanel() {
        // Create CRUD Search Panels
        mCrudSearchPanel = new FNPanel();
        mCrudSearchTextFieldPanel = new FNPanel();
        // create Panels
        mEditFNDataPanel = new FNPanel();
        mEditFNButtonPanel = new FNPanel();
        // create TextFields
        mCRUDSearch = new FNTextField();
        mTextEditDataName = new FNTextField();
        mTextEditDataWellName = new FNTextField();
        mTextEditDataMileageStart = new FNTextField();
        mTextEditDataDescription = new FNTextField();
        mTextEditDataMileageEnd = new FNTextField();
        mTextEditDataProject = new FNTextField();
        mTextEditDataGPS = new FNTextField();
        // create Spinners
        mSpinnerEditDataLocation = new JSpinner(FNUtil.getInstance().getLocations());
        mSpinnerEditDataBillable = new JSpinner(FNUtil.getInstance().getBillable());
        // create TimeChooser
        mEditTicketTimeStart = new JTimeChooser(new Date());
        mEditTicketTimeEnd = new JTimeChooser(new Date());
        // create DatePickers
        mEditTicketStartModel = new UtilDateModel();
        mEditTicketStartProperties = new Properties();
        JDatePanelImpl mEditTicketStartDatePanel = new JDatePanelImpl(mEditTicketStartModel, mEditTicketStartProperties);
        mEditTicketStartDatePicker = new JDatePickerImpl(mEditTicketStartDatePanel, new DateLabelFormatter());
        mEditTicketEndModel = new UtilDateModel();
        mEditTicketEndProperties = new Properties();
        JDatePanelImpl mEditTicketEndDatePanel = new JDatePanelImpl(mEditTicketEndModel, mEditTicketEndProperties);
        mEditTicketEndDatePicker = new JDatePickerImpl(mEditTicketEndDatePanel, new DateLabelFormatter());
        // create Buttons
        buttonEdit = new FNButton(FNConstants.BUTTON_UPDATE);
        // create Strings
        mFlexTicketNumber = "";
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
        // CRUD Search Labels
        FNLabel crudTicketLabel = new FNLabel(FNConstants.CRUD_SEARCH_TICKET_NUMBER);
        // CRUD Search Buttons
        FNButton buttonCrudSearch = new FNButton(FNConstants.BUTTON_SEARCH);
        // Panels/Layouts
        GridLayout editNDataPanelLayout = new GridLayout(0, 2);
        mEditFNDataPanel.setLayout(editNDataPanelLayout);
        FlowLayout editDataPanelLayout = new FlowLayout();
        mEditFNButtonPanel.setLayout(editDataPanelLayout);
        // Labels
        FNLabel lblEditDataName = new FNLabel(FNConstants.FN_USERNAME_LABEL);
        FNLabel lblEditDataWellName = new FNLabel(FNConstants.FN_WELLNAME_LABEL);
        FNLabel lblEditDataDateStart = new FNLabel(FNConstants.FN_DATE_START_LABEL);
        FNLabel lblEditDataTimeStart = new FNLabel(FNConstants.FN_TIME_START_LABEL);
        FNLabel lblEditDataMileageStart = new FNLabel(FNConstants.FN_MILEAGE_START_LABEL);
        FNLabel lblEditDataDescription = new FNLabel(FNConstants.FN_DESCRIPTION_LABEL);
        FNLabel lblEditDataMileageEnd = new FNLabel(FNConstants.FN_MILEAGE_END_LABEL);
        FNLabel lblEditDataDateEnd = new FNLabel(FNConstants.FN_DATE_END_LABEL);
        FNLabel lblEditDataTimeEnd = new FNLabel(FNConstants.FN_TIME_END_LABEL);
        FNLabel lblEditDataProject = new FNLabel(FNConstants.FN_PROJECT_LABEL);
        FNLabel lblEditDataLocation = new FNLabel(FNConstants.FN_LOCATION_LABEL);
        FNLabel lblEditDataGPS = new FNLabel(FNConstants.FN_GPS_LABEL);
        FNLabel lblEditDataBillable = new FNLabel(FNConstants.FN_BILLING_LABEL);
        // NOT override the userName text field - in this case, they should be able to change the user if they need to.
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
        mCrudSearchTextFieldPanel.add(mCRUDSearch);
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
                if (CrudSearchValidation.validate(mCRUDSearch.getText())) {
                    //mCrudSearchPanel.setVisible(false);
                    mFlexTicketNumber = mCRUDSearch.getText();
                    // send Ticket Number to controller for CRUD searchData
                    FNDataController conn = new FNDataController();
                    FieldNote searchResult = conn.searchDataByTicketNumber(mFlexTicketNumber);
                    // if the returned value has a ticket number, then it is a valid FieldNote
                    if (searchResult.getTicketNumber() == null) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "No Data Found in Database");
                        mEditFNDataPanel.setVisible(false);
                        FNControlPanel.getFieldNotesFrame().repaint();
                        FNControlPanel.getFieldNotesFrame().revalidate();
                    } else {
                        if (FNUtil.getInstance().hasAdminAccess() || searchResult.getUserName().equals(FNUtil.getInstance().getCurrentUsername())) {
                            mEditFNDataPanel.setVisible(true);
                            mCRUDSearch.setText(mFlexTicketNumber);

                            mTextEditDataName.setText(searchResult.getUserName());
                            mTextEditDataWellName.setText(searchResult.getWellName());
                            mEditTicketStartDatePicker.getJFormattedTextField().setText(searchResult.getDateStart());
                            mEditTicketTimeStart.setTime(parseTime(searchResult.getTimeStart()));
                            mTextEditDataMileageStart.setText(searchResult.getMileageStart());
                            mTextEditDataDescription.setText(searchResult.getDescription());
                            mTextEditDataMileageEnd.setText(searchResult.getMileageEnd());
                            mEditTicketEndDatePicker.getJFormattedTextField().setText(searchResult.getDateEnd());
                            mEditTicketTimeEnd.setTime(parseTime(searchResult.getTimeEnd()));
                            mTextEditDataProject.setText(searchResult.getProjectNumber());
                            mSpinnerEditDataLocation.setValue(matchCase(searchResult.getLocation()));
                            mTextEditDataGPS.setText(searchResult.getGPSCoords());
                            mSpinnerEditDataBillable.setValue(matchCase(searchResult.getBillingType()));

                            FNControlPanel.getFieldNotesFrame().repaint();
                            FNControlPanel.getFieldNotesFrame().revalidate();
                        } else {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "You can only edit you own tickets");
                        }
                    }
                }
            }
        });

        buttonEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Build FieldNote
                FieldNote fieldNote = new FieldNoteBuilder()
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
                // Validate FieldNote
                if (FNValidation.validate(fieldNote)) {
                    // send to controller for CUD Event
                    FNDataController conn = new FNDataController();
                    boolean result = conn.updateFieldNote(fieldNote, mFlexTicketNumber);

                    // code 1 == success, code 0 == failure
                    if (result) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Note Changes Submitted");
                        resetGui();
                    } else {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Connection Error - NOTE NOT UPDATED");
                    }
                }
            }
        });
    }

    private String formatTime(String time) {
        return time.substring(0, 5);
    }

    /*
     * updates the older entries for billing and location to match the "approved" codes
     */
    private String matchCase(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

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
        mCRUDSearch.setText(null);
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
        mEditTicketStartDatePicker.getJFormattedTextField().setText("");
        mEditTicketEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mEditTicketEndDatePicker.getJFormattedTextField().setText("");
    }
}
