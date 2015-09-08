package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.Date;

public class PlanBean implements Serializable {

    private int planId;
    private String planTitle;
    private String planner;
    private String plannerName;
    private Date planDatetime;
    private Date startDate;
    private Date endDate;
    private String planComment;
    private int executeFlag;

    private int commentCount;
    private int pointCount;

    public int getCommentCount() {
	return commentCount;
    }

    public void setCommentCount(int commentCount) {
	this.commentCount = commentCount;
    }

    public int getPointCount() {
	return pointCount;
    }

    public void setPointCount(int pointCount) {
	this.pointCount = pointCount;
    }

    public int getPlanId() {
	return planId;
    }

    public void setPlanId(int planId) {
	this.planId = planId;
    }

    public String getPlanTitle() {
	return planTitle;
    }

    public void setPlanTitle(String planTitle) {
	this.planTitle = planTitle;
    }

    public String getPlanner() {
	return planner;
    }

    public void setPlanner(String planner) {
	this.planner = planner;
    }

    public String getPlannerName() {
	return plannerName;
    }

    public void setPlannerName(String plannerName) {
	this.plannerName = plannerName;
    }

    public Date getPlanDatetime() {
	return planDatetime;
    }

    public void setPlanDatetime(Date planDatetime) {
	this.planDatetime = planDatetime;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public String getPlanComment() {
	return planComment;
    }

    public void setPlanComment(String planComment) {
	this.planComment = planComment;
    }

    public int getExecuteFlag() {
	return executeFlag;
    }

    public void setExecuteFlag(int executeFlag) {
	this.executeFlag = executeFlag;
    };

}
