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

public class FNPasswordField extends JPasswordField {

    public FNPasswordField() {
        new JPasswordField();
        setPreferredSize(FNSessionService.getInstance().getLargeTextFieldDimen());
        setBorder(BorderFactory.createLineBorder(FNSessionService.getInstance().getPrimaryColor()));

        float mCenterAlignment = Component.CENTER_ALIGNMENT;

        setAlignmentX(mCenterAlignment);
        setAlignmentY(mCenterAlignment);
    }
}