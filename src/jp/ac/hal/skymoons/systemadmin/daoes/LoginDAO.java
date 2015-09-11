package jp.ac.hal.skymoons.systemadmin.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 管理システムログイン認証機能DAO
 * @author YAMAZAKI GEN
 * @since 2015/08/18
 * @version 1.0
 */
public class LoginDAO {
	
	private Connection connection = null;
	
	/**
	 * JNDIを利用してデータベースコネクションを取得します。
	 * @throws Exception
	 */
	public LoginDAO() throws Exception {
		jp.ac.hal.skymoons.controllers.ConnectionGet connectionGet
			= new jp.ac.hal.skymoons.controllers.ConnectionGet();
		this.connection = connectionGet.getCon();
	}
	
	/**
	 * 指定のデータベースコネクションを利用します。
	 * @param connection
	 */
	public LoginDAO(Connection connection) {
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
	 * 管理者ユーザのログイン認証を行う。
	 * @param id
	 * @param password
	 * @return
	 * TRUE:認証成功<br />
	 * FALSE:認証失敗
	 * @throws SQLException 
	 */
	public Boolean loginAuthentication(String id, String password) throws SQLException {
		String sql = "select count(*) as hits from administrators"
			+ " where binary administrator_ID = ? and binary password = ?";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, password);
		ResultSet resultSet = ps.executeQuery();
		resultSet.next();
		switch (resultSet.getInt("hits")) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				System.out.println("ERROR:重大なエラーが発生しました。");
				System.out.println("IDとパスワードが重複している複数のユーザが存在します。");
				return null;
		}
	}
}
