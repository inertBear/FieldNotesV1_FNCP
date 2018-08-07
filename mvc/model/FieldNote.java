/**
 * © 2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fieldnotes.mvc.model;

/**
 * The <i>FieldNote</i> class encapsulates the data and separates it from the
 * work flow of the program. Fields within a <i>FieldNote</i> are set by using a
 * <i>FieldNoteBuilder</>. This class supplies getter methods to retrieve the
 * values when needed.
 */

public class FieldNote {

	private String mUserName;
	private String mTicketNumber;
	private String mDateStart;
	private String mDateEnd;
	private String mTimeStart;
	private String mTimeEnd;
	private String mMileageStart;
	private String mMileageEnd;

	private String mProjectNumber;
	private String mWellName;
	private String mDescription;
	private String mLocation;
	private String mGPSCoords;
	private String mBillingType;

	private boolean mIsTicketMobile;

	private FieldNote(final String userName, final String ticketNumber, final String dateStart, final String dateEnd,
			final String timeStart, final String timeEnd, final String mileageStart, final String mileageEnd,
			final String projectNumber, final String wellName, final String description, final String location,
			final String gps, final String billingType) {

		this.mUserName = userName;
		this.mTicketNumber = ticketNumber;
		this.mDateStart = dateStart;
		this.mDateEnd = dateEnd;
		this.mTimeStart = timeStart;
		this.mTimeEnd = timeEnd;
		this.mMileageStart = mileageStart;
		this.mMileageEnd = mileageEnd;

		this.mProjectNumber = projectNumber;
		this.mWellName = wellName;
		this.mDescription = description;
		this.mLocation = location;
		this.mGPSCoords = gps;
		this.mBillingType = billingType;
	}

	public String getUserName() {
		return this.mUserName;
	}

	public String getTicketNumber() {
		return this.mTicketNumber;
	}

	public String getDateStart() {
		return this.mDateStart;
	}

	public String getDateEnd() {
		return this.mDateEnd;
	}

	public String getTimeStart() {
		return this.mTimeStart;
	}

	public String getTimeEnd() {
		return this.mTimeEnd;
	}

	public String getMileageStart() {
		return this.mMileageStart;
	}

	public String getMileageEnd() {
		return this.mMileageEnd;
	}

	public String getProjectNumber() {
		return this.mProjectNumber;
	}

	public String getWellName() {
		return this.mWellName;
	}

	public String getDescription() {
		return this.mDescription;
	}

	public String getLocation() {
		return this.mLocation;
	}

	public String getGPSCoords() {
		return this.mGPSCoords;
	}

	public String getBillingType() {
		return this.mBillingType;
	}

	public boolean getIsMobile() {
		return this.mIsTicketMobile;
	}

	/**
	 * FieldNoteBuilder is used to create a FieldNote object. This is accomplished
	 * by creating a new FieldNoteBuilder and calling all the set methods in order
	 * to populate the required fields
	 * 
	 * @author devHunter Jan 21, 2018
	 *
	 */

	public static class FieldNoteBuilder {

		private String mNewUserName;
		private String mNewTicketNumber;
		private String mNewDateStart;
		private String mNewTimeStart;
		private String mNewMileageStart;
		private String mNewDateEnd;
		private String mNewTimeEnd;
		private String mNewMileageEnd;

		private String mNewProjectNumber;
		private String mNewWellName;
		private String mNewDescription;
		private String mNewLocation;
		private String mNewGPSCoords;
		private String mNewBillingType;

		public FieldNoteBuilder() {
		}

		public FieldNoteBuilder setUserName(String newUserName) {
			this.mNewUserName = newUserName;
			return this;
		}

		public FieldNoteBuilder setTicketNumber(String newTicketNumber) {
			this.mNewTicketNumber = newTicketNumber;
			return this;
		}

		public FieldNoteBuilder setDateStart(String newDateStart) {
			this.mNewDateStart = newDateStart;
			return this;
		}

		public FieldNoteBuilder setDateEnd(String newDateEnd) {
			this.mNewDateEnd = newDateEnd;
			return this;
		}

		public FieldNoteBuilder setTimeStart(String newTimeStart) {
			this.mNewTimeStart = newTimeStart;
			return this;
		}

		public FieldNoteBuilder setTimeEnd(String newTimeEnd) {
			this.mNewTimeEnd = newTimeEnd;
			return this;
		}

		public FieldNoteBuilder setMileageStart(String newMileageStart) {
			this.mNewMileageStart = newMileageStart;
			return this;
		}

		public FieldNoteBuilder setMileageEnd(String newMileageEnd) {
			this.mNewMileageEnd = newMileageEnd;
			return this;
		}

		public FieldNoteBuilder setProjectNumber(String newProjectNumber) {
			this.mNewProjectNumber = newProjectNumber;
			return this;
		}

		public FieldNoteBuilder setWellName(String newWellName) {
			this.mNewWellName = newWellName;
			return this;
		}

		public FieldNoteBuilder setDescription(String newDescription) {
			this.mNewDescription = newDescription;
			return this;
		}

		public FieldNoteBuilder setLocation(String newLocation) {
			this.mNewLocation = newLocation;
			return this;
		}

		public FieldNoteBuilder setGPSCoords(String newGPSCoords) {
			this.mNewGPSCoords = newGPSCoords;
			return this;
		}

		public FieldNoteBuilder setBillingType(String newBillingType) {
			this.mNewBillingType = newBillingType;
			return this;
		}

		public FieldNote createFieldNote() {
			return new FieldNote(mNewUserName, mNewTicketNumber, mNewDateStart, mNewDateEnd, mNewTimeStart, mNewTimeEnd,
					mNewMileageStart, mNewMileageEnd, mNewProjectNumber, mNewWellName, mNewDescription, mNewLocation,
					mNewGPSCoords, mNewBillingType);
		}
		
		public FieldNote createEmptyFieldNote() {
			return new FieldNote(null, null , null, null, null, null, null, null, null, null, null, null, null, null);
		}
	}
}
