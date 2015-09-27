package jp.ac.hal.skymoons.systemadmin.beans;

/**
 * 管理者ユーザ情報
 * @author YAMAZAKI GEN
 * @since 2015/09/27
 * @version 1.0
 */
public class AdministratorBean {

	private String	administrator_id = null;
	private String	password = null;
	private String	first_name = null;
	private String	last_name = null;
	private int		delete_flag = -1;
	private int		lapse_flag = -1;
	private String	huri_first_name = null;
	private String	huri_last_name = null;
	
	public String getAdministrator_id() {
		return administrator_id;
	}
	public void setAdministrator_id(String administrator_id) {
		this.administrator_id = administrator_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	public String getHuri_first_name() {
		return huri_first_name;
	}
	public void setHuri_first_name(String huri_first_name) {
		this.huri_first_name = huri_first_name;
	}
	public String getHuri_last_name() {
		return huri_last_name;
	}
	public void setHuri_last_name(String huri_last_name) {
		this.huri_last_name = huri_last_name;
	}
	
}
