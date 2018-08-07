/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.model;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.devhunter.fieldnotes.utilities.FieldNotesUtil;

public class FNLabel extends JLabel{
	
	public FNLabel() {
		setText("");
		setHorizontalAlignment(JLabel.CENTER);
		setPreferredSize(FieldNotesUtil.getInstance().getStandardLabelDimension());
	}
	
	public FNLabel(String labelText) {
		setText(labelText);
		setPreferredSize(FieldNotesUtil.getInstance().getStandardLabelDimension());
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(BorderFactory.createLineBorder(FieldNotesUtil.getInstance().getPrimaryColor()));
	}
}
