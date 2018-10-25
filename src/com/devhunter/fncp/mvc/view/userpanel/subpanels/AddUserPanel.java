/**
 * © 2017-2018 FieldNotes All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.constants.FNUserConstants;
import com.devhunter.fncp.mvc.controller.FNUserController;
import com.devhunter.fncp.mvc.model.fnview.*;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserPanel extends FNPanel {

    private static AddUserPanel mInstance;
    private static FNPanel mAddUserPanel;
    private FNPanel mAddUserTextFieldPanel;
    private FNPanel mCheckBoxPanel;
    private FNTextField mAddUser;
    private FNTextField mAddPassword;
    private FNButton mButtonAdd;
    private FNCheckbox mAdminCheckbox;
    private FNCheckbox mUserCheckbox;
    private FNCheckbox mTestCheckbox;

    private AddUserPanel() {
        mAddUserPanel = new FNPanel();
        mAddUserTextFieldPanel = new FNPanel();
        mCheckBoxPanel = new FNPanel();
        mAddUser = new FNTextField();
        mAddPassword = new FNTextField();
        mButtonAdd = new FNButton(FNConstants.BUTTON_ADD);
        mAdminCheckbox = new FNCheckbox();
        mUserCheckbox = new FNCheckbox();
        mTestCheckbox = new FNCheckbox();

        init();
    }

    public static AddUserPanel getInstance() {
        if (mInstance == null) {
            mInstance = new AddUserPanel();
        }
        return mInstance;
    }

    private void init() {
        BorderLayout addUserLayout = new BorderLayout();
        mAddUserPanel.setLayout(addUserLayout);

        FNLabel addUserLbl = new FNLabel(FNConstants.USER_NEW_USERNAME_LABEL);
        FNLabel addPassLbl = new FNLabel(FNConstants.USER_NEW_PASSWORD_LABEL);

        ButtonGroup userButtonGroup = new ButtonGroup();
        userButtonGroup.add(mUserCheckbox);
        userButtonGroup.add(mAdminCheckbox);
        userButtonGroup.add(mTestCheckbox);

        mUserCheckbox.setSelected(true);

        mCheckBoxPanel.add(new FNLabel("as User:"));
        mCheckBoxPanel.add(mUserCheckbox);
        mCheckBoxPanel.add(new FNLabel("as Admin:"));
        mCheckBoxPanel.add(mAdminCheckbox);
        mCheckBoxPanel.add(new FNLabel("as Test User:"));
        mCheckBoxPanel.add(mTestCheckbox);

        // Add Views to TextField Panel with Gridbag layout
        mAddUserTextFieldPanel.setLayout(new GridBagLayout());
        //set constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.insets = new Insets(15, 20, 15, 20);
        mAddUserTextFieldPanel.add(addUserLbl, constraints);
        //set constraints
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 3;
        constraints.insets = new Insets(15, 20, 15, 20);
        mAddUserTextFieldPanel.add(mAddUser, constraints);
        //set constraints
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.insets = new Insets(15, 20, 15, 20);
        mAddUserTextFieldPanel.add(addPassLbl, constraints);
        //set constraints
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 3;
        constraints.insets = new Insets(15, 20, 15, 20);
        mAddUserTextFieldPanel.add(mAddPassword, constraints);
        //set constraints
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(15, 20, 15, 20);
        mAddUserTextFieldPanel.add(mCheckBoxPanel, constraints);
        //set constraints
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(15, 50, 15, 50);
        mAddUserTextFieldPanel.add(mButtonAdd, constraints);
        // Add Views to Main Panel
        mAddUserPanel.add(mAddUserTextFieldPanel, BorderLayout.NORTH);

        // Initial View Settings
        mAddUserPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        mButtonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = mAddUser.getText();
                String newPassword = mAddPassword.getText();
                String newType;

                if (mAdminCheckbox.isSelected()) {
                    newType = FNUserConstants.ADMIN_USER;
                } else if (mTestCheckbox.isSelected()) {
                    newType = FNUserConstants.TEST_USER;
                } else {
                    newType = FNUserConstants.REGULAR_USER;
                }

                addUser(newUsername, newPassword, newType);
            }
        });
    }

    private void addUser(String username, String password, String type) {
        JSONObject addUserResponse = FNUserController.addUser(username, password, type);

        String addUserMessage = addUserResponse.getString("message");
        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), addUserMessage);
    }

    public static JPanel getView() {
        return mAddUserPanel;
    }

    public static void showView() {
        mAddUserPanel.setVisible(true);
    }

    public static void hideView() {
        mAddUserPanel.setVisible(false);
        mInstance.resetGui();
    }

    private void resetGui() {
        mAddUser.setText(null);
        mAddPassword.setText(null);
    }
}
