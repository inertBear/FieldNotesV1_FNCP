/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.view.datapanel;

import com.devhunter.fncp.constants.FNConstants;
import com.devhunter.fncp.mvc.model.fnview.FNButton;
import com.devhunter.fncp.mvc.model.fnview.FNPanel;
import com.devhunter.fncp.mvc.view.FNControlPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.AddDataPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.DeleteDataPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.EditDataPanel;
import com.devhunter.fncp.mvc.view.datapanel.subpanels.SearchDataPanel;
import com.devhunter.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;

public class FNDataPanel extends FNPanel {

    private static FNDataPanel sInstance;
    private static FNPanel sDataControlPanel;
    private FNButton mBtnDataSearch;
    private FNButton mBtnDataAdd;
    private FNButton mBtnDataDeleteSearch;
    private FNButton mBtnDataEditSearch;

    private FNDataPanel() {
        sDataControlPanel = new FNPanel();
        mBtnDataSearch = new FNButton(FNConstants.DATA_SEARCH_BUTTON);
        mBtnDataAdd = new FNButton(FNConstants.DATA_ADD_BUTTON);
        mBtnDataDeleteSearch = new FNButton(FNConstants.DATA_DELETE_BUTTON);
        mBtnDataEditSearch = new FNButton(FNConstants.DATA_EDIT_BUTTON);

        init();
    }

    public static FNDataPanel getInstance() {
        if (sInstance == null) {
            sInstance = new FNDataPanel();
        }
        return sInstance;
    }

    private void init() {
        sDataControlPanel.add(mBtnDataSearch);
        sDataControlPanel.add(mBtnDataAdd);
        // ADMIN ACCESS
        if (FNUtil.getInstance().hasAdminAccess()) {
            sDataControlPanel.add(mBtnDataDeleteSearch);
        }
        sDataControlPanel.add(mBtnDataEditSearch);
        sDataControlPanel.setVisible(false);

        SearchDataPanel.getInstance();
        AddDataPanel.getInstance();
        DeleteDataPanel.getInstance();
        EditDataPanel.getInstance();

        mBtnDataSearch.addActionListener(e -> {
            resetGui();
            mBtnDataSearch.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(SearchDataPanel.getView(), BorderLayout.CENTER);
            sDataControlPanel.setVisible(true);
            SearchDataPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnDataAdd.addActionListener(e -> {
            resetGui();
            mBtnDataAdd.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(AddDataPanel.getView(), BorderLayout.CENTER);
            sDataControlPanel.setVisible(true);
            AddDataPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnDataDeleteSearch.addActionListener(e -> {
            resetGui();
            mBtnDataDeleteSearch.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(DeleteDataPanel.getView(), BorderLayout.CENTER);
            sDataControlPanel.setVisible(true);
            DeleteDataPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        mBtnDataEditSearch.addActionListener(e -> {
            resetGui();
            mBtnDataEditSearch.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(EditDataPanel.getView(), BorderLayout.CENTER);
            sDataControlPanel.setVisible(true);
            EditDataPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        sDataControlPanel.setVisible(true);
    }

    // clears visual aspects of the GUI
    private void resetGui() {
        sDataControlPanel.setVisible(false);

        SearchDataPanel.hideView();
        AddDataPanel.hideView();
        DeleteDataPanel.hideView();
        EditDataPanel.hideView();

        //reset button colors
        mBtnDataSearch.setBackground(null);
        mBtnDataAdd.setBackground(null);
        mBtnDataDeleteSearch.setBackground(null);
        mBtnDataEditSearch.setBackground(null);
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
