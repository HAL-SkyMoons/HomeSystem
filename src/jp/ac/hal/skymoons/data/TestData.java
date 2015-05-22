package jp.ac.hal.skymoons.data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestData extends MysqlData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String aId;
	private String bId;
	private int cId;
	
	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	@Override
	public ArrayList<String> getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRequestValues(ArrayList<String> values) {
		setaId(values.get(0));
		setbId(values.get(1));
		setcId(Integer.parseInt(values.get(2)));
	}

	@Override
	public void setResultValues(ResultSet result) throws SQLException {
		setaId(result.getString("employee_id"));
		setbId(result.getString("authority_name"));
		setcId(result.getInt("authority_id"));
	}

}