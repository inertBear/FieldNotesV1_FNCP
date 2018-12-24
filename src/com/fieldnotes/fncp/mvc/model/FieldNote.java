/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model;

public abstract class FieldNote {

    String mId;
    String mName;
    // consider adding a description field to display in the FNPreview of a ListView

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}