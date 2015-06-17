package jp.ac.hal.skymoons.beans;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContentsEmployeeBean implements Serializable {
	private String employeeId;
	private String firstName;
	private String lastName;
}
