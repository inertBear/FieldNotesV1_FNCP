package com.devhunter.fncp.mvc.view.loginpanel;

import com.devhunter.fncp.FNInit;
import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.JsonParser;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.utilities.FNUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FNRegister extends FNPanel {

    // Frame
    private static JFrame mRegisterFrame;
    private FNPanel mRegisterPanel;
    private FNLabel mRegisterLabel;
    private FNTextField mProductKey;
    private FNButton mRegisterButton;

    private JsonParser mJsonParser = new JsonParser();

    // Panels
    private static FNRegister mInstance;

    private FNRegister() {
        // Create Frame
        mRegisterFrame = FNInit.getFieldNotesJFrame();
        // Create Panel
        mRegisterPanel = new FNPanel();
        // Label
        mRegisterLabel = new FNLabel("Enter your Product key");
        // Textfield
        mProductKey = new FNTextField();
        // Button
        mRegisterButton = new FNButton("Register");

        init();
    }

    public static FNRegister getInstance() {
        if (mInstance == null) {
            mInstance = new FNRegister();
        }
        return mInstance;
    }

    private void init() {
        mRegisterFrame.setSize(FNConstants.lOGIN_PANEL_X_AXIS,
                FNConstants.lOGIN_PANEL_Y_AXIS);
        mRegisterFrame.setLocationRelativeTo(null);
        mRegisterFrame.setVisible(true);
        mRegisterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoxLayout controlPanel_Layout = new BoxLayout(mRegisterPanel, BoxLayout.Y_AXIS);
        mRegisterPanel.setLayout(controlPanel_Layout);
        mRegisterPanel.setBorder(FNUtil.getInstance().getLineBorder());

        mRegisterPanel.add(mRegisterLabel);
        mRegisterPanel.add(Box.createVerticalGlue());
        mRegisterPanel.add(mProductKey);
        mRegisterPanel.add(Box.createVerticalGlue());
        mRegisterPanel.add(mRegisterButton);
        mRegisterPanel.add(Box.createVerticalGlue());

        mRegisterPanel.setVisible(true);

        mRegisterFrame.add(mRegisterPanel, BorderLayout.CENTER);
        mRegisterFrame.repaint();
        mRegisterFrame.revalidate();

        mRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // try to register
                // for test: 1159616266
                String productKey = mProductKey.getText();

                JSONObject response = register(productKey);

                String status = response.getString("status");
                String message = response.getString("message");

                if (status.equals("success")) {
                    //write product key to file
                    String path = System.getProperty("user.home") + File.separator + "Documents";

                    try {
                        File textFile = new File(path, "FNCP_product_key.txt");
                        BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
                        out.write(productKey);

                        out.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    // display success
                    JOptionPane.showMessageDialog(mRegisterFrame, message);

                    //close register panel
                    mRegisterFrame.dispatchEvent(new WindowEvent(mRegisterFrame, WindowEvent.WINDOW_CLOSING));
                } else {
                    // display failure
                    JOptionPane.showMessageDialog(mRegisterFrame, message);

                    //reset UI
                    mRegisterPanel.setVisible(true);
                }
            }
        });
    }

    JSONObject register(String productKey) {
        final String REGISTER_URL = "http://www.fieldnotesfn.com/FNA_test/FNA_register.php";

        // convert to List of params
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("customerKey", productKey));

        // make HTTP connection
        return mJsonParser.createHttpRequest(REGISTER_URL, "POST", params);
    }
}
