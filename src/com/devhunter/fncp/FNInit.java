/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.view.loginpanel.FNLogin;
import com.devhunter.fncp.mvc.view.loginpanel.FNRegister;
import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FNInit {

    private static JFrame FieldNotesFrame;

    public static void main(String[] args) {
        FieldNotesFrame = new JFrame(FNConstants.APPLICATION_NAME);

        String productKey = null;

        //get product key from saved file
        String path = System.getProperty("user.home") + File.separator + "Documents";
        File textFile = new File(path, "FNCP_product_key.txt");

        FileReader fileReader;
        try {
            fileReader = new FileReader(textFile);
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