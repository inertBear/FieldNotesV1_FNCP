/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.FNValidation;
import com.devhunter.fncp.mvc.controller.sql.SQLDataController;
import com.devhunter.fncp.mvc.model.*;
import com.devhunter.fncp.mvc.model.FieldNote.FieldNoteBuilder;
import com.devhunter.fncp.mvc.model.dateutils.DateLabelFormatter;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

public class AddDataPanel extends FNPanel {

    private static final long serialVersionUID = 1L;
    // Panels
    public static AddDataPanel sInstance;
    public static FNPanel mAddFNMainPanel;
    public FNPanel mAddFNButtonPanel;
    // TextFields
    private FNTextField mTextNewDataName;
    private FNTextField mTextNewDataWellName;
    private FNTextField mTextNewDataMileageStart;
    private FNTextField mTextNewDataDescription;
    private FNTextField mTextNewDataMileageEnd;
    private FNTextField mTextNewDataDateEnd;
    private FNTextField mTextNewDataTimeEnd;
    private FNTextField mTextNewDataProject;
    private FNTextField mTextNewDataGPS;
    // Spinners
    private JSpinner mSpinnerNewDataLocation;
    private JSpinner mSpinnerNewDataBillable;
    // TimeChoosers
    private final JTimeChooser mNewTicketTimeStart;
    private final JTimeChooser mNewTicketTimeEnd;
    // DatePickers
    private UtilDateModel mNewTicketStartModel;
    private Properties mNewTicketStartProperties;
    private JDatePanelImpl mNewTicketStartDatePanel;
    private JDatePickerImpl mNewTicketStartDatePicker;
    private UtilDateModel mNewTicketEndModel;
    private Properties mNewTicketEndProperties;
    private JDatePanelImpl mNewTicketEndDatePanel;
    private JDatePickerImpl mNewTicketEndDatePicker;
    // Buttons
    private FNButton buttonSubmit;

    private AddDataPanel() {
        // create Panels
        mAddFNMainPanel = new FNPanel();
        mAddFNButtonPanel = new FNPanel();
        // create TextFields
        mTextNewDataName = new FNTextField();
        mTextNewDataWellName = new FNTextField();
        mTextNewDataMileageStart = new FNTextField();
        mTextNewDataDescription = new FNTextField();
        mTextNewDataMileageEnd = new FNTextField();
        mTextNewDataDateEnd = new FNTextField();
        mTextNewDataTimeEnd = new FNTextField();
        mTextNewDataProject = new FNTextField();
        mTextNewDataGPS = new FNTextField();
        // create Spinners
        mSpinnerNewDataLocation = new JSpinner(FNUtil.getInstance().getLocations());
        mSpinnerNewDataBillable = new JSpinner(FNUtil.getInstance().getBillable());
        // create TimeChoosers
        mNewTicketTimeStart = new JTimeChooser(new Date());
        mNewTicketTimeEnd = new JTimeChooser(new Date());
        // create DatePickers
        // FIXME: need to resize the JDatePicker's TextFields
        mNewTicketStartModel = new UtilDateModel();
        mNewTicketStartProperties = new Properties();
        mNewTicketStartDatePanel = new JDatePanelImpl(mNewTicketStartModel, mNewTicketStartProperties);
        mNewTicketStartDatePanel.setSize(FNUtil.getInstance().getLargeTextFieldDimen());
        mNewTicketStartDatePicker = new JDatePickerImpl(mNewTicketStartDatePanel, new DateLabelFormatter());
        mNewTicketEndModel = new UtilDateModel();
        mNewTicketEndProperties = new Properties();
        mNewTicketEndDatePanel = new JDatePanelImpl(mNewTicketEndModel, mNewTicketEndProperties);
        mNewTicketEndDatePicker = new JDatePickerImpl(mNewTicketEndDatePanel, new DateLabelFormatter());
        // create Buttons
        buttonSubmit = new FNButton(FNConstants.BUTTON_SUBMIT);
        init();
    }

    public static AddDataPanel getInstance() {
        if (sInstance == null) {
            sInstance = new AddDataPanel();
        }
        return sInstance;
    }

    void init() {
        // Panels/Layouts
        GridLayout addFNDataPanelLayout = new GridLayout(0, 2);
        mAddFNMainPanel.setLayout(addFNDataPanelLayout);
        FlowLayout addFNButtonPanelLayout = new FlowLayout();
        mAddFNButtonPanel.setLayout(addFNButtonPanelLayout);
        mAddFNButtonPanel.setBorder(FNUtil.getInstance().getLineBorder());
        // Labels
        FNLabel lblNewDataName = new FNLabel(FNConstants.FN_USERNAME_LABEL);
        FNLabel lblNewDataWellName = new FNLabel(FNConstants.FN_WELLNAME_LABEL);
        FNLabel lblNewDataDateStart = new FNLabel(FNConstants.FN_DATE_START_LABEL);
        FNLabel lblNewDataTimeStart = new FNLabel(FNConstants.FN_TIME_START_LABEL);
        FNLabel lblNewDataMileageStart = new FNLabel(FNConstants.FN_MILEAGE_START_LABEL);
        FNLabel lblNewDataDescription = new FNLabel(FNConstants.FN_DESCRIPTION_LABEL);
        FNLabel lblNewDataMileageEnd = new FNLabel(FNConstants.FN_MILEAGE_END_LABEL);
        FNLabel lblNewDataDateEnd = new FNLabel(FNConstants.FN_DATE_END_LABEL);
        FNLabel lblNewDataTimeEnd = new FNLabel(FNConstants.FN_TIME_END_LABEL);
        FNLabel lblNewDataProject = new FNLabel(FNConstants.FN_PROJECT_LABEL);
        FNLabel lblNewDataLocation = new FNLabel(FNConstants.FN_LOCATION_LABEL);
        FNLabel lblNewDataGPS = new FNLabel(FNConstants.FN_GPS_LABEL);
        FNLabel lblNewDataBillable = new FNLabel(FNConstants.FN_BILLING_LABEL);
        // Override TextField properties
        mTextNewDataName.setEditable(false);
        mTextNewDataName.setText(FNUtil.getInstance().getCurrentUser());
        // Customize Spinner properties
        mSpinnerNewDataLocation.setPreferredSize(FNUtil.getInstance().getLargeTextFieldDimen());
        mSpinnerNewDataLocation.setEditor(new JSpinner.DefaultEditor(mSpinnerNewDataLocation));
        mSpinnerNewDataBillable.setPreferredSize(FNUtil.getInstance().getLargeTextFieldDimen());
        mSpinnerNewDataBillable.setEditor(new JSpinner.DefaultEditor(mSpinnerNewDataBillable));
        // Set DatePicker properties
        mNewTicketStartProperties.put("text.today", "Today");
        mNewTicketStartProperties.put("text.month", "Month");
        mNewTicketStartProperties.put("text.year", "Year");
        mNewTicketEndProperties.put("text.today", "Today");
        mNewTicketEndProperties.put("text.month", "Month");
        mNewTicketEndProperties.put("text.year", "Year");

        // Add Views to Panel
        mAddFNMainPanel.add(lblNewDataName);
        mAddFNMainPanel.add(mTextNewDataName);
        mAddFNMainPanel.add(lblNewDataWellName);
        mAddFNMainPanel.add(mTextNewDataWellName);
        mAddFNMainPanel.add(lblNewDataDateStart);
        mAddFNMainPanel.add(mNewTicketStartDatePicker);
        mAddFNMainPanel.add(lblNewDataTimeStart);
        mAddFNMainPanel.add(mNewTicketTimeStart);
        mAddFNMainPanel.add(lblNewDataMileageStart);
        mAddFNMainPanel.add(mTextNewDataMileageStart);
        mAddFNMainPanel.add(lblNewDataDescription);
        mAddFNMainPanel.add(mTextNewDataDescription);
        mAddFNMainPanel.add(lblNewDataMileageEnd);
        mAddFNMainPanel.add(mTextNewDataMileageEnd);
        mAddFNMainPanel.add(lblNewDataDateEnd);
        mAddFNMainPanel.add(mNewTicketEndDatePicker);
        mAddFNMainPanel.add(lblNewDataTimeEnd);
        mAddFNMainPanel.add(mNewTicketTimeEnd);
        mAddFNMainPanel.add(lblNewDataProject);
        mAddFNMainPanel.add(mTextNewDataProject);
        mAddFNMainPanel.add(lblNewDataLocation);
        mAddFNMainPanel.add(mSpinnerNewDataLocation);
        mAddFNMainPanel.add(lblNewDataGPS);
        mAddFNMainPanel.add(mTextNewDataGPS);
        mAddFNMainPanel.add(lblNewDataBillable);
        mAddFNMainPanel.add(mSpinnerNewDataBillable);
        mAddFNMainPanel.add(new FNLabel());
        mAddFNMainPanel.add(buttonSubmit);
        // Initial view settings
        mAddFNMainPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        buttonSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // build a FieldNote from the data entered by user
                FieldNote fieldNote = new FieldNoteBuilder()
                        .setUserName(mTextNewDataName.getText())
                        .setWellName(mTextNewDataWellName.getText())
                        .setTimeStart(formatTime(mNewTicketTimeStart.getFormatedTime()))
                        .setTimeEnd(formatTime(mNewTicketTimeEnd.getFormatedTime()))
                        .setDateStart(mNewTicketStartDatePicker.getJFormattedTextField().getText())
                        .setDateEnd(mNewTicketEndDatePicker.getJFormattedTextField().getText())
                        .setMileageStart(mTextNewDataMileageStart.getText())
                        .setMileageEnd(mTextNewDataMileageEnd.getText())
                        .setDescription(mTextNewDataDescription.getText())
                        .setWellName(mTextNewDataWellName.getText())
                        .setProjectNumber(mTextNewDataProject.getText())
                        .setLocation((String) mSpinnerNewDataLocation.getValue())
                        .setGPSCoords(mTextNewDataGPS.getText())
                        .setBillingType((String) mSpinnerNewDataBillable.getValue())
                        .build();
                // validate FieldNote
                if (FNValidation.validate(fieldNote)) {
                    // send FieldNote to controller for CUD event
                    SQLDataController conn = new SQLDataController();
                    boolean result = conn.addFieldNote(fieldNote);

                    // code 1 == success, code 0 == failure
                    if (result) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "New Note Submitted");
                        resetGui();
                    } else {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Connection Error: NEW NOTE WAS NOT SUBMITTED");
                    }
                } else {
                    //Validation failed - Do nothing and allow then to make changes and send again
                }
            }
        });
    }

    public String formatTime(String time) {
        return time.substring(0, 5);
    }

    public static JPanel getView() {
        return mAddFNMainPanel;
    }

    public static void showView() {
        mAddFNMainPanel.setVisible(true);
    }

    public static void hideView() {
        mAddFNMainPanel.setVisible(false);
        sInstance.resetGui();
    }

    private void resetGui() {
        // remove editable values in GUI
        mTextNewDataWellName.setText(null);
        mNewTicketTimeStart.setTime(FNUtil.getInstance().getZeroHour());
        mNewTicketTimeEnd.setTime(FNUtil.getInstance().getZeroHour());
        mTextNewDataMileageStart.setText(null);
        mTextNewDataDescription.setText(null);
        mTextNewDataMileageEnd.setText(null);
        mTextNewDataDateEnd.setText(null);
        mTextNewDataTimeEnd.setText(null);
        mTextNewDataProject.setText(null);
        mTextNewDataGPS.setText(null);
        mSpinnerNewDataBillable.setValue("");
        mSpinnerNewDataLocation.setValue("");

        // reset the TimePickers back to current time
        LocalDate now = LocalDate.now();
        mNewTicketStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mNewTicketStartDatePicker.getJFormattedTextField().setText("");
        mNewTicketEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mNewTicketEndDatePicker.getJFormattedTextField().setText("");
    }
}
