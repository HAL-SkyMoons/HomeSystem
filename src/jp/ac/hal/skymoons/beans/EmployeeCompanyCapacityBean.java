package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class EmployeeCompanyCapacityBean implements Serializable{
	private String capacityName;
	private String capacityDate;

	public String getCapacityName() {
		return capacityName;
	}

	public void setCapacityName(String capasictyName) {
		this.capacityName = capasictyName;
	}

	public String getCapacityDate() {
		return capacityDate;
	}

	public void setCapacityDate(String capacityDate) {
		this.capacityDate = capacityDate;
	}


}
