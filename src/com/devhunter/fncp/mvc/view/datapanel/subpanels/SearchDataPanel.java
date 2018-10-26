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
import com.devhunter.fncp.mvc.model.FieldNote;
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
import org.json.JSONArray;
import org.json.JSONObject;

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

    private static SearchDataPanel sInstance;
    private static FNPanel mSearchDataPanel;
    private static FNPanel mSearchTextFieldPanel;
    private FNTextField mTextDataUsername;
    private JTextArea mSearchDataOutput;
    private UtilDateModel mSearchStartModel;
    private Properties mSearchStartProperties;
    private JDatePickerImpl mDatePickerSearchStart;
    private UtilDateModel mSearchEndModel;
    private Properties mSearchEndProperties;
    private JDatePickerImpl mDatePickerSearchEnd;
    private FNButton mButtonSearch;
    private FNButton mButtonExport;
    private ArrayList<FieldNote> mFieldNotes;

    private SearchDataPanel() {
        mSearchDataPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        mTextDataUsername = new FNTextField();
        mSearchDataOutput = new JTextArea(28, 32);
        mSearchStartModel = new UtilDateModel();
        mSearchStartProperties = new Properties();
        JDatePanelImpl mDatePanelSearchStart = new JDatePanelImpl(mSearchStartModel, mSearchStartProperties);
        mDatePickerSearchStart = new JDatePickerImpl(mDatePanelSearchStart, new DateLabelFormatter());
        mSearchEndModel = new UtilDateModel();
        mSearchEndProperties = new Properties();
        JDatePanelImpl mDatePanelSearchEnd = new JDatePanelImpl(mSearchEndModel, mSearchEndProperties);
        mDatePickerSearchEnd = new JDatePickerImpl(mDatePanelSearchEnd, new DateLabelFormatter());
        mButtonSearch = new FNButton(FNConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNConstants.BUTTON_EXPORT);
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

        BorderLayout searchFNPanelLayout = new BorderLayout();
        mSearchDataPanel.setLayout(searchFNPanelLayout);
        GridLayout searchFNTextFieldPanelLayout = new GridLayout(0, 2);
        mSearchTextFieldPanel.setLayout(searchFNTextFieldPanelLayout);

        FNLabel lblUsernameSearch = new FNLabel(FNConstants.FN_USERNAME_LABEL);
        FNLabel lblDataSearchDateStart = new FNLabel(FNConstants.FN_DATE_START_LABEL);
        FNLabel lblDataSearchDateEnd = new FNLabel(FNConstants.FN_DATE_END_LABEL);

        JScrollPane dataSearchScroll = new JScrollPane(mSearchDataOutput);
        mSearchDataOutput.setEditable(false);

        mSearchStartProperties.put("text.today", "Today");
        mSearchStartProperties.put("text.month", "Month");
        mSearchStartProperties.put("text.year", "Year");
        mSearchEndProperties.put("text.today", "Today");
        mSearchEndProperties.put("text.month", "Month");
        mSearchEndProperties.put("text.year", "Year");

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

                String dateStart = mDatePickerSearchStart.getJFormattedTextField().getText();
                String dateEnd = mDatePickerSearchEnd.getJFormattedTextField().getText();
                String username = mTextDataUsername.getText();

                if (dateStart.isEmpty() && !dateEnd.isEmpty() || !dateStart.isEmpty() && dateEnd.isEmpty()) {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Please enter a date range");
                } else {
                    searchFieldNotes(username, dateStart, dateEnd);
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

    private void searchFieldNotes(String username, String dateStart, String dateEnd) {
        JSONObject searchFieldNoteResponse = FNDataController.searchFieldNotes(username, dateStart, dateEnd);
        String status = searchFieldNoteResponse.getString("status");
        String messageString = searchFieldNoteResponse.getString("message");
        JSONArray messageArray = new JSONArray(messageString);

        if (status.equals("success")) {
            printJsonObject(messageArray, mSearchDataOutput);
        } else {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
        }
    }

    private void printJsonObject(JSONArray message, JTextArea areaToPrintOn) {
        for (int i = 0; i < message.length(); i++) {
            JSONObject jsonObject = message.getJSONObject(i);

            String ticketNumber = jsonObject.getString("ticketNumber");
            String username = jsonObject.getString("userName");
            String wellname = jsonObject.getString("wellName");
            String timeStart = jsonObject.getString("timeStart");
            String timeEnd = jsonObject.getString("timeEnd");
            String dateStart = jsonObject.getString("dateStart");
            String dateEnd = jsonObject.getString("dateEnd");
            String mileageStart = jsonObject.getString("mileageStart");
            String mileageEnd = jsonObject.getString("mileageEnd");
            String description = jsonObject.getString("description");
            String projectNumber = jsonObject.getString("projectNumber");
            String location = jsonObject.getString("location");
            String billing = jsonObject.getString("billing");
            String gps = jsonObject.getString("gps");

            areaToPrintOn.append("TicketNumber: " + ticketNumber + "\n");
            areaToPrintOn.append("Username: " + username + "\n");
            areaToPrintOn.append("Wellname: " + wellname + "\n");
            areaToPrintOn.append("Time Start: " + timeStart + "\n");
            areaToPrintOn.append("Time End: " + timeEnd + "\n");
            areaToPrintOn.append("Date Start: " + dateStart + "\n");
            areaToPrintOn.append("Date End: " + dateEnd + "\n");
            areaToPrintOn.append("Mileage Start: " + mileageStart + "\n");
            areaToPrintOn.append("Mileage End: " + mileageEnd + "\n");
            areaToPrintOn.append("Description: " + description + "\n");
            areaToPrintOn.append("Project Number: " + projectNumber + "\n");
            areaToPrintOn.append("Location: " + location + "\n");
            areaToPrintOn.append("Billing: " + billing + "\n");
            areaToPrintOn.append("GPS: " + gps + "\n\n");
        }
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
