package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class ContentsSearchBean implements Serializable {

	private int homeContentId;
	private String homeContentTitle;
	private String homeContentDatetime;
	private int bigGenreId;
	private String bigGenreName;
	private int genreId;
	private String genreName;
	private int employeeId;
	private String firstName;
	private String lastName;
	
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
	public String getHomeContentDatetime() {
		return homeContentDatetime;
	}
	public void setHomeContentDatetime(String homeContentDatetime) {
		this.homeContentDatetime = homeContentDatetime;
	}
	public int getBigGenreId() {
		return bigGenreId;
	}
	public void setBigGenreId(int bigGenreId) {
		this.bigGenreId = bigGenreId;
	}
	public String getBigGenreName() {
		return bigGenreName;
	}
	public void setBigGenreName(String bigGenreName) {
		this.bigGenreName = bigGenreName;
	}
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
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
	
	
	
}