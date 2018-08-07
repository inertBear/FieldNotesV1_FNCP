/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.model;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import com.devhunter.fieldnotes.utilities.FieldNotesUtil;

public class FNTextField extends JTextField {
	
	float mCenterAlignment = Component.CENTER_ALIGNMENT;
	
	public FNTextField() {
		new JTextField();
		setPreferredSize(FieldNotesUtil.getInstance().getLargeTextFieldDimen());
		setBorder(BorderFactory.createLineBorder(FieldNotesUtil.getInstance().getPrimaryColor()));
		setAlignmentX(mCenterAlignment);
		setAlignmentY(mCenterAlignment);
	}
}
