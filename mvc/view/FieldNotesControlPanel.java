/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.devhunter.fieldnotes.FieldNotesInit;
import com.devhunter.fieldnotes.constants.FieldNotesConstants;
import com.devhunter.fieldnotes.mvc.model.FNButton;
import com.devhunter.fieldnotes.mvc.model.FNImageLabel;
import com.devhunter.fieldnotes.mvc.model.FNLabel;
import com.devhunter.fieldnotes.mvc.model.FNPanel;
import com.devhunter.fieldnotes.mvc.view.datapanel.FieldNotesDataPanel;
import com.devhunter.fieldnotes.mvc.view.userpanel.FieldNotesUserPanel;
import com.devhunter.fieldnotes.utilities.FieldNotesUtil;

public class FieldNotesControlPanel extends FNPanel {

	// added the default serialVersionUID (because LINT)
	public static final long serialVersionUID = 1L;

	private static FieldNotesControlPanel mFieldNotesControlPanel;

	private static JFrame mMainControlFrame;
	private FNPanel mControlPanel;
	private FNButton mControlPanelUserButton;
	private FNButton mControlPanelDataButton;
	private FNImageLabel mControlPanelImageLabel;

	private FieldNotesControlPanel() {
		mMainControlFrame = FieldNotesInit.getFieldNotesJFrame();
		mControlPanel = new FNPanel();
		mControlPanelUserButton = new FNButton(FieldNotesConstants.USER_CONTROLS_BUTTON);
		mControlPanelDataButton = new FNButton(FieldNotesConstants.DATA_CONTROLS_BUTTON);
		init();
	}

	/**
	 * This method is called when the creating a new Instance of the Control Panel.
	 * It will in turn create a singleton instance of the panel.
	 */

	public static FieldNotesControlPanel getInstance() {
		mFieldNotesControlPanel = new FieldNotesControlPanel();
		return mFieldNotesControlPanel;
	}

	void init() {
		mMainControlFrame.setSize(FieldNotesConstants.MAIN_CONTROL_PANEL_X_AXIS,
				FieldNotesConstants.MAIN_CONTROL_PANEL_Y_AXIS);
		mMainControlFrame.setLocationRelativeTo(null);
		mMainControlFrame.setVisible(true);
		mMainControlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BoxLayout controlPanel_Layout = new BoxLayout(mControlPanel, BoxLayout.Y_AXIS);
		mControlPanel.setLayout(controlPanel_Layout);
		mControlPanel.setBorder(FieldNotesUtil.getInstance().getLineBorder());

		// ImageLabels
		try {
			Image img = ImageIO.read(getClass().getResource(FieldNotesConstants.APPLICATION_ICON_FOLDER));
			mControlPanelImageLabel = new FNImageLabel(new ImageIcon(img));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(mMainControlFrame, "title image failed to load");
		}
		mControlPanelImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mControlPanelImageLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		mControlPanelImageLabel.setBorder(null);
		FNLabel controlPanelLabel2 = new FNLabel(FieldNotesConstants.APPLICATION_SUB_NAME);
		controlPanelLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		controlPanelLabel2.setAlignmentY(Component.CENTER_ALIGNMENT);
		mControlPanelUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mControlPanelUserButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		mControlPanelDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mControlPanelDataButton.setAlignmentY(Component.CENTER_ALIGNMENT);

		// TODO: export to EXCEL should only be visible after running a search

		mControlPanel.add(mControlPanelImageLabel);
		mControlPanel.add(Box.createVerticalGlue());
		mControlPanel.add(mControlPanelUserButton);
		mControlPanel.add(Box.createVerticalGlue());
		mControlPanel.add(mControlPanelDataButton);
		mControlPanel.add(Box.createVerticalGlue());
		mControlPanel.setVisible(true);
		
		mMainControlFrame.add(mControlPanel, BorderLayout.WEST);
		mMainControlFrame.repaint();
		mMainControlFrame.revalidate();
		
		// FUTURE TODO: apply lazy loading to these panels to reduce startup time
		FieldNotesUserPanel.getInstance();
		FieldNotesDataPanel.getInstance();

		// when user navigates to the USER MENU
		mControlPanelUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FieldNotesDataPanel.hideView();
				FieldNotesUserPanel.showView();

				mMainControlFrame.add(FieldNotesUserPanel.getView(), BorderLayout.NORTH);
				mMainControlFrame.repaint();
				mMainControlFrame.revalidate();
			}
		});

		// when user navigates to DATA MENU
		mControlPanelDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FieldNotesUserPanel.hideView();
				FieldNotesDataPanel.showView();

				mMainControlFrame.add(FieldNotesDataPanel.getView(), BorderLayout.NORTH);
				mMainControlFrame.repaint();
				mMainControlFrame.revalidate();
			}
		});
	}

	public static JFrame getFieldNotesFrame() {
		return mMainControlFrame;
	}
}
