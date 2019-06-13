/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.view.billingpanel;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.constants.FNPConstants;
import com.fieldnotes.fncp.mvc.controller.billingStateMachine.BillingState;
import com.fieldnotes.fncp.mvc.model.fnview.FNButton;
import com.fieldnotes.fncp.mvc.model.fnview.FNPanel;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
import com.fieldnotes.fncp.mvc.view.billingpanel.subpanels.BillingStatePanel;
import com.fieldnotes.fncp.mvc.controller.FNSessionService;

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
        mBtnUnbilled = new FNButton(FNCPConstants.UNBILLED_DATA);
        mBtnBilled = new FNButton(FNCPConstants.BILLED_DATA);
        mBtnCompleted = new FNButton(FNCPConstants.COMPLETED_DATA);
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
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        BillingStatePanel.getInstance();

        mBtnUnbilled.addActionListener(e -> {
            resetGui();
            mBtnUnbilled.setBackground(FNSessionService.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(BillingStatePanel.getView(), BorderLayout.CENTER);
            BillingStatePanel.setBillingState(new BillingState(FNPConstants.BILLING_STATE_CREATED));

            sBillingControlPanel.setVisible(true);
            BillingStatePanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnBilled.addActionListener(e -> {
            resetGui();
            mBtnBilled.setBackground(FNSessionService.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(BillingStatePanel.getView(), BorderLayout.CENTER);
            BillingStatePanel.setBillingState(new BillingState(FNPConstants.BILLING_STATE_BILLED));

            sBillingControlPanel.setVisible(true);
            BillingStatePanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnCompleted.addActionListener(e -> {
            resetGui();
            mBtnCompleted.setBackground(FNSessionService.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(BillingStatePanel.getView(), BorderLayout.CENTER);
            BillingStatePanel.setBillingState(new BillingState(FNPConstants.BILLING_STATE_COMPLETE));

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