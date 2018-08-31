/**
 * © 2017-2018 FieldNotes All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.FNUserValidation;
import com.devhunter.fncp.mvc.controller.sql.SQLUserController;
import com.devhunter.fncp.mvc.model.*;
import com.devhunter.fncp.mvc.model.FNUser.FNAdmin;
import com.devhunter.fncp.mvc.model.FNUser.FNEntity;
import com.devhunter.fncp.mvc.model.FNUser.FNTestUser;
import com.devhunter.fncp.mvc.model.FNUser.FNUser;
import com.devhunter.fncp.mvc.view.FNControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserPanel extends FNPanel {

    // Panels
    private static AddUserPanel mInstance;
    private static FNPanel mAddUserPanel;
    private FNPanel mAddUserTextFieldPanel;
    private FNPanel mCheckBoxPanel;
    // TextFields
    private FNTextField mAddUser;
    private FNTextField mAddPassword;
    // Buttons
    private FNButton mButtonAdd;
    //user checkboxes
    private FNCheckbox mAdminCheckbox;
    private FNCheckbox mUserCheckbox;
    private FNCheckbox mTestCheckbox;

    private AddUserPanel() {
        // Create Panels
        mAddUserPanel = new FNPanel();
        mAddUserTextFieldPanel = new FNPanel();
        mCheckBoxPanel = new FNPanel();
        // Create TextFields
        mAddUser = new FNTextField();
        mAddPassword = new FNTextField();
        // Create Buttons
        mButtonAdd = new FNButton(FNConstants.BUTTON_ADD);
        //Create Checkboxes
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

    void init() {
        // Panel Layouts
        BorderLayout addUserLayout = new BorderLayout();
        mAddUserPanel.setLayout(addUserLayout);
        // Labels
        FNLabel addUserLbl = new FNLabel(FNConstants.USER_NEW_USERNAME_LABEL);
        FNLabel addPassLbl = new FNLabel(FNConstants.USER_NEW_PASSWORD_LABEL);
        //Checkboxes
        ButtonGroup userButtonGroup = new ButtonGroup();
        userButtonGroup.add(mUserCheckbox);
        userButtonGroup.add(mAdminCheckbox);
        userButtonGroup.add(mTestCheckbox);
        //set FNUser by default
        mUserCheckbox.setSelected(true);
        //add checkboxes to panel
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
                // Create FNEntity
                FNEntity user;
                if (mAdminCheckbox.isSelected()) {
                    user = new FNAdmin(mAddUser.getText(), mAddPassword.getText());
                } else if (mTestCheckbox.isSelected()) {
                    user = new FNTestUser(mAddUser.getText(), mAddPassword.getText());
                } else {
                    user = new FNUser(mAddUser.getText(), mAddPassword.getText());
                }

                // validate USER
                if (FNUserValidation.validate(user)) {
                    // send user to controller for CUD event
                    SQLUserController conn = new SQLUserController();
                    int resultCode = conn.addUser(user);
                    // code 1 == success, code 2 == user already exists, code 3 == failure
                    if (resultCode == 1) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                "User added to Field Notes");
                    } else if (resultCode == 2) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                "User already exists in Field Notes");
                    } else {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                "Connection Error - USER WAS NOT ADDED");
                    }
                }
            }
        });
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
