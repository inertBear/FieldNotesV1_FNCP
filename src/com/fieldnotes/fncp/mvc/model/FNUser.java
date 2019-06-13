/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model;

/**
 * The <i>FNUser</i> class contains the data for a User entry into the USER table of the database.
 */

public class FNUser extends FieldNote {

    private String mType;
    private String mPassword;
    private String mToken;

    private FNUser(String id, String username, String password, String type, String token) {
        this.mId = id;
        this.mName = username;
        this.mPassword = password;
        this.mType = type;
        this.mToken = token;
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

    public String getToken() {
        return this.mToken;
    }

    /**
     * FNEntityBuilder
     */
    public static class FNUserBuilder {
        private String mId;
        private String mType;
        private String mUsername;
        private String mPassword;
        private String mToken;

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

        public FNUserBuilder setToken(String token) {
            this.mToken = token;
            return this;
        }

        public FNUser build() {
            return new FNUser(mId, mUsername, mPassword, mType, mToken);
        }
    }
}