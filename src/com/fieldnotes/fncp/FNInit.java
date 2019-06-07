/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.mvc.view.loginpanel.FNLogin;

import javax.swing.*;

public class FNInit {

    private static JFrame FieldNotesFrame;

    public static void main(String[] args) {
        FieldNotesFrame = new JFrame(FNCPConstants.APPLICATION_NAME);

        FNLogin.getInstance();
    }

    public static JFrame getFieldNotesJFrame() {
        return FieldNotesFrame;
    }
}