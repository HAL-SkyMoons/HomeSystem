package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class UserBean implements Serializable {

	private String userId;
	private String password;
	private String lastName;
	private String firstName;
	private int classFlag;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getClassFlag() {
		return classFlag;
	}

	public void setClassFlag(int classFlag) {
		this.classFlag = classFlag;
	}

}
