/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.view.userpanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.devhunter.fieldnotes.constants.FieldNotesConstants;
import com.devhunter.fieldnotes.mvc.model.FNButton;
import com.devhunter.fieldnotes.mvc.model.FNPanel;
import com.devhunter.fieldnotes.mvc.view.FieldNotesControlPanel;
import com.devhunter.fieldnotes.mvc.view.userpanel.subpanels.AddUserPanel;
import com.devhunter.fieldnotes.mvc.view.userpanel.subpanels.ChangeUserPasswordPanel;
import com.devhunter.fieldnotes.mvc.view.userpanel.subpanels.DeleteUserPanel;
import com.devhunter.fieldnotes.mvc.view.userpanel.subpanels.SearchUserPanel;

public class FieldNotesUserPanel extends FNPanel {

	private static FieldNotesUserPanel sInstance;
	private static FNPanel sUserControlPanel;
	private FNButton mBtnUserSearch;
	private FNButton mBtnUserAdd;
	private FNButton mBtnUserDelete;
	private FNButton mBtnUserPassword;

	private FieldNotesUserPanel() {
		sUserControlPanel = new FNPanel();
		mBtnUserSearch = new FNButton(FieldNotesConstants.USER_SEARCH_BUTTON);
		mBtnUserAdd = new FNButton(FieldNotesConstants.USER_ADD_BUTTON);
		mBtnUserDelete = new FNButton(FieldNotesConstants.USER_DELETE_BUTTON);
		mBtnUserPassword = new FNButton(FieldNotesConstants.USER_EDIT_PASSWORD_BUTTON);
		init();
	}

	public static FieldNotesUserPanel getInstance() {
		if (sInstance == null) {
			sInstance = new FieldNotesUserPanel();
		}
		return sInstance;
	}

	void init() {
		sUserControlPanel.add(mBtnUserSearch);
		sUserControlPanel.add(mBtnUserAdd);
		sUserControlPanel.add(mBtnUserDelete);
		sUserControlPanel.add(mBtnUserPassword);
		sUserControlPanel.setVisible(false);

		//FUTURE TODO: use lazy loading to reduce startup time
		// Initialize User Search Panel
		SearchUserPanel.getInstance();
		// Initialize Add User Panel
		AddUserPanel.getInstance();
		// Initialize Delete User Panel
		DeleteUserPanel.getInstance();
		// Initialize Change Password User Panel
		ChangeUserPasswordPanel.getInstance();

		// when user navigates to Search User TAB
		mBtnUserSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(SearchUserPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				SearchUserPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		// When user navigates to Add User TAB
		mBtnUserAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(AddUserPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				AddUserPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		// When user navigates to Delete User TAB
		mBtnUserDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(DeleteUserPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				DeleteUserPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		// When user navigates to Change User Password TAB
		mBtnUserPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(ChangeUserPasswordPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				ChangeUserPasswordPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});
	}

	public static JPanel getView() {
		return sUserControlPanel;
	}

	public static void showView() {
		sUserControlPanel.setVisible(true);
	}

	public static void hideView() {
		sUserControlPanel.setVisible(false);
		sInstance.resetGui();
	}

	private void resetGui() {
		sUserControlPanel.setVisible(false);

		SearchUserPanel.hideView();
		AddUserPanel.hideView();
		DeleteUserPanel.hideView();
		ChangeUserPasswordPanel.hideView();
	}
}
