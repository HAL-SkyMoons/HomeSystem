package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContentsEditBean implements Serializable {
	private int homeContentId;
	private String homeContentTitle;
	private String homeContentComment;
	private String homeContentDatetime;
	private ArrayList<Integer> bigGenreId;
	private ArrayList<String> bigGenreName;
	private ArrayList<Integer> genreId;
	private ArrayList<String> genreName;
	private int employeeId;
	private String firstName;
	private String lastName;
}
