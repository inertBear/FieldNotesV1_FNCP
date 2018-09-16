package com.devhunter.fncp.constants;

public class FNStorage {

    // entity data
    public static final String ENTITY_NAME = "RHL";
    public static final int ENTITY_ID = 1;

    // Database connection information
    private static final String AWS_DATABASE_ENDPOINT = "fieldnotes-rhl-restore.ckzbugsctcko.us-west-2.rds.amazonaws.com:3306";
    private static final String FIELDNOTES_DATABASE_NAME = "FieldNotes_RHL";
    public static final String FIELDNOTES_DATABASE_USERNAME = "FNAdmin";
    public static final String FIELDNOTES_DATABASE_PASSWORD = "Nashv1ll3$";
    public static final String CONNECTION_URL = "jdbc:mysql://" + AWS_DATABASE_ENDPOINT + "/" + FIELDNOTES_DATABASE_NAME;
}
