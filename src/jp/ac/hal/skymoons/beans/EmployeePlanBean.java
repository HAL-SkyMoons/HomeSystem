package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.Date;

public class EmployeePlanBean implements Serializable{
	private String planTitle;
	private Date days;

	public String getPlanTitle() {
		return planTitle;
	}
	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	public Date getDays() {
		return days;
	}
	public void setDays(Date days) {
		this.days = days;
	}
}
