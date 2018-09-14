/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.fnuser;

import static com.devhunter.fncp.constants.FNUserConstants.TEST_USER;

public class FNTestUser extends FNEntity {

    public FNTestUser(String userName, String password) {
        this.mUsername = userName;
        this.mPassword = password;
        this.mType = TEST_USER;
    }
}
