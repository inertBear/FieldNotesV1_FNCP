/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * 
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model;

public class FieldNoteUser {
	
	private String mUserName;
	//TODO: convert this to Char array
	private String mPassword; 
	//TODO: implement user attributes
	//			address,
	//			title..... and so on
	private boolean mIsAdmin;
	//TODO: implement admin privledges for specific users 
	//			admins can grant admin access to other users,
	//			can change passwords for other users,
	//			can add users,
	//			can delete users,
	//			??see passwords when searching users??,	
	//			can delete FieldNotes,
	
	public FieldNoteUser(String userName, String password) {
		this.mUserName = userName;
		this.mPassword = password;
	}
	
	public String getUserName() {
		return mUserName;
	}
	
	public String getPassword() {
		return mPassword;
	}
	
//	public void setUserName(String userName) {
//		this.mUserName = userName;
//	}
//	
//	public void setPassword(String password) {
//		this.mPassword = password;
//	}
	
	public void setIsAdmin(boolean value) {
		this.mIsAdmin = value;
	}
	
	public static class FieldNoteUserBuilder {
		
		private String mNewUserName;
		private String mNewPassword;
		private boolean mIsNewAdmin;
		
		public FieldNoteUserBuilder() {
		}

		public FieldNoteUserBuilder setUserName(String newUserName) {
			this.mNewUserName = newUserName;
			return this;
		}
		
		public FieldNoteUserBuilder setPassword(String newPassword) {
			this.mNewPassword = newPassword;
			return this;
		}
		
		public FieldNoteUserBuilder setIsAdmin(boolean value) {
			this.mIsNewAdmin = value;
			return this;
		}
		
		public FieldNoteUser createUser() {
			return new FieldNoteUser(mNewUserName, mNewPassword);
		}
		
		public FieldNoteUser createEmptyUser() {
			return new FieldNoteUser(null, null);
		}
	}
}
