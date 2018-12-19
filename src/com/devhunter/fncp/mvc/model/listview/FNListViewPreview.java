/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.listview;

import com.devhunter.fncp.mvc.model.FieldNote;

class FNListViewPreview {

    private String mId;
    private String mName;

    FNListViewPreview(FieldNote note) {
        mId = note.getId();
        mName = note.getName();
    }

    String getId() {
        return this.mId;
    }

    String getUsername() {
        return this.mName;
    }
}
