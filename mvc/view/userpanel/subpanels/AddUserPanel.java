/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.view.userpanel.subpanels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.devhunter.fieldnotes.constants.FieldNotesConstants;
import com.devhunter.fieldnotes.mvc.controller.FieldNoteUserValidation;
import com.devhunter.fieldnotes.mvc.controller.sql.SQLUserController;
import com.devhunter.fieldnotes.mvc.model.FNButton;
import com.devhunter.fieldnotes.mvc.model.FNLabel;
import com.devhunter.fieldnotes.mvc.model.FNPanel;
import com.devhunter.fieldnotes.mvc.model.FNTextField;
import com.devhunter.fieldnotes.mvc.model.FieldNoteUser;
import com.devhunter.fieldnotes.mvc.model.FieldNoteUser.FieldNoteUserBuilder;
import com.devhunter.fieldnotes.mvc.view.FieldNotesControlPanel;

public class AddUserPanel extends FNPanel {

	// Panels
	private static AddUserPanel mInstance;
	private static FNPanel mAddUserPanel;
	private FNPanel mAddUserTextFieldPanel;
	// TextFields
	private FNTextField mAddUser;
	private FNTextField mAddPassword;
	// Buttons
	private FNButton mButtonAdd;

	private AddUserPanel() {
		// Create Panels
		mAddUserPanel = new FNPanel();
		mAddUserTextFieldPanel = new FNPanel();
		// Create TextFields
		mAddUser = new FNTextField();
		mAddPassword = new FNTextField();
		// Create Buttons
		mButtonAdd = new FNButton(FieldNotesConstants.BUTTON_ADD);
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
		GridLayout addUSERTextFieldPanelLayout = new GridLayout(0,2);
		mAddUserTextFieldPanel.setLayout(addUSERTextFieldPanelLayout);
		// Labels
		FNLabel addUserLbl = new FNLabel(FieldNotesConstants.USER_NEW_USERNAME_LABEL);
		FNLabel addPassLbl = new FNLabel(FieldNotesConstants.USER_NEW_PASSWORD_LABEL);
		
		// Add Views to TextField Panel
		mAddUserTextFieldPanel.add(addUserLbl);
		mAddUserTextFieldPanel.add(mAddUser);
		mAddUserTextFieldPanel.add(addPassLbl);
		mAddUserTextFieldPanel.add(mAddPassword);
		mAddUserTextFieldPanel.add(new FNLabel());
		mAddUserTextFieldPanel.add(mButtonAdd);
		// Add Views to Main Panel
		mAddUserPanel.add(mAddUserTextFieldPanel, BorderLayout.NORTH);
		
		// Initial View Settings
		mAddUserPanel.setVisible(false);
		FieldNotesControlPanel.getFieldNotesFrame().repaint();
		FieldNotesControlPanel.getFieldNotesFrame().revalidate();
		
		mButtonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create FieldNoteUser
				FieldNoteUser user = new FieldNoteUserBuilder()
						.setUserName(mAddUser.getText())
						.setPassword(mAddPassword.getText())
						.createUser();
				// validate USER
				if (FieldNoteUserValidation.validate(user)) {
					// send user to controller for CUD event
					SQLUserController conn = new SQLUserController();
					int resultCode = conn.addUser(user);
					// code 1 == success, code 2 == user already exists, code 3 == failure
					if (resultCode == 1) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"User added to Field Notes");
					} else if (resultCode == 2) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"User already exists in Field Notes");
					} else {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"Connection Error - USER WAS NOT ADDED");
					}
				} else {
					//Validation failed - Do nothing and allow then to make changes and send again
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
