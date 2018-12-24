/**
 * © 2017-2018 FieldNotes All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.controller.validation;

import com.fieldnotes.fncp.mvc.model.FNUser;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;

import javax.swing.*;

/**
 * This class will be responsible for user function validation
 * 0) login validation
 * 2) no short or weak passwords (5-6 characters)
 * 3) validate admin privledges
 */

public class FNUserValidation {

    public FNUserValidation() {
    }

    public static boolean validate(FNUser user) {
        String error = "";
        try {
            if (user.getUsername().equals("")) {
                error = "Please enter a UserName";
                throw new IllegalArgumentException();
            }

            if (user.getPassword().equals("")) {
                error = "Please enter your password";
                throw new IllegalArgumentException();
            }
            if (user.getUsername().equals(user.getPassword())) {
                error = "UserName and Password cannot be the same";
                throw new IllegalArgumentException();
            }
            if (user.getType().isEmpty()) {
                error = "User Type not defined";
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), error);
            return false;
        }
        return true;
    }
}