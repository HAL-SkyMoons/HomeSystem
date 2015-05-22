package jp.ac.hal.skymoons.core;


import java.sql.SQLException;
import java.util.List;


import jp.ac.hal.skymoons.dao.OracleDao;
import jp.ac.hal.skymoons.data.OracleData;

public abstract class Core {
	private OracleDao oracleDao;
	private OracleData oracleData;
	private List<OracleData> list;
	private String uri;

	public OracleDao getOracleDao() {
		return oracleDao;
	}
	public void setOracleDao(OracleDao oracleDao) {
		this.oracleDao = oracleDao;
	}
	public OracleData getOracleData() {
		return oracleData;
	}
	public void setOracleData(OracleData oracleData) {
		this.oracleData = oracleData;
	}
	public List<OracleData> getList() {
		return list;
	}
	public void setList(List<OracleData> list) {
		this.list = list;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	abstract public void executeSQL() throws SQLException;
}
