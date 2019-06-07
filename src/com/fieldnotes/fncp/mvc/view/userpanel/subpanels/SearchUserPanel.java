/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.view.userpanel.subpanels;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.mvc.controller.FNUserService;
import com.fieldnotes.fncp.mvc.controller.exporter.ExportController;
import com.fieldnotes.fncp.mvc.model.FNUser;
import com.fieldnotes.fncp.mvc.model.fnview.FNButton;
import com.fieldnotes.fncp.mvc.model.fnview.FNLabel;
import com.fieldnotes.fncp.mvc.model.fnview.FNPanel;
import com.fieldnotes.fncp.mvc.model.fnview.FNTextField;
import com.fieldnotes.fncp.mvc.model.listview.FNListView;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
import com.fieldnotes.fncp.mvc.controller.services.FNSessionService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.fieldnotes.fncp.constants.FNPConstants.*;

public class SearchUserPanel extends FNPanel {

    private static SearchUserPanel mInstance;
    private static FNPanel mSearchUserPanel;
    private static FNPanel mSearchTextFieldPanel;
    private FNTextField mSearchUser;
    private FNButton mButtonSearch;
    private FNButton mButtonExport;
    private FNListView mListView;
    private ArrayList<FNUser> mUsers;

    private SearchUserPanel() {
        mSearchUserPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        mSearchUser = new FNTextField();
        mButtonSearch = new FNButton(FNCPConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNCPConstants.BUTTON_EXPORT);
        mListView = new FNListView(false);
        mUsers = new ArrayList<>();

        init();
    }

    public static SearchUserPanel getInstance() {
        if (mInstance == null) {
            mInstance = new SearchUserPanel();
        }
        return mInstance;
    }

    private void init() {
        BorderLayout searchUserLayout = new BorderLayout();
        mSearchUserPanel.setLayout(searchUserLayout);
        GridLayout searchUserTextFieldPanelLayout = new GridLayout(0, 2);
        mSearchTextFieldPanel.setLayout(searchUserTextFieldPanelLayout);

        FNLabel searchUserLbl = new FNLabel(FNCPConstants.FN_USERNAME_LABEL);

        // Add Views to TextField Panel
        mSearchTextFieldPanel.add(searchUserLbl);
        mSearchTextFieldPanel.add(mSearchUser);
        mSearchTextFieldPanel.add(new FNLabel());
        mSearchTextFieldPanel.add(mButtonSearch);
        // Add Views to Main Panel
        mSearchUserPanel.add(mSearchTextFieldPanel, BorderLayout.NORTH);
        mSearchUserPanel.add(mListView);
        mSearchUserPanel.add(mButtonExport, BorderLayout.SOUTH);

        // Initial View Settings
        mSearchUserPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        mButtonSearch.addActionListener(e -> {
            mListView.removeItems();

            String searchUsername = mSearchUser.getText();
            searchUser(searchUsername);
        });

        // When user tries to export CSV file to user desktop
        mButtonExport.addActionListener(e -> {
            boolean exportSuccessCode = ExportController.writeUserToCSVFile(mUsers);

            if (exportSuccessCode) {
                JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Success! CVS report generated");
            } else {
                JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Failure! CVS export error");
            }
        });
    }

    /**
     * search users in FieldNotes
     *
     * @param username
     */
    private void searchUser(String username) {
        JSONObject searchResponse = FNUserService.searchUsers(username);
        String status = searchResponse.getString(RESPONSE_STATUS_TAG);
        String messageString = searchResponse.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            JSONArray messageArray = new JSONArray(messageString);

            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject message = messageArray.getJSONObject(i);

                FNUser note = FNSessionService.buildUser(message);
                // add to ListView
                mListView.addItem(note);
            }
        } else {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
        }
    }

    public static JPanel getView() {
        return mSearchUserPanel;
    }

    public static void showView() {
        mSearchUserPanel.setVisible(true);
    }

    public static void hideView() {
        mSearchUserPanel.setVisible(false);
        mInstance.resetGui();
    }

    private void resetGui() {
        mSearchUser.setText(null);

        // remove items from ListView
        mListView.removeItems();
    }
}