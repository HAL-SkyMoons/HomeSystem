
package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.ac.hal.skymoons.controllers.ConnectionGet;

/**
 * ログイン認証機能でのDB操作。
 * @author YAMAZAKI GEN
 * @since 2015/05/29
 * @version 1.0
 */
public class LoginDAO {
	
	private Connection con = null;
	
	/**
	 * コネクションを取得する。
	 * @throws Exception
	 */
	public LoginDAO() throws Exception {
		ConnectionGet connectionGet = new ConnectionGet();
		this.con = connectionGet.getCon();
	}

	/**
	 * コネクションをクローズする。
	 */
	public void close() {
		try {
			if (con != null) {
				if (con.isClosed() == false) {
					con.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ログイン認証共通機能。
	 * @param id
	 * ログインID
	 * @param pass
	 * パスワード
	 * @param sql
	 * SQL文
	 * @return
	 * 認証成功:true/認証失敗/false
	 * @throws Exception
	 */
	private boolean checkUser(String id,String pass,String sql) throws Exception {
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1, id);
		statement.setString(2, pass);
		ResultSet result = statement.executeQuery();
		if(result != null) {
			result.next();
			if(result.getString(1).equals("1")) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("ERROR:データベースの結果が取得できません。");
			return false;
		}
	}
	
	/**
	 * DBに問合せ、管理者ユーザの認証を行う。
	 * @param id
	 * 管理者ユーザのログインID
	 * @param pass
	 * 管理者ユーザのパスワード
	 * @return
	 * 認証成功:true/認証失敗/false
	 */
	public boolean checkAdministratorUser(String id,String pass) throws Exception {
		return checkUser(
					id,
					pass,
					"SELECT COUNT(*) FROM administrators WHERE administrator_ID = ? AND password = ?");
	}
	
	/**
	 * DBに問合せ、顧客ユーザの認証を行う。
	 * @param id
	 * 顧客ユーザのログインID
	 * @param pass
	 * 顧客ユーザのパスワード
	 * @return
	 * 認証成功:true/認証失敗/false
	 */
	public boolean checkCustomerUser(String id,String pass) throws Exception {
		return checkUser(
					id,
					pass,
					"SELECT COUNT(*) FROM users WHERE user_id = ? AND password = ?");
	}
	
	/**
	 * DBに問合せ、顧客ユーザ・社員ユーザの認証を行う。
	 * @param id
	 * 顧客ユーザ・社員ユーザのログインID
	 * @param pass
	 * 顧客ユーザ・社員ユーザのパスワード
	 * @return
	 * 認証成功:Class_flag/認証失敗:null
	 */
	public String checkCsUser(String id,String pass) throws Exception {
		PreparedStatement statement = con.prepareStatement(
				"SELECT Class_flag FROM users WHERE user_id = ? AND password = ?");
		statement.setString(1, id);
		statement.setString(2, pass);
		ResultSet result = statement.executeQuery();
		if(result != null) {
			if(result.next()) {
			return result.getString(1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}

