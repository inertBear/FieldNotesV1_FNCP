/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
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

public class DeleteUserPanel extends FNPanel {

    // Panels
    private static DeleteUserPanel mInstance;
    private static FNPanel mDeleteUserPanel;
    private FNPanel mDeleteUserTextFieldPanel;
    // TextFields
    private FNTextField mDeleteUser;
    // Buttons
    private FNButton mButtonDelete;

    private DeleteUserPanel() {
        // Create Panels
        mDeleteUserPanel = new FNPanel();
        mDeleteUserTextFieldPanel = new FNPanel();
        // Create TextFields
        mDeleteUser = new FNTextField();
        // Create Buttons
        mButtonDelete = new FNButton(FNConstants.BUTTON_DELETE);
        init();
    }

    public static DeleteUserPanel getInstance() {
        if (mInstance == null) {
            mInstance = new DeleteUserPanel();
        }
        return mInstance;
    }

    void init() {
        // Panel Layouts
        BorderLayout deleteUserPanelLayout = new BorderLayout();
        mDeleteUserPanel.setLayout(deleteUserPanelLayout);
        GridLayout deleteUserTextFieldPanelLayout = new GridLayout(0, 2);
        mDeleteUserTextFieldPanel.setLayout(deleteUserTextFieldPanelLayout);
        // Labels
        FNLabel deleteUserlbl = new FNLabel(FNConstants.CRED_USERNAME_LABEL);

        mDeleteUserTextFieldPanel.add(deleteUserlbl);
        mDeleteUserTextFieldPanel.add(mDeleteUser);
        mDeleteUserTextFieldPanel.add(new FNLabel());
        mDeleteUserTextFieldPanel.add(mButtonDelete);

        mDeleteUserPanel.add(mDeleteUserTextFieldPanel, BorderLayout.NORTH);

        mDeleteUserPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        mButtonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // the existence of the user is validated by the server
                FNEntity user = FNUtil.getInstance().getEntityByUserName(mDeleteUser.getText());
                if (!user.getUsername().equals("UNKNOWN")) {
                    if (FNUserValidation.validate(user)) {
                        // send user to controller for CUD event
                        SQLUserController conn = new SQLUserController();
                        int resultCode = conn.deleteUser(user);
                        // code 1 == success, code 2 == already exists, code 3 == failure
                        if (resultCode == 1) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                    "User deleted from Field Notes");
                        } else if (resultCode == 2) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                    "UserName does not exist");
                        } else {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                    "Connection Error - USER NOT DELETED");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                            "UserName does not exist");
                }
            }
        });
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
