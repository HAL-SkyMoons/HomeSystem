package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class BigGenreBean implements Serializable {

    private int bigGenreId;
    private String bigGenreName;



    public int getBigGenreId() {
        return bigGenreId;
    }

    public void setBigGenreId(int bigGenreId) {
	this.bigGenreId = bigGenreId;
    }

    public String getBigGenreName() {
	return bigGenreName;
    }

    public void setBigGenreName(String bigGenreName) {
	this.bigGenreName = bigGenreName;
    }

}
