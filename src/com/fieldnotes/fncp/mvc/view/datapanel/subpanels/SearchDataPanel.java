/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.view.datapanel.subpanels;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.mvc.controller.FNDataService;
import com.fieldnotes.fncp.mvc.controller.exporter.ExportController;
import com.fieldnotes.fncp.mvc.model.FNNote;
import com.fieldnotes.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.fieldnotes.fncp.mvc.model.fnview.FNButton;
import com.fieldnotes.fncp.mvc.model.fnview.FNLabel;
import com.fieldnotes.fncp.mvc.model.fnview.FNPanel;
import com.fieldnotes.fncp.mvc.model.fnview.FNTextField;
import com.fieldnotes.fncp.mvc.model.listview.FNListView;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
import com.fieldnotes.fncp.mvc.controller.services.FNSessionService;
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

import static com.fieldnotes.fncp.constants.FNCPConstants.FN_USERNAME_LABEL;
import static com.fieldnotes.fncp.constants.FNPConstants.*;

public class SearchDataPanel extends FNPanel {

    private static SearchDataPanel sInstance;
    private static FNPanel mSearchDataPanel;
    private static FNPanel mSearchTextFieldPanel;
    private FNTextField mTextDataUsername;
    private UtilDateModel mSearchStartModel;
    private Properties mSearchStartProperties;
    private JDatePickerImpl mDatePickerSearchStart;
    private UtilDateModel mSearchEndModel;
    private Properties mSearchEndProperties;
    private JDatePickerImpl mDatePickerSearchEnd;
    private FNButton mButtonSearch;
    private FNButton mButtonExport;
    private FNListView mListView;
    private ArrayList<FNNote> mNotes;

    private SearchDataPanel() {
        mSearchDataPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        mTextDataUsername = new FNTextField();
        mSearchStartModel = new UtilDateModel();
        mSearchStartProperties = new Properties();
        JDatePanelImpl mDatePanelSearchStart = new JDatePanelImpl(mSearchStartModel, mSearchStartProperties);
        mDatePickerSearchStart = new JDatePickerImpl(mDatePanelSearchStart, new DateLabelFormatter());
        mSearchEndModel = new UtilDateModel();
        mSearchEndProperties = new Properties();
        JDatePanelImpl mDatePanelSearchEnd = new JDatePanelImpl(mSearchEndModel, mSearchEndProperties);
        mDatePickerSearchEnd = new JDatePickerImpl(mDatePanelSearchEnd, new DateLabelFormatter());
        mButtonSearch = new FNButton(FNCPConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNCPConstants.BUTTON_EXPORT);
        mListView = new FNListView(false);
        mNotes = new ArrayList<>();

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

        FNLabel lblUsernameSearch = new FNLabel(FN_USERNAME_LABEL);
        FNLabel lblDataSearchDateStart = new FNLabel(FNCPConstants.FN_DATE_START_LABEL);
        FNLabel lblDataSearchDateEnd = new FNLabel(FNCPConstants.FN_DATE_END_LABEL);

        mSearchStartProperties.put("text.today", "Today");
        mSearchStartProperties.put("text.month", "Month");
        mSearchStartProperties.put("text.year", "Year");
        mSearchEndProperties.put("text.today", "Today");
        mSearchEndProperties.put("text.month", "Month");
        mSearchEndProperties.put("text.year", "Year");

        if (!FNSessionService.getInstance().hasAdminAccess()) {
            mTextDataUsername.setText(FNSessionService.getInstance().getCurrentUsername());
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
        mSearchDataPanel.add(mListView);
        mSearchDataPanel.add(mButtonExport, BorderLayout.SOUTH);

        mButtonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mListView.removeItems();

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
                boolean exportSuccess = ExportController.writeDataToCSVFile(mNotes);
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

    /**
     * search FieldNotes by params
     *
     * @param username
     * @param dateStart
     * @param dateEnd
     */
    private void searchFieldNotes(String username, String dateStart, String dateEnd) {
        JSONObject searchFieldNoteResponse = FNDataService.searchFieldNotes(username, dateStart, dateEnd, null);
        String status = searchFieldNoteResponse.getString(RESPONSE_STATUS_TAG);
        String messageString = searchFieldNoteResponse.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            JSONArray messageArray = new JSONArray(messageString);

            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject message = messageArray.getJSONObject(i);
                FNNote note = FNSessionService.buildNoteForReadback(message);
                // add to ListView
                mListView.addItem(note);
            }
        } else {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
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
        if (FNSessionService.getInstance().hasAdminAccess()) {
            mTextDataUsername.setText(null);
        }
        // remove items from ListView
        mListView.removeItems();

        // reset time models to current date
        LocalDate now = LocalDate.now();
        mSearchStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchStart.getJFormattedTextField().setText("");
        mSearchEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        mDatePickerSearchEnd.getJFormattedTextField().setText("");
    }
}