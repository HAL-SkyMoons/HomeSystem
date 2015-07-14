package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class EmployeePageBean implements Serializable{
	private String employeeId;
	private String employeeName;
	private String departmentName;
	private String employeeComment;
	private int level;
	private int experience;

	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmployeeComment() {
		return employeeComment;
	}
	public void setEmployeeComment(String employeeComment) {
		this.employeeComment = employeeComment;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}

}
