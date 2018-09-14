/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.exporter.ExportController;
import com.devhunter.fncp.mvc.controller.sql.FNUserController;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.mvc.model.fnuser.FNEntity;
import com.devhunter.fncp.mvc.view.FNControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchUserPanel extends FNPanel {

    // Panels
    public static SearchUserPanel mInstance;
    private static FNPanel mSearchUserPanel;
    private static FNPanel mSearchTextFieldPanel;
    // TextFields
    private FNTextField mSearchUser;
    // TextAreas
    private JTextArea mSearchUserOutput;
    // Buttons
    private FNButton mButtonSearch;
    private FNButton mButtonExport;
    // ArrayLists
    private ArrayList<FNEntity> mUsers;

    private static final String ID = "ID: ";
    private static final String USERNAME = "Username: ";
    private static final String PASSWORD = "Password: ";
    private static final String USER_TYPE = "User Type: ";

    private SearchUserPanel() {
        // Create Panels
        mSearchUserPanel = new FNPanel();
        mSearchTextFieldPanel = new FNPanel();
        // Create TextFields
        mSearchUser = new FNTextField();
        // Create TextAreas
        mSearchUserOutput = new JTextArea(28, 32);
        // Create Buttons
        mButtonSearch = new FNButton(FNConstants.BUTTON_SEARCH);
        mButtonExport = new FNButton(FNConstants.BUTTON_EXPORT);
        // Create ArrayLists
        mUsers = new ArrayList<>();
        init();
    }

    public static SearchUserPanel getInstance() {
        if (mInstance == null) {
            mInstance = new SearchUserPanel();
        }
        return mInstance;
    }

    void init() {
        // Panel Layouts
        BorderLayout searchUserLayout = new BorderLayout();
        mSearchUserPanel.setLayout(searchUserLayout);
        GridLayout searchUserTextFieldPanelLayout = new GridLayout(0, 2);
        mSearchTextFieldPanel.setLayout(searchUserTextFieldPanelLayout);
        // Labels
        FNLabel searchUserLbl = new FNLabel(FNConstants.FN_USERNAME_LABEL);
        // ScrollPanes/TextAreas
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

        mButtonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mSearchUserOutput.setVisible(true);
                mSearchUserOutput.setText(null);
                FNUserController conn = new FNUserController();

                if (mSearchUser.getText().trim().isEmpty()) {
                    mUsers = conn.mySQLSearchUser();

                    for (FNEntity each : mUsers) {
                        //TODO: [FNCP-023] create static print user method in FNEntity EX: public static void printUser(where to print)
                        mSearchUserOutput.append(ID + each.getId() + "\n");
                        mSearchUserOutput.append(USERNAME + each.getUsername() + "\n");
                        mSearchUserOutput.append(PASSWORD + each.getPassword() + "\n");
                        mSearchUserOutput.append(USER_TYPE + each.getType() + "\n\n");
                    }
                } else {
                    String username = mSearchUser.getText();
                    FNEntity user = conn.mySQLSearchUser(username);
                    mSearchUserOutput.setText(ID + user.getId() + "\n");
                    mSearchUserOutput.append(USERNAME + user.getUsername() + "\n");
                    mSearchUserOutput.append(PASSWORD + user.getPassword() + "\n");
                    mSearchUserOutput.append(USER_TYPE + user.getType() + "\n\n");
                }
            }
        });

        // When user trys to export CSV file to user desktop
        mButtonExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ExportController exporter = new ExportController();
                boolean exportSuccessCode = exporter.writeUserToCSVFile(mUsers);
                if (exportSuccessCode) {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Success! CVS report generated");
                } else {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Failure! CVS export error");
                }
            }
        });
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
