/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.loginpanel;

import com.devhunter.fncp.FNInit;
import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.sql.FNLoginController;
import com.devhunter.fncp.mvc.model.*;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class draws the login panel as a singleton. Successful login will create an instance of
 * the Control Panel
 *
 * @author devHunter Feb 20, 2018
 */

public class FNLogin extends FNPanel {

    // Login Frame
    private static JFrame mLoginFrame;
    // Panels
    private static FNLogin mInstance;
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

    private FNLogin() {
        // Create Frames
        mLoginFrame = FNInit.getFieldNotesJFrame();
        // Create Panels
        mLoginPanel = new FNPanel();
        mLoginCredentialPanel = new FNPanel();
        // Create TextFields
        mLoginUsername = new FNTextField();
        // Create PasswordFields
        mLoginPassword = new FNPasswordField();
        init();
    }

    public static FNLogin getInstance() {
        if (mInstance == null) {
            mInstance = new FNLogin();
        }
        return mInstance;
    }

    public void init() {
        // Frame
        mLoginFrame.setSize(FNConstants.lOGIN_PANEL_X_AXIS, FNConstants.lOGIN_PANEL_Y_AXIS);
        mLoginFrame.setLocationRelativeTo(null);
        mLoginFrame.setVisible(true);
        mLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Panels/Layouts
        BorderLayout loginPanelMainLayout = new BorderLayout();
        mLoginPanel.setLayout(loginPanelMainLayout);
        GridLayout loginCredentialPanelLayout = new GridLayout(0, 2);
        mLoginCredentialPanel.setLayout(loginCredentialPanelLayout);
        // ImageLabels
        try {
            Image img = ImageIO.read(getClass().getResource(FNConstants.APPLICATION_LOGO_FOLDER));
            mLoginTitleLabel = new FNImageLabel(new ImageIcon(img));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mLoginFrame, "title image failed to load");
        }
        // ImageButtons
        try {
            Image img = ImageIO.read(getClass().getResource(FNConstants.BUTTON_SUBMIT_LOCATION));
            mButtonLogin = new FNImageButton(new ImageIcon(img));
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(mLoginFrame, "Image button failed to load");
        }
        // Labels
        FNLabel lblUsername = new FNLabel(FNConstants.CRED_USERNAME_LABEL);
        FNLabel lblPassword = new FNLabel(FNConstants.CRED_PASSWORD_LABEL);
        // Override Label properties
        mLoginUsername.setPreferredSize(FNUtil.getInstance().getStandardTextFieldDimension());
        mLoginPassword.setPreferredSize(FNUtil.getInstance().getStandardTextFieldDimension());
        // Override PasswordField properties
        mLoginPassword.setBorder(BorderFactory.createLineBorder(FNUtil.getInstance().getPrimaryColor()));

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

        mButtonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mLoginPanel.setVisible(false);
                String username = mLoginUsername.getText();

                if (username != null) {
                    FNUtil.getInstance().setCurrentUser(username);
                    String password = mLoginPassword.getText();
                    // send FNEntity to controller for login validation
                    boolean result;
                    FNLoginController action = new FNLoginController();
                    result = action.SQLLogin(username, password);
                    if (result) {
                        //TODO: check user type: if admin load ALL, if user load:
                        FNControlPanel.getInstance();
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
            }
        });
    }

    public static JFrame getFieldNotesLoginFrame() {
        return mLoginFrame;
    }
}
