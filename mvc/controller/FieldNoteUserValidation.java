/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.controller;

import javax.swing.JOptionPane;

import com.devhunter.fieldnotes.mvc.model.FieldNoteUser;
import com.devhunter.fieldnotes.mvc.view.FieldNotesControlPanel;

/**
 * This class will be responsible for user function validation
 * 	0) login validation
 * 	2) no short or weak passwords (5-6 characters)
 * 	3) validate admin privledges
 */

public class FieldNoteUserValidation {
	
	public FieldNoteUserValidation() {
	}

	public static boolean validate(FieldNoteUser user) {
		String error = "";
		try {
			if(user.getUserName().equals("")) {
				error = "Please enter a UserName";
				throw new IllegalArgumentException();
			} 
			
			if(user.getPassword().equals("")) {
				error = "Please enter your password";
				throw new IllegalArgumentException();
			}
			if(user.getUserName().equals(user.getPassword())) {
				error = "UserName and Password cannot be the same";
				throw new IllegalArgumentException();
			}
			
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(), error);
			return false;
		}
		return true;
	}
}
