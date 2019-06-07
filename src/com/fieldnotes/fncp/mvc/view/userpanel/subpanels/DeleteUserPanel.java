/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.view.userpanel.subpanels;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.mvc.controller.FNUserService;
import com.fieldnotes.fncp.mvc.model.fnview.FNButton;
import com.fieldnotes.fncp.mvc.model.fnview.FNLabel;
import com.fieldnotes.fncp.mvc.model.fnview.FNPanel;
import com.fieldnotes.fncp.mvc.model.fnview.FNTextField;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

import static com.fieldnotes.fncp.constants.FNPConstants.*;

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
        mButtonDelete = new FNButton(FNCPConstants.BUTTON_DELETE);

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

        FNLabel deleteUserlbl = new FNLabel(FNCPConstants.USER_USERNAME_LABEL);

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

    /**
     * delete user from FieldNotes
     *
     * @param username
     */
    private void deleteUser(String username) {
        // delete user
        JSONObject deleteUserResponse = FNUserService.deleteUser(username);

        String deleteStatus = deleteUserResponse.getString(RESPONSE_STATUS_TAG);
        if (deleteStatus.equals(RESPONSE_STATUS_SUCCESS)) {
            resetGui();
        }
        String message = deleteUserResponse.getString(RESPONSE_MESSAGE_TAG);
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