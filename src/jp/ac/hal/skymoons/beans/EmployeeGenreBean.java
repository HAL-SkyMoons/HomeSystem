package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

public class EmployeeGenreBean implements Serializable{
	private String genreName;
	private int genreCount;

	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public int getGenreCount() {
		return genreCount;
	}
	public void setGenreCount(int genreCount) {
		this.genreCount = genreCount;
	}
}
