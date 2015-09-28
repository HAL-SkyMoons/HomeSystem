package jp.ac.hal.skymoons.systemadmin.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.hal.skymoons.systemadmin.beans.AdministratorBean;

/**
 * 管理者ユーザ情報のデータベース操作機能
 * @author YAMAZAKI GEN
 * @since 2015/09/27
 * @version 1.0
 */
public class AdministratorDAO {
	
	private Connection connection = null;

	/**
	 * JNDIを利用してデータベースコネクションを取得します。
	 * @throws Exception
	 */
	public AdministratorDAO() throws Exception {
		jp.ac.hal.skymoons.controllers.ConnectionGet connectionGet
			= new jp.ac.hal.skymoons.controllers.ConnectionGet();
		this.connection = connectionGet.getCon();
	}
	
	/**
	 * 指定のデータベースコネクションを利用します。
	 * @param connection
	 */
	public AdministratorDAO(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * データベースコネクションの接続状況を取得します。
	 * @return
	 * TRUE:接続済<br />
	 * FALSE:未接続
	 */
	public boolean getConnectionStatus() {
		if(this.connection == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * データベースコネクションをクローズします。
	 */
	public void connectionClose() {
		if(this.connection != null) {
			try {
				if(this.connection.isClosed() == false) {
					this.connection.close();
				} else {
					System.out.println("ERROR:コネクションは既にクローズされています。");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR:コネクションのクローズ処理が失敗しました。");
			}
		} else {
			System.out.println("ERROR:コネクションが確立されていないのでクローズ処理を実行出来ません。");
		}
	}
	
	/**
	 * トランザクションを開始します。
	 * @throws SQLException
	 */
	public void startTransaction() throws SQLException {
		this.connection.setAutoCommit(false);
	}
	
	/**
	 * トランザクションをコミットします。
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		this.connection.commit();
	}
	
	/**
	 * トランザクションをロールバックします。
	 * @throws SQLException 
	 */
	public void rollback() throws SQLException {
		this.connection.rollback();
	}
	
	/**
	 * トランザクションを終了します。
	 * @throws SQLException
	 */
	public void endTransaction() throws SQLException {
		this.connection.setAutoCommit(true);
	}
	
	// ==========================================================================================
	
	/**
	 * 管理者ユーザリストを取得する。
	 * @param where
	 * 条件
	 * @return
	 * 管理者ユーザリスト
	 * @throws SQLException 
	 */
	public List<AdministratorBean> getList(String[] where) throws SQLException {
		List<AdministratorBean> result = new ArrayList<AdministratorBean>();
		String sql ="SELECT * FROM administrators";
		
		// 条件指定
		if(where != null) {
			sql += " WHERE";
			for(int i = 0; i < where.length; i++) {
				if (i > 0) {
					sql += " AND";
				}
				sql += " CONCAT(first_name, last_name) LIKE '%" + where[i] + "%'";
				sql += " OR CONCAT(huri_first_name, huri_last_name) LIKE '%" + where[i] + "%'";
			}
		}
		
		System.out.println("SQL:" + sql);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			AdministratorBean record = new AdministratorBean();
			record.setAdministrator_id(resultSet.getString("administrator_ID"));
			record.setPassword(resultSet.getString("password"));
			record.setFirst_name(resultSet.getString("first_name"));
			record.setLast_name(resultSet.getString("last_name"));
			record.setDelete_flag(resultSet.getInt("delete_flag"));
			record.setLapse_flag(resultSet.getInt("lapse_flag"));
			record.setHuri_first_name(resultSet.getString("huri_first_name"));
			record.setHuri_last_name(resultSet.getString("huri_last_name"));
			result.add(record);
		}
		
		return result;
	}
	
	/**
	 * 管理者ユーザ詳細を取得する。
	 * @param administrator_id
	 * 管理者ユーザID
	 * @return
	 * 管理者ユーザ詳細
	 * @throws SQLException 
	 */
	public AdministratorBean getDetail(String administrator_id) throws SQLException {
		String sql = "SELECT * FROM administrators WHERE administrator_id = ?";
		PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
		preparedStatement.setString(1, administrator_id);
		ResultSet resultSet = preparedStatement.executeQuery();
		AdministratorBean result = null;
		if(resultSet.next()) {
			result = new AdministratorBean();
			result.setAdministrator_id(resultSet.getString("administrator_ID"));
			result.setPassword(resultSet.getString("password"));
			result.setFirst_name(resultSet.getString("first_name"));
			result.setLast_name(resultSet.getString("last_name"));
			result.setDelete_flag(resultSet.getInt("delete_flag"));
			result.setLapse_flag(resultSet.getInt("lapse_flag"));
			result.setHuri_first_name(resultSet.getString("huri_first_name"));
			result.setHuri_last_name(resultSet.getString("huri_last_name"));
		}
		return result;
	}

}
