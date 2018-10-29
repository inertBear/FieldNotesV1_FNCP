package com.fieldnotes.fncp.mvc.model.fnview;

import com.fieldnotes.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;

public class FNPasswordField extends JPasswordField {

    public FNPasswordField() {
        new JPasswordField();
        setPreferredSize(FNUtil.getInstance().getLargeTextFieldDimen());
        setBorder(BorderFactory.createLineBorder(FNUtil.getInstance().getPrimaryColor()));

        float mCenterAlignment = Component.CENTER_ALIGNMENT;

        setAlignmentX(mCenterAlignment);
        setAlignmentY(mCenterAlignment);
    }
}
