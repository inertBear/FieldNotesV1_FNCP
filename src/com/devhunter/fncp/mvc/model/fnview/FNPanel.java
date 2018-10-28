/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.fnview;

import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;

public class FNPanel extends JPanel {

    public FNPanel() {
        new JPanel();
        setBorder(FNUtil.getInstance().getLineBorder());
    }
}
