package jp.ac.hal.skymoons.systemadmin.daoes;

import java.sql.Connection;
import java.sql.SQLException;

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

}
