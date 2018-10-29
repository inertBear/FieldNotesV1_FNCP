/**
 * ? 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.view.loginpanel;

import com.fieldnotes.fncp.FNInit;
import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.mvc.controller.FNUserController;
import com.fieldnotes.fncp.mvc.model.fnview.*;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
import com.fieldnotes.fncp.utilities.FNUtil;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.fieldnotes.fncp.constants.FNPConstants.*;

/**
 * This class draws the login panel as a singleton. Successful login will create an instance of
 * the Control Panel
 *
 * @author devHunter Feb 20, 2018
 */
public class FNLogin extends FNPanel {

    private static JFrame mLoginFrame;
    private static FNLogin mInstance;
    private FNPanel mLoginPanel;
    private FNPanel mLoginCredentialPanel;
    private FNImageLabel mLoginTitleLabel;
    private FNTextField mLoginUsername;
    private JPasswordField mLoginPassword;
    private FNImageButton mButtonLogin;


    private FNLogin() {
        mLoginFrame = FNInit.getFieldNotesJFrame();
        mLoginPanel = new FNPanel();
        mLoginCredentialPanel = new FNPanel();
        mLoginUsername = new FNTextField();
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
        mLoginFrame.setSize(FNCPConstants.lOGIN_PANEL_X_AXIS, FNCPConstants.lOGIN_PANEL_Y_AXIS);
        mLoginFrame.setLocationRelativeTo(null);
        mLoginFrame.setVisible(true);
        mLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        BorderLayout loginPanelMainLayout = new BorderLayout();
        mLoginPanel.setLayout(loginPanelMainLayout);
        GridLayout loginCredentialPanelLayout = new GridLayout(0, 2);
        mLoginCredentialPanel.setLayout(loginCredentialPanelLayout);

        // ImageLabels
        try {
            Image img = ImageIO.read(getClass().getResource(FNCPConstants.APPLICATION_LOGO_FOLDER));
            mLoginTitleLabel = new FNImageLabel(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Title image failed to load");
        }

        // ImageButtons
        try {
            Image img = ImageIO.read(getClass().getResource(FNCPConstants.BUTTON_SUBMIT_LOCATION));
            mButtonLogin = new FNImageButton(new ImageIcon(img));
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("Image button failed to load");
        }

        FNLabel lblUsername = new FNLabel(FNCPConstants.USER_USERNAME_LABEL);
        FNLabel lblPassword = new FNLabel(FNCPConstants.USER_PASSWORD_LABEL);

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

        mButtonLogin.addActionListener(e -> {
            mLoginPanel.setVisible(false);

            String username = mLoginUsername.getText();
            String password = mLoginPassword.getText();

            login(username, password);
        });
    }

    private void login(String username, String password) {
        JSONObject loginResponse = FNUserController.login(username, password);

        String status = loginResponse.getString(RESPONSE_STATUS_TAG);
        String message = loginResponse.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            //set username & password to session
            FNUtil.getInstance().setCurrentUsername(username);
            FNUtil.getInstance().setCurrentPassword(password);

            //find out if user has Admin Access
            if (FNUserController.hasAdminAccess(username)) {
                FNUtil.getInstance().setAdminAccess(true);
            } else {
                FNUtil.getInstance().setAdminAccess(false);
            }

            //open Control Panel
            FNControlPanel.getInstance();
        } else {
            // display failure
            JOptionPane.showMessageDialog(mLoginFrame, message);
            resetGui();
        }
    }

    private void resetGui() {
        mLoginPanel.setVisible(true);
        mLoginPassword.setText(null);
    }
}

