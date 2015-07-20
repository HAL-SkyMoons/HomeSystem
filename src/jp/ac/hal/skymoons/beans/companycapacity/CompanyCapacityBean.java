package jp.ac.hal.skymoons.beans.companycapacity;

public class CompanyCapacityBean {
    private int capacityId;
    private String capacityName;
    private String capacityComment;
    private int deleteFlag;

    public int getCapacityId() {
	return capacityId;
    }

    public void setCapacityId(int capacityId) {
	this.capacityId = capacityId;
    }

    public String getCapacityName() {
	return capacityName;
    }

    public void setCapacityName(String capacityName) {
	this.capacityName = capacityName;
    }

    public String getCapacityComment() {
	return capacityComment;
    }

    public void setCapacityComment(String capacityComment) {
	this.capacityComment = capacityComment;
    }

    public int getDeleteFlag() {
	return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
	this.deleteFlag = deleteFlag;
    }

}
