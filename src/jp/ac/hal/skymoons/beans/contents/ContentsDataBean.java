package jp.ac.hal.skymoons.beans.contents;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentsDataBean implements Serializable {
	private int homeContentId;
	private int homeDataNo;
	private String homeDataName;
	private int deleteFlag;
	private String fileImagePath;
	public int getHomeContentId() {
		return homeContentId;
	}
	public void setHomeContentId(int homeContentId) {
		this.homeContentId = homeContentId;
	}
	public int getHomeDataNo() {
		return homeDataNo;
	}
	public void setHomeDataNo(int homeDataNo) {
		this.homeDataNo = homeDataNo;
	}
	public String getHomeDataName() {
		return homeDataName;
	}
	public void setHomeDataName(String homeDataName) {
		this.homeDataName = homeDataName;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getFileImagePath() {
		return fileImagePath;
	}
	public void setFileImagePath(String fileImagePath) {
		this.fileImagePath = fileImagePath;
	}
	
}
