package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.Date;

public class PlanBean implements Serializable {

	private int planId;
	private String planTitle;
	private String planner;
	private String plannerName;
	private Date planDatetime;
	private String planComment;
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
	public String getPlanComment() {
		return planComment;
	}
	public void setPlanComment(String planComment) {
		this.planComment = planComment;
	}




}
