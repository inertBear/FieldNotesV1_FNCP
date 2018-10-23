/**
 * ? 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.loginpanel;

import com.devhunter.fncp.FNInit;
import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.JsonParser;
import com.devhunter.fncp.mvc.model.fnview.*;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private JsonParser mJsonParser;

    private FNLogin() {
        mLoginFrame = FNInit.getFieldNotesJFrame();
        mLoginPanel = new FNPanel();
        mLoginCredentialPanel = new FNPanel();
        mLoginUsername = new FNTextField();
        mLoginPassword = new FNPasswordField();
        mJsonParser = new JsonParser();

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

        mButtonLogin.addActionListener(e -> {
            mLoginPanel.setVisible(false);

            // get login info
            String username = mLoginUsername.getText();
            String password = mLoginPassword.getText();
            String productKey = FNUtil.getInstance().getCurrentProductKey();

            JSONObject loginResponse = login(username, password, productKey);

            String status = loginResponse.getString("status");
            String message = loginResponse.getString("message");

            if (status.equals("success")) {
                //set username to session
                FNUtil.getInstance().setCurrentUsername(username);
                FNUtil.getInstance().setCurrentPassword(password);

                //find out if user has Admin Access
                if(hasAdminAccess(username)) {
                    FNUtil.getInstance().setAdminAccess(true);
                } else {
                    FNUtil.getInstance().setAdminAccess(false);
                }

                //open Control Panel
                FNControlPanel.getInstance();
            } else {
                // display failure
                JOptionPane.showMessageDialog(mLoginFrame, message);

                //reset UI
                mLoginPanel.setVisible(true);
                mLoginPassword.setText("");
            }
        });
    }


    public static JFrame getFieldNotesLoginFrame() {
        return mLoginFrame;
    }

    private JSONObject login(String username, String password, String productKey) {
        // set URL for web service
        final String postUrl = "http://www.fieldnotesfn.com/FNA_test/FNA_login.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(postUrl, "POST", params);
    }

    private boolean hasAdminAccess(String username) {
        //get productKey
        String productKey = FNUtil.getInstance().getCurrentProductKey();

        // set URL for web service
        final String searchUrl = "http://www.fieldnotesfn.com/FNA_test/FN_searchUser.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        JSONObject response = mJsonParser.createHttpRequest(searchUrl, "POST", params);

        //retrieve values from response
        String status = response.getString("status");
        String message = response.getString("message");

        if(status.equals("success")){
            FNUtil.getInstance().setCurrentUserType(message);
            return message.equals("admin");
        }
        return false;
    }
}

