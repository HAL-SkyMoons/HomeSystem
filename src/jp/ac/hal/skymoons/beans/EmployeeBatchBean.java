package jp.ac.hal.skymoons.beans;

public class EmployeeBatchBean {
	private String batchName;
	private String batchImgPath;
	private int batchCount;

	public String getBadgeName() {
		return batchName;
	}
	public void setBadgeName(String badgename) {
		this.batchName = badgename;
	}
	public String getBadgeImgPath() {
		return batchImgPath;
	}
	public void setBadgeImgPath(String badgeImgPath) {
		this.batchImgPath = badgeImgPath;
	}
	public int getBadgeCount() {
		return batchCount;
	}
	public void setBadgeCount(int badgeCount) {
		this.batchCount = badgeCount;
	}
}
