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
import com.fieldnotes.fncp.mvc.model.fnview.FNButton;
import com.fieldnotes.fncp.mvc.model.fnview.FNLabel;
import com.fieldnotes.fncp.mvc.model.fnview.FNPanel;
import com.fieldnotes.fncp.mvc.model.fnview.FNTextField;
import com.fieldnotes.fncp.utilities.FNUtil;
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

import static com.fieldnotes.fncp.constants.FNCPConstants.*;
import static com.fieldnotes.fncp.constants.FNPConstants.*;

public class FNRegister extends FNPanel {

    private static JFrame mRegisterFrame;
    private FNPanel mRegisterPanel;
    private FNLabel mRegisterLabel;
    private FNTextField mProductKey;
    private FNButton mRegisterButton;
    private static FNRegister mInstance;

    private FNRegister() {
        mRegisterFrame = FNInit.getFieldNotesJFrame();
        mRegisterPanel = new FNPanel();
        mRegisterLabel = new FNLabel(REGISTRATION_LABEL);
        mProductKey = new FNTextField();
        mRegisterButton = new FNButton(BUTTON_REGISTER);

        init();
    }

    public static FNRegister getInstance() {
        if (mInstance == null) {
            mInstance = new FNRegister();
        }
        return mInstance;
    }

    private void init() {
        mRegisterFrame.setSize(FNCPConstants.lOGIN_PANEL_X_AXIS,
                FNCPConstants.lOGIN_PANEL_Y_AXIS);
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
                String productKey = mProductKey.getText();
                register(productKey);
            }
        });
    }

    private void register(String productKey) {
        // save key
        FNUtil.getInstance().setCurrentProductKey(productKey);
        // try to register
        JSONObject response = FNUserController.register(productKey);

        String status = response.getString(RESPONSE_STATUS_TAG);
        String message = response.getString(RESPONSE_MESSAGE_TAG);

        if (status.equals(RESPONSE_STATUS_SUCCESS)) {
            //write product key to file
            try {
                File textFile = new File(REGISTRATION_FILE_PATH);
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
            // clear the key
            FNUtil.getInstance().setCurrentProductKey("");

            JOptionPane.showMessageDialog(mRegisterFrame, message);
            resetGui();
        }
    }

    private void resetGui() {
        mProductKey.setText(null);
        mRegisterPanel.setVisible(true);
    }
}
