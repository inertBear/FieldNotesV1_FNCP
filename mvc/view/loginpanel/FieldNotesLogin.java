/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.view.loginpanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import com.devhunter.fieldnotes.FieldNotesInit;
import com.devhunter.fieldnotes.constants.FieldNotesConstants;
import com.devhunter.fieldnotes.mvc.controller.sql.FNLoginController;
import com.devhunter.fieldnotes.mvc.model.FNImageButton;
import com.devhunter.fieldnotes.mvc.model.FNImageLabel;
import com.devhunter.fieldnotes.mvc.model.FNLabel;
import com.devhunter.fieldnotes.mvc.model.FNPanel;
import com.devhunter.fieldnotes.mvc.model.FNTextField;
import com.devhunter.fieldnotes.mvc.view.FieldNotesControlPanel;
import com.devhunter.fieldnotes.utilities.FieldNotesUtil;

/**
 * This class draws the login panel as a singleton. Successful login will create an instance of
 * the Control Panel
 * 
 * @author devHunter Feb 20, 2018
 *
 */

public class FieldNotesLogin extends FNPanel {

	// Login Frame
	private static JFrame mLoginFrame;
	// Panels
	private static FieldNotesLogin mInstance;
	private FNPanel mLoginPanel;
	private FNPanel mLoginCredentialPanel;
	// ImageLabels
	private FNImageLabel mLoginTitleLabel;
	// TextFields
	private FNTextField mLoginUsername;
	// PasswordFields
	private JPasswordField mLoginPassword;
	// ImageButtons
	private FNImageButton mButtonLogin;

	private FieldNotesLogin() {
		// Create Frames
		mLoginFrame = FieldNotesInit.getFieldNotesJFrame();
		// Create Panels
		mLoginPanel = new FNPanel();
		mLoginCredentialPanel = new FNPanel();
		// Create TextFields
		mLoginUsername = new FNTextField();
		// Create PasswordFields
		mLoginPassword = new JPasswordField();
		init();
	}

	public static FieldNotesLogin getInstance() {
		if (mInstance == null) {
			mInstance = new FieldNotesLogin();
		}
		return mInstance;
	}

	public void init() {
		// Frame
		mLoginFrame.setSize(FieldNotesConstants.lOGIN_PANEL_X_AXIS, FieldNotesConstants.lOGIN_PANEL_Y_AXIS);
		mLoginFrame.setLocationRelativeTo(null);
		mLoginFrame.setVisible(true);
		mLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Panels/Layouts
		BorderLayout loginPanelMainLayout = new BorderLayout();
		mLoginPanel.setLayout(loginPanelMainLayout);
		GridLayout loginCredentialPanelLayout = new GridLayout(0,2);
		mLoginCredentialPanel.setLayout(loginCredentialPanelLayout);
		// ImageLabels
		try {
			Image img = ImageIO.read(getClass().getResource(FieldNotesConstants.APPLICATION_LOGO_FOLDER));
			mLoginTitleLabel = new FNImageLabel(new ImageIcon(img));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(mLoginFrame, "title image failed to load");
		}
		// ImageButtons
		try {
			Image img = ImageIO.read(getClass().getResource(FieldNotesConstants.BUTTON_SUBMIT_LOCATION));
			mButtonLogin = new FNImageButton(new ImageIcon(img));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(mLoginFrame, "Image button failed to load");
		}
		// Labels
		FNLabel lblUsername = new FNLabel(FieldNotesConstants.CRED_USERNAME_LABEL);
		FNLabel lblPassword = new FNLabel(FieldNotesConstants.CRED_PASSWORD_LABEL);
		// Override Label properties
		mLoginUsername.setPreferredSize(FieldNotesUtil.getInstance().getStandardTextFieldDimension());
		mLoginPassword.setPreferredSize(FieldNotesUtil.getInstance().getStandardTextFieldDimension());
		// Override PasswordField properties
		mLoginPassword.setBorder(BorderFactory.createLineBorder(FieldNotesUtil.getInstance().getPrimaryColor()));
		
		// Add Views to UserName/Password Panels
		mLoginCredentialPanel.add(lblUsername);
		mLoginCredentialPanel.add(mLoginUsername);
		mLoginCredentialPanel.add(lblPassword);
		mLoginCredentialPanel.add(mLoginPassword);
		// Add Views to Main Panel
		mLoginPanel.add(mLoginTitleLabel, BorderLayout.NORTH);
		mLoginPanel.add(mLoginCredentialPanel, BorderLayout.CENTER);
		mLoginPanel.add(mButtonLogin, BorderLayout.SOUTH);
		mLoginPanel.setVisible(true);
		// Add Main Panel to Frame
		mLoginFrame.add(mLoginPanel, BorderLayout.CENTER);
		mLoginFrame.repaint();
		mLoginFrame.revalidate();
		//pressing "Enter" triggers the Login Button
		JRootPane rootPane = SwingUtilities.getRootPane(mButtonLogin);
		rootPane.setDefaultButton(mButtonLogin);

		mButtonLogin.addActionListener(
			// TODO: refactor to use FieldNoteUser
			mLoginPanel.setVisible(false);
			String username = mLoginUsername.getText();

			if(username != null) {
				FieldNotesUtil.getInstance().setCurrentUser(username);
				// TODO: depreciated (for security reasons): need to change to .getPassword() -- this returns a char[]
				String password = mLoginPassword.getText();
				// send FNUser to controller for login validation
				boolean result = false;
				FNLoginController action = new FNLoginController();
				result = action.SQLLogin(username, password);
				if (result) {
					FieldNotesControlPanel.getInstance();
					//mLoginFrame.dispatchEvent(new WindowEvent(mLoginFrame, WindowEvent.WINDOW_CLOSING));
				} else {
					JOptionPane.showMessageDialog(mLoginFrame, "Invalid username or password");
					mLoginPanel.setVisible(true);
					mLoginPassword.setText("");
				}
			} else {
				JOptionPane.showMessageDialog(mLoginFrame, "Please enter a username");
				mLoginPanel.setVisible(true);
				mLoginPassword.setText("");
			}
		});
	}
	
	public static JFrame getFieldNotesLoginFrame() {
		return mLoginFrame;
	}
}
