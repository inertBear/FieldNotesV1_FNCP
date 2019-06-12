/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.fnview;

import com.fieldnotes.fncp.mvc.controller.FNSessionService;

import javax.swing.*;

public class FNLabel extends JLabel {

    public FNLabel() {
        setText("");
        setHorizontalAlignment(JLabel.CENTER);
        setPreferredSize(FNSessionService.getInstance().getStandardLabelDimension());
    }

    public FNLabel(String labelText) {
        setText(labelText);
        setPreferredSize(FNSessionService.getInstance().getStandardLabelDimension());
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(BorderFactory.createLineBorder(FNSessionService.getInstance().getPrimaryColor()));
    }
}