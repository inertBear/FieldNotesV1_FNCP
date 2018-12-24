/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.fnview;

import com.fieldnotes.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;

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