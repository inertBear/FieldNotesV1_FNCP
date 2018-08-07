/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel.subpanels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.devhunter.fncp.constants.FieldNotesConstants;
import com.devhunter.fncp.mvc.controller.FieldNoteUserValidation;
import com.devhunter.fncp.mvc.controller.sql.SQLUserController;
import com.devhunter.fncp.mvc.model.FNButton;
import com.devhunter.fncp.mvc.model.FNLabel;
import com.devhunter.fncp.mvc.model.FNPanel;
import com.devhunter.fncp.mvc.model.FNTextField;
import com.devhunter.fncp.mvc.model.FieldNoteUser;
import com.devhunter.fncp.mvc.model.FieldNoteUser.FieldNoteUserBuilder;
import com.devhunter.fncp.mvc.view.FieldNotesControlPanel;

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
		mButtonChangePassword = new FNButton(FieldNotesConstants.BUTTON_UPDATE);
		init();
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
		GridLayout changeUserPasswordTextFieldPanelLayout = new GridLayout(0,2);
		mChangePasswordTextFieldPanel.setLayout(changeUserPasswordTextFieldPanelLayout);
		// Labels
		FNLabel passUserlbl = new FNLabel(FieldNotesConstants.CRED_USERNAME_LABEL);
		FNLabel newPassUserlbl = new FNLabel(FieldNotesConstants.USER_NEW_PASSWORD_LABEL);
		
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
		FieldNotesControlPanel.getFieldNotesFrame().repaint();
		FieldNotesControlPanel.getFieldNotesFrame().revalidate();

		mButtonChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create FieldNoteUser -- with new password
				FieldNoteUser user = new FieldNoteUserBuilder()
						.setUserName(mChangeUserPass.getText())
						.setPassword(mNewUserPass.getText())
						.createUser();
				// Validate UserName and password
				if (FieldNoteUserValidation.validate(user)) {
					// send user to controller for database update
					SQLUserController conn = new SQLUserController();
					int changePasswordResultCode = conn.updatePassword(user);
					// code 1 == success, code 2 == already exists, code 3 ==
					// failure
					if (changePasswordResultCode == 1) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"User password updated in Field Notes");
					} else if (changePasswordResultCode == 2) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"User doesn't exist in Field Notes");
					} else {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"Runtime Error - PLEASE CONTACT FIELD NOTES ADMIN SUPPORT");
					}
				} else {
					//Validation failed - Do nothing and allow user to resubmit
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
