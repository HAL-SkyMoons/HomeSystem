package jp.ac.hal.skymoons.daoes.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.ac.hal.skymoons.beans.customer.CustomerTestBean;
import jp.ac.hal.skymoons.beans.customer.CustomerUsersBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;

/**
 * 顧客情報操作機能のDB操作
 * @author YAMAZAKI GEN
 * @since 2015/06/09
 * @version 1.0
 */
public class CustomerDAO {
	private Connection con = null;
	/**
	 * コネクションを取得する。
	 * @throws Exception
	 */
	public CustomerDAO() throws Exception {
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

// ==========================================================================================
//  SELECT
// ==========================================================================================

	/**
	 * 顧客ユーザリストを取得する。
	 * @param keyword
	 * 検索キーワード
	 * @return
	 * @throws SQLException
	 */
	public List<CustomerUsersBean> getCustomerUsersList(String keyword) throws SQLException {
		String sql	=	"SELECT user_id, password, last_name, first_name, Class_flag,"
					+		" delete_flag, lapse_flag, customer_id, customer_company"
					+	" FROM users"
					+	" JOIN customers AS cus"
					+	" ON users.user_id = cus.customer_id" 
					+	" WHERE users.delete_flag = 0";

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if(keyword != null) {
			if(keyword.equals("") == false) {
				// 半角スペース削除
				keyword = keyword.replace(" ", "");
				// 全角スペース削除
				keyword = keyword.replace("　", "");
				sql +=	" AND (cus.customer_company LIKE ?"
					+	" OR users.last_name LIKE ?"
					+	" OR users.first_name LIKE ?"
					+	" OR users.last_name || users.first_name LIKE ?)";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, "%" + keyword + "%");
				preparedStatement.setString(2, "%" + keyword + "%");
				preparedStatement.setString(3, "%" + keyword + "%");
				preparedStatement.setString(4, "%" + keyword + "%");
				resultSet = preparedStatement.executeQuery();
			}
		}
		if(resultSet == null) {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
		}
		
		List<CustomerUsersBean> list = new ArrayList<CustomerUsersBean>();
		while(resultSet.next()) {
			CustomerUsersBean record = new CustomerUsersBean();
			record.setUser_id(resultSet.getString("user_id"));
			record.setPassword(resultSet.getString("password"));
			record.setLast_name(resultSet.getString("last_name"));
			record.setFirst_name(resultSet.getString("first_name"));
			record.setClass_flag(resultSet.getInt("Class_flag"));
			record.setDelete_flag(resultSet.getInt("delete_flag"));
			record.setLapse_flag(resultSet.getInt("lapse_flag"));
			record.setCustomer_id(resultSet.getString("customer_id"));
			record.setCustomer_company(resultSet.getString("customer_company"));
			list.add(record);
		}
		
		return list;
	}

	/**
	 * 顧客ユーザリストを取得する。
	 * @return
	 * 顧客ユーザリスト
	 * @throws SQLException
	 */
	public List<CustomerTestBean> getCustomerList() throws SQLException {
		String sql =	"SELECT * "
					+	"FROM Customers AS cus "
					+	"JOIN users "
					+	"ON cus.customer_id = users.user_id "
					+	"ORDER BY cus.customer_company";
		PreparedStatement statement = con.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		List<CustomerTestBean> result = new ArrayList<CustomerTestBean>();
		if(resultSet != null) {
			while(resultSet.next()) {
				CustomerTestBean record = new CustomerTestBean();
				record.setCustomer_id(resultSet.getString(1));
				record.setCustomer_company(resultSet.getString(2));
				record.setUser_id(resultSet.getString(3));
				record.setPassword(resultSet.getString(4));
				record.setLast_name(resultSet.getString(5));
				record.setFirst_name(resultSet.getString(6));
				record.setClass_flag(resultSet.getInt(7));
				result.add(record);
			}
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * データベースから顧客詳細情報を取得する。
	 * @return
	 * 顧客詳細情報
	 * @throws SQLException 
	 */
	public HashMap<String, String> getCustomerDetail(String id) throws SQLException {
		String sql	=	"SELECT cus.customer_company, users.last_name, users.first_name,  users.user_id, users.password "
					+	"FROM Customers AS cus "
					+	"JOIN users "
					+	"ON cus.customer_id = users.user_id "
					+	"WHERE users.user_id = ?";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		preparedStatement.setString(1, id);
		ResultSet result = preparedStatement.executeQuery();
		HashMap<String, String> value = new HashMap<String, String>();
		result.next();
		value.put("company", result.getString(1));
		value.put("lastname", result.getString(2));
		value.put("firstname", result.getString(3));
		value.put("id", result.getString(4));
		value.put("password", result.getString(5));
		return value;
	}

// ==========================================================================================
//  INSERT
// ==========================================================================================
	
	/**
	 * データベースに顧客情報をINSERTする。
	 * @param value
	 * @throws SQLException 
	 */
	public void insertCustomer(HashMap<String, String> value) throws SQLException {
		String sql1 = "INSERT INTO"
			+ " users(user_id, password, last_name, first_name, Class_flag)"
			+ " VALUES(?, ?, ?, ?, 0)";
		String sql2 = "INSERT INTO"
			+ " customers(customer_id, customer_company)"
			+ " VALUES(?, ?)";
		
		this.con.setAutoCommit(false);
		try {
			PreparedStatement preparedStatement = this.con.prepareStatement(sql1);
			preparedStatement.setString(1, value.get("id").toString());
			preparedStatement.setString(2, value.get("password").toString());
			preparedStatement.setString(3, value.get("lastname").toString());
			preparedStatement.setString(4, value.get("firstname").toString());
			preparedStatement.executeUpdate();
			preparedStatement = this.con.prepareStatement(sql2);
			preparedStatement.setString(1, value.get("id").toString());
			preparedStatement.setString(2, value.get("company").toString());
			preparedStatement.executeUpdate();
			this.con.commit();
		} catch(Exception e) {
			e.printStackTrace();
			this.con.rollback();
		}
	}
	
// ==========================================================================================
//  UPDATE
// ==========================================================================================

// ==========================================================================================
//  DELETE
// ==========================================================================================
	
	/**
	 * データベースの顧客テーブルとユーザテーブルから、顧客ユーザIDで指定した顧客ユーザ情報を削除する。
	 * @param customerId
	 * 顧客ユーザのID
	 * @throws SQLException
	 */
	public void deleteCustomer(String customerId) throws SQLException {
		// 顧客テーブルのレコード削除
		String sql1 = "DELETE FROM customers WHERE customer_id = ?";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql1);
		preparedStatement.setString(1, customerId);
		preparedStatement.executeUpdate();
		// ユーザテーブルのレコード削除
		String sql2 = "DELETE FROM users WHERE user_id = ?";
		preparedStatement = this.con.prepareStatement(sql2);
		preparedStatement.setString(1, customerId);
		preparedStatement.executeUpdate();
		
		this.con.commit();
	}
}
