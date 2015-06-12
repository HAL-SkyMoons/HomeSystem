package jp.ac.hal.skymoons.beans;

import java.io.Serializable;

/*
 * 2015/06/09
 * 中野 裕史郎
 * 社員検索の為のジャンル取得Bean
 * ジャンルID/ジャンル名
 */
public class GenreBean implements Serializable{

	private int genreId;
	private String genreName;
	private int bigGenreId;

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
	public int getBigGenreId() {
		return bigGenreId;
	}
	public void setBigGenreId(int bigGenreId) {
		this.bigGenreId = bigGenreId;
	}

}
