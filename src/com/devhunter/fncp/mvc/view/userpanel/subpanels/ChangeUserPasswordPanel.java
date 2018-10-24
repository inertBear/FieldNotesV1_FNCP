/**
 * © 2017-2018 FieldNotes All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.validation.FNUserValidation;
import com.devhunter.fncp.mvc.controller.sql.FNUserController;
import com.devhunter.fncp.mvc.model.FNUser;
import com.devhunter.fncp.mvc.model.fnview.*;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;
import org.json.JSONObject;

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
                    String username = mChangePassUsername.getText();
                    String currentPassword = FNUtil.getInstance().getCurrentPassword();
                    String newPassword = mNewUserPass.getText();

                    updatePassword(username, currentPassword, newPassword);
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
                    String username = FNUtil.getInstance().getCurrentUsername();
                    String currentPassword = mCurrentPass.getText();
                    String newPassword = mNewUserPass.getText();

                    updatePassword(username, currentPassword, newPassword);
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
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    private void updatePassword(String username, String oldPassword, String newPassword) {
        JSONObject updatePasswordResponse = null;
        // if they supply their current password
        if(FNUtil.getInstance().getCurrentPassword().equals(oldPassword)) {
            if (!oldPassword.equals(newPassword)) {
                // update their password
                updatePasswordResponse = FNUserController.updatePassword(username, newPassword);
                if (updatePasswordResponse != null) {
                    String message = updatePasswordResponse.getString("message");

                    FNUtil.getInstance().setCurrentPassword(newPassword);

                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), message);
                } else {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Failure: contact admin");
                }
            } else {
                JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "cannot use old password");

            }
        } else {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "entered wrong current password");
        }
        mCurrentPass.setText(null);
        mNewUserPass.setText(null);
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
