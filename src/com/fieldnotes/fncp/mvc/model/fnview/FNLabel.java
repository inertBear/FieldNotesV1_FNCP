/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.fnview;

import com.fieldnotes.fncp.utilities.FNUtil;

import javax.swing.*;

public class FNLabel extends JLabel {

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