/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.exporter.ExportController;
import com.devhunter.fncp.mvc.controller.sql.FNDataController;
import com.devhunter.fncp.mvc.model.*;
import com.devhunter.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
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

/**
 * Determines how the user will searchData for data and parses data into a JTextField
 */

public class SearchDataPanel extends FNPanel {

    // Panels
    private static SearchDataPanel sInstance;
    private static FNPanel mSearchDataPanel;
    private static FNPanel mSearchTextFieldPanel;
    // TextFields
    private FNTextField mTextDataUsername;
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
    private FNButton mButtonSearch;
    private FNButton mButtonExport;
    // searchData results
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
        JDatePanelImpl mDatePanelSearchStart = new JDatePanelImpl(mSearchStartModel, mSearchStartProperties);
        mDatePickerSearchStart = new JDatePickerImpl(mDatePanelSearchStart, new DateLabelFormatter());
        mSearchEndModel = new UtilDateModel();
        mSearchEndProperties = new Properties();
        JDatePanelImpl mDatePanelSearchEnd = new JDatePanelImpl(mSearchEndModel, mSearchEndProperties);
        mDatePickerSearchEnd = new JDatePickerImpl(mDatePanelSearchEnd, new DateLabelFormatter());
        // create Buttons
        mButtonSearch = new FNButton(FNConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNConstants.BUTTON_EXPORT);
        //searchData results
        mFieldNotes = new ArrayList<>();
        init();
    }

    public static SearchDataPanel getInstance() {
        if (sInstance == null) {
            sInstance = new SearchDataPanel();
        }
        return sInstance;
    }

    private void init() {
        // Panel Layouts
        BorderLayout searchFNPanelLayout = new BorderLayout();
        mSearchDataPanel.setLayout(searchFNPanelLayout);
        GridLayout searchFNTextFieldPanelLayout = new GridLayout(0, 2);
        mSearchTextFieldPanel.setLayout(searchFNTextFieldPanelLayout);
        // Labels
        FNLabel lblUsernameSearch = new FNLabel(FNConstants.FN_USERNAME_LABEL);
        FNLabel lblDataSearchDateStart = new FNLabel(FNConstants.FN_DATE_START_LABEL);
        FNLabel lblDataSearchDateEnd = new FNLabel(FNConstants.FN_DATE_END_LABEL);
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

        // NO-ADMIN USERS: can only searchData their own data
        if (!FNUtil.getInstance().hasAdminAccess()) {
            mTextDataUsername.setText(FNUtil.getInstance().getCurrentUsername());
            mTextDataUsername.setEditable(false);
            mTextDataUsername.setBackground(Color.WHITE);
        }

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

        mButtonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mSearchDataOutput.setEnabled(true);
                mSearchDataOutput.setVisible(true);
                mSearchDataOutput.setText(null);

                FNDataController conn = new FNDataController();

                // if start date but no end, or end date but no start
                if (mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty() && !mDatePickerSearchEnd.getJFormattedTextField().getText().isEmpty()
                        || (!mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty() && mDatePickerSearchEnd.getJFormattedTextField().getText().isEmpty())) {
                    // do nothing
                    // if both are empty
                } else if (mTextDataUsername.getText().trim().isEmpty()
                        && mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // searchData all names and dates
                    mFieldNotes = conn.searchAllData();
                    FNUtil.printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

                    // if user searchData bar has a name, but no dates are selected
                } else if (!mTextDataUsername.getText().trim().isEmpty() && mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // searchData specific name and all dates
                    String username = mTextDataUsername.getText();
                    mFieldNotes = conn.searchDataByUsername(username);
                    FNUtil.printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

                    // if no user, but dates are selected
                } else if (mTextDataUsername.getText().trim().isEmpty() && !mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // searchData by selected dates, independent of username
                    mSearchDataOutput.setEnabled(true);
                    mSearchDataOutput.setVisible(true);
                    mSearchDataOutput.setText(null);

                    if (mDatePickerSearchEnd.getJFormattedTextField().getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a date range");
                    } else {
                        try {
                            String startDate = mDatePickerSearchStart.getJFormattedTextField().getText();
                            String endDate = mDatePickerSearchEnd.getJFormattedTextField().getText();

                            mFieldNotes = conn.searchDataByDateRange(startDate, endDate);
                            FNUtil.printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

                        } catch (StringIndexOutOfBoundsException e1) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a valid date range");
                        }
                    }
                    //if there is a user AND a date range
                } else if (!mTextDataUsername.getText().trim().isEmpty() && !mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
                    // searchData by user name and date
                    if (mDatePickerSearchStart.getJFormattedTextField().getText().trim().isEmpty() || mDatePickerSearchEnd.getJFormattedTextField().getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a date range");
                    } else {
                        try {
                            String username = mTextDataUsername.getText();
                            String startDate = mDatePickerSearchStart.getJFormattedTextField().getText();
                            String endDate = mDatePickerSearchEnd.getJFormattedTextField().getText();

                            mFieldNotes = conn.searchDataByUserAndDateRange(username, startDate, endDate);
                            FNUtil.printFieldNotesToJTextArea(mFieldNotes, mSearchDataOutput);

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

        // Initial View Settings
        mSearchDataPanel.setVisible(false);

        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();
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
            mTextDataUsername.setText(null);
        }
        mSearchDataOutput.setText(null);

        LocalDate now = LocalDate.now();

        mSearchStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchStart.getJFormattedTextField().setText("");
        mSearchEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchEnd.getJFormattedTextField().setText("");
    }
}
