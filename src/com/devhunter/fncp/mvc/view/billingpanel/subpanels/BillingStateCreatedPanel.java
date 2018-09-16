/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.billingpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.validation.CrudSearchValidation;
import com.devhunter.fncp.mvc.controller.sql.billing.FNBillingController;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.utilities.FNUtil;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class BillingStateCreatedPanel {

    // Panels
    private static BillingStateCreatedPanel sInstance;
    private static FNPanel mSearchDataPanel;
    private static FNPanel mSearchTextFieldPanel;
    // TextFields
    private FNTextField mSearchUsername;
    private FNTextField mSearchProjectNumber;
    // TextArea - searchData results
    private JTextArea mSearchDataOutput;
    // DatePicker
    private UtilDateModel mSearchStartModel;
    private Properties mSearchStartProperties;
    private JDatePickerImpl mDatePickerSearchStart;
    private UtilDateModel mSearchEndModel;
    private Properties mSearchEndProperties;
    private JDatePickerImpl mDatePickerSearchEnd;
    // Buttons
    private FNButton mBtnSearch;
    private FNButton mButtonExport;
    // searchData results
    private ArrayList<FieldNote> mFieldNotes;

    private BillingStateCreatedPanel() {
        // create Panels
        mSearchDataPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        // create TextFields
        mSearchUsername = new FNTextField();
        mSearchProjectNumber = new FNTextField();
        // create DatePickers
        mSearchStartModel = new UtilDateModel();
        mSearchStartProperties = new Properties();
        JDatePanelImpl mDatePanelSearchStart = new JDatePanelImpl(mSearchStartModel, mSearchStartProperties);
        mDatePickerSearchStart = new JDatePickerImpl(mDatePanelSearchStart, new DateLabelFormatter());
        mSearchEndModel = new UtilDateModel();
        mSearchEndProperties = new Properties();
        JDatePanelImpl mDatePanelSearchEnd = new JDatePanelImpl(mSearchEndModel, mSearchEndProperties);
        mDatePickerSearchEnd = new JDatePickerImpl(mDatePanelSearchEnd, new DateLabelFormatter());
        // create Buttons
        mBtnSearch = new FNButton(FNConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNConstants.BUTTON_EXPORT);

        // TODO: [FNCP-007] rewrite display of searched FieldNotes :: create TextAreas
        mSearchDataOutput = new JTextArea(28, 32);
        //searchData results
        mFieldNotes = new ArrayList<>();

        init();
    }

    public static BillingStateCreatedPanel getInstance() {
        if (sInstance == null) {
            sInstance = new BillingStateCreatedPanel();
        }
        return sInstance;
    }

    private void init() {
        // Panel Layouts
        mSearchDataPanel.setLayout(new BorderLayout());
        mSearchTextFieldPanel.setLayout(new GridLayout(0, 2));
        // ScrollPane/TextArea
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
        mSearchTextFieldPanel.add(new FNLabel(FNConstants.FN_USERNAME_LABEL));
        mSearchTextFieldPanel.add(mSearchUsername);
        mSearchTextFieldPanel.add(new FNLabel(FNConstants.FN_PROJECT_LABEL));
        mSearchTextFieldPanel.add(mSearchProjectNumber);
        mSearchTextFieldPanel.add(new FNLabel(FNConstants.FN_DATE_START_LABEL));
        mSearchTextFieldPanel.add(mDatePickerSearchStart);
        mSearchTextFieldPanel.add(new FNLabel(FNConstants.FN_DATE_END_LABEL));
        mSearchTextFieldPanel.add(mDatePickerSearchEnd);
        mSearchTextFieldPanel.add(new FNLabel());
        mSearchTextFieldPanel.add(mBtnSearch);
        // Add Views to Main Panel
        mSearchDataPanel.add(mSearchTextFieldPanel, BorderLayout.NORTH);
        mSearchDataPanel.add(dataSearchScroll, BorderLayout.CENTER);
        mSearchDataPanel.add(mButtonExport, BorderLayout.SOUTH);

        mBtnSearch.addActionListener(e -> {
            mSearchDataOutput.setEnabled(true);
            mSearchDataOutput.setVisible(true);
            mSearchDataOutput.setText(null);

            String username = mSearchUsername.getText();
            String projectName = mSearchProjectNumber.getText();
            String startDate = mDatePickerSearchStart.getJFormattedTextField().getText();
            String endDate = mDatePickerSearchEnd.getJFormattedTextField().getText();

            FNBillingController conn = new FNBillingController();

            //TODO: pull this out, let all three panels call this when they are searching
            //TODO: The state they search for will be passed in and handed down to the search

            // if start date is valid
            if (CrudSearchValidation.isDateRangeValid(startDate, endDate)) {
                // if there is a date
                if (CrudSearchValidation.hasDateRange(startDate, endDate)) {
                    // if there is a username
                    if (!username.isEmpty()) {
                        // if there is a project name
                        if (!projectName.isEmpty()) {
                            mFieldNotes = conn.searchDataByUsernameProjectAndDateRange(username, projectName, startDate, endDate);
                        } else {
                            // searchData by user name and date range
                            mFieldNotes = conn.searchDataByUsernameAndDateRange(username, startDate, endDate);
                        }
                    } else if (!projectName.isEmpty()) {
                        // searchData by project name and date range
                        mFieldNotes = conn.searchDataByProjectAndDateRange(projectName, startDate, endDate);
                    } else {
                        // searchData by date range
                        mFieldNotes = conn.searchDataByDateRange(startDate, endDate);
                    }
                } else {
                    // if there is a username
                    if (!username.isEmpty()) {
                        // if there is a project name
                        if (!projectName.isEmpty()) {
                            mFieldNotes = conn.searchDataByUsernameAndProject(username, projectName);
                        } else {
                            // searchData by user name
                            mFieldNotes = conn.searchDataByUsername(username);
                        }
                    } else if (!projectName.isEmpty()) {
                        mFieldNotes = conn.searchDataByProject(projectName);
                    } else {
                        // searchData all user names, project names, and date ranges
                        mFieldNotes = conn.searchAllData(String state);
                    }
                }

                if (!mFieldNotes.isEmpty()) {
                    FNUtil.printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);
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
        if (FNUtil.getInstance().hasAdminAccess()) {
            mSearchUsername.setText(null);
        }
        mSearchDataOutput.setText(null);

        LocalDate now = LocalDate.now();

        mSearchStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchStart.getJFormattedTextField().setText("");
        mSearchEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchEnd.getJFormattedTextField().setText("");
    }
}
