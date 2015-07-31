package jp.ac.hal.skymoons.beans.contents;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentsRegistBean implements Serializable {
	private int homeContentId;
	private String homeContentTitle;
	private String homeContentComment;
	private String startYear;
	private String startMonth;
	private String startDay;
	private String startHour;
	private String startMinute;
	private String endYear;
	private String endMonth;
	private String endDay;
	private String endHour;
	private String endMinute;
	private String employeeId;
	private String firstName;
	private String lastName;
	private ArrayList<Integer> bigGenreId;
	private ArrayList<String> bigGenreName;
	private ArrayList<Integer> genreId;
	private ArrayList<String> genreName;
	private ArrayList<Integer> homeDataNo;
	private ArrayList<String> homeDataName;
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
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getStartMinute() {
		return startMinute;
	}
	public void setStartMinute(String startMinute) {
		this.startMinute = startMinute;
	}
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getEndMinute() {
		return endMinute;
	}
	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
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
	public ArrayList<Integer> getHomeDataNo() {
		return homeDataNo;
	}
	public void setHomeDataNo(ArrayList<Integer> homeDataNo) {
		this.homeDataNo = homeDataNo;
	}
	public ArrayList<String> getHomeDataName() {
		return homeDataName;
	}
	public void setHomeDataName(ArrayList<String> homeDataName) {
		this.homeDataName = homeDataName;
	}
	
}