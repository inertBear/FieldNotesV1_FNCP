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
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.devhunter.fncp.constants.FieldNotesConstants;
import com.devhunter.fncp.mvc.controller.exporter.ExportController;
import com.devhunter.fncp.mvc.controller.sql.SQLUserController;
import com.devhunter.fncp.mvc.model.FNButton;
import com.devhunter.fncp.mvc.model.FNLabel;
import com.devhunter.fncp.mvc.model.FNPanel;
import com.devhunter.fncp.mvc.model.FNTextField;
import com.devhunter.fncp.mvc.view.FieldNotesControlPanel;

public class SearchUserPanel extends FNPanel {

	// Panels
	public static SearchUserPanel mInstance;
	private static FNPanel mSearchUserPanel;
	private static FNPanel mSearchTextFieldPanel;
	// TextFields
	private FNTextField mSearchUser;
	// TextAreas
	private JTextArea mSearchUserOutput;
	// Buttons
	private FNButton mButtonSearch;
	private FNButton mButtonExport;
	// ArrayLists
	private ArrayList<String> mUserSearchResults;

	private SearchUserPanel() {
		// Create Panels
		mSearchUserPanel = new FNPanel();
		mSearchTextFieldPanel = new FNPanel();
		// Create TextFields
		mSearchUser = new FNTextField();
		// Create TextAreas
		mSearchUserOutput = new JTextArea(28, 32);
		// Create Buttons
		mButtonSearch = new FNButton(FieldNotesConstants.BUTTON_SEARCH);
		mButtonExport = new FNButton(FieldNotesConstants.BUTTON_EXPORT);
		// Create ArrayLists
		mUserSearchResults = new ArrayList<String>();
		init();
	}

	public static SearchUserPanel getInstance() {
		if (mInstance == null) {
			mInstance = new SearchUserPanel();
		}
		return mInstance;
	}

	void init() {
		// Panel Layouts
		BorderLayout searchUserLayout = new BorderLayout();
		mSearchUserPanel.setLayout(searchUserLayout);
		GridLayout searchUserTextFieldPanelLayout = new GridLayout(0,2);
		mSearchTextFieldPanel.setLayout(searchUserTextFieldPanelLayout);
		// Labels
		FNLabel searchUserLbl = new FNLabel(FieldNotesConstants.FN_USERNAME_LABEL);
		// ScrollPanes/TextAreas
		JScrollPane userSearchScroll = new JScrollPane(mSearchUserOutput);
		mSearchUserOutput.setEditable(false);
		
		// Add Views to TextField Panel
		mSearchTextFieldPanel.add(searchUserLbl);
		mSearchTextFieldPanel.add(mSearchUser);
		mSearchTextFieldPanel.add(new FNLabel());
		mSearchTextFieldPanel.add(mButtonSearch);
		// Add Views to Main Panel
		mSearchUserPanel.add(mSearchTextFieldPanel, BorderLayout.NORTH);
		mSearchUserPanel.add(userSearchScroll, BorderLayout.CENTER);
		mSearchUserPanel.add(mButtonExport, BorderLayout.SOUTH);
		
		// Initial View Settings
		mSearchUserPanel.setVisible(false);
		FieldNotesControlPanel.getFieldNotesFrame().repaint();
		FieldNotesControlPanel.getFieldNotesFrame().revalidate();
		
		/*
		 * TODO: Move controller code
		 */
		mButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mSearchUserOutput.setVisible(true);
				mSearchUserOutput.setText(null);
				SQLUserController conn = new SQLUserController();

				if (mSearchUser.getText().trim().isEmpty()) {
					mUserSearchResults = conn.mySQLSearchUser();

					for (int i = 0; i < mUserSearchResults.size(); i++) {
						if (i % 3 == 0) {
							mSearchUserOutput.append("User ID:      " + mUserSearchResults.get(i) + "\n");
						} else if ((i + 1) % 3 == 0) {
							mSearchUserOutput.append("Password:  " + mUserSearchResults.get(i) + "\n\n");
						} else {
							mSearchUserOutput.append("Username:  " + mUserSearchResults.get(i) + "\n");
						}
					}
				} else {
					String username = mSearchUser.getText();
					mUserSearchResults = conn.mySQLSearchUser(username);
					mSearchUserOutput.setText("User ID:      " + mUserSearchResults.get(0) + "\n");
					mSearchUserOutput.append("Username:  " + mUserSearchResults.get(1) + "\n");
					mSearchUserOutput.append("Password:  " + mUserSearchResults.get(2) + "\n\n");
				}
			}
		});

		// When user trys to export CSV file to user desktop
		mButtonExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int exportSuccessCode;

				ExportController exporter = new ExportController();
				exportSuccessCode = exporter.writeUserToCSVFile(mUserSearchResults);
				if (exportSuccessCode == 1) {
					JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
							"Success! CVS report generated");
				} else {
					JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
							"Failure! CVS export error");
				}
			}
		});
	}

	public static JPanel getView() {
		return mSearchUserPanel;
	}

	public static void showView() {
		mSearchUserPanel.setVisible(true);
	}

	public static void hideView() {
		mSearchUserPanel.setVisible(false);
		mInstance.resetGui();
	}

	private void resetGui() {
		mSearchUser.setText(null);
		mSearchUserOutput.setText(null);
	}
}
