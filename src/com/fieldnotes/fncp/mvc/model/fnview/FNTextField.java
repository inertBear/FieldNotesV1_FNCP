/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.fnview;

import com.fieldnotes.fncp.mvc.controller.services.FNSessionService;

import javax.swing.*;
import java.awt.*;

public class FNTextField extends JTextField {

    public FNTextField() {
        new JTextField();
        setPreferredSize(FNSessionService.getInstance().getLargeTextFieldDimen());
        setBorder(BorderFactory.createLineBorder(FNSessionService.getInstance().getPrimaryColor()));

        float mCenterAlignment = Component.CENTER_ALIGNMENT;

        setAlignmentX(mCenterAlignment);
        setAlignmentY(mCenterAlignment);
    }
}