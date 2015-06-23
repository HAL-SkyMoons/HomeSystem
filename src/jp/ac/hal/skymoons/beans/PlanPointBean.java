package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class PlanPointBean implements Serializable {

	private int planId;
	private String employeeId;
	private int point;

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

}
