/**
 * © 2017-2018 FieldNotes All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.validation.FNUserValidation;
import com.devhunter.fncp.mvc.controller.sql.FNLoginController;
import com.devhunter.fncp.mvc.controller.sql.FNUserController;
import com.devhunter.fncp.mvc.model.FNUser;
import com.devhunter.fncp.mvc.model.fnview.*;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeUserPasswordPanel extends FNPanel {

    // Panels
    private static ChangeUserPasswordPanel mInstance;
    private static FNPanel mChangeUserPasswordPanel;
    private FNPanel mChangePasswordTextFieldPanel;
    // TextFields
    private FNTextField mChangePassUsername;
    private FNPasswordField mCurrentPass;
    private FNPasswordField mNewUserPass;
    // Buttons
    private FNButton mButtonChangePassword;

    private ChangeUserPasswordPanel() {
        // Create Panels
        mChangeUserPasswordPanel = new FNPanel();
        mChangePasswordTextFieldPanel = new FNPanel();
        // Create TextFields
        mChangePassUsername = new FNTextField();
        mCurrentPass = new FNPasswordField();
        mNewUserPass = new FNPasswordField();

        // Create Buttons
        mButtonChangePassword = new FNButton(FNConstants.BUTTON_UPDATE);
        init();
    }

    public static ChangeUserPasswordPanel getInstance() {
        if (mInstance == null) {
            mInstance = new ChangeUserPasswordPanel();
        }
        return mInstance;
    }

    private void init() {
        // Panel Layouts
        BorderLayout changeUserPasswordLayout = new BorderLayout();
        mChangeUserPasswordPanel.setLayout(changeUserPasswordLayout);
        GridLayout changeUserPasswordTextFieldPanelLayout = new GridLayout(0, 2);
        mChangePasswordTextFieldPanel.setLayout(changeUserPasswordTextFieldPanelLayout);

        // ADMIN ACCESS: change any user password
        if (FNUtil.getInstance().hasAdminAccess()) {
            // Labels
            FNLabel passUserlbl = new FNLabel(FNConstants.CRED_USERNAME_LABEL);
            FNLabel newPassUserlbl = new FNLabel(FNConstants.USER_NEW_PASSWORD_LABEL);

            // Add Views to TextField Panel
            mChangePasswordTextFieldPanel.add(passUserlbl);
            mChangePasswordTextFieldPanel.add(mChangePassUsername);
            mChangePasswordTextFieldPanel.add(newPassUserlbl);
            mChangePasswordTextFieldPanel.add(mNewUserPass);
            mChangePasswordTextFieldPanel.add(new FNLabel());
            mChangePasswordTextFieldPanel.add(mButtonChangePassword);

            mButtonChangePassword.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Create FNUser -- with new password
                    FNUser user = FNUtil.getInstance().getEntityByUserName(mChangePassUsername.getText());
                    user.setPassword(mNewUserPass.getText());
                    changePassword(user);
                }
            });
            // NON-ADMIN ACCESS: only change your own password
        } else {
            // Labels
            FNLabel currentPassLbl = new FNLabel(FNConstants.USER_CURRENT_PASSWORD_LABEL);
            FNLabel newPasslbl = new FNLabel(FNConstants.USER_NEW_PASSWORD_LABEL);

            // Add Views to TextField Panel
            mChangePasswordTextFieldPanel.add(currentPassLbl);
            mChangePasswordTextFieldPanel.add(mCurrentPass);
            mChangePasswordTextFieldPanel.add(newPasslbl);
            mChangePasswordTextFieldPanel.add(mNewUserPass);
            mChangePasswordTextFieldPanel.add(new FNLabel());
            mChangePasswordTextFieldPanel.add(mButtonChangePassword);

            mButtonChangePassword.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if they supply their current password
                    FNLoginController action = new FNLoginController();
                    if (action.SQLLogin(FNUtil.getInstance().getCurrentUsername(), mCurrentPass.getText())) {
                        // Create FNUser with new password
                        FNUser user = new FNUser.FNEntityBuilder()
                                .setUsername(FNUtil.getInstance().getCurrentUsername())
                                .setPassword(mNewUserPass.getText())
                                .setType(null)
                                .build();
                        changePassword(user);
                    } else {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Current Password was incorrect");
                    }
                }
            });
        }
        // Add Views to Main Panel
        mChangeUserPasswordPanel.add(mChangePasswordTextFieldPanel, BorderLayout.NORTH);
        // Initial View Settings
        mChangeUserPasswordPanel.setVisible(false);

        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();
    }

    /**
     * change the password of the entity
     *
     * @param entity
     */
    private void changePassword(FNUser entity) {
        if (!entity.getUsername().equals("UNKNOWN")) {
            // Validate UserName and password
            if (FNUserValidation.validate(entity)) {
                // send user to controller for database updateData
                FNUserController conn = new FNUserController();
                int changePasswordResultCode = conn.updatePassword(entity);
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
        mChangePassUsername.setText(null);
        mNewUserPass.setText(null);
    }
}
