package jp.ac.hal.skymoons.beans;

/**
 * 顧客情報テストBean。
 * @author Yamazaki Gen
 * @since 2015/06/09
 * @version 1.0
 */
public class CustomerTestBean {
	private String	customer_id =		null;
	private String	customer_company =	null;
	private String	user_id =			null;
	private String	password =			null;
	private String	last_name =			null;
	private String	first_name =		null;
	private int		class_flag =		-1;
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_company() {
		return customer_company;
	}
	public void setCustomer_company(String customer_company) {
		this.customer_company = customer_company;
	}
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
}
