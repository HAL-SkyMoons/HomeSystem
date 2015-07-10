package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class EmployeeTrophyBean implements Serializable{
	private String trophyName;
	private int trophyCount;
	private String trophyImg;

	public String getTrophyName() {
		return trophyName;
	}
	public void setTrophyName(String trophyName) {
		this.trophyName = trophyName;
	}
	public int getTrophyCount() {
		return trophyCount;
	}
	public void setTrophyCount(int trophyCount) {
		this.trophyCount = trophyCount;
	}
	public String getTrophyImg() {
		return trophyImg;
	}
	public void setTrophyImg(String trophyImg) {
		this.trophyImg = trophyImg;
	}
}
