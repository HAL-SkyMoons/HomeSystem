package jp.ac.hal.skymoons.systemadmin.beans;

/**
 * Departments Table
 * @author Yamazaki Gen
 * @since 2015/09/28
 * @version 1.0
 */
public class DepartmentsBean {

	int department_id = -1;
	String department_name = null;
	
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	
}
