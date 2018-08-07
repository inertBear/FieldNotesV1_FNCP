/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller;

import javax.swing.JOptionPane;

import com.devhunter.fncp.mvc.view.FieldNotesControlPanel;

public class CrudSearchValidation {
	
	public CrudSearchValidation() {
	}
	
	public static boolean validate(String ticketNumber) {
		String error = "";
		try {
			if (ticketNumber.equals("")) {
				error = "Please enter a Ticket Number";
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(), error);
			return false;
		}
		return true;
	}
}
