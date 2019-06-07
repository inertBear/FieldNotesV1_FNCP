/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.fnview;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.fieldnotes.fncp.mvc.controller.services.FNSessionService;

import java.awt.*;

public class FNImageLabel extends JLabel {

    public FNImageLabel(ImageIcon icon) {
        setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setHorizontalAlignment(JLabel.CENTER);
        setIcon(icon);
        setIconTextGap(0);
        setBorder(null);
        setText(null);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(FNSessionService.getInstance().getPrimaryColor()));
    }
}