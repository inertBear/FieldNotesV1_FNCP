/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller;

import com.devhunter.fncp.constants.FNConstants;
import com.mysql.jdbc.CommunicationsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * gets an instance of the connection between the MySQL database and FieldNotes.
 * A statement is created and return for immediate reuse by the client
 * creating a singleton instance will keep the jdbc.Driver from having
 * to be loaded every time a call is made. The same instance of the
 * connection can be called every time. This will increase efficiency
 * for all connection activities except the initial call
 */
public class FNBridgeService {

    private static FNBridgeService sInstance;
    private static Statement mStatement;

    private static final String JDBC_DRIVER_CLASSNAME = "com.mysql.jdbc.Driver";

    private FNBridgeService() {
        try {
            Class.forName(JDBC_DRIVER_CLASSNAME);
            Connection mConnection = DriverManager.getConnection(FNConstants.CONNECTION_URL, FNConstants.FIELDNOTES_DATABASE_USERNAME, FNConstants.FIELDNOTES_DATABASE_PASSWORD);
            mStatement = mConnection.createStatement();
        } catch (CommunicationsException e) {
            e.printStackTrace();
            System.out.println("Connection Failed: Could not log in");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql exception: Login Failed");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JBDC class not found: Login Failed");
        }
    }

    public static FNBridgeService getInstance() {
        if (sInstance == null) {
            sInstance = new FNBridgeService();
        }
        return sInstance;
    }

    public Statement getSQLBridgeStatement() {
        if (mStatement == null) {
            throw new IllegalStateException("SQLBridgeStatement is NULL");
        }
        return mStatement;
    }
}
