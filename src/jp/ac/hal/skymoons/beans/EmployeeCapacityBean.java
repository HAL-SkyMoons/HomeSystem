package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class EmployeeCapacityBean implements Serializable{
	private String capacityName;

	public String getCapacityName() {
		return capacityName;
	}
	public void setCapacityName(String capacityName) {
		this.capacityName = capacityName;
	}
}
