/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.billingpanel;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.mvc.view.billingpanel.subpanels.BillingStateCreatedPanel;

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

        BillingStateCreatedPanel.getInstance();
        //BillingCurrentPanel.getInstance();
        //BillingCompletedPanel.getInstance();

        mBtnUnbilled.addActionListener(e -> {
            resetGui();
            FNControlPanel.getFieldNotesFrame().add(BillingStateCreatedPanel.getView(), BorderLayout.CENTER);
            sBillingControlPanel.setVisible(true);
            BillingStateCreatedPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnBilled.addActionListener(e -> {
            //TODO: implement Billed Tab

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnCompleted.addActionListener(e -> {
            //TODO: implement Complete Tab

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        sBillingControlPanel.setVisible(true);
    }

    // clears visual aspects of the GUI
    private void resetGui() {
        sBillingControlPanel.setVisible(false);

        BillingStateCreatedPanel.hideView();
        //BillingCurrentPanel.hideView();
        //BillingCompletePanel.hideView();
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
