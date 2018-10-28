/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNCPConstants;
import com.devhunter.fncp.mvc.controller.FNUserController;
import com.devhunter.fncp.mvc.controller.exporter.ExportController;
import com.devhunter.fncp.mvc.model.FNUser;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.devhunter.fncp.constants.FNCPConstants.*;
import static com.devhunter.fncp.constants.FNPConstants.*;

public class SearchUserPanel extends FNPanel {

    private static SearchUserPanel mInstance;
    private static FNPanel mSearchUserPanel;
    private static FNPanel mSearchTextFieldPanel;
    private FNTextField mSearchUser;
    private JTextArea mSearchUserOutput;
    private FNButton mButtonSearch;
    private FNButton mButtonExport;
    private ArrayList<FNUser> mUsers;

    private SearchUserPanel() {
        mSearchUserPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        mSearchUser = new FNTextField();
        mSearchUserOutput = new JTextArea(28, 32);
        mButtonSearch = new FNButton(FNCPConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNCPConstants.BUTTON_EXPORT);
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

        JScrollPane userSearchScroll = new JScrollPane(mSearchUserOutput);
        mSearchUserOutput.setEditable(false);

        // Add Views to TextField Panel
        mSearchTextFieldPanel.add(searchUserLbl);
        mSearchTextFieldPanel.add(mSearchUser);
        mSearchTextFieldPanel.add(new FNLabel());
        mSearchTextFieldPanel.add(mButtonSearch);
        // Add Views to Main Panel
        mSearchUserPanel.add(mSearchTextFieldPanel, BorderLayout.NORTH);
        mSearchUserPanel.add(userSearchScroll, BorderLayout.CENTER);
        mSearchUserPanel.add(mButtonExport, BorderLayout.SOUTH);

        // Initial View Settings
        mSearchUserPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        mButtonSearch.addActionListener(e -> {
            mSearchUserOutput.setVisible(true);
            mSearchUserOutput.setText(null);

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
        JSONObject searchResponse = FNUserController.searchUsers(username);
        String status = searchResponse.getString(RESPONSE_STATUS_TAG);
        String messageString = searchResponse.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            JSONArray messageArray = new JSONArray(messageString);
            printJsonObject(messageArray, mSearchUserOutput);
        } else {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
        }
    }

    /**
     * print search results to JTextArea
     *
     * @param message
     * @param areaToPrintOn
     */
    private void printJsonObject(JSONArray message, JTextArea areaToPrintOn) {
        for (int i = 0; i < message.length(); i++) {
            JSONObject jsonObject = message.getJSONObject(i);
            String userId = jsonObject.getString(USER_USER_ID_TAG);
            String username = jsonObject.getString(USER_USERNAME_TAG);
            String password = jsonObject.getString(USER_PASSWORD_TAG);
            String type = jsonObject.getString(USER_TYPE_TAG);

            areaToPrintOn.append(USER_ID_LABEL + " " + userId + "\n");
            areaToPrintOn.append(USER_USERNAME_LABEL + " " + username + "\n");
            areaToPrintOn.append(USER_PASSWORD_LABEL + " " + password + "\n");
            areaToPrintOn.append(USER_USER_TYPE_LABEL + " " + type + "\n\n");
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
        mSearchUserOutput.setText(null);
    }
}
