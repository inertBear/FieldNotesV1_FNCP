/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.CrudSearchValidation;
import com.devhunter.fncp.mvc.controller.sql.SQLDataController;
import com.devhunter.fncp.mvc.model.*;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteDataPanel extends FNPanel {
    // CRUD SEARCH PANELS
    private static FNPanel mCrudSearchPanel;
    private FNPanel mCrudSearchTextFieldPanel;
    // Panels
    private static DeleteDataPanel sInstance;
    private FNPanel mDeleteFNDataPanel;
    private FNPanel mDeleteFNButtonPanel;
    // TextFields
    private FNTextField mCRUDSearch;
    private FNTextField mTextDeleteDataName;
    private FNTextField mTextDeleteDataWellName;
    private FNTextField mTextDeleteDataDateStart;
    private FNTextField mTextDeleteDataTimeStart;
    private FNTextField mTextDeleteDataMileageStart;
    private FNTextField mTextDeleteDataDescription;
    private FNTextField mTextDeleteDataMileageEnd;
    private FNTextField mTextDeleteDataDateEnd;
    private FNTextField mTextDeleteDataTimeEnd;
    private FNTextField mTextDeleteDataProject;
    private FNTextField mTextDeleteDataLocation;
    private FNTextField mTextDeleteDataGPS;
    private FNTextField mTextDeleteDataBillable;
    // Buttons
    private FNButton buttonDelete;
    // Strings
    private String mFlexTicketNumber;

    private DeleteDataPanel() {
        // create CRUD Search Panels
        mCrudSearchPanel = new FNPanel();
        mCrudSearchTextFieldPanel = new FNPanel();
        // Create Panels
        mDeleteFNDataPanel = new FNPanel();
        mDeleteFNButtonPanel = new FNPanel();
        // create TextFields
        mCRUDSearch = new FNTextField();
        mTextDeleteDataName = new FNTextField();
        mTextDeleteDataWellName = new FNTextField();
        mTextDeleteDataDateStart = new FNTextField();
        mTextDeleteDataTimeStart = new FNTextField();
        mTextDeleteDataMileageStart = new FNTextField();
        mTextDeleteDataDescription = new FNTextField();
        mTextDeleteDataMileageEnd = new FNTextField();
        mTextDeleteDataDateEnd = new FNTextField();
        mTextDeleteDataTimeEnd = new FNTextField();
        mTextDeleteDataProject = new FNTextField();
        mTextDeleteDataLocation = new FNTextField();
        mTextDeleteDataGPS = new FNTextField();
        mTextDeleteDataBillable = new FNTextField();
        // create Buttons
        buttonDelete = new FNButton(FNConstants.BUTTON_DELETE);
        // create Strings
        mFlexTicketNumber = "";
        init();
    }

    public static DeleteDataPanel getInstance() {
        if (sInstance == null) {
            sInstance = new DeleteDataPanel();
        }
        return sInstance;
    }

    void init() {
        // CRUD Search Panel Layouts
        BorderLayout crudSearchLayout = new BorderLayout();
        mCrudSearchPanel.setLayout(crudSearchLayout);
        GridLayout crudSearchTextFieldPanelLayout = new GridLayout(0, 2);
        mCrudSearchTextFieldPanel.setLayout(crudSearchTextFieldPanelLayout);
        // CRUD Search Labels
        FNLabel crudTicketLabel = new FNLabel(FNConstants.CRUD_SEARCH_TICKET_NUMBER);
        // CRUD Search Buttons
        FNButton buttonCrudSearch = new FNButton(FNConstants.BUTTON_SEARCH);
        // Panels/layouts
        GridLayout deleteFNDataPanelLayout = new GridLayout(0, 2);
        mDeleteFNDataPanel.setLayout(deleteFNDataPanelLayout);
        FlowLayout deleteFNButtonPanelLayout = new FlowLayout();
        mDeleteFNButtonPanel.setLayout(deleteFNButtonPanelLayout);
        mDeleteFNButtonPanel.setBorder(FNUtil.getInstance().getLineBorder());

        // Labels
        FNLabel lblDeleteDataName = new FNLabel(FNConstants.FN_USERNAME_LABEL);
        FNLabel lblDeleteDataWellName = new FNLabel(FNConstants.FN_WELLNAME_LABEL);
        FNLabel lblDeleteDataDateStart = new FNLabel(FNConstants.FN_DATE_START_LABEL);
        FNLabel lblDeleteDataTimeStart = new FNLabel(FNConstants.FN_TIME_START_LABEL);
        FNLabel lblDeleteDataMileageStart = new FNLabel(FNConstants.FN_MILEAGE_START_LABEL);
        FNLabel lblDeleteDataDescription = new FNLabel(FNConstants.FN_DESCRIPTION_LABEL);
        FNLabel lblDeleteDataMileageEnd = new FNLabel(FNConstants.FN_MILEAGE_END_LABEL);
        FNLabel lblDeleteDataDateEnd = new FNLabel(FNConstants.FN_DATE_END_LABEL);
        FNLabel lblDeleteDataTimeEnd = new FNLabel(FNConstants.FN_TIME_END_LABEL);
        FNLabel lblDeleteDataProject = new FNLabel(FNConstants.FN_PROJECT_LABEL);
        FNLabel lblDeleteDataLocation = new FNLabel(FNConstants.FN_LOCATION_LABEL);
        FNLabel lblDeleteDataGPS = new FNLabel(FNConstants.FN_GPS_LABEL);
        FNLabel lblDeleteDataBillable = new FNLabel(FNConstants.FN_BILLING_LABEL);
        // Override TextField properties
        mTextDeleteDataName.setEditable(false);
        mTextDeleteDataWellName.setEditable(false);
        mTextDeleteDataDateStart.setEditable(false);
        mTextDeleteDataTimeStart.setEditable(false);
        mTextDeleteDataMileageStart.setEditable(false);
        mTextDeleteDataDescription.setEditable(false);
        mTextDeleteDataMileageEnd.setEditable(false);
        mTextDeleteDataDateEnd.setEditable(false);
        mTextDeleteDataTimeEnd.setEditable(false);
        mTextDeleteDataProject.setEditable(false);
        mTextDeleteDataLocation.setEditable(false);
        mTextDeleteDataGPS.setEditable(false);
        mTextDeleteDataBillable.setEditable(false);

        //Add CRUD Search Views to CRUD Search TextField Panel
        mCrudSearchTextFieldPanel.add(crudTicketLabel);
        mCrudSearchTextFieldPanel.add(mCRUDSearch);
        mCrudSearchTextFieldPanel.add(new FNLabel());
        mCrudSearchTextFieldPanel.add(buttonCrudSearch);
        // Add Views to Panel
        mDeleteFNDataPanel.add(lblDeleteDataName);
        mDeleteFNDataPanel.add(mTextDeleteDataName);
        mDeleteFNDataPanel.add(lblDeleteDataWellName);
        mDeleteFNDataPanel.add(mTextDeleteDataWellName);
        mDeleteFNDataPanel.add(lblDeleteDataDateStart);
        mDeleteFNDataPanel.add(mTextDeleteDataDateStart);
        mDeleteFNDataPanel.add(lblDeleteDataTimeStart);
        mDeleteFNDataPanel.add(mTextDeleteDataTimeStart);
        mDeleteFNDataPanel.add(lblDeleteDataMileageStart);
        mDeleteFNDataPanel.add(mTextDeleteDataMileageStart);
        mDeleteFNDataPanel.add(lblDeleteDataDescription);
        mDeleteFNDataPanel.add(mTextDeleteDataDescription);
        mDeleteFNDataPanel.add(lblDeleteDataMileageEnd);
        mDeleteFNDataPanel.add(mTextDeleteDataMileageEnd);
        mDeleteFNDataPanel.add(lblDeleteDataDateEnd);
        mDeleteFNDataPanel.add(mTextDeleteDataDateEnd);
        mDeleteFNDataPanel.add(lblDeleteDataTimeEnd);
        mDeleteFNDataPanel.add(mTextDeleteDataTimeEnd);
        mDeleteFNDataPanel.add(lblDeleteDataProject);
        mDeleteFNDataPanel.add(mTextDeleteDataProject);
        mDeleteFNDataPanel.add(lblDeleteDataLocation);
        mDeleteFNDataPanel.add(mTextDeleteDataLocation);
        mDeleteFNDataPanel.add(lblDeleteDataGPS);
        mDeleteFNDataPanel.add(mTextDeleteDataGPS);
        mDeleteFNDataPanel.add(lblDeleteDataBillable);
        mDeleteFNDataPanel.add(mTextDeleteDataBillable);
        mDeleteFNDataPanel.add(new FNLabel());
        mDeleteFNDataPanel.add(buttonDelete);
        // Add CRUD Search Views to Main Panel
        mCrudSearchPanel.add(mCrudSearchTextFieldPanel, BorderLayout.NORTH);
        mCrudSearchPanel.add(mDeleteFNDataPanel, BorderLayout.CENTER);

        // set initial View settings
        mCrudSearchPanel.setVisible(false);
        FNControlPanel.getFieldNotesFrame().repaint();
        FNControlPanel.getFieldNotesFrame().revalidate();

        buttonCrudSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // CRUDSearch Validation
                if (CrudSearchValidation.validate(mCRUDSearch.getText())) {
                    mFlexTicketNumber = mCRUDSearch.getText();
                    // send Ticket Number to controller for CRUD search
                    SQLDataController conn = new SQLDataController();
                    FieldNote result = conn.mySQLSearchDataByTicketNumber(mFlexTicketNumber);
                    // if the returned value has a ticket number, then it is a valid FieldNote
                    if (result.getTicketNumber() == null) {
                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(),
                                "No Data Found in Database");
                        // hide fieldnote data
                        mDeleteFNDataPanel.setVisible(false);
                        FNControlPanel.getFieldNotesFrame().repaint();
                        FNControlPanel.getFieldNotesFrame().revalidate();
                    } else {
                        // show search bar and field note data
                        mDeleteFNDataPanel.setVisible(true);
                        mCRUDSearch.setText(mFlexTicketNumber);

                        mTextDeleteDataName.setText(result.getUserName());
                        mTextDeleteDataWellName.setText(result.getWellName());
                        mTextDeleteDataDateStart.setText(result.getDateStart());
                        mTextDeleteDataTimeStart.setText(result.getTimeStart());
                        mTextDeleteDataMileageStart.setText(result.getMileageStart());
                        mTextDeleteDataDescription.setText(result.getDescription());
                        mTextDeleteDataMileageEnd.setText(result.getMileageEnd());
                        mTextDeleteDataDateEnd.setText(result.getDateEnd());
                        mTextDeleteDataTimeEnd.setText(result.getTimeEnd());
                        mTextDeleteDataProject.setText(result.getProjectNumber());
                        mTextDeleteDataLocation.setText(matchCase(result.getLocation()));
                        mTextDeleteDataGPS.setText(result.getGPSCoords());
                        mTextDeleteDataBillable.setText(matchCase(result.getBillingType()));

                        FNControlPanel.getFieldNotesFrame().repaint();
                        FNControlPanel.getFieldNotesFrame().revalidate();
                    }
                } else {
                    //Validation failed - Do nothing and allow them to search again
                }
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //  Verify the user meant to delete a ticket
                int res = JOptionPane.showConfirmDialog(null, "Are you want to permenantly delete this note?", "",
                        JOptionPane.YES_NO_OPTION);
                switch (res) {
                    case JOptionPane.YES_OPTION:
                        // send to controller for CUD Event
                        SQLDataController conn = new SQLDataController();
                        boolean result = conn.deleteFieldNote(mFlexTicketNumber);
                        // code 1 == success, code 0 == failure
                        if (result) {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Note Deleted");
                        } else {
                            JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), "Connection error - NOTE NOT DELETED");
                        }
                        break;
                    case JOptionPane.NO_OPTION:
                        // DO NOTHING - cancel the delete
                        break;
                }
                resetGui();
                mCrudSearchPanel.setVisible(true);
            }
        });
    }

    public String formatTime(String time) {
        return time.substring(0, 5);
    }

    /**
     * updates the old billing codes and locations as they are viewed.
     * @param value
     * @return
     */

    private String matchCase(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static JPanel getView() {
        return mCrudSearchPanel;
    }

    public static void showView() {
        mCrudSearchPanel.setVisible(true);
    }

    public static void hideView() {
        mCrudSearchPanel.setVisible(false);
        sInstance.resetGui();
    }

    private void resetGui() {
        //return to empty view
        mDeleteFNDataPanel.setVisible(false);
        mCrudSearchPanel.setVisible(false);
        // clear all values in views
        mCRUDSearch.setText(null);
        mTextDeleteDataName.setText(null);
        mTextDeleteDataWellName.setText(null);
        mTextDeleteDataDateStart.setText(null);
        mTextDeleteDataTimeStart.setText(null);
        mTextDeleteDataMileageStart.setText(null);
        mTextDeleteDataDescription.setText(null);
        mTextDeleteDataMileageEnd.setText(null);
        mTextDeleteDataDateEnd.setText(null);
        mTextDeleteDataTimeEnd.setText(null);
        mTextDeleteDataProject.setText(null);
        mTextDeleteDataLocation.setText(null);
        mTextDeleteDataGPS.setText(null);
        mTextDeleteDataBillable.setText(null);
    }

}
