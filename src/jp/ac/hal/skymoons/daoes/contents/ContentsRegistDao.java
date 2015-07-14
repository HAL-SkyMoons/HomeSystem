package jp.ac.hal.skymoons.daoes.contents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.contents.ContentsGenreBean;
import jp.ac.hal.skymoons.beans.contents.ContentsRegistBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsRegistDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsRegistDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsRegistDao(Connection con){
		this.con = con;
	}

	public ContentsRegistBean findEmployee(String userId) throws SQLException{
		//コンテンツの取得
		ContentsRegistBean registBean = new ContentsRegistBean();
		PreparedStatement employeePst = con.prepareStatement("select last_name, first_name from users where user_id = ?");
		employeePst.setString(1, userId);
		ResultSet employeeResult = employeePst.executeQuery();
		registBean.setEmployeeId(userId);
		if (employeeResult.next()) {
			registBean.setLastName(employeeResult.getString("last_name"));
			registBean.setFirstName(employeeResult.getString("first_name"));
		}
		employeeResult.close();
		employeePst.close();
		return registBean;
	}
	/**
	 * 接続を閉じる
	 *
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		con.close();
	}

	/**
	 * コミット
	 *
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		con.commit();
	}

	/**
	 * ロールバック
	 *
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		con.rollback();
	}
}

