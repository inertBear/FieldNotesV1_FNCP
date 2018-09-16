/**
 * © 2017-2018 FieldNotes. All Rights Reserved
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.fnuser;

public class FNEntity {

    //TODO: [FNCP-022] create an Entity factory to create the entity type by the String "type" passed in

    private int mId = 0;
    private String mType;
    private String mUsername;
    //TODO: [FNCP-022] convert this to Char array
    private String mPassword;

    private FNEntity(int id, String username, String password, String type) {
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
    public static class FNEntityBuilder {
        private int mId = 0;
        private String mType = "UNKNOWN";
        private String mUsername = "FNUSER";
        //TODO: [FNCP-022] convert this to Char array
        private String mPassword = "FNPASSWORD";

        public FNEntityBuilder() {
        }

        public FNEntityBuilder setId(int id) {
            this.mId = id;
            return this;
        }

        public FNEntityBuilder setUsername(String username) {
            this.mUsername = username;
            return this;
        }

        public FNEntityBuilder setPassword(String password) {
            this.mPassword = password;
            return this;
        }

        public FNEntityBuilder setType(String type) {
            this.mType = type;
            return this;
        }

        public FNEntity build() {
            return new FNEntity(mId, mUsername, mPassword, mType);
        }

        public FNEntity buildEmptyFNEntity() {
            return new FNEntity(0, null, null, null);
        }
    }
}
