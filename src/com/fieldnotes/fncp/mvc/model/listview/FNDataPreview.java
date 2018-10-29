/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.listview;

import com.fieldnotes.fncp.mvc.model.FieldNote;

class FNDataPreview {

    private String mTicketNumber;
    private String mUsername;
    private String mProject;
    private String mDate;

    FNDataPreview(FieldNote fieldNote) {
        mTicketNumber = fieldNote.getTicketNumber();
        mUsername = fieldNote.getUserName();
        mProject = fieldNote.getProject();
        mDate = fieldNote.getDateEnd();
    }

    String getTicketNumber() {
        return this.mTicketNumber;
    }

    String getUsername() {
        return this.mUsername;
    }

    String getProject() {
        return this.mProject;
    }

    String getDate() {
        return this.mDate;
    }
}
