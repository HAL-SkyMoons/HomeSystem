package jp.ac.hal.skymoons.daoes.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	 * 指定の顧客ユーザIDの顧客ユーザ詳細１件を取得する。
	 * @param customer_id
	 * 顧客ユーザID
	 * @return
	 * 顧客ユーザレコード。指定のユーザIDのデータが見つからない場合、nullが返されます。
	 * @throws SQLException
	 */
	public CustomerUsersBean getCustomerUsersDetail(String customer_id) throws SQLException {
		String sql	=	"SELECT user_id, password, last_name, first_name, Class_flag,"
					+		" delete_flag, lapse_flag, customer_id, customer_company"
					+	" FROM users"
					+	" JOIN customers AS cus"
					+	" ON users.user_id = cus.customer_id" 
					+	" WHERE users.delete_flag = 0 AND BINARY users.user_id = ?";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		preparedStatement.setString(1, customer_id);
		ResultSet resultSet = preparedStatement.executeQuery();
		CustomerUsersBean record = new CustomerUsersBean();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		if(resultSetMetaData.getColumnCount() != 0) {
			resultSet.next();
			record.setUser_id(resultSet.getString("user_id"));
			record.setPassword(resultSet.getString("password"));
			record.setLast_name(resultSet.getString("last_name"));
			record.setFirst_name(resultSet.getString("first_name"));
			record.setClass_flag(resultSet.getInt("Class_flag"));
			record.setDelete_flag(resultSet.getInt("delete_flag"));
			record.setLapse_flag(resultSet.getInt("lapse_flag"));
			record.setCustomer_id(resultSet.getString("customer_id"));
			record.setCustomer_company(resultSet.getString("customer_company"));
			return record;
		} else {
			return null;
		}
	}
	
	/**
	 * 指定したユーザIDが、登録されているユーザIDと重複していないかチェックする。
	 * @param userId
	 * ユーザID
	 * @return
	 * false:重複無し<br />
	 * true:重複有り
	 * @throws SQLException 
	 */
	public Boolean checkUserId(String userId) throws SQLException {
		String sql = "SELECT COUNT(*) AS count FROM users WHERE BINARY user_id = ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		int count = resultSet.getInt("count");
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}

// ==========================================================================================
//  INSERT
// ==========================================================================================
	
	/**
	 * 顧客ユーザ情報を追加する。
	 * @param record
	 * 顧客ユーザ情報
	 * @throws SQLException 
	 */
	public void insertCustomerUsers(CustomerUsersBean record) throws Exception {
		String usersSQL =	"INSERT INTO users(user_id, password, last_name, first_name, Class_flag, delete_flag, lapse_flag)"
						+	" VALUES(?, ?, ?, ?, 0, 0, 0)";
		String customersSQL =	"INSERT INTO customers(customer_id, customer_company)"
							+	" VALUES(?, ?)";
		
		con.setAutoCommit(false);
		try {
			PreparedStatement preparedStatement = con.prepareStatement(usersSQL);
			preparedStatement.setString(1, record.getUser_id());
			preparedStatement.setString(2, record.getPassword());
			preparedStatement.setString(3, record.getLast_name());
			preparedStatement.setString(4, record.getFirst_name());
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(customersSQL);
			preparedStatement.setString(1, record.getUser_id());
			preparedStatement.setString(2, record.getCustomer_company());
			preparedStatement.executeUpdate();
			
			con.commit();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:データベース追加処理中に問題が発生しました。ロールバックを実行します。");
			con.rollback();
			System.out.println("ロールバックが完了しました。");
			throw new RuntimeException("ERROR:データベース追加処理中に問題が発生しました。");
		}
	}
	
// ==========================================================================================
//  UPDATE
// ==========================================================================================
	
	/**
	 * 指定の顧客ユーザIDの姓と名を更新する。
	 * @param id
	 * 顧客ユーザID
	 * @param lastName
	 * 姓
	 * @param firstName
	 * 名
	 * @throws SQLException
	 */
	public void updateCustomerName(String id, String lastName, String firstName) throws SQLException {
		String sql = "UPDATE users SET last_name = ?, first_name = ? WHERE BINARY user_id = ?";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		preparedStatement.setString(1, lastName);
		preparedStatement.setString(2, firstName);
		preparedStatement.setString(3, id);
		preparedStatement.executeUpdate();
		con.commit();
	}
	
	/**
	 * 指定の顧客ユーザIDの企業名を更新する。
	 * @param id
	 * 顧客ユーザID
	 * @param company
	 * 企業名
	 * @throws SQLException
	 */
	public void updateCustomerCompany(String id, String company) throws SQLException {
		String sql = "UPDATE customers SET customer_company = ? WHERE BINARY customer_id = ?";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		preparedStatement.setString(1, company);
		preparedStatement.setString(2, id);
		preparedStatement.executeUpdate();
		con.commit();
	}
	
	/**
	 * 指定の顧客ユーザIDのパスワードを変更する。
	 * @param id
	 * 顧客ユーザID
	 * @param password
	 * パスワード
	 * @throws SQLException
	 */
	public void updateCustomerPassword(String id, String password) throws SQLException {
		String sql = "UPDATE users SET password = ? WHERE BINARY user_id = ?";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		preparedStatement.setString(1, password);
		preparedStatement.setString(2, id);
		preparedStatement.executeUpdate();
		con.commit();
	}
	
	/**
	 * 指定の顧客ユーザを論理削除する。
	 * @param customerId
	 * 顧客ユーザのID
	 * @throws SQLException
	 */
	public void updateCustomer(String customerId) throws SQLException {
		String sql = "UPDATE users SET delete_flag = 1 WHERE BINARY user_id = ?";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		preparedStatement.setString(1, customerId);
		preparedStatement.executeUpdate();
		con.commit();
	}
}
