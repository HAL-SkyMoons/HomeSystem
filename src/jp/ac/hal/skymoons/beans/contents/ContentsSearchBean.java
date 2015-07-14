package jp.ac.hal.skymoons.beans.contents;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentsSearchBean implements Serializable {
	private int homeContentId;
	private String homeContentTitle;
	private String homeContentComment;
	private String startDatetime;
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
	public String getStartDatetime() {
		return startDatetime;
	}
	public void setStartDatetime(String startDatetime) {
		this.startDatetime = startDatetime;
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