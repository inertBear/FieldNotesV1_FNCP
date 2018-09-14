package com.devhunter.fncp.mvc.model.fnview;

import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.devhunter.fncp.utilities.FNUtil;

public class FNImageButton extends JButton{
	
	public FNImageButton(ImageIcon icon) throws FileNotFoundException {
		setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
		setHorizontalAlignment(JButton.CENTER);
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(FNUtil.getInstance().getPrimaryColor()));
	}

}
