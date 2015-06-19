package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContentsGenreBean implements Serializable {
	private int bigGenreId;
	private String bigGenreName;
	private int genreId;
	private String genreName;
}
