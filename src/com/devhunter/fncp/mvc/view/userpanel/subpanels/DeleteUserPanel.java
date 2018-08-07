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

public class DeleteUserPanel extends FNPanel{

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
		mButtonDelete = new FNButton(FieldNotesConstants.BUTTON_DELETE);
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
		GridLayout deleteUserTextFieldPanelLayout = new GridLayout(0,2);
		mDeleteUserTextFieldPanel.setLayout(deleteUserTextFieldPanelLayout);
		// Labels
		FNLabel deleteUserlbl = new FNLabel(FieldNotesConstants.CRED_USERNAME_LABEL);

		mDeleteUserTextFieldPanel.add(deleteUserlbl);
		mDeleteUserTextFieldPanel.add(mDeleteUser);
		mDeleteUserTextFieldPanel.add(new FNLabel());
		mDeleteUserTextFieldPanel.add(mButtonDelete);

		mDeleteUserPanel.add(mDeleteUserTextFieldPanel,BorderLayout.NORTH);

		mDeleteUserPanel.setVisible(false);
		FieldNotesControlPanel.getFieldNotesFrame().repaint();
		FieldNotesControlPanel.getFieldNotesFrame().revalidate();

		mButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create FieldNoteUser -- we don't need to know the password to delete the user
				FieldNoteUser user = new FieldNoteUserBuilder()
						.setUserName(mDeleteUser.getText())
						// bypass password validation, the existence of the user is validated by the server
						.setPassword("password")
						.createUser();
				if (FieldNoteUserValidation.validate(user)) {
					// send user to controller for CUD event
					SQLUserController conn = new SQLUserController();
					int resultCode = conn.deleteUser(user);
					// code 1 == success, code 2 == already exists, code 3 == failure
					if (resultCode == 1) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"User deleted from Field Notes");
					} else if (resultCode == 2) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"UserName does not exist");
					} else {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"Connection Error - USER NOT DELETED");
					}
				} else {
					// Validation failed - Do nothing and allow user to resend
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
