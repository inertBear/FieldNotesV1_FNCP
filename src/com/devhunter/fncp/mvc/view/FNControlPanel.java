/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view;

import com.devhunter.fncp.FNInit;
import com.devhunter.fncp.constants.FNCPConstants;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNImageLabel;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.view.billingpanel.FNBillingPanel;
import com.devhunter.fncp.mvc.view.datapanel.FNDataPanel;
import com.devhunter.fncp.mvc.view.userpanel.FNUserPanel;
import com.devhunter.fncp.utilities.FNUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class FNControlPanel extends FNPanel {

    // added the default serialVersionUID (because LINT)
    public static final long serialVersionUID = 1L;

    private static JFrame mMainControlFrame;
    private FNPanel mControlPanel;
    private FNButton mControlPanelUserButton;
    private FNButton mControlPanelDataButton;
    private FNButton mControlPanelBillingButton;
    private FNImageLabel mControlPanelImageLabel;

    private FNControlPanel() {
        mMainControlFrame = FNInit.getFieldNotesJFrame();
        mControlPanel = new FNPanel();
        mControlPanelUserButton = new FNButton(FNCPConstants.USER_CONTROLS_BUTTON);
        mControlPanelDataButton = new FNButton(FNCPConstants.DATA_CONTROLS_BUTTON);
        mControlPanelBillingButton = new FNButton(FNCPConstants.BILLING_CONTROLS_BUTTON);
        init();
    }

    /**
     * This method is called when the creating a new Instance of the Control Panel.
     * It will in turn create a singleton instance of the panel.
     */
    public static FNControlPanel getInstance() {
        return new FNControlPanel();
    }

    private void init() {
        mMainControlFrame.setSize(FNCPConstants.MAIN_CONTROL_PANEL_X_AXIS,
                FNCPConstants.MAIN_CONTROL_PANEL_Y_AXIS);
        mMainControlFrame.setLocationRelativeTo(null);
        mMainControlFrame.setVisible(true);
        mMainControlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoxLayout controlPanel_Layout = new BoxLayout(mControlPanel, BoxLayout.Y_AXIS);
        mControlPanel.setLayout(controlPanel_Layout);
        mControlPanel.setBorder(FNUtil.getInstance().getLineBorder());

        // ImageLabels
        try {
            Image img = ImageIO.read(getClass().getResource(FNCPConstants.APPLICATION_ICON_FOLDER));
            mControlPanelImageLabel = new FNImageLabel(new ImageIcon(img));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mMainControlFrame, "title image failed to load");
        }
        mControlPanelImageLabel.setBorder(null);
        FNLabel controlPanelLabel2 = new FNLabel(FNCPConstants.APPLICATION_SUB_NAME);
        controlPanelLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanelLabel2.setAlignmentY(Component.CENTER_ALIGNMENT);

        mControlPanel.add(mControlPanelImageLabel);
        mControlPanel.add(Box.createVerticalGlue());
        mControlPanel.add(mControlPanelUserButton);
        mControlPanel.add(Box.createVerticalGlue());
        mControlPanel.add(mControlPanelDataButton);
        mControlPanel.add(Box.createVerticalGlue());
        // ADMIN ACCESS
        if (FNUtil.getInstance().hasAdminAccess()) {
            mControlPanel.add(mControlPanelBillingButton);
            mControlPanel.add(Box.createVerticalGlue());
        }

        mControlPanel.setVisible(true);

        mMainControlFrame.add(mControlPanel, BorderLayout.WEST);
        mMainControlFrame.repaint();
        mMainControlFrame.revalidate();

        FNUserPanel.getInstance();
        FNDataPanel.getInstance();
        FNBillingPanel.getInstance();

        // when user navigates to the USER MENU
        mControlPanelUserButton.addActionListener(e -> {
            resetGui();
            FNUserPanel.showView();

            mControlPanelUserButton.setBackground(FNUtil.getInstance().getPrimaryColor());

            mMainControlFrame.add(FNUserPanel.getView(), BorderLayout.NORTH);
            mMainControlFrame.repaint();
            mMainControlFrame.revalidate();
        });

        // when user navigates to DATA MENU
        mControlPanelDataButton.addActionListener(e -> {
            resetGui();
            FNDataPanel.showView();

            mControlPanelDataButton.setBackground(FNUtil.getInstance().getPrimaryColor());

            mMainControlFrame.add(FNDataPanel.getView(), BorderLayout.NORTH);
            mMainControlFrame.repaint();
            mMainControlFrame.revalidate();
        });

        //when the admin nagivates to the BILLING MENU
        mControlPanelBillingButton.addActionListener(e -> {
            resetGui();
            FNBillingPanel.showView();

            mControlPanelBillingButton.setBackground(FNUtil.getInstance().getPrimaryColor());

            mMainControlFrame.add(FNBillingPanel.getView(), BorderLayout.NORTH);
            mMainControlFrame.repaint();
            mMainControlFrame.revalidate();
        });
    }

    private void resetGui() {
        FNUserPanel.hideView();
        FNDataPanel.hideView();
        FNBillingPanel.hideView();

        //reset button colors
        mControlPanelUserButton.setBackground(null);
        mControlPanelDataButton.setBackground(null);
        mControlPanelBillingButton.setBackground(null);
    }

    public static JFrame getFieldNotesFrame() {
        return mMainControlFrame;
    }
}
