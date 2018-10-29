/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.fnview;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import com.fieldnotes.fncp.utilities.FNUtil;

public class FNTextField extends JTextField {

    public FNTextField() {
		new JTextField();
		setPreferredSize(FNUtil.getInstance().getLargeTextFieldDimen());
		setBorder(BorderFactory.createLineBorder(FNUtil.getInstance().getPrimaryColor()));

		float mCenterAlignment = Component.CENTER_ALIGNMENT;

		setAlignmentX(mCenterAlignment);
		setAlignmentY(mCenterAlignment);
	}
}
