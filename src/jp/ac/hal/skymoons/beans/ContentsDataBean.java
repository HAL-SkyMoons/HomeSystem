package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContentsDataBean implements Serializable {
	private int homeContentId;
	private int homeDataNo;
	private String homeDataName;
	private int deleteFlag;
	private String fileImagePath;
}
