/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp;

import com.devhunter.fncp.constants.FNCPConstants;
import com.devhunter.fncp.mvc.view.loginpanel.FNLogin;
import com.devhunter.fncp.mvc.view.loginpanel.FNRegister;
import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.devhunter.fncp.constants.FNCPConstants.REGISTRATION_FILE_PATH;

public class FNInit {

    private static JFrame FieldNotesFrame;

    public static void main(String[] args) {
        FieldNotesFrame = new JFrame(FNCPConstants.APPLICATION_NAME);

        String productKey = null;
        FileReader fileReader;
        try {
            fileReader = new FileReader(REGISTRATION_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            productKey = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("No key located: Registering");
        }

        if (productKey != null) {
            // save product key to FNUtils
            FNUtil.getInstance().setCurrentProductKey(productKey);
            // open login dialog
            FNLogin.getInstance();
        } else {
            // open register dialog
            FNRegister.getInstance();
        }
    }

    public static JFrame getFieldNotesJFrame() {
        return FieldNotesFrame;
    }
}