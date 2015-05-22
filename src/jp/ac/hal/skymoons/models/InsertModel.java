package jp.ac.hal.skymoons.models;

import java.sql.SQLException;
import jp.ac.hal.skymoons.core.Core;

public class InsertModel extends Core {
	private String sql;
	InsertModel(String sql){
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
		getOracleDao().insert(getOracleData(),getSql());
		return;
	}
}
