/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import com.devhunter.fncp.utilities.FNUtil;

public class FNTextField extends JTextField {
	
	float mCenterAlignment = Component.CENTER_ALIGNMENT;
	
	public FNTextField() {
		new JTextField();
		setPreferredSize(FNUtil.getInstance().getLargeTextFieldDimen());
		setBorder(BorderFactory.createLineBorder(FNUtil.getInstance().getPrimaryColor()));
		setAlignmentX(mCenterAlignment);
		setAlignmentY(mCenterAlignment);
	}
}
