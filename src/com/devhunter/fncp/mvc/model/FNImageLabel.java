package com.devhunter.fncp.mvc.model;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.devhunter.fncp.utilities.FNUtil;

public class FNImageLabel extends JLabel{
	
	public FNImageLabel(ImageIcon icon) {
		setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
		setHorizontalAlignment(JLabel.CENTER);
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(FNUtil.getInstance().getPrimaryColor()));
	}

}
