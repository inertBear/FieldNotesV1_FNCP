/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.devhunter.fncp.constants.FieldNotesConstants;
import com.devhunter.fncp.mvc.controller.exporter.ExportController;
import com.devhunter.fncp.mvc.controller.sql.SQLDataController;
import com.devhunter.fncp.mvc.model.FNButton;
import com.devhunter.fncp.mvc.model.FNLabel;
import com.devhunter.fncp.mvc.model.FNPanel;
import com.devhunter.fncp.mvc.model.FNTextField;
import com.devhunter.fncp.mvc.model.dateutils.DateLabelFormatter;
import com.devhunter.fncp.mvc.view.FieldNotesControlPanel;

/**
 * Determines how the user will search for data and parses data into a JTextField
 * 
 * *****Personally I don't like this architecture. As soon as I come up with a better way to do this, I will implement it.
 * IDEAS:
 * 	investigate the Java version of a (Android) ListView. Produce a list similar to the mobile version.
 * 	Need to get rid of the JTextArea at the very least. It is really sloppy and ugly to look at.
 *
 * TODO: SQL search methods now return arraylist<FieldNote> implement the search result display (like i did for the users)
 */

public class SearchDataPanel extends FNPanel {

	// Panels
	public static SearchDataPanel sInstance;
	private static FNPanel mSearchDataPanel;
	private static FNPanel mSearchTextFieldPanel;
	// TextFields
	private FNTextField mTextDataUsername;
	// TextArea - search results
	private JTextArea mSearchDataOutput;
	// DatePicker
	private UtilDateModel mSearchStartModel;
	private Properties mSearchStartProperties;
	private JDatePanelImpl mDatePanelSearchStart;
	private JDatePickerImpl mDatePickerSearchStart;
	private UtilDateModel mSearchEndModel;
	private Properties mSearchEndProperties;
	private JDatePanelImpl mDatePanelSearchEnd;
	private JDatePickerImpl mDatePickerSearchEnd;
	// Buttons
	private FNButton mButtonSearch;
	private FNButton mButtonExport;
	// ArrayLists
	private ArrayList<String> mDataSearchResults;

	private SearchDataPanel() {
		// create Panels
		mSearchDataPanel = new FNPanel();
		mSearchTextFieldPanel = new FNPanel();
		// create TextFields
		mTextDataUsername = new FNTextField();
		// create TextAreas
		mSearchDataOutput = new JTextArea(28, 32);
		// create DatePickers
		mSearchStartModel = new UtilDateModel();
		mSearchStartProperties = new Properties();
		mDatePanelSearchStart = new JDatePanelImpl(mSearchStartModel, mSearchStartProperties);
		mDatePickerSearchStart = new JDatePickerImpl(mDatePanelSearchStart, new DateLabelFormatter());
		mSearchEndModel = new UtilDateModel();
		mSearchEndProperties = new Properties();
		mDatePanelSearchEnd = new JDatePanelImpl(mSearchEndModel, mSearchEndProperties);
		mDatePickerSearchEnd = new JDatePickerImpl(mDatePanelSearchEnd, new DateLabelFormatter());
		// create Buttons
		mButtonSearch = new FNButton(FieldNotesConstants.BUTTON_SEARCH);
		mButtonExport = new FNButton(FieldNotesConstants.BUTTON_EXPORT);
		// create ArrayList
		mDataSearchResults = new ArrayList<String>();
		init();
	}

	public static SearchDataPanel getInstance() {
		if (sInstance == null) {
			sInstance = new SearchDataPanel();
		}
		return sInstance;
	}

	void init() {
		// Panel Layouts
		BorderLayout searchFNPanelLayout = new BorderLayout();
		mSearchDataPanel.setLayout(searchFNPanelLayout);
		GridLayout searchFNTextFieldPanelLayout = new GridLayout(0,2);
		mSearchTextFieldPanel.setLayout(searchFNTextFieldPanelLayout);
		// Labels
		FNLabel lblUsernameSearch = new FNLabel(FieldNotesConstants.FN_USERNAME_LABEL);
		FNLabel lblDataSearchDateStart = new FNLabel(FieldNotesConstants.FN_DATE_START_LABEL);
		FNLabel lblDataSearchDateEnd = new FNLabel(FieldNotesConstants.FN_DATE_END_LABEL);
		// ScrolPane/TextArea
		JScrollPane dataSearchScroll = new JScrollPane(mSearchDataOutput);
		mSearchDataOutput.setEditable(false);
		// Set DatePicker Properties
		mSearchStartProperties.put("text.today", "Today");
		mSearchStartProperties.put("text.month", "Month");
		mSearchStartProperties.put("text.year", "Year");
		mSearchEndProperties.put("text.today", "Today");
		mSearchEndProperties.put("text.month", "Month");
		mSearchEndProperties.put("text.year", "Year");
		
		// Add Views to TextFieldPanel
		mSearchTextFieldPanel.add(lblUsernameSearch);
		mSearchTextFieldPanel.add(mTextDataUsername);
		mSearchTextFieldPanel.add(lblDataSearchDateStart);
		mSearchTextFieldPanel.add(mDatePickerSearchStart);
		mSearchTextFieldPanel.add(lblDataSearchDateEnd);
		mSearchTextFieldPanel.add(mDatePickerSearchEnd);
		mSearchTextFieldPanel.add(new FNLabel());
		mSearchTextFieldPanel.add(mButtonSearch);
		// Add Views to Main Panel
		mSearchDataPanel.add(mSearchTextFieldPanel, BorderLayout.NORTH);
		mSearchDataPanel.add(dataSearchScroll, BorderLayout.CENTER);
		mSearchDataPanel.add(mButtonExport, BorderLayout.SOUTH);
		// Initial View Settings
		mSearchDataPanel.setVisible(false);
		FieldNotesControlPanel.getFieldNotesFrame().repaint();
		FieldNotesControlPanel.getFieldNotesFrame().revalidate();

		mButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SQLDataController conn = new SQLDataController();
				mSearchDataOutput.setEnabled(true);
				mSearchDataOutput.setVisible(true);
				mSearchDataOutput.setText(null);

				// if start date but no end, or end date but no start
				if (mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()
						&& !mDatePickerSearchEnd.getJFormattedTextField().getText().isEmpty()
						|| (!mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()
								&& mDatePickerSearchEnd.getJFormattedTextField().getText().isEmpty())) {
					// do nothing
					// if both are empty
				} else if (mTextDataUsername.getText().trim().isEmpty()
						&& mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
					// search all names and dates
					mDataSearchResults = conn.mySQLSearchData();

					int newLine = 0;
					for (int i = 0; i < mDataSearchResults.size(); i++) {
						if (i % 2 == 0) {
							newLine++;
							mSearchDataOutput.append(mDataSearchResults.get(i));
						} else {
							if (newLine == 14) {
								mSearchDataOutput.append(mDataSearchResults.get(i) + "\n\n");
								newLine = 0;
							} else {
								mSearchDataOutput.append(mDataSearchResults.get(i) + "\n");
							}
						}
					}
					// if user search bar has a name, but no dates are selected
				} else if (!mTextDataUsername.getText().trim().isEmpty()
						&& mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
					// search specific name and all dates
					String searchByUserName = mTextDataUsername.getText();
					mDataSearchResults = conn.mySQLSearchData(searchByUserName);

					int newLine = 0;
					for (int i = 0; i < mDataSearchResults.size(); i++) {
						if (i % 2 == 0) {
							newLine++;
							mSearchDataOutput.append(mDataSearchResults.get(i));
						} else {
							if (newLine == 14) {
								mSearchDataOutput.append(mDataSearchResults.get(i) + "\n\n");
								newLine = 0;
							} else {
								mSearchDataOutput.append(mDataSearchResults.get(i) + "\n");
							}
						}
					}
					// if no user, but dates are selected
				} else if (mTextDataUsername.getText().trim().isEmpty()
						&& !mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
					// search by selected dates
					mSearchDataOutput.setEnabled(true);
					mSearchDataOutput.setVisible(true);
					mSearchDataOutput.setText(null);

					if (mDatePickerSearchStart.getJFormattedTextField().getText().trim().isEmpty()
							|| mDatePickerSearchEnd.getJFormattedTextField().getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"Please enter a date range");
					} else {
						String dataSearchStartDate = "";
						String dataSearchEndDate = "";
						try {
							dataSearchStartDate = mDatePickerSearchStart.getJFormattedTextField().getText();

							dataSearchEndDate = mDatePickerSearchEnd.getJFormattedTextField().getText();
						} catch (StringIndexOutOfBoundsException e1) {
							JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
									"Please enter a valid date range");
						}

						mDataSearchResults = conn.mySQLSearchDataByDateRange(dataSearchStartDate, dataSearchEndDate);

						int newLine = 0;
						for (int i = 0; i < mDataSearchResults.size(); i++) {
							if (i % 2 == 0) {
								newLine++;
								mSearchDataOutput.append(mDataSearchResults.get(i));
							} else {
								if (newLine == 14) {
									mSearchDataOutput.append(mDataSearchResults.get(i) + "\n\n");
									newLine = 0;
								} else {
									mSearchDataOutput.append(mDataSearchResults.get(i) + "\n");
								}
							}
						}
					}
				} else if (!mTextDataUsername.getText().trim().isEmpty()
						&& !mDatePickerSearchStart.getJFormattedTextField().getText().isEmpty()) {
					// search by user name and date
					if (mDatePickerSearchStart.getJFormattedTextField().getText().trim().isEmpty()
							|| mDatePickerSearchEnd.getJFormattedTextField().getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
								"Please enter a date range");
					} else {
						String dataSearchStartDate = "";
						String dataSearchEndDate = "";
						try {
							dataSearchStartDate = mDatePickerSearchStart.getJFormattedTextField().getText();
							dataSearchEndDate = mDatePickerSearchEnd.getJFormattedTextField().getText();

							String dataSearchUserName = mTextDataUsername.getText();

							mDataSearchResults = conn.mySQLSearchDataByUserAndDateRange(dataSearchUserName,
									dataSearchStartDate, dataSearchEndDate);

							int newLine = 0;
							for (int i = 0; i < mDataSearchResults.size(); i++) {
								if (i % 2 == 0) {
									newLine++;
									mSearchDataOutput.append(mDataSearchResults.get(i));
								} else {
									if (newLine == 14) {
										mSearchDataOutput.append(mDataSearchResults.get(i) + "\n\n");
										newLine = 0;
									} else {
										mSearchDataOutput.append(mDataSearchResults.get(i) + "\n");
									}
								}
							}

						} catch (StringIndexOutOfBoundsException e1) {
							JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
									"Please enter a valid date range");
						}
					}
				}
			}
		});

		// export CSV file to User Desktop
		mButtonExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// export controller
				ExportController exporter = new ExportController();
				int exportSuccessCode = exporter.writeDataToCSVFile(mDataSearchResults);
				
				if (exportSuccessCode == 1) {
					JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
							"Success! CVS report generated");
				} else {
					JOptionPane.showMessageDialog(FieldNotesControlPanel.getFieldNotesFrame(),
							"Failure! CVS export error");
				}
			}
		});
	}

	public static JPanel getView() {
		return mSearchDataPanel;
	}

	public static void showView() {
		mSearchDataPanel.setVisible(true);
	}

	public static void hideView() {
		mSearchDataPanel.setVisible(false);
		sInstance.resetGui();
	}

	private void resetGui() {
		mSearchDataPanel.setVisible(false);
		mTextDataUsername.setText(null);
		mSearchDataOutput.setText(null);

		LocalDate now = LocalDate.now();

		mSearchStartModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		mDatePickerSearchStart.getJFormattedTextField().setText("");
		mSearchEndModel.setDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		mDatePickerSearchEnd.getJFormattedTextField().setText("");
	}
}
