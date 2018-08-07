/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model;

import java.awt.Component;

import javax.swing.JButton;

import com.devhunter.fncp.utilities.FieldNotesUtil;

public class FNButton extends JButton {

	public FNButton(String buttonText) {
		setText(buttonText);
		setBorder(FieldNotesUtil.getInstance().getLineBorder());
		setPreferredSize(FieldNotesUtil.getInstance().getStandardButtonDimension());
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
	}
}
