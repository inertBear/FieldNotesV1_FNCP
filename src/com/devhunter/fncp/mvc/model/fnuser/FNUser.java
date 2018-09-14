/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.fnuser;

import static com.devhunter.fncp.constants.FNUserConstants.REGULAR_USER;

public class FNUser extends FNEntity {

    public FNUser(String userName, String password) {
        this.mUsername = userName;
        this.mPassword = password;
        this.mType = REGULAR_USER;
    }
}
