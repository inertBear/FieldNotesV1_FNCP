/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel.subpanels;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.controller.FNDataController;
import com.devhunter.fncp.mvc.controller.validation.CrudSearchValidation;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNLabel;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.model.fnview.FNTextField;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.utilities.FNUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class DeleteDataPanel extends FNPanel {
    private static FNPanel mCrudSearchPanel;
    private FNPanel mCrudSearchTextFieldPanel;
    private static DeleteDataPanel sInstance;
    private FNPanel mDeleteFNDataPanel;
    private FNPanel mDeleteFNButtonPanel;
    private FNTextField mDeleteTicketNumber;
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
    private FNButton buttonDelete;

    private String mDeleteTicketNumberString;

    private DeleteDataPanel() {
        mCrudSearchPanel = new FNPanel();
        mCrudSearchTextFieldPanel = new FNPanel();
        mDeleteFNDataPanel = new FNPanel();
        mDeleteFNButtonPanel = new FNPanel();
        mDeleteTicketNumber = new FNTextField();
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
        buttonDelete = new FNButton(FNConstants.BUTTON_DELETE);
        mDeleteTicketNumberString = "";

        init();
    }

    public static DeleteDataPanel getInstance() {
        if (sInstance == null) {
            sInstance = new DeleteDataPanel();
        }
        return sInstance;
    }

    private void init() {
        // CRUD Search Panel Layouts
        BorderLayout crudSearchLayout = new BorderLayout();
        mCrudSearchPanel.setLayout(crudSearchLayout);
        GridLayout crudSearchTextFieldPanelLayout = new GridLayout(0, 2);
        mCrudSearchTextFieldPanel.setLayout(crudSearchTextFieldPanelLayout);

        FNLabel crudTicketLabel = new FNLabel(FNConstants.CRUD_SEARCH_TICKET_NUMBER);
        FNButton buttonCrudSearch = new FNButton(FNConstants.BUTTON_SEARCH);

        // Panels/layouts
        GridLayout deleteFNDataPanelLayout = new GridLayout(0, 2);
        mDeleteFNDataPanel.setLayout(deleteFNDataPanelLayout);
        FlowLayout deleteFNButtonPanelLayout = new FlowLayout();
        mDeleteFNButtonPanel.setLayout(deleteFNButtonPanelLayout);
        mDeleteFNButtonPanel.setBorder(FNUtil.getInstance().getLineBorder());

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
        mCrudSearchTextFieldPanel.add(mDeleteTicketNumber);
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
                if (CrudSearchValidation.validate(mDeleteTicketNumber.getText())) {
                    mDeleteTicketNumberString = mDeleteTicketNumber.getText();

                    JSONObject searchResult = FNDataController.searchFieldNotes(null, null, null, mDeleteTicketNumberString);
                    String status = searchResult.getString(RESPONSE_STATUS_TAG);
                    String messageString = searchResult.getString(RESPONSE_MESSAGE_TAG);

                    if (status.equals(RESPONSE_STATUS_SUCCESS)) {
                        JSONArray messageArray = new JSONArray(messageString);
                        JSONObject message = messageArray.getJSONObject(0);

                        // display ticket data in view
                        // show searchData bar and field note data
                        mDeleteFNDataPanel.setVisible(true);
                        mDeleteTicketNumber.setText(mDeleteTicketNumberString);

                        mTextDeleteDataName.setText(message.getString(USERNAME_TAG));
                        mTextDeleteDataWellName.setText(message.getString(WELLNAME_TAG));
                        mTextDeleteDataDateStart.setText(message.getString(DATE_START_TAG));
                        mTextDeleteDataTimeStart.setText(message.getString(TIME_START_TAG));
                        mTextDeleteDataMileageStart.setText(message.getString(MILEAGE_START_TAG));
                        mTextDeleteDataDescription.setText(message.getString(DESCRIPTION_TAG));
                        mTextDeleteDataMileageEnd.setText(message.getString(MILEAGE_END_TAG));
                        mTextDeleteDataDateEnd.setText(message.getString(DATE_END_TAG));
                        mTextDeleteDataTimeEnd.setText(message.getString(TIME_END_TAG));
                        mTextDeleteDataProject.setText(message.getString(PROJECT_NUMBER_TAG));
                        mTextDeleteDataLocation.setText(matchCase(message.getString(LOCATION_TAG)));
                        mTextDeleteDataGPS.setText(message.getString(GPS_TAG));
                        mTextDeleteDataBillable.setText(matchCase(message.getString(BILLING_TAG)));

                        FNControlPanel.getFieldNotesFrame().repaint();
                        FNControlPanel.getFieldNotesFrame().revalidate();

                        // show searchData bar and field note data
                        mDeleteFNDataPanel.setVisible(true);
                    } else {
                        mDeleteFNDataPanel.setVisible(false);
                        FNControlPanel.getFieldNotesFrame().repaint();
                        FNControlPanel.getFieldNotesFrame().revalidate();

                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), messageString);
                    }
                }
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //  Verify the user meant to deleteData a ticket
                int res = JOptionPane.showConfirmDialog(null, "Confirm DELETE FieldNote " +
                        mDeleteTicketNumberString, "", JOptionPane.YES_NO_OPTION);
                switch (res) {
                    case JOptionPane.YES_OPTION:
                        JSONObject deleteResult = FNDataController.deleteFieldNote(mDeleteTicketNumberString);
                        String message = deleteResult.getString(RESPONSE_MESSAGE_TAG);

                        JOptionPane.showMessageDialog(FNControlPanel.getFieldNotesFrame(), message);

                        resetGui();
                        mCrudSearchPanel.setVisible(true);
                        break;
                    case JOptionPane.NO_OPTION:
                        // DO NOTHING - cancel the delete command
                        break;
                }
            }
        });
    }

    /**
     * updates the old billing codes and locations as they are viewed.
     *
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
        mDeleteTicketNumber.setText(null);
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
