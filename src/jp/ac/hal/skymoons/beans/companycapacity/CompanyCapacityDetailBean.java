package jp.ac.hal.skymoons.beans.companycapacity;

public class CompanyCapacityDetailBean {
    private int capacityId;
    private String batchName;
    private int batchId;
    private int typeCount;

    public int getCapacityId() {
	return capacityId;
    }

    public void setCapacityId(int capacityId) {
	this.capacityId = capacityId;
    }

    public int getBatchId() {
	return batchId;
    }

    public void setBatchId(int batchId) {
	this.batchId = batchId;
    }

    public int getTypeCount() {
	return typeCount;
    }

    public void setTypeCount(int typeCount) {
	this.typeCount = typeCount;
    }

    public String getBatchName() {
	return batchName;
    }

    public void setBatchName(String batchName) {
	this.batchName = batchName;
    }

}
