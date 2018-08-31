/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.exporter.ExportController;
import com.devhunter.fncp.mvc.controller.sql.SQLDataController;
import com.devhunter.fncp.mvc.model.*;
import com.devhunter.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Determines how the user will search for data and parses data into a JTextField
 */

public class SearchDataPanel extends FNPanel {

    // Panels
    public static SearchDataPanel sInstance;
    private static FNPanel mSearchDataPanel;
    private static FNPanel mSearchTextFieldPanel;
    // TextFields
    private FNTextField mTextDataUsername;
    // TextArea - search results
    private JTextArea mSearchDataOutput;
    // DatePicker
    private UtilDateModel mSearchStartModel;
    private Properties mSearchStartProperties;
    private JDatePanelImpl mDatePanelSearchStart;
    private JDatePickerImpl mDatePickerSearchStart;
    private UtilDateModel mSearchEndModel;
    private Properties mSearchEndProperties;
    private JDatePanelImpl mDatePanelSearchEnd;
    private JDatePickerImpl mDatePickerSearchEnd;
    // Buttons
    private FNButton mButtonSearch;
    private FNButton mButtonExport;
    // search results
    private ArrayList<FieldNote> mFieldNotes;

    private SearchDataPanel() {
        // create Panels
        mSearchDataPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        // create TextFields
        mTextDataUsername = new FNTextField();
        // create TextAreas
        mSearchDataOutput = new JTextArea(28, 32);
        // create DatePickers
        mSearchStartModel = new UtilDateModel();
        mSearchStartProperties = new Properties();
        mDatePanelSearchStart = new JDatePanelImpl(mSearchStartModel, mSearchStartProperties);
        mDatePickerSearchStart = new JDatePickerImpl(mDatePanelSearchStart, new DateLabelFormatter());
        mSearchEndModel = new UtilDateModel();
        mSearchEndProperties = new Properties();
        mDatePanelSearchEnd = new JDatePanelImpl(mSearchEndModel, mSearchEndProperties);
        mDatePickerSearchEnd = new JDatePickerImpl(mDatePanelSearchEnd, new DateLabelFormatter());
        // create Buttons
        mButtonSearch = new FNButton(FNConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNConstants.BUTTON_EXPORT);
        //search results
        mFieldNotes = new ArrayList<>();
        init();
    }

    public static SearchDataPanel getInstance() {
        if (sInstance == null) {
            sInstance = new SearchDataPanel();
        }
        return sInstance;
    }

    void init() {
        // Panel Layouts
        BorderLayout searchFNPanelLayout = new BorderLayout();
        mSearchDataPanel.setLayout(searchFNPanelLayout);
        GridLayout searchFNTextFieldPanelLayout = new GridLayout(0, 2);
        mSearchTextFieldPanel.setLayout(searchFNTextFieldPanelLayout);
        // Labels
        FNLabel lblUsernameSearch = new FNLabel(FNConstants.FN_USERNAME_LABEL);
        FNLabel lblDataSearchDateStart = new FNLabel(FNConstants.FN_DATE_START_LABEL);
        FNLabel lblDataSearchDateEnd = new FNLabel(FNConstants.FN_DATE_END_LABEL);
        // ScrolPane/TextArea
        JScrollPane dataSearchScroll = new JScrollPane(mSearchDataOutput);
        mSearchDataOutput.setEditable(false);
        // Set DatePicker Properties
        mSearchStartProperties.put("text.today", "Today");
        mSearchStartProperties.put("text.month", "Month");
        mSearchStartProperties.put("text.year", "Year");
        mSearchEndProperties.put("text.today", "Today");
        mSearchEndProperties.put("text.month", "Month");
        mSearchEndProperties.put("text.year", "Year");

        // Add Views to TextFieldPanel
        mSearchTextFieldPanel.add(lblUsernameSearch);
        mSearchTextFieldPanel.add(mTextDataUsername);
        mSearchTextFieldPanel.add(lblDataSearchDateStart);
        mSearchTextFieldPanel.add(mDatePickerSearchStart);
        mSearchTextFieldPanel.add(lblDataSearchDateEnd);
        mSearchTextFieldPanel.add(mDatePickerSearchEnd);
        mSearchTextFieldPanel.add(new FNLabel());
        mSearchTextFieldPanel.add(mButtonSearch);
        // Add Views to Main Panel
        mSearchDataPanel.add(mSearchTextFieldPanel, BorderLayout.NORTH);
        mSearchDataPanel.add(dataSearchScroll, BorderLayout.CENTER);
        mSearchDataPanel.add(mButtonExport, BorderLayout.SOUTH);
        // Initial View Settings
        mSearchDataPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        mButtonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SQLDataController conn = new SQLDataController();
                mSearchDataOutput.setEnabled(true);
                mSearchDataOutput.setVisible(true);
                mSearchDataOutput.setText(null);

                // if start date but no end, or end date but no start
                if (mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty() && !mDatePickerSearchEnd.getJFormattedTextField().getText().isEmpty()
                        || (!mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty() && mDatePickerSearchEnd.getJFormattedTextField().getText().isEmpty())) {
                    // do nothing
                    // if both are empty
                } else if (mTextDataUsername.getText().trim().isEmpty()
                        && mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // search all names and dates
                    mFieldNotes = conn.mySQLSearchData();
                    printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

                    // if user search bar has a name, but no dates are selected
                } else if (!mTextDataUsername.getText().trim().isEmpty() && mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // search specific name and all dates
                    String username = mTextDataUsername.getText();
                    mFieldNotes = conn.mySQLSearchData(username);
                    printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

                    // if no user, but dates are selected
                } else if (mTextDataUsername.getText().trim().isEmpty() && !mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // search by selected dates, independent of username
                    mSearchDataOutput.setEnabled(true);
                    mSearchDataOutput.setVisible(true);
                    mSearchDataOutput.setText(null);

                    if (mDatePickerSearchEnd.getJFormattedTextField().getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a date range");
                    } else {
                        try {
                            String startDate = mDatePickerSearchStart.getJFormattedTextField().getText();
                            String endDate = mDatePickerSearchEnd.getJFormattedTextField().getText();

                            mFieldNotes = conn.mySQLSearchDataByDateRange(startDate, endDate);
                            printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

                        } catch (StringIndexOutOfBoundsException e1) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a valid date range");
                        }
                    }
                    //if there is a user AND a date range
                } else if (!mTextDataUsername.getText().trim().isEmpty() && !mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // search by user name and date
                    if (mDatePickerSearchStart.getJFormattedTextField().getText().trim().isEmpty() || mDatePickerSearchEnd.getJFormattedTextField().getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a date range");
                    } else {
                        try {
                            String username = mTextDataUsername.getText();
                            String startDate = mDatePickerSearchStart.getJFormattedTextField().getText();
                            String endDate = mDatePickerSearchEnd.getJFormattedTextField().getText();

                            mFieldNotes = conn.mySQLSearchDataByUserAndDateRange(username, startDate, endDate);
                            printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

                        } catch (StringIndexOutOfBoundsException e1) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a valid date range");
                        }
                    }
                }
            }
        });

        // export CSV file to User Desktop
        mButtonExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // export controller
                ExportController exporter = new ExportController();
                boolean exportSuccess = exporter.writeDataToCSVFile(mFieldNotes);

                if (exportSuccess) {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Success! CVS report generated");
                } else {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Failure! CVS export error");
                }
            }
        });
    }

    public static JPanel getView() {
        return mSearchDataPanel;
    }

    public static void showView() {
        mSearchDataPanel.setVisible(true);
    }

    public static void hideView() {
        mSearchDataPanel.setVisible(false);
        sInstance.resetGui();
    }

    private void resetGui() {
        mSearchDataPanel.setVisible(false);
        mTextDataUsername.setText(null);
        mSearchDataOutput.setText(null);

        LocalDate now = LocalDate.now();

        mSearchStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchStart.getJFormattedTextField().setText("");
        mSearchEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchEnd.getJFormattedTextField().setText("");
    }

    private void printFieldNotesToJTextArea(ArrayList<FieldNote> fieldNotes, JTextArea textArea) {
        for (FieldNote each : fieldNotes) {
            textArea.append(FNConstants.CRUD_SEARCH_TICKET_NUMBER + " " + each.getTicketNumber() + "\n");
            textArea.append(FNConstants.FN_USERNAME_LABEL + " " + each.getUserName() + "\n");
            textArea.append(FNConstants.FN_PROJECT_LABEL + " " + each.getProjectNumber() + "\n");
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
}
