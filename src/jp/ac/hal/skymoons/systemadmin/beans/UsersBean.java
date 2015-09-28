package jp.ac.hal.skymoons.systemadmin.beans;

/**
 * Users Table
 * @author Yamazaki Gen
 * @since 2015/09/28
 * @version 1.0
 */
public class UsersBean {

	String user_id = null;
	String password = null;
	String last_name = null;
	String first_name = null;
	int class_flag = -1;
	int delete_flag = -1;
	int lapse_flag = -1;
	String first_name_kana = null;
	String last_name_kana = null;
	
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
	public int getClass_flag() {
		return class_flag;
	}
	public void setClass_flag(int class_flag) {
		this.class_flag = class_flag;
	}
	public int getDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
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
	
}
