package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class BatchBean implements Serializable {

	private int batchId;
	private String batchName;
	private String batchComment;

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

}
