/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes;

import javax.swing.JFrame;

import com.devhunter.fieldnotes.constants.FieldNotesConstants;
import com.devhunter.fieldnotes.mvc.view.loginpanel.FieldNotesLogin;

public class FieldNotesInit {

	private static JFrame FieldNotesFrame;

	// starting point
	public static void main(String[] args) {
		FieldNotesFrame = new JFrame(FieldNotesConstants.APPLICATION_NAME);
		FieldNotesLogin.getInstance();
	}

	public static JFrame getFieldNotesJFrame() {
		return FieldNotesFrame;
	}
}