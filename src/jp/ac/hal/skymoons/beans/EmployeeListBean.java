package jp.ac.hal.skymoons.beans;

/*
 * 2015/05/26
 * 中野 裕史郎
 * 社員一覧用のBean
 * 社員ID/顔写真ファイルパス/部署名
 */

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeListBean implements Serializable{

	private String employeeId;
	private String employeeName;
	private String photoSrc;
	private String departmentName;
	private ArrayList<String> employeeGenre;


	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeID) {
		this.employeeId = employeeID;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getPhotoSrc() {
		return photoSrc;
	}
	public void setPhotoSrc(String photoSrc) {
		this.photoSrc = photoSrc;
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public ArrayList<String> getEmployeeGenre() {
		return employeeGenre;
	}
	public void setEmployeeGenre(ArrayList<String> employeeGenre) {
		this.employeeGenre = employeeGenre;
	}

}
