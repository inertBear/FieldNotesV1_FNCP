/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model;

public class FNUser {

    private int mId;
    private String mType;
    private String mUsername;
    //TODO: [FNCP-022] convert this to Char array
    private String mPassword;

    private FNUser(int id, String username, String password, String type) {
        mId = id;
        mUsername = username;
        mPassword = password;
        mType = type;
    }

    public void setID(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }

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

    /**
     * FNEntityBuilder
     */
    public static class FNUserBuilder {
        private int mId;
        private String mType;
        private String mUsername;
        //TODO: [FNCP-022] convert this to Char array
        private String mPassword;

        public FNUserBuilder() {
        }

        public FNUserBuilder setId(int id) {
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
