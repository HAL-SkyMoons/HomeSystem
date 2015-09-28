package jp.ac.hal.skymoons.systemadmin.beans;

/**
 * Employees Table
 * @author Yamazaki Gen
 * @since 2015/09/28
 * @version 1.0
 */
public class EmployeesBean {

	String employee_id = null;
	String comment = null;
	int level = -1;
	int experience = -1;
	int delete_flag = -1;
	int department_id = -1;
	
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public int getDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	
}
