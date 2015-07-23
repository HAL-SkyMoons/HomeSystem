package jp.ac.hal.skymoons.beans.trophy;

public class TrophyDetailBean {
    private int trophyId;
    private int batchId;
    private String batchName;
    private int typeCount;

    public int getTrophyId() {
	return trophyId;
    }

    public void setTrophyId(int trophyId) {
	this.trophyId = trophyId;
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
