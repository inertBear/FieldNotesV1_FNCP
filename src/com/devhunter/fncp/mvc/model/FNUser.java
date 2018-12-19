/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model;

/**
 * The <i>FNUser</i> class contains the data for a User entry into the USER table of the database.
 */

public class FNUser extends FieldNote {

    private String mType;
    //TODO: [FNCP-022] convert this to Char array
    private String mPassword;

    private FNUser(String id, String username, String password, String type) {
        this.mId = id;
        this.mName = username;
        this.mPassword = password;
        this.mType = type;
    }

    public String getUsername() {
        return this.mName;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public String getType() {
        return this.mType;
    }

    /**
     * FNEntityBuilder
     */
    public static class FNUserBuilder {
        private String mId;
        private String mType;
        private String mUsername;
        //TODO: [FNCP-022] convert this to Char array
        private String mPassword;

        public FNUserBuilder() {
        }

        public FNUserBuilder setId(String id) {
            this.mId = id;
            return this;
        }

        public FNUserBuilder setUsername(String username) {
            this.mUsername = username;
            return this;
        }

        public FNUserBuilder setPassword(String password) {
            this.mPassword = password;
            return this;
        }

        public FNUserBuilder setType(String type) {
            this.mType = type;
            return this;
        }

        public FNUser build() {
            return new FNUser(mId, mUsername, mPassword, mType);
        }
    }
}
