/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.billingpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.exporter.ExportController;
import com.devhunter.fncp.mvc.controller.billing.statemachine.BillingState;
import com.devhunter.fncp.mvc.controller.billing.statemachine.FNBillingStateMachine;
import com.devhunter.fncp.mvc.controller.validation.CrudSearchValidation;
import com.devhunter.fncp.mvc.controller.billing.FNBillingController;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.mvc.model.listview.FNListView;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;
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

public class BillingStatePanel {

    // Panels
    private static BillingStatePanel sInstance;
    private static FNPanel mSearchDataPanel;
    private static FNPanel mSearchTextFieldPanel;
    // Billing State
    private static BillingState mBillingState;
    // TextFields
    private FNTextField mSearchUsername;
    private FNTextField mSearchProjectNumber;
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
    // ListView - searchData results
    private FNListView mListView;
    // searchData results
    private ArrayList<FieldNote> mFieldNotes;

    private BillingStatePanel() {
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
        // create ListView
        mListView = new FNListView();
        // create ArrayList
        mFieldNotes = new ArrayList<>();

        init();
    }

    public static BillingStatePanel getInstance() {
        if (sInstance == null) {
            sInstance = new BillingStatePanel();
        }
        return sInstance;
    }

    public void init() {

        // Panel Layouts
        mSearchDataPanel.setLayout(new BorderLayout());
        mSearchTextFieldPanel.setLayout(new GridLayout(0, 2));
        // JavaFX ListView panel
        mListView = new FNListView();

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
        mSearchDataPanel.add(mListView);
        mSearchDataPanel.add(mButtonExport, BorderLayout.SOUTH);

        mBtnSearch.addActionListener(e -> {
            //initialize BillingStates of new FieldNotes to "created" on each search
            FNBillingStateMachine.getInstance().initializeStates();
            //clear the previous search
            mListView.removeItems();

            String username = mSearchUsername.getText();
            String projectName = mSearchProjectNumber.getText();
            String startDate = mDatePickerSearchStart.getJFormattedTextField().getText();
            String endDate = mDatePickerSearchEnd.getJFormattedTextField().getText();

            FNBillingController conn = new FNBillingController();

            // if start date is valid
            if (CrudSearchValidation.isDateRangeValid(startDate, endDate)) {
                // if there is a date
                if (CrudSearchValidation.hasDateRange(startDate, endDate)) {
                    // if there is a username
                    if (!username.isEmpty()) {
                        // if there is a project name
                        if (!projectName.isEmpty()) {
                            mFieldNotes = conn.searchDataByUsernameProjectAndDateRange(mBillingState, username, projectName, startDate, endDate);
                        } else {
                            // searchData by user name and date range
                            mFieldNotes = conn.searchDataByUsernameAndDateRange(mBillingState, username, startDate, endDate);
                        }
                    } else if (!projectName.isEmpty()) {
                        // searchData by project name and date range
                        mFieldNotes = conn.searchDataByProjectAndDateRange(mBillingState, projectName, startDate, endDate);
                    } else {
                        // searchData by date range
                        mFieldNotes = conn.searchDataByDateRange(mBillingState, startDate, endDate);
                    }
                } else {
                    // if there is a username
                    if (!username.isEmpty()) {
                        // if there is a project name
                        if (!projectName.isEmpty()) {
                            mFieldNotes = conn.searchDataByUsernameAndProject(mBillingState, username, projectName);
                        } else {
                            // searchData by user name
                            mFieldNotes = conn.searchDataByUsername(mBillingState, username);
                        }
                    } else if (!projectName.isEmpty()) {
                        mFieldNotes = conn.searchDataByProject(mBillingState, projectName);
                    } else {
                        // searchData all user names, project names, and date ranges
                        mFieldNotes = conn.searchAllData(mBillingState);
                    }
                }

                if (!mFieldNotes.isEmpty()) {
                    // add all FieldNotes to ListView
                    mListView.addItems(mFieldNotes);
                }
            }
        });

        // export CSV file to User Desktop
        mButtonExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // export controller
                ExportController exporter = new ExportController();
                boolean exportSuccess = exporter.writeBillingToCSVFile(mFieldNotes);

                if (exportSuccess) {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Success! CVS report generated");
                } else {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Failure! CVS export error");
                }
            }
        });
    }

    public static void setBillingState(BillingState state) {
        mBillingState = state;
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
        mListView.removeItems();

        LocalDate now = LocalDate.now();

        mSearchStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchStart.getJFormattedTextField().setText("");
        mSearchEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchEnd.getJFormattedTextField().setText("");
    }
}
