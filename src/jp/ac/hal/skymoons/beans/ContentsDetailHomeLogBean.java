package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContentsDetailHomeLogBean implements Serializable {
	private int homeContentId;
	private String homeTarget;
	private String homeTargetFirstName;
	private String homeTargetLastName;
	private String homeUser;
	private String homeUserFirstName;
	private String homeUserLastName;
	private String homeDatetime;
	private int batchId;
	private String batchName;
	private String batchComment;
	private int homePoint;
	private String homeComment;
	
}
