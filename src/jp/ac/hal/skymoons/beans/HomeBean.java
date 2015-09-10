package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.Date;

public class HomeBean implements Serializable {

	private String toUserId;
	private String fromUserId;
	private Date homeDatetime;
	private int batchId;
	private int homePoint;
	private String comment;
	private int contentsId;
	private int level;

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Date getHomeDatetime() {
		return homeDatetime;
	}

	public void setHomeDatetime(Date homeDatetime) {
		this.homeDatetime = homeDatetime;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public int getHomePoint() {
		return homePoint;
	}

	public void setHomePoint(int homePoint) {
		this.homePoint = homePoint;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getContentsId() {
		return contentsId;
	}

	public void setContentsId(int contentsId) {
		this.contentsId = contentsId;
	}

	public int getLevel() {
	    return level;
	}

	public void setLevel(int level) {
	    this.level = level;
	}

}
