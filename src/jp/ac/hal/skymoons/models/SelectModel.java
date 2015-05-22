package jp.ac.hal.skymoons.models;

import java.sql.SQLException;
import jp.ac.hal.skymoons.core.Core;

public class SelectModel extends Core {
	private String sql;
	SelectModel(String sql){
		setSql(sql);
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	@Override
	public void executeSQL() throws SQLException{
		setList(getMysqlDao().select(getSql(),getUri()));
		return;
	}
}
