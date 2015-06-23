package jp.ac.hal.skymoons.daoes.ranking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
