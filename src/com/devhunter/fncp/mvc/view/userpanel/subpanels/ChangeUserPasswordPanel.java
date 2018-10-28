/**
 * © 2017-2018 FieldNotes All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.FNUserController;
import com.devhunter.fncp.mvc.model.fnview.*;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class ChangeUserPasswordPanel extends FNPanel {

    private static ChangeUserPasswordPanel mInstance;
    private static FNPanel mChangeUserPasswordPanel;
    private FNPanel mChangePasswordTextFieldPanel;
    private FNTextField mChangePassUsername;
    private FNPasswordField mCurrentPass;
    private FNPasswordField mNewUserPass;
    private FNButton mButtonChangePassword;

    private ChangeUserPasswordPanel() {
        mChangeUserPasswordPanel = new FNPanel();
        mChangePasswordTextFieldPanel = new FNPanel();
        mChangePassUsername = new FNTextField();
        mCurrentPass = new FNPasswordField();
        mNewUserPass = new FNPasswordField();
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
        BorderLayout changeUserPasswordLayout = new BorderLayout();
        mChangeUserPasswordPanel.setLayout(changeUserPasswordLayout);
        GridLayout changeUserPasswordTextFieldPanelLayout = new GridLayout(0, 2);
        mChangePasswordTextFieldPanel.setLayout(changeUserPasswordTextFieldPanelLayout);

        // ADMIN ACCESS: change any user password
        if (FNUtil.getInstance().hasAdminAccess()) {
            FNLabel passUserlbl = new FNLabel(FNConstants.USER_USERNAME_LABEL);
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
        } else {
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
     */
    private void updatePassword(String username, String oldPassword, String newPassword) {
        JSONObject updatePasswordResponse;
        // if they supply their current password
        if (FNUtil.getInstance().getCurrentPassword().equals(oldPassword)) {
            // update their password
            updatePasswordResponse = FNUserController.updatePassword(username, newPassword);

            String status = updatePasswordResponse.getString(RESPONSE_STATUS_TAG);
            String message = updatePasswordResponse.getString(RESPONSE_MESSAGE_TAG);
            if (status.equals(RESPONSE_STATUS_SUCCESS)) {
                FNUtil.getInstance().setCurrentPassword(newPassword);
                resetGui();
            } else {
                mCurrentPass.setText(null);
                mNewUserPass.setText(null);
            }

            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), message);
        } else {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Current password incorrect");
            mCurrentPass.setText(null);
            mNewUserPass.setText(null);
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
        mChangePassUsername.setText(null);
        mCurrentPass.setText(null);
        mNewUserPass.setText(null);
    }
}
