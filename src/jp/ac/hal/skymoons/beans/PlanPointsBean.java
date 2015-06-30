package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class PlanPointsBean implements Serializable {

    private int planId;
    private int goodCount;
    private int badCount;

    public int getPlanId() {
	return planId;
    }

    public void setPlanId(int planId) {
	this.planId = planId;
    }

    public int getGoodCount() {
	return goodCount;
    }

    public void setGoodCount(int goodCount) {
	this.goodCount = goodCount;
    }

    public int getBadCount() {
	return badCount;
    }

    public void setBadCount(int badCount) {
	this.badCount = badCount;
    }

}
