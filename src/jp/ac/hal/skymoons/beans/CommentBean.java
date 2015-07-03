package jp.ac.hal.skymoons.beans;

import java.util.Date;

public class CommentBean {

	private int planID;
	private int commentNo;
	private String commentUser;
	private String commentName;;
	private Date commentDatetime;
	private int deleteFlag;
	private String comment;

	public int getPlanID() {
		return planID;
	}

	public void setPlanID(int planID) {
		this.planID = planID;
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public String getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}

	public String getCommentName() {
		return commentName;
	}

	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}

	public Date getCommentDatetime() {
		return commentDatetime;
	}

	public void setCommentDatetime(Date commentDatetime) {
		this.commentDatetime = commentDatetime;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
