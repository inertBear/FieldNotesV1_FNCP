/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.FNUser;

import static com.devhunter.fncp.constants.FNUserConstants.ADMIN_USER;

public class FNAdmin extends FNEntity {

    public FNAdmin(String userName, String password) {
        this.mUsername = userName;
        this.mPassword = password;
        this.mType = ADMIN_USER;
    }
}
