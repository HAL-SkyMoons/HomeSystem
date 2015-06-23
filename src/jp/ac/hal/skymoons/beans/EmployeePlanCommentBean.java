package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.Date;

public class EmployeePlanCommentBean implements Serializable{

	private String planName;
	private String plannerName;
	private Date days;

	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlannerName() {
		return plannerName;
	}
	public void setPlannerName(String plannerName) {
		this.plannerName = plannerName;
	}
	public Date getDays() {
		return days;
	}
	public void setDays(Date days) {
		this.days = days;
	}
}
