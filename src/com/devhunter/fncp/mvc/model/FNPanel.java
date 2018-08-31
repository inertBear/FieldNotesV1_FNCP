/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model;

import javax.swing.JPanel;

import com.devhunter.fncp.utilities.FNUtil;

public class FNPanel extends JPanel { 
	
	public FNPanel() {
		new JPanel();
		setBorder(FNUtil.getInstance().getLineBorder());
	}
}
