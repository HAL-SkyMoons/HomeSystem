package jp.ac.hal.skymoons.core;


import java.sql.SQLException;
import java.util.List;


import jp.ac.hal.skymoons.dao.MysqlDao;
import jp.ac.hal.skymoons.data.MysqlData;

public abstract class Core {
	private MysqlDao mysqlDao;
	private MysqlData mysqlData;
	private List<MysqlData> list;
	private String uri;

	public MysqlDao getMysqlDao() {
		return mysqlDao;
	}

	public void setMysqlDao(MysqlDao mysqlDao) {
		this.mysqlDao = mysqlDao;
	}

	public MysqlData getMysqlData() {
		return mysqlData;
	}

	public void setMysqlData(MysqlData mysqlData) {
		this.mysqlData = mysqlData;
	}

	public List<MysqlData> getList() {
		return list;
	}

	public void setList(List<MysqlData> list) {
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
