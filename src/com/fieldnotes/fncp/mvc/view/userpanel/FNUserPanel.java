/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.view.userpanel;

import com.fieldnotes.fncp.constants.FNCPConstants;
import com.fieldnotes.fncp.mvc.model.fnview.FNButton;
import com.fieldnotes.fncp.mvc.model.fnview.FNPanel;
import com.fieldnotes.fncp.mvc.view.FNControlPanel;
import com.fieldnotes.fncp.mvc.view.userpanel.subpanels.AddUserPanel;
import com.fieldnotes.fncp.mvc.view.userpanel.subpanels.ChangeUserPasswordPanel;
import com.fieldnotes.fncp.mvc.view.userpanel.subpanels.DeleteUserPanel;
import com.fieldnotes.fncp.mvc.view.userpanel.subpanels.SearchUserPanel;
import com.fieldnotes.fncp.utilities.FNUtil;

import javax.swing.*;
import java.awt.*;

public class FNUserPanel extends FNPanel {

    private static FNUserPanel sInstance;
    private static FNPanel sUserControlPanel;
    private FNButton mBtnUserSearch;
    private FNButton mBtnUserAdd;
    private FNButton mBtnUserDelete;
    private FNButton mBtnUserPassword;

    private FNUserPanel() {
        sUserControlPanel = new FNPanel();
        mBtnUserSearch = new FNButton(FNCPConstants.USER_SEARCH_BUTTON);
        mBtnUserAdd = new FNButton(FNCPConstants.USER_ADD_BUTTON);
        mBtnUserDelete = new FNButton(FNCPConstants.USER_DELETE_BUTTON);
        mBtnUserPassword = new FNButton(FNCPConstants.USER_EDIT_PASSWORD_BUTTON);

        init();
    }

    public static FNUserPanel getInstance() {
        if (sInstance == null) {
            sInstance = new FNUserPanel();
        }
        return sInstance;
    }

    private void init() {
        // ADMIN ACCESS
        if (FNUtil.getInstance().hasAdminAccess()) {
            sUserControlPanel.add(mBtnUserSearch);
            sUserControlPanel.add(mBtnUserAdd);
            sUserControlPanel.add(mBtnUserDelete);
        }
        sUserControlPanel.add(mBtnUserPassword);
        sUserControlPanel.setVisible(false);

        SearchUserPanel.getInstance();
        AddUserPanel.getInstance();
        DeleteUserPanel.getInstance();
        ChangeUserPasswordPanel.getInstance();

        // when user navigates to Search User TAB
        mBtnUserSearch.addActionListener(e -> {
            resetGui();
            mBtnUserSearch.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(SearchUserPanel.getView(), BorderLayout.CENTER);
            sUserControlPanel.setVisible(true);
            SearchUserPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        // When user navigates to Add User TAB
        mBtnUserAdd.addActionListener(e -> {
            resetGui();
            mBtnUserAdd.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(AddUserPanel.getView(), BorderLayout.CENTER);
            sUserControlPanel.setVisible(true);
            AddUserPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        // When user navigates to Delete User TAB
        mBtnUserDelete.addActionListener(e -> {
            resetGui();
            mBtnUserDelete.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(DeleteUserPanel.getView(), BorderLayout.CENTER);
            sUserControlPanel.setVisible(true);
            DeleteUserPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });

        // When user navigates to Change User Password TAB
        mBtnUserPassword.addActionListener(e -> {
            resetGui();
            mBtnUserPassword.setBackground(FNUtil.getInstance().getPrimaryColor());
            FNControlPanel.getFieldNotesFrame().add(ChangeUserPasswordPanel.getView(), BorderLayout.CENTER);
            sUserControlPanel.setVisible(true);
            ChangeUserPasswordPanel.getView().setVisible(true);

            FNControlPanel.getFieldNotesFrame().repaint();
            FNControlPanel.getFieldNotesFrame().revalidate();
        });
    }

    public static JPanel getView() {
        return sUserControlPanel;
    }

    public static void showView() {
        sUserControlPanel.setVisible(true);
    }

    public static void hideView() {
        sUserControlPanel.setVisible(false);
        sInstance.resetGui();
    }

    private void resetGui() {
        sUserControlPanel.setVisible(false);

        SearchUserPanel.hideView();
        AddUserPanel.hideView();
        DeleteUserPanel.hideView();
        ChangeUserPasswordPanel.hideView();

        //reset button colors
        mBtnUserSearch.setBackground(null);
        mBtnUserAdd.setBackground(null);
        mBtnUserDelete.setBackground(null);
        mBtnUserPassword.setBackground(null);
    }
}