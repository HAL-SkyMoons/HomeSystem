package jp.ac.hal.skymoons.daoes.ranking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.hal.skymoons.beans.ranking.BatchBean;
import jp.ac.hal.skymoons.beans.ranking.TopNumRankingBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;

/**
 * ランキング作成機能のDB操作
 * @author YAMAZAKI GEN
 * @since 2015/06/05
 * @version 1.0
 */
public class RankingDAO {

	private Connection con = null;

	/**
	 * コネクションを取得する。
	 * @throws Exception
	 */
	public RankingDAO() throws Exception {
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
			System.out.println("ERROR:コネクションのクローズ処理中に問題が発生しました。");
		}
	}
	
	/**
	 * バッチリストを取得する。
	 * @return
	 * バッチリスト
	 * @throws SQLException
	 */
	public List<BatchBean> getBatchList() throws SQLException {
		String sql = "SELECT * FROM batch";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<BatchBean> batchList = new ArrayList<BatchBean>();
		while(resultSet.next()) {
			BatchBean record = new BatchBean();
			record.setBatch_id(resultSet.getInt("batch_id"));
			record.setBatch_name(resultSet.getString("batch_name"));
			record.setBatch_comment(resultSet.getString("batch_comment"));
			batchList.add(record);
		}
		return batchList;
	}
	
	/**
	 * 年リストを取得する。
	 * @return
	 * 年リスト
	 * @throws SQLException
	 */
	public List<String> getYearList() throws SQLException {
		String sql	= "SELECT DATE_FORMAT(home_datetime, '%Y') AS year_list"
					+ " FROM home_log"
					+ " GROUP BY DATE_FORMAT(home_datetime, '%Y')"
					+ " ORDER BY home_datetime desc";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		if(metaData.getColumnCount() != 0) {
			List<String> yearList = new ArrayList<String>();
			while(resultSet.next()) {
				yearList.add(resultSet.getString("year_list"));
			}
			return yearList;
		}
		return null;
	}
	
	/**
	 * 総獲得数ランキング表の取得。
	 * @return
	 * 総獲得数ランキングリスト
	 * @throws SQLException
	 */
	public List<TopNumRankingBean> getTopNumRanking1() throws SQLException {
		String sql = "SELECT COUNT(*) AS value, users.last_name || users.first_name AS name, hom.home_target "
			+ "FROM home_log AS hom "
			+ "JOIN employees AS emp ON hom.home_target = emp.employee_id "
			+ "JOIN users ON emp.employee_id = users.user_id "
			+ "GROUP BY hom.home_target "
			+ "ORDER BY value desc";
		PreparedStatement statement = con.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		List<TopNumRankingBean> result = new ArrayList<TopNumRankingBean>();
		
		if(resultSet != null) {
			while(resultSet.next()) {
				TopNumRankingBean record = new TopNumRankingBean();
				record.setValue(resultSet.getLong(1));
				record.setName(resultSet.getString(2));
				record.setId(resultSet.getString(3));
				result.add(record);
			}
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * 総獲得数ランキング表の取得。<br />
	 * オプション:年と月の絞り込み
	 * @param year
	 * 年
	 * @param month
	 * 月
	 * @return
	 * 総獲得数ランキングリスト
	 * @throws SQLException
	 */
	public List<TopNumRankingBean> getTopNumRanking2(String year, String month) throws SQLException {
		String sql	=	"SELECT COUNT(*) AS value, users.last_name || users.first_name AS name, hom.home_target"
					+	" FROM home_log AS hom"
					+	" JOIN employees AS emp ON hom.home_target = emp.employee_id"
					+	" JOIN users ON emp.employee_id = users.user_id";
		
		if(year != null || month != null) {
			sql += " WHERE";
			if(year != null) {
				sql +=	" DATE_FORMAT(home_datetime, '%Y') = " + year;
			}
			if(year != null && month != null) {
				sql +=	" AND";
			}
			if(month != null) {
				sql +=	" DATE_FORMAT(home_datetime, '%m') = " + month;
			}
		}
		
				sql	+=	" GROUP BY hom.home_target"
					+	" ORDER BY value desc";
				
				System.out.println(sql);
		
		PreparedStatement statement = con.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		List<TopNumRankingBean> result = new ArrayList<TopNumRankingBean>();
		
		if(resultSet != null) {
			while(resultSet.next()) {
				TopNumRankingBean record = new TopNumRankingBean();
				record.setValue(resultSet.getLong(1));
				record.setName(resultSet.getString(2));
				record.setId(resultSet.getString(3));
				result.add(record);
			}
			return result;
		} else {
			return null;
		}
	}
}
