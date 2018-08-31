package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.devhunter.fncp.mvc.model.FNPanel;
import com.devhunter.fncp.mvc.model.FNTextField;
import com.devhunter.fncp.utilities.FNUtil;


/**
 * This class will be called when trying to edit or delete a ticket in the Data Functions. Clicking the "Edit Data" or "Delete Data"
 * button from the FNDataPanel will navigate the user here as well as provde a reference to the function that will be done on
 * the data (do i really want this class to worry about what will be done with the data? NO.)so that it can be sent to the proper
 * view....
 * 
 * This architecture is going to require some sort of channel for the data to pass through.. 1) maybe a set of listeners?.. 
 * Since this may be a large architectural decision, the refactor of the this section has been delayed until a time when it will be
 * needed to expand FieldNotes - this will be at the discretion on the Project Owner
 * 
 *
 */
public class CrudSearchPanel extends FNPanel {

	// Panels
	private static CrudSearchPanel sInstance;
	private static FNPanel mCrudSearchPanel;
	// TextFields
	private FNTextField mCrudSearch;
	// Strings
	private String mFlexTicketNumber;
	
	private CrudSearchPanel() {
		// Create Panels
		mCrudSearchPanel = new FNPanel();
		// Create TextFields
		mCrudSearch = new FNTextField();
		// Create Strings
		mFlexTicketNumber = "";
		init();
	}
	
	//NEED A REFERENCE TO WHICHEVER PANEL IS CALLIN THIS - so we know where it's coming from
	public static CrudSearchPanel getInstance() {
		if (sInstance == null) {
			sInstance = new CrudSearchPanel();
		}
		return sInstance;
	}
	
	void init() {
		//TODO: REFACTOR
		mCrudSearchPanel.setBorder(new LineBorder(FNUtil.getInstance().getPrimaryColor(), 5, true));

		JLabel editDatalbl = new JLabel("Ticket Number:");
		editDatalbl.setPreferredSize(FNUtil.getInstance().getStandardLabelDimension());

		mCrudSearch.setPreferredSize(new Dimension(200, 24));

		JPanel searchForEditPanel = new JPanel();
		searchForEditPanel.setPreferredSize(new Dimension(400, 40));
		searchForEditPanel.setMaximumSize(searchForEditPanel.getPreferredSize());
		searchForEditPanel.add(editDatalbl);
		searchForEditPanel.add(mCrudSearch);

		JButton btnSearchForEdit = new JButton("Edit Ticket Search");

		mCrudSearchPanel.add(searchForEditPanel);
		mCrudSearchPanel.add(btnSearchForEdit);

		mCrudSearchPanel.setVisible(false);
	
	
//		// TODO: Move Controller Code
//		btnSearchForEdit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (mCrudSearch.getText().trim().isEmpty()) {
//					JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
//							"Please enter search information");
//				} else {
//					mFlexTicketNumber = mCrudSearch.getText();
//					ArrayList<String> editDataSearchResults = new ArrayList<String>();
//	
//					//show panel based on the reference
//					mEditFNMainPanel.setVisible(true);
//					mCrudSearch.setVisible(false);
//	
//					MySQLDataFunctions conn = new MySQLDataFunctions();
//					editDataSearchResults = conn.mySQLEditDataSearch(mFlexTicketNumber);
//	
//					if (editDataSearchResults.size() <= 1) {
//						JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
//								"No Data Found in Database");
//						resetGui();
//						FNControlPanel.getFieldNotesFrame().add(mEditDataSearchPanel, BorderLayout.CENTER);
//						FNDataPanel.getView().setVisible(true);
//						mCrudSearch.setVisible(true);
//	
//						FNControlPanel.getFieldNotesFrame().repaint();
//						FNControlPanel.getFieldNotesFrame().revalidate();
//					} else {
//						//clear search
//						resetGui();
//						//show EditMainPanel or DeleteMainPanel
//						FNControlPanel.getFieldNotesFrame().add(mEditFNMainPanel, BorderLayout.CENTER);
//						//show data panel
//						FNDataPanel.getView().setVisible(true);
//						// show eith the EditPanel or DeltePanel
//						mEditFNMainPanel.setVisible(true);
//						//pack it into a FieldNote and send it to the calling class
//						//using a listener?
//	
//	//					mTextEditDataName.setText(editDataSearchResults.get(0));
//	//					mTextEditDataWellName.setText(editDataSearchResults.get(1));
//	//					mEditTicketStartDatePicker.getJFormattedTextField().setText(editDataSearchResults.get(2));
//	//					mTextEditDataTimeStart.setText(editDataSearchResults.get(3));
//	//					mTextEditDataMileageStart.setText(editDataSearchResults.get(4));
//	//					mTextEditDataDescription.setText(editDataSearchResults.get(5));
//	//					mTextEditDataMileageEnd.setText(editDataSearchResults.get(6));
//	//					mEditTicketEndDatePicker.getJFormattedTextField().setText(editDataSearchResults.get(7));
//	//					mTextEditDataTimeEnd.setText(editDataSearchResults.get(8));
//	//					mTextEditDataProject.setText(editDataSearchResults.get(9));
//	//					mTextEditDataLocation.setText(editDataSearchResults.get(10));
//	//					mTextEditDataGPS.setText(editDataSearchResults.get(11));
//	//					mTextEditDataBillable.setText(editDataSearchResults.get(12));
//					}
//				}
//	
//				FNControlPanel.getFieldNotesFrame().repaint();
//				FNControlPanel.getFieldNotesFrame().revalidate();
//			}
//		});
	}
	
	public static JPanel getView() {
		return mCrudSearchPanel;
	}

	public static void showView() {
		mCrudSearchPanel.setVisible(true);
	}
	
	private void resetGui() {
		//mEditFNMainPanel.setVisible(false);
		mCrudSearchPanel.setVisible(false);
	}
}
