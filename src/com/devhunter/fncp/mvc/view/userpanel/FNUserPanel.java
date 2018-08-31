/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.userpanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.model.FNButton;
import com.devhunter.fncp.mvc.model.FNPanel;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.mvc.view.userpanel.subpanels.AddUserPanel;
import com.devhunter.fncp.mvc.view.userpanel.subpanels.ChangeUserPasswordPanel;
import com.devhunter.fncp.mvc.view.userpanel.subpanels.DeleteUserPanel;
import com.devhunter.fncp.mvc.view.userpanel.subpanels.SearchUserPanel;

public class FNUserPanel extends FNPanel {

	private static FNUserPanel sInstance;
	private static FNPanel sUserControlPanel;
	private FNButton mBtnUserSearch;
	private FNButton mBtnUserAdd;
	private FNButton mBtnUserDelete;
	private FNButton mBtnUserPassword;

	private FNUserPanel() {
		sUserControlPanel = new FNPanel();
		mBtnUserSearch = new FNButton(FNConstants.USER_SEARCH_BUTTON);
		mBtnUserAdd = new FNButton(FNConstants.USER_ADD_BUTTON);
		mBtnUserDelete = new FNButton(FNConstants.USER_DELETE_BUTTON);
		mBtnUserPassword = new FNButton(FNConstants.USER_EDIT_PASSWORD_BUTTON);
		init();
	}

	public static FNUserPanel getInstance() {
		if (sInstance == null) {
			sInstance = new FNUserPanel();
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
				FNControlPanel.getFieldNotesFrame().add(SearchUserPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				SearchUserPanel.getView().setVisible(true);

				FNControlPanel.getFieldNotesFrame().repaint();
				FNControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		// When user navigates to Add User TAB
		mBtnUserAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FNControlPanel.getFieldNotesFrame().add(AddUserPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				AddUserPanel.getView().setVisible(true);

				FNControlPanel.getFieldNotesFrame().repaint();
				FNControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		// When user navigates to Delete User TAB
		mBtnUserDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FNControlPanel.getFieldNotesFrame().add(DeleteUserPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				DeleteUserPanel.getView().setVisible(true);

				FNControlPanel.getFieldNotesFrame().repaint();
				FNControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		// When user navigates to Change User Password TAB
		mBtnUserPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FNControlPanel.getFieldNotesFrame().add(ChangeUserPasswordPanel.getView(), BorderLayout.CENTER);
				sUserControlPanel.setVisible(true);
				ChangeUserPasswordPanel.getView().setVisible(true);

				FNControlPanel.getFieldNotesFrame().repaint();
				FNControlPanel.getFieldNotesFrame().revalidate();
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
