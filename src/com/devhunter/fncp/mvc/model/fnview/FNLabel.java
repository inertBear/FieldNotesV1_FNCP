/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.fnview;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.devhunter.fncp.utilities.FNUtil;

public class FNLabel extends JLabel{
	
	public FNLabel() {
		setText("");
		setHorizontalAlignment(JLabel.CENTER);
		setPreferredSize(FNUtil.getInstance().getStandardLabelDimension());
	}
	
	public FNLabel(String labelText) {
		setText(labelText);
		setPreferredSize(FNUtil.getInstance().getStandardLabelDimension());
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(BorderFactory.createLineBorder(FNUtil.getInstance().getPrimaryColor()));
	}
}
