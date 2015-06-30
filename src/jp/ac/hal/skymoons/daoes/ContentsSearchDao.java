package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsEmployeeBean;
import jp.ac.hal.skymoons.beans.ContentsSearchBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsSearchDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsSearchDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsSearchDao(Connection con){
		this.con = con;
	}


	public ArrayList<ContentsSearchBean> findAll() throws SQLException{
		return null;
	}
	public ArrayList<ContentsEmployeeBean> findEmployee() throws SQLException{
		//戻り値のListを生成
		ArrayList<ContentsEmployeeBean> employeeList = new ArrayList<>();
		//コンテンツの取得
		PreparedStatement employeePst = con.prepareStatement("select * from employees, users where employees.employee_id = users.user_id order by users.first_name, users.last_name asc;");
		ResultSet employeeResult = employeePst.executeQuery();
		while (employeeResult.next()) {
			ContentsEmployeeBean employeeBean = new ContentsEmployeeBean();
			employeeBean.setEmployeeId(employeeResult.getString("employee_id"));
			employeeBean.setFirstName(employeeResult.getString("first_name"));
			employeeBean.setLastName(employeeResult.getString("last_name"));
			employeeList.add(employeeBean);
		}
		employeeResult.close();
		employeePst.close();
		return employeeList;
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

