/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.model;

import javax.swing.JPanel;

import com.devhunter.fieldnotes.utilities.FieldNotesUtil;

public class FNPanel extends JPanel { 
	
	public FNPanel() {
		new JPanel();
		setBorder(FieldNotesUtil.getInstance().getLineBorder());
	}
}
