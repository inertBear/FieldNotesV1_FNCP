/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.FNUserController;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class DeleteUserPanel extends FNPanel {

    private static DeleteUserPanel mInstance;
    private static FNPanel mDeleteUserPanel;
    private FNPanel mDeleteUserTextFieldPanel;
    private FNTextField mDeleteUser;
    private FNButton mButtonDelete;

    private DeleteUserPanel() {
        mDeleteUserPanel = new FNPanel();
        mDeleteUserTextFieldPanel = new FNPanel();
        mDeleteUser = new FNTextField();
        mButtonDelete = new FNButton(FNConstants.BUTTON_DELETE);

        init();
    }

    public static DeleteUserPanel getInstance() {
        if (mInstance == null) {
            mInstance = new DeleteUserPanel();
        }
        return mInstance;
    }

    private void init() {
        BorderLayout deleteUserPanelLayout = new BorderLayout();
        mDeleteUserPanel.setLayout(deleteUserPanelLayout);

        GridLayout deleteUserTextFieldPanelLayout = new GridLayout(0, 2);
        mDeleteUserTextFieldPanel.setLayout(deleteUserTextFieldPanelLayout);

        FNLabel deleteUserlbl = new FNLabel(FNConstants.CRED_USERNAME_LABEL);

        mDeleteUserTextFieldPanel.add(deleteUserlbl);
        mDeleteUserTextFieldPanel.add(mDeleteUser);
        mDeleteUserTextFieldPanel.add(new FNLabel());
        mDeleteUserTextFieldPanel.add(mButtonDelete);
        mDeleteUserPanel.add(mDeleteUserTextFieldPanel, BorderLayout.NORTH);
        mDeleteUserPanel.setVisible(false);

        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        mButtonDelete.addActionListener(e -> {
            String deleteUsername = mDeleteUser.getText();
            deleteUser(deleteUsername);
        });
    }

    private void deleteUser(String username) {
        JSONObject deleteUserResponse = FNUserController.deleteUser(username);

        String status = deleteUserResponse.getString("status");
        if (status.equals("success")) {
            resetGui();
        }
        String message = deleteUserResponse.getString("message");
        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), message);
    }

    public static JPanel getView() {
        return mDeleteUserPanel;
    }

    public static void showView() {
        mDeleteUserPanel.setVisible(true);
    }

    public static void hideView() {
        mDeleteUserPanel.setVisible(false);
        mInstance.resetGui();
    }

    private void resetGui() {
        mDeleteUser.setText(null);
    }
}
