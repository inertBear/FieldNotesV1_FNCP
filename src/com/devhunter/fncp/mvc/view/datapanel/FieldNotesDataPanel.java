/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.devhunter.fncp.constants.FieldNotesConstants;
import com.devhunter.fncp.mvc.model.FNButton;
import com.devhunter.fncp.mvc.model.FNPanel;
import com.devhunter.fncp.mvc.view.FieldNotesControlPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.AddDataPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.DeleteDataPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.EditDataPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.SearchDataPanel;


public class FieldNotesDataPanel extends FNPanel {

	private static FieldNotesDataPanel sInstance;
	private static FNPanel sDataControlPanel;
	private FNButton mBtnDataSearch;
	private FNButton mBtnDataAdd;
	private FNButton mBtnDataDeleteSearch;
	private FNButton mBtnDataEditSearch;

	private FieldNotesDataPanel() {
		sDataControlPanel = new FNPanel();
		mBtnDataSearch = new FNButton(FieldNotesConstants.DATA_SEARCH_BUTTON);
		mBtnDataAdd = new FNButton(FieldNotesConstants.DATA_ADD_BUTTON);
		mBtnDataDeleteSearch = new FNButton(FieldNotesConstants.DATA_DELETE_BUTTON);
		mBtnDataEditSearch = new FNButton(FieldNotesConstants.DATA_EDIT_BUTTON);
		init();
		
		sDataControlPanel.setVisible(true);
	}

	public static FieldNotesDataPanel getInstance() {
		if (sInstance == null) {
			sInstance = new FieldNotesDataPanel();
		}
		return sInstance;
	}

	void init() {
		// Initialize Data Control Panel
		sDataControlPanel.add(mBtnDataSearch);
		sDataControlPanel.add(mBtnDataAdd);
		sDataControlPanel.add(mBtnDataDeleteSearch);
		sDataControlPanel.add(mBtnDataEditSearch);
		sDataControlPanel.setVisible(false);

		//FUTURE TODO: introduce lazy loading to reduce startup time
		SearchDataPanel.getInstance();
		AddDataPanel.getInstance();
		DeleteDataPanel.getInstance();
		EditDataPanel.getInstance();

		mBtnDataSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(SearchDataPanel.getView(), BorderLayout.CENTER);
				sDataControlPanel.setVisible(true);
				SearchDataPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		mBtnDataAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(AddDataPanel.getView(), BorderLayout.CENTER);
				sDataControlPanel.setVisible(true);
				AddDataPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		mBtnDataDeleteSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(DeleteDataPanel.getView(), BorderLayout.CENTER);
				sDataControlPanel.setVisible(true);
				DeleteDataPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});

		mBtnDataEditSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGui();
				FieldNotesControlPanel.getFieldNotesFrame().add(EditDataPanel.getView(), BorderLayout.CENTER);
				sDataControlPanel.setVisible(true);
				EditDataPanel.getView().setVisible(true);

				FieldNotesControlPanel.getFieldNotesFrame().repaint();
				FieldNotesControlPanel.getFieldNotesFrame().revalidate();
			}
		});

	}

	// clears visual aspects of the GUI
	private void resetGui() {
		sDataControlPanel.setVisible(false);

		SearchDataPanel.hideView();
		AddDataPanel.hideView();
		DeleteDataPanel.hideView();
		EditDataPanel.hideView();
	}

	public String formatTime(String time) {
		return time.substring(0, 5);
	}

	public static JPanel getView() {
		return sDataControlPanel;
	}

	public static void showView() {
		sDataControlPanel.setVisible(true);
	}

	public static void hideView() {
		sDataControlPanel.setVisible(false);
		sInstance.resetGui();
	}
}
