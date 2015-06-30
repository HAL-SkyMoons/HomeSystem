package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentsEmployeeBean implements Serializable {
	private String employeeId;
	private String firstName;
	private String lastName;
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
