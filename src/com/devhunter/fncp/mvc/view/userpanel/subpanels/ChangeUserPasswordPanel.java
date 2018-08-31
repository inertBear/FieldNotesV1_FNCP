/**
 * © 2017-2018 FieldNotes All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.FNUserValidation;
import com.devhunter.fncp.mvc.controller.sql.SQLUserController;
import com.devhunter.fncp.mvc.model.FNButton;
import com.devhunter.fncp.mvc.model.FNLabel;
import com.devhunter.fncp.mvc.model.FNPanel;
import com.devhunter.fncp.mvc.model.FNTextField;
import com.devhunter.fncp.mvc.model.FNUser.FNEntity;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeUserPasswordPanel extends FNPanel {

    // Panels
    public static ChangeUserPasswordPanel mInstance;
    private static FNPanel mChangeUserPasswordPanel;
    private FNPanel mChangePasswordTextFieldPanel;
    // TextFields
    private FNTextField mChangeUserPass;
    private FNTextField mNewUserPass;
    // Buttons
    private FNButton mButtonChangePassword;

    private ChangeUserPasswordPanel() {
        // Create Panels
        mChangeUserPasswordPanel = new FNPanel();
        mChangePasswordTextFieldPanel = new FNPanel();
        // Create TextFields
        mChangeUserPass = new FNTextField();
        mNewUserPass = new FNTextField();
        // Create Buttons
        mButtonChangePassword = new FNButton(FNConstants.BUTTON_UPDATE);
        init();
        //
    }

    public static ChangeUserPasswordPanel getInstance() {
        if (mInstance == null) {
            mInstance = new ChangeUserPasswordPanel();
        }
        return mInstance;
    }

    void init() {
        // Panel Layouts
        BorderLayout changeUserPasswordLayout = new BorderLayout();
        mChangeUserPasswordPanel.setLayout(changeUserPasswordLayout);
        GridLayout changeUserPasswordTextFieldPanelLayout = new GridLayout(0, 2);
        mChangePasswordTextFieldPanel.setLayout(changeUserPasswordTextFieldPanelLayout);
        // Labels
        FNLabel passUserlbl = new FNLabel(FNConstants.CRED_USERNAME_LABEL);
        FNLabel newPassUserlbl = new FNLabel(FNConstants.USER_NEW_PASSWORD_LABEL);

        // Add Views to TextField Panel
        mChangePasswordTextFieldPanel.add(passUserlbl);
        mChangePasswordTextFieldPanel.add(mChangeUserPass);
        mChangePasswordTextFieldPanel.add(newPassUserlbl);
        mChangePasswordTextFieldPanel.add(mNewUserPass);
        mChangePasswordTextFieldPanel.add(new FNLabel());
        mChangePasswordTextFieldPanel.add(mButtonChangePassword);
        // Add Views to Main Panel
        mChangeUserPasswordPanel.add(mChangePasswordTextFieldPanel, BorderLayout.NORTH);
        // Initial View Settings
        mChangeUserPasswordPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        mButtonChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create FNEntity -- with new password
                FNEntity user = FNUtil.getInstance().getEntityByUserName(mChangeUserPass.getText());
                // Validate UserName and password
                if (!user.getUsername().equals("UNKNOWN")) {
                    if (FNUserValidation.validate(user)) {
                        // send user to controller for database update
                        SQLUserController conn = new SQLUserController();
                        int changePasswordResultCode = conn.updatePassword(user);
                        // code 1 == success, code 2 == already exists, code 3 ==
                        // failure
                        if (changePasswordResultCode == 1) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                    "User password updated in Field Notes");
                        } else if (changePasswordResultCode == 2) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                    "User doesn't exist in Field Notes");
                        } else {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                    "Runtime Error - PLEASE CONTACT FIELD NOTES ADMIN SUPPORT");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                            "User doesn't exist in Field Notes");
                }
            }
        });
    }

    public static JPanel getView() {
        return mChangeUserPasswordPanel;
    }

    public static void showView() {
        mChangeUserPasswordPanel.setVisible(true);
    }

    public static void hideView() {
        mChangeUserPasswordPanel.setVisible(false);
        mInstance.resetGui();
    }

    private void resetGui() {
        mChangeUserPasswordPanel.setVisible(false);

        mChangeUserPasswordPanel.setVisible(false);
        mChangeUserPass.setText(null);
        mNewUserPass.setText(null);
    }
}
