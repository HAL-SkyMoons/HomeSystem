package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.Date;

public class EmployeeHomeLogBean implements Serializable{
	private String targetName;
	private Date days;
	private String employeeId;


	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public Date getDays() {
		return days;
	}
	public void setDays(Date days) {
		this.days = days;
	}
}
