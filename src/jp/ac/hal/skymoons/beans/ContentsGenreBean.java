package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentsGenreBean implements Serializable {
	private int bigGenreId;
	private String bigGenreName;
	private int genreId;
	private String genreName;
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
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
}
