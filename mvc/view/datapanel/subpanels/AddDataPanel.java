/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.view.datapanel.subpanels;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.devhunter.fieldnotes.constants.FieldNotesConstants;
import com.devhunter.fieldnotes.mvc.controller.FieldNoteValidation;
import com.devhunter.fieldnotes.mvc.controller.sql.SQLDataController;
import com.devhunter.fieldnotes.mvc.model.FNButton;
import com.devhunter.fieldnotes.mvc.model.FNLabel;
import com.devhunter.fieldnotes.mvc.model.FNPanel;
import com.devhunter.fieldnotes.mvc.model.FNTextField;
import com.devhunter.fieldnotes.mvc.model.FieldNote;
import com.devhunter.fieldnotes.mvc.model.FieldNote.FieldNoteBuilder;
import com.devhunter.fieldnotes.mvc.model.dateutils.DateLabelFormatter;
import com.devhunter.fieldnotes.mvc.view.FieldNotesControlPanel;
import com.devhunter.fieldnotes.utilities.FieldNotesUtil;

import lu.tudor.santec.jtimechooser.JTimeChooser;

public class AddDataPanel extends FNPanel {

	private static final long serialVersionUID = 1L;
	// Panels
	public static AddDataPanel sInstance;
	public static FNPanel mAddFNMainPanel;
	public FNPanel mAddFNButtonPanel;
	// TextFields
	private FNTextField mTextNewDataName;
	private FNTextField mTextNewDataWellName;
	private FNTextField mTextNewDataMileageStart;
	private FNTextField mTextNewDataDescription;
	private FNTextField mTextNewDataMileageEnd;
	private FNTextField mTextNewDataDateEnd;
	private FNTextField mTextNewDataTimeEnd;
	private FNTextField mTextNewDataProject;
	private FNTextField mTextNewDataGPS;
	// Spinners
	private JSpinner mSpinnerNewDataLocation;
	private JSpinner mSpinnerNewDataBillable;
	// TimeChoosers
	private final JTimeChooser mNewTicketTimeStart;
	private final JTimeChooser mNewTicketTimeEnd;
	// DatePickers
	private UtilDateModel mNewTicketStartModel;
	private Properties mNewTicketStartProperties;
	private JDatePanelImpl mNewTicketStartDatePanel;
	private JDatePickerImpl mNewTicketStartDatePicker;
	private UtilDateModel mNewTicketEndModel;
	private Properties mNewTicketEndProperties;
	private JDatePanelImpl mNewTicketEndDatePanel;
	private JDatePickerImpl mNewTicketEndDatePicker;
	// Buttons
	private FNButton buttonSubmit;

	private AddDataPanel() {
		// create Panels
		mAddFNMainPanel = new FNPanel();
		mAddFNButtonPanel = new FNPanel();
		// create TextFields
		mTextNewDataName = new FNTextField();
		mTextNewDataWellName = new FNTextField();
		mTextNewDataMileageStart = new FNTextField();
		mTextNewDataDescription = new FNTextField();
		mTextNewDataMileageEnd = new FNTextField();
		mTextNewDataDateEnd = new FNTextField();
		mTextNewDataTimeEnd = new FNTextField();
		mTextNewDataProject = new FNTextField();
		mTextNewDataGPS = new FNTextField();
		// create Spinners
		mSpinnerNewDataLocation = new JSpinner(FieldNotesUtil.getInstance().getLocations());
		mSpinnerNewDataBillable = new JSpinner(FieldNotesUtil.getInstance().getBillable());
		// create TimeChoosers
		mNewTicketTimeStart = new JTimeChooser(new Date());
		mNewTicketTimeEnd = new JTimeChooser(new Date());
		// create DatePickers
		// FIXME: need to resize the JDatePicker's TextFields
		mNewTicketStartModel = new UtilDateModel();
		mNewTicketStartProperties = new Properties();
		mNewTicketStartDatePanel = new JDatePanelImpl(mNewTicketStartModel, mNewTicketStartProperties);
		mNewTicketStartDatePanel.setSize(FieldNotesUtil.getInstance().getLargeTextFieldDimen());
		mNewTicketStartDatePicker = new JDatePickerImpl(mNewTicketStartDatePanel, new DateLabelFormatter());
		mNewTicketEndModel = new UtilDateModel();
		mNewTicketEndProperties = new Properties();
		mNewTicketEndDatePanel = new JDatePanelImpl(mNewTicketEndModel, mNewTicketEndProperties);
		mNewTicketEndDatePicker = new JDatePickerImpl(mNewTicketEndDatePanel, new DateLabelFormatter());
		// create Buttons
		buttonSubmit = new FNButton(FieldNotesConstants.BUTTON_SUBMIT);
		init();
	}

	public static AddDataPanel getInstance() {
		if (sInstance == null) {
			sInstance = new AddDataPanel();
		}
		return sInstance;
	}

	void init() {
		// Panels/Layouts
		GridLayout addFNDataPanelLayout = new GridLayout(0,2);
		mAddFNMainPanel.setLayout(addFNDataPanelLayout);
		FlowLayout addFNButtonPanelLayout = new FlowLayout();
		mAddFNButtonPanel.setLayout(addFNButtonPanelLayout);
		mAddFNButtonPanel.setBorder(FieldNotesUtil.getInstance().getLineBorder());
		// Labels
		FNLabel lblNewDataName = new FNLabel(FieldNotesConstants.FN_USERNAME_LABEL);
		FNLabel lblNewDataWellName = new FNLabel(FieldNotesConstants.FN_WELLNAME_LABEL);
		FNLabel lblNewDataDateStart = new FNLabel(FieldNotesConstants.FN_DATE_START_LABEL);
		FNLabel lblNewDataTimeStart = new FNLabel(FieldNotesConstants.FN_TIME_START_LABEL);
		FNLabel lblNewDataMileageStart = new FNLabel(FieldNotesConstants.FN_MILEAGE_START_LABEL);
		FNLabel lblNewDataDescription = new FNLabel(FieldNotesConstants.FN_DESCRIPTION_LABEL);
		FNLabel lblNewDataMileageEnd = new FNLabel(FieldNotesConstants.FN_MILEAGE_END_LABEL);
		FNLabel lblNewDataDateEnd = new FNLabel(FieldNotesConstants.FN_DATE_END_LABEL);
		FNLabel lblNewDataTimeEnd = new FNLabel(FieldNotesConstants.FN_TIME_END_LABEL);
		FNLabel lblNewDataProject = new FNLabel(FieldNotesConstants.FN_PROJECT_LABEL);
		FNLabel lblNewDataLocation = new FNLabel(FieldNotesConstants.FN_LOCATION_LABEL);
		FNLabel lblNewDataGPS = new FNLabel(FieldNotesConstants.FN_GPS_LABEL);
		FNLabel lblNewDataBillable = new FNLabel(FieldNotesConstants.FN_BILLING_LABEL);
		// Override TextField properties
		mTextNewDataName.setEditable(false);
		mTextNewDataName.setText(FieldNotesUtil.getInstance().getCurrentUser());
		// Customize Spinner properties
		mSpinnerNewDataLocation.setPreferredSize(FieldNotesUtil.getInstance().getLargeTextFieldDimen());
		mSpinnerNewDataLocation.setEditor(new JSpinner.DefaultEditor(mSpinnerNewDataLocation));
		mSpinnerNewDataBillable.setPreferredSize(FieldNotesUtil.getInstance().getLargeTextFieldDimen());
		mSpinnerNewDataBillable.setEditor(new JSpinner.DefaultEditor(mSpinnerNewDataBillable));
		// Set DatePicker properties
		mNewTicketStartProperties.put("text.today", "Today");
		mNewTicketStartProperties.put("text.month", "Month");
		mNewTicketStartProperties.put("text.year", "Year");
		mNewTicketEndProperties.put("text.today", "Today");
		mNewTicketEndProperties.put("text.month", "Month");
		mNewTicketEndProperties.put("text.year", "Year");

		// Add Views to Panel
		mAddFNMainPanel.add(lblNewDataName);
		mAddFNMainPanel.add(mTextNewDataName);
		mAddFNMainPanel.add(lblNewDataWellName);
		mAddFNMainPanel.add(mTextNewDataWellName);
		mAddFNMainPanel.add(lblNewDataDateStart);
		mAddFNMainPanel.add(mNewTicketStartDatePicker);
		mAddFNMainPanel.add(lblNewDataTimeStart);
		mAddFNMainPanel.add(mNewTicketTimeStart);
		mAddFNMainPanel.add(lblNewDataMileageStart);
		mAddFNMainPanel.add(mTextNewDataMileageStart);
		mAddFNMainPanel.add(lblNewDataDescription);
		mAddFNMainPanel.add(mTextNewDataDescription);
		mAddFNMainPanel.add(lblNewDataMileageEnd);
		mAddFNMainPanel.add(mTextNewDataMileageEnd);
		mAddFNMainPanel.add(lblNewDataDateEnd);
		mAddFNMainPanel.add(mNewTicketEndDatePicker);
		mAddFNMainPanel.add(lblNewDataTimeEnd);
		mAddFNMainPanel.add(mNewTicketTimeEnd);
		mAddFNMainPanel.add(lblNewDataProject);
		mAddFNMainPanel.add(mTextNewDataProject);
		mAddFNMainPanel.add(lblNewDataLocation);
		mAddFNMainPanel.add(mSpinnerNewDataLocation);
		mAddFNMainPanel.add(lblNewDataGPS);
		mAddFNMainPanel.add(mTextNewDataGPS);
		mAddFNMainPanel.add(lblNewDataBillable);
		mAddFNMainPanel.add(mSpinnerNewDataBillable);
		mAddFNMainPanel.add(new FNLabel());
		mAddFNMainPanel.add(buttonSubmit);
		// Initial view settings
		mAddFNMainPanel.setVisible(false);
		FieldNotesControlPanel.getFieldNotesFrame().repaint();
		FieldNotesControlPanel.getFieldNotesFrame().revalidate();
		
		buttonSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// build a FieldNote from the data entered by user
				FieldNote fieldNote = new FieldNoteBuilder()
						.setUserName(mTextNewDataName.getText())
						.setWellName(mTextNewDataWellName.getText())
						.setTimeStart(formatTime(mNewTicketTimeStart.getFormatedTime()))
						.setTimeEnd(formatTime(mNewTicketTimeEnd.getFormatedTime()))
						.setDateStart(mNewTicketStartDatePicker.getJFormattedTextField().getText())
						.setDateEnd(mNewTicketEndDatePicker.getJFormattedTextField().getText())
						.setMileageStart(mTextNewDataMileageStart.getText())
						.setMileageEnd(mTextNewDataMileageEnd.getText())
						.setDescription(mTextNewDataDescription.getText())
						.setWellName(mTextNewDataWellName.getText())
						.setProjectNumber(mTextNewDataProject.getText())
						.setLocation((String) mSpinnerNewDataLocation.getValue())
						.setGPSCoords(mTextNewDataGPS.getText())
						.setBillingType((String) mSpinnerNewDataBillable.getValue())
						.createFieldNote();
				// validate FieldNote
				if(FieldNoteValidation.validate(fieldNote)) {
					// send FieldNote to controller for CUD event
					SQLDataController conn = new SQLDataController();
					int newDataResultCode = conn.addFieldNote(fieldNote);

					// code 1 == success, code 0 == failure
					if (newDataResultCode == 1) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(), 
								"New Note Submitted");
						resetGui();
					} else {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(), 
								"Connection Error: NEW NOTE WAS NOT SUBMITTED");
					}
				} else {
					//Validation failed - Do nothing and allow then to make changes and send again
				}
			}
		});
	}

	public String formatTime(String time) {
		return time.substring(0, 5);
	}

	public static JPanel getView() {
		return mAddFNMainPanel;
	}

	public static void showView() {
		mAddFNMainPanel.setVisible(true);
	}

	public static void hideView() {
		mAddFNMainPanel.setVisible(false);
		sInstance.resetGui();
	}

	private void resetGui() {
		// remove editable values in GUI
		mTextNewDataWellName.setText(null);
		mNewTicketTimeStart.setTime(FieldNotesUtil.getInstance().getZeroHour());
		mNewTicketTimeEnd.setTime(FieldNotesUtil.getInstance().getZeroHour());
		mTextNewDataMileageStart.setText(null);
		mTextNewDataDescription.setText(null);
		mTextNewDataMileageEnd.setText(null);
		mTextNewDataDateEnd.setText(null);
		mTextNewDataTimeEnd.setText(null);
		mTextNewDataProject.setText(null);
		mTextNewDataGPS.setText(null);
		mSpinnerNewDataBillable.setValue("");
		mSpinnerNewDataLocation.setValue("");

		// reset the TimePickers back to current time
		LocalDate now = LocalDate.now();
		mNewTicketStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		mNewTicketStartDatePicker.getJFormattedTextField().setText("");
		mNewTicketEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		mNewTicketEndDatePicker.getJFormattedTextField().setText("");
	}
}
