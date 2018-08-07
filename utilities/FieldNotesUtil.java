/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.border.LineBorder;

import com.devhunter.fieldnotes.constants.FieldNotesConstants;

/**
 * This class holds all of the common View characteristics including colors, borders, and Dimensions. 
 * These can be accessed from anywhere in FieldNotes and ensures that all dimensions, colors, and 
 * borders stay the same throughout the entire project.
 *
 */

public class FieldNotesUtil {

	private static FieldNotesUtil sInstance;
	private static String sCurrentUser;
	private SpinnerListModel mLocationModel;
	private SpinnerListModel mBillableModel;
	private SimpleDateFormat mFormatter;
	private Dimension mSmallLabelDimension;
	private Dimension mStandardLabelDimension;
	private Dimension mLargeLabelDimension;
	private Dimension mStandardTextFieldDimensions;
	private Dimension mLargeTextFieldDimension;
	private Dimension mLoginPanelDimension;
	private Dimension mStandardButtonDimension;
	private Color mPrimaryColor;
	
	/**
	 * This class has been established as a singleton to disallow the creation of multiple Utility objects.
	 * 
	 * TODO: include utilities for Thread control- i.e. UI VS background threading
	 */

	private FieldNotesUtil() {
		mLocationModel = new SpinnerListModel(FieldNotesConstants.APPROVED_BILLING_LOCATIONS);
		mBillableModel = new SpinnerListModel(FieldNotesConstants.APPROVED_BILLING_CODES);
		mFormatter = new SimpleDateFormat("HHmmss");

		mPrimaryColor = new Color(100, 149, 237);
		mSmallLabelDimension = new Dimension(50, 24);
		mStandardLabelDimension = new Dimension(100, 24);
		mLargeLabelDimension = new Dimension(200, 24);
		mStandardTextFieldDimensions = new Dimension(200, 24);
		mLargeTextFieldDimension = new Dimension(500, 24);
		mLoginPanelDimension = new Dimension(325, 40);
	}

	public static FieldNotesUtil getInstance() {
		if (sInstance == null) {
			sInstance = new FieldNotesUtil();
		}
		return sInstance;
	}
	
	public void setCurrentUser(String currentUser) {
		sCurrentUser = currentUser;
	}
	
	/**
	 * get the UserName of the currently logged in User.
	 * @return String, user name
	 */
	
	public String getCurrentUser() {
		return sCurrentUser;
	}

	/**
	 * get the approved FieldNotes billing locations
	 * 
	 * @return SpinnerModel of approved Locations
	 */

	public SpinnerModel getLocations() {
		return mLocationModel;
	}

	/**
	 * get the approved FieldNotes billing codes
	 * 
	 * @return SpinnerModel of approved billing codes
	 */

	public SpinnerModel getBillable() {
		return mBillableModel;
	}

	/**
	 * get the current time
	 * 
	 * @return current Date
	 */

	public Date getZeroHour() {
		String timeStamp = mFormatter.format(Calendar.getInstance().getTime());
		Date now = null;
		try {
			now = mFormatter.parse(timeStamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block, need to create a method of error 
			// handling throughout all of FieldNotes 
			e.printStackTrace();
		}
		return now;
	}
	
	/**
	 * get small label dimension
	 * 
	 * @return small label dimension
	 */
	
	public Dimension getSmallLabelDimension() {
		return mSmallLabelDimension;
	}

	/**
	 * get standard label dimension
	 * 
	 * @return standard label Dimension
	 */

	public Dimension getStandardLabelDimension() {
		return mStandardLabelDimension;
	}
	
	/**
	 * get large label dimension, used in login panel (4/23/2018)
	 * 
	 * @return large label Dimension
	 */
	
	public Dimension getLargeLabelDimension() {
		return mLargeLabelDimension;
	}
	
	/**
	 * get standard TextField dimension, used for the TextFields holding Login Information
	 * for example: login panel
	 * 
	 * @return standard TextField Dimension
	 */

	public Dimension getStandardTextFieldDimension() {
		return mStandardTextFieldDimensions;
	}

	/**
	 * get large TextField dimension, used for the TextFields holding FieldNote Values
	 * for example: in the add, edit, delete panels
	 * 
	 * @return large TextField Dimension
	 */

	public Dimension getLargeTextFieldDimen() {
		return mLargeTextFieldDimension;
	}
	
	/**
	 * get login panel Dimension
	 * @return login panel Dimension
	 */
	
	public Dimension getLoginPanelDimension() {
		return mLoginPanelDimension;
	}
	
	public Dimension getStandardButtonDimension() {
		return mStandardButtonDimension;
	}

	/**
	 * get primary FieldNotes blue color
	 * 
	 * @return Color (blue)
	 */

	public Color getPrimaryColor() {
		return mPrimaryColor;
	}
	
	/**
	 * get the standard FieldNotes window border
	 * @return standard LineBorder (blue)
	 */
	
	public LineBorder getLineBorder() {
		return new LineBorder(getPrimaryColor(), 5, true);
	}
	
	/**
	 * get the standard rigid area for padding 
	 * @return standard rigid area
	 */
	
	public Dimension getStandardRigidArea() {
		return new Dimension(0, 75);
	}
	
	/**
	 * reset ALL GUI elements within FieldNotes
	 */

	public void resetAllGui() {

	}

}
