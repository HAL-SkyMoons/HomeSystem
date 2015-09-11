package jp.ac.hal.skymoons.systemadmin.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.hal.skymoons.systemadmin.beans.StaffBean;

/**
 * 社員ユーザ情報のデータベース操作機能
 * @author YAMAZAKI GEN
 * @since 2015/09/08
 * @version 1.0
 */
public class StaffDAO {
	
	private Connection connection = null;
	
	/**
	 * JNDIを利用してデータベースコネクションを取得します。
	 * @throws Exception
	 */
	public StaffDAO() throws Exception {
		jp.ac.hal.skymoons.controllers.ConnectionGet connectionGet
			= new jp.ac.hal.skymoons.controllers.ConnectionGet();
		this.connection = connectionGet.getCon();
	}
	
	/**
	 * 指定のデータベースコネクションを利用します。
	 * @param connection
	 */
	public StaffDAO(Connection connection) {
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
					System.out.println("コネクションは既にクローズされています。");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR:コネクションのクローズ処理が失敗しました。");
			}
		} else {
			System.out.println("コネクションが確立されていないのでクローズ処理を実行出来ません。");
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
	 * 社員リストを取得する。
	 * @return
	 * 社員ユーザ一覧画面用の社員ユーザリスト
	 * @throws SQLException 
	 */
	public List<StaffBean> getList(String[] where) throws SQLException {
		List<StaffBean> result = new ArrayList<StaffBean>();
		String sql = "SELECT u.user_id, u.password, u.last_name, u.first_name, u.lapse_flag, u.first_name_kana, u.last_name_kana, e.comment, e.level, e.experience, d.department_ID, d.department_name"
			+ " FROM users AS u"
			+ " JOIN employees AS e ON u.user_id = e.employee_ID"
			+ " JOIN departments AS d ON e.department_ID = d.department_ID";
		
		// 条件指定
		if(where != null) {
			sql += " where";
			for (int i = 0; i < where.length; i++) {
				if (i > 0) {
					sql += " AND";
				}
				sql += " CONCAT(u.last_name, u.first_name, u.first_name_kana , u.last_name_kana, d.department_name) LIKE '%" + where[i] + "%'";
			}
		}
		
		sql += " ORDER BY u.user_id";
		PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			StaffBean record = new StaffBean();
			record.setUser_id(resultSet.getString("user_id"));
			record.setPassword(resultSet.getString("password"));
			record.setLast_name(resultSet.getString("last_name"));
			record.setFirst_name(resultSet.getString("first_name"));
			record.setLapse_flag(resultSet.getInt("lapse_flag"));
			record.setFirst_name_kana(resultSet.getString("first_name_kana"));
			record.setLast_name_kana(resultSet.getString("last_name_kana"));
			record.setComment(resultSet.getString("comment"));
			record.setLevel(resultSet.getInt("level"));
			record.setExperience(resultSet.getInt("experience"));
			record.setDepartment_ID(resultSet.getInt("department_ID"));
			record.setDepartment_name(resultSet.getString("department_name"));
			result.add(record);
		}
		return result;
	}
	
	/**
	 * 社員ユーザ情報を登録します。
	 * @param value
	 * 社員ユーザレコード情報
	 * @return
	 * 処理件数
	 * @throws SQLException 
	 */
	public int addStaffUser(String[] values) throws SQLException {
		String sql1 = "INSERT INTO users VALUES (?, ?, ?, ?, 1, 0, ?, ?, ?)";
		PreparedStatement preparedStatement = this.connection.prepareStatement(sql1);
		preparedStatement.setString(1, values[4]);
		preparedStatement.setString(2, values[5]);
		preparedStatement.setString(3, values[0]);
		preparedStatement.setString(4, values[1]);
		preparedStatement.setString(5, values[7]);
		preparedStatement.setString(6, values[3]);
		preparedStatement.setString(7, values[2]);
		preparedStatement.executeUpdate();
		String sql2 = "INSERT INTO employees VALUES (?, '', 1, 0, 0, ?);";
		preparedStatement = this.connection.prepareStatement(sql2);
		preparedStatement.setString(1, values[4]);
		preparedStatement.setString(2, values[6]);
		return preparedStatement.executeUpdate();
	}
}
