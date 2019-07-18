/**
 * ? 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.view.billingpanel.subpanels;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.mvc.controller.FNDataService;
import com.fieldnotes.fncp.mvc.controller.FNSessionService;
import com.fieldnotes.fncp.mvc.controller.billingStateMachine.BillingState;
import com.fieldnotes.fncp.mvc.controller.billingStateMachine.FNBillingStateMachine;
import com.fieldnotes.fncp.mvc.controller.exporter.ExportController;
import com.fieldnotes.fncp.mvc.model.FNNote;
import com.fieldnotes.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.fieldnotes.fncp.mvc.model.fnview.FNButton;
import com.fieldnotes.fncp.mvc.model.fnview.FNLabel;
import com.fieldnotes.fncp.mvc.model.fnview.FNPanel;
import com.fieldnotes.fncp.mvc.model.fnview.FNTextField;
import com.fieldnotes.fncp.mvc.model.listview.FNListView;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
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

import static com.fieldnotes.fncp.constants.FNPConstants.*;

public class BillingStatePanel extends FNPanel {

    private static BillingStatePanel sInstance;
    private static FNPanel mSearchDataPanel;
    private static FNPanel mSearchTextFieldPanel;
    private static BillingState mBillingState;
    private FNTextField mSearchUsername;
    private FNTextField mSearchProjectNumber;
    private UtilDateModel mSearchStartModel;
    private Properties mSearchStartProperties;
    private JDatePickerImpl mDatePickerSearchStart;
    private UtilDateModel mSearchEndModel;
    private Properties mSearchEndProperties;
    private JDatePickerImpl mDatePickerSearchEnd;
    private FNButton mBtnSearch;
    private FNButton mButtonExport;
    private FNListView mListView;
    private ArrayList<FNNote> mNotes;

    private BillingStatePanel() {
        mSearchDataPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        mSearchUsername = new FNTextField();
        mSearchProjectNumber = new FNTextField();
        mSearchStartModel = new UtilDateModel();
        mSearchStartProperties = new Properties();
        JDatePanelImpl mDatePanelSearchStart = new JDatePanelImpl(mSearchStartModel, mSearchStartProperties);
        mDatePickerSearchStart = new JDatePickerImpl(mDatePanelSearchStart, new DateLabelFormatter());
        mSearchEndModel = new UtilDateModel();
        mSearchEndProperties = new Properties();
        JDatePanelImpl mDatePanelSearchEnd = new JDatePanelImpl(mSearchEndModel, mSearchEndProperties);
        mDatePickerSearchEnd = new JDatePickerImpl(mDatePanelSearchEnd, new DateLabelFormatter());
        mBtnSearch = new FNButton(FNCPConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNCPConstants.BUTTON_EXPORT);
        mListView = new FNListView(true);
        mNotes = new ArrayList<>();

        drawPanel();
    }

    public static BillingStatePanel getInstance() {
        if (sInstance == null) {
            sInstance = new BillingStatePanel();
        }
        return sInstance;
    }

    private void drawPanel() {

        // Panel Layouts
        mSearchDataPanel.setLayout(new BorderLayout());
        mSearchTextFieldPanel.setLayout(new GridLayout(0, 2));

        // Set DatePicker Properties
        mSearchStartProperties.put("text.today", "Today");
        mSearchStartProperties.put("text.month", "Month");
        mSearchStartProperties.put("text.year", "Year");
        mSearchEndProperties.put("text.today", "Today");
        mSearchEndProperties.put("text.month", "Month");
        mSearchEndProperties.put("text.year", "Year");

        // Add Views to TextFieldPanel
        mSearchTextFieldPanel.add(new FNLabel(FNCPConstants.FN_USERNAME_LABEL));
        mSearchTextFieldPanel.add(mSearchUsername);
        mSearchTextFieldPanel.add(new FNLabel(FNCPConstants.FN_PROJECT_LABEL));
        mSearchTextFieldPanel.add(mSearchProjectNumber);
        mSearchTextFieldPanel.add(new FNLabel(FNCPConstants.FN_DATE_START_LABEL));
        mSearchTextFieldPanel.add(mDatePickerSearchStart);
        mSearchTextFieldPanel.add(new FNLabel(FNCPConstants.FN_DATE_END_LABEL));
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

            searchFieldNotes(mBillingState, username, projectName, startDate, endDate);
        });

        // export CSV file to User Desktop
        mButtonExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean exportSuccess = ExportController.writeBillingToCSVFile(mNotes);

                if (exportSuccess) {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Success! CVS report generated");
                } else {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Failure! CVS export error");
                }
            }
        });
    }

    private void searchFieldNotes(BillingState state, String username, String projectNumber, String dateStart, String dateEnd) {
        JSONObject searchFieldNoteResponse = FNDataService.searchFieldNotes(state, projectNumber, username, dateStart, dateEnd);
        String status = searchFieldNoteResponse.getString(RESPONSE_STATUS_TAG);
        String messageString = searchFieldNoteResponse.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            JSONArray messageArray = new JSONArray(messageString);

            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject message = messageArray.getJSONObject(i);
                FNNote note = FNSessionService.buildNote(message);

                // add to ListView
                mListView.addItem(note);
                // add to Export List
                mNotes.add(note);
            }
        } else {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
        }
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
        if (FNSessionService.getInstance().hasAdminAccess()) {
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