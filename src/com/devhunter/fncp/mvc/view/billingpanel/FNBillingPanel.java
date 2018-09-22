/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.billingpanel;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.sql.billing.statemachine.BillingState;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.mvc.view.billingpanel.subpanels.BillingStatePanel;
import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;

public class FNBillingPanel extends FNPanel {

    private static FNBillingPanel sInstance;
    private static FNPanel sBillingControlPanel;
    private FNButton mBtnUnbilled;
    private FNButton mBtnBilled;
    private FNButton mBtnCompleted;

    private FNBillingPanel() {
        sBillingControlPanel = new FNPanel();
        mBtnUnbilled = new FNButton(FNConstants.UNBILLED_DATA);
        mBtnBilled = new FNButton(FNConstants.BILLED_DATA);
        mBtnCompleted = new FNButton(FNConstants.COMPLETED_DATA);
        init();
    }

    public static FNBillingPanel getInstance() {
        if (sInstance == null) {
            sInstance = new FNBillingPanel();
        }
        return sInstance;
    }

    private void init() {
        // Initialize Billing Panel
        sBillingControlPanel.add(mBtnUnbilled);
        sBillingControlPanel.add(mBtnBilled);
        sBillingControlPanel.add(mBtnCompleted);

        sBillingControlPanel.setVisible(false);

        // initialize javafx toolkit
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        BillingStatePanel.getInstance();

        mBtnUnbilled.addActionListener(e -> {
            resetGui();
            mBtnUnbilled.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(BillingStatePanel.getView(), BorderLayout.CENTER);
            BillingStatePanel.setBillingState(new BillingState(FNSqlConstants.BILLING_STATE_CREATED));

            sBillingControlPanel.setVisible(true);
            BillingStatePanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnBilled.addActionListener(e -> {
            resetGui();
            mBtnBilled.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(BillingStatePanel.getView(), BorderLayout.CENTER);
            BillingStatePanel.setBillingState(new BillingState(FNSqlConstants.BILLING_STATE_BILLED));

            sBillingControlPanel.setVisible(true);
            BillingStatePanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnCompleted.addActionListener(e -> {
            resetGui();
            mBtnCompleted.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(BillingStatePanel.getView(), BorderLayout.CENTER);
            BillingStatePanel.setBillingState(new BillingState(FNSqlConstants.BILLING_STATE_COMPLETE));

            sBillingControlPanel.setVisible(true);
            BillingStatePanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        sBillingControlPanel.setVisible(true);
    }

    // clears visual aspects of the GUI
    private void resetGui() {
        sBillingControlPanel.setVisible(false);

        BillingStatePanel.hideView();

        //reset button colors
        mBtnUnbilled.setBackground(null);
        mBtnBilled.setBackground(null);
        mBtnCompleted.setBackground(null);
    }

    public static JPanel getView() {
        return sBillingControlPanel;
    }

    public static void showView() {
        sBillingControlPanel.setVisible(true);
    }

    public static void hideView() {
        sBillingControlPanel.setVisible(false);
        sInstance.resetGui();
    }
}
