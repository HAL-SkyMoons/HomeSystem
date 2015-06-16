package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class FileBean implements Serializable {

	private int planId;
	private int commentNo;
	private int dataNo;
	private String dataName;

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getDataNo() {
		return dataNo;
	}

	public void setDataNo(int dataNo) {
		this.dataNo = dataNo;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

}
