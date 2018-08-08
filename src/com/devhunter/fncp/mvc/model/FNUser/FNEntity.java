/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.FNUser;

public class FNEntity {

    public String mType = "UNKNOWN";

    public String mUsername = "FNUSER";
    //TODO: convert this to Char array
    public String mPassword = "FNPASSWORD";

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }
}
