package jp.ac.hal.skymoons.beans;

public class EmployeeBadgeBean {
	private String badgeName;
	private String badgeImgPath;
	private int badgeCount;

	public String getBadgeName() {
		return badgeName;
	}
	public void setBadgeName(String badgename) {
		this.badgeName = badgename;
	}
	public String getBadgeImgPath() {
		return badgeImgPath;
	}
	public void setBadgeImgPath(String badgeImgPath) {
		this.badgeImgPath = badgeImgPath;
	}
	public int getBadgeCount() {
		return badgeCount;
	}
	public void setBadgeCount(int badgeCount) {
		this.badgeCount = badgeCount;
	}
}
