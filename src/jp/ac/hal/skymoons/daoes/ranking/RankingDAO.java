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
	 * 年リストを取得する。<br />
	 * データベースに登録されている日付から生成。
	 * @return
	 * 年リスト
	 * @throws SQLException
	 */
	public List<String> getYearList1() throws SQLException {
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
	 * 年を取得する。<br />
	 * データベースに登録されている一番古いデータから取り出す。
	 * @return
	 * 年
	 * @throws SQLException
	 */
	public String getYearList2() throws SQLException {
		String sql	= "SELECT DATE_FORMAT(MIN(home_datetime), '%Y') AS year FROM home_log";
		PreparedStatement preparedStatement = this.con.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		return resultSet.getString("year");
	}

	/**
	 * 獲得数ランキングリストの取得。<br />
	 * オプション:バッチ・年・月の絞り込み
	 * @param whereBatch
	 * バッチID
	 * @param whereYear
	 * 年
	 * @param whereMonth
	 * 月
	 * @return
	 * 獲得数ランキングリスト
	 * @throws SQLException
	 */
	public List<TopNumRankingBean> getRankingList(String whereBatch, String whereYear, String whereMonth) throws SQLException {
		String sql	=	"SELECT COUNT(*) AS value, users.last_name || users.first_name AS name, hom.home_target"
					+	" FROM home_log AS hom"
					+	" JOIN employees AS emp ON hom.home_target = emp.employee_id"
					+	" JOIN users ON emp.employee_id = users.user_id";
		
		// 条件式の生成
		String whereSQL = null;
		if(whereBatch != null) {
			whereSQL = " hom.batch_id = " + whereBatch;
		}
		if(whereYear != null) {
			if(whereSQL == null) {
				whereSQL = " DATE_FORMAT(home_datetime, '%Y') = " + whereYear;
			} else {
				whereSQL += " AND DATE_FORMAT(home_datetime, '%Y') = " + whereYear;
			}
		}
		if(whereMonth != null) {
			if(whereSQL == null) {
				whereSQL = " DATE_FORMAT(home_datetime, '%m') = " + whereMonth;
			} else {
				whereSQL += " AND DATE_FORMAT(home_datetime, '%m') = " + whereMonth;
			}
		}
		// 条件式結合
		if(whereSQL != null) {
			sql += " WHERE" + whereSQL;
		}
		
		sql	+=	" GROUP BY hom.home_target"
			+	" ORDER BY value desc";
				
		/*System.out.println("SQL:" + sql);*/
		
		PreparedStatement statement = con.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		List<TopNumRankingBean> result = new ArrayList<TopNumRankingBean>();
		
		while(resultSet.next()) {
			TopNumRankingBean record = new TopNumRankingBean();
			record.setValue(resultSet.getLong(1));
			record.setName(resultSet.getString(2));
			record.setId(resultSet.getString(3));
			result.add(record);
		}
		return result;
	}
}
