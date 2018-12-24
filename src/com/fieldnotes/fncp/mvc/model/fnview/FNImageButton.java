/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.fnview;

import com.fieldnotes.fncp.utilities.FNUtil;

import javax.swing.*;
import java.io.FileNotFoundException;

public class FNImageButton extends JButton {

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