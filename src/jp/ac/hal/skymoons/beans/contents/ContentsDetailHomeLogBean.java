package jp.ac.hal.skymoons.beans.contents;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentsDetailHomeLogBean implements Serializable {
	private int homeContentId;
	private String homeTarget;
	private String homeTargetFirstName;
	private String homeTargetLastName;
	private String homeUser;
	private String homeUserFirstName;
	private String homeUserLastName;
	private String homeDatetime;
	private int batchId;
	private String batchName;
	private String batchComment;
	private int homePoint;
	private String homeComment;
	public int getHomeContentId() {
		return homeContentId;
	}
	public void setHomeContentId(int homeContentId) {
		this.homeContentId = homeContentId;
	}
	public String getHomeTarget() {
		return homeTarget;
	}
	public void setHomeTarget(String homeTarget) {
		this.homeTarget = homeTarget;
	}
	public String getHomeTargetFirstName() {
		return homeTargetFirstName;
	}
	public void setHomeTargetFirstName(String homeTargetFirstName) {
		this.homeTargetFirstName = homeTargetFirstName;
	}
	public String getHomeTargetLastName() {
		return homeTargetLastName;
	}
	public void setHomeTargetLastName(String homeTargetLastName) {
		this.homeTargetLastName = homeTargetLastName;
	}
	public String getHomeUser() {
		return homeUser;
	}
	public void setHomeUser(String homeUser) {
		this.homeUser = homeUser;
	}
	public String getHomeUserFirstName() {
		return homeUserFirstName;
	}
	public void setHomeUserFirstName(String homeUserFirstName) {
		this.homeUserFirstName = homeUserFirstName;
	}
	public String getHomeUserLastName() {
		return homeUserLastName;
	}
	public void setHomeUserLastName(String homeUserLastName) {
		this.homeUserLastName = homeUserLastName;
	}
	public String getHomeDatetime() {
		return homeDatetime;
	}
	public void setHomeDatetime(String homeDatetime) {
		this.homeDatetime = homeDatetime;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getBatchComment() {
		return batchComment;
	}
	public void setBatchComment(String batchComment) {
		this.batchComment = batchComment;
	}
	public int getHomePoint() {
		return homePoint;
	}
	public void setHomePoint(int homePoint) {
		this.homePoint = homePoint;
	}
	public String getHomeComment() {
		return homeComment;
	}
	public void setHomeComment(String homeComment) {
		this.homeComment = homeComment;
	}
	
}
