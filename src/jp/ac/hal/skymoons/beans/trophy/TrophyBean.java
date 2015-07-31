package jp.ac.hal.skymoons.beans.trophy;

public class TrophyBean {
    private int trophyId;
    private String trophyName;
    private String trophyComment;
    private int deleteFlag;

    public int getTrophyId() {
	return trophyId;
    }

    public void setTrophyId(int trophyId) {
	this.trophyId = trophyId;
    }

    public String getTrophyName() {
	return trophyName;
    }

    public void setTrophyName(String trophyName) {
	this.trophyName = trophyName;
    }

    public String getTrophyComment() {
	return trophyComment;
    }

    public void setTrophyComment(String trophyComment) {
	this.trophyComment = trophyComment;
    }

    public int getDeleteFlag() {
	return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
	this.deleteFlag = deleteFlag;
    }

}
