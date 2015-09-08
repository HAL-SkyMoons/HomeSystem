package jp.ac.hal.skymoons.beans.contents;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentsDetailBean implements Serializable {
	private String userId;
	private Integer planId;
	private int homeContentId;
	private String homeContentTitle;
	private String homeContentComment;
	private String planDatetime;
	private String startDatetime;
	private String endDatetime;
	private String employeeId;
	private String firstName;
	private String lastName;
	private int level;
	private ArrayList<Integer> bigGenreId;
	private ArrayList<String> bigGenreName;
	private ArrayList<Integer> genreId;
	private ArrayList<String> genreName;
	//private ArrayList<Integer> homeDataNo;
	//private ArrayList<String> homeDataName;
	private int deleteFlag;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public int getHomeContentId() {
		return homeContentId;
	}
	public void setHomeContentId(int homeContentId) {
		this.homeContentId = homeContentId;
	}
	public String getHomeContentTitle() {
		return homeContentTitle;
	}
	public void setHomeContentTitle(String homeContentTitle) {
		this.homeContentTitle = homeContentTitle;
	}
	public String getHomeContentComment() {
		return homeContentComment;
	}
	public void setHomeContentComment(String homeContentComment) {
		this.homeContentComment = homeContentComment;
	}
	public String getPlanDatetime() {
		return planDatetime;
	}
	public void setPlanDatetime(String planDatetime) {
		this.planDatetime = planDatetime;
	}
	public String getStartDatetime() {
		return startDatetime;
	}
	public void setStartDatetime(String startDatetime) {
		this.startDatetime = startDatetime;
	}
	public String getEndDatetime() {
		return endDatetime;
	}
	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public ArrayList<Integer> getBigGenreId() {
		return bigGenreId;
	}
	public void setBigGenreId(ArrayList<Integer> bigGenreId) {
		this.bigGenreId = bigGenreId;
	}
	public ArrayList<String> getBigGenreName() {
		return bigGenreName;
	}
	public void setBigGenreName(ArrayList<String> bigGenreName) {
		this.bigGenreName = bigGenreName;
	}
	public ArrayList<Integer> getGenreId() {
		return genreId;
	}
	public void setGenreId(ArrayList<Integer> genreId) {
		this.genreId = genreId;
	}
	public ArrayList<String> getGenreName() {
		return genreName;
	}
	public void setGenreName(ArrayList<String> genreName) {
		this.genreName = genreName;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
