package jp.ac.hal.skymoons.systemadmin.beans;

/**
 * 社員ユーザ情報
 * @author YAMAZAKI GEN
 * @since 2015/09/08
 * @version 1.0
 */
public class StaffBean {

	private String user_id = null;
	private String password = null;
	private String last_name = null;
	private String first_name = null;
	private int lapse_flag = -1;
	private String first_name_kana = null;
	private String last_name_kana = null;
	private String comment = null;
	private int level = -1;
	private int experience = -1;
	private int department_ID = -1;
	private String department_name = null;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public int getLapse_flag() {
		return lapse_flag;
	}
	public void setLapse_flag(int lapse_flag) {
		this.lapse_flag = lapse_flag;
	}
	public String getFirst_name_kana() {
		return first_name_kana;
	}
	public void setFirst_name_kana(String first_name_kana) {
		this.first_name_kana = first_name_kana;
	}
	public String getLast_name_kana() {
		return last_name_kana;
	}
	public void setLast_name_kana(String last_name_kana) {
		this.last_name_kana = last_name_kana;
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
	public int getDepartment_ID() {
		return department_ID;
	}
	public void setDepartment_ID(int department_ID) {
		this.department_ID = department_ID;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
}
