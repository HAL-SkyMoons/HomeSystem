package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsUpdateBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsUpdateDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsUpdateDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsUpdateDao(Connection con){
		this.con = con;
	}

	public void updateContent(ContentsUpdateBean updateBean) throws SQLException{
		PreparedStatement updateContentsPst = con.prepareStatement(
				"update home_contents set home_content_id = ?, "
				+ "employee_id = ?, "
				+ "home_content_title = ?, "
				+ "start_datetime = ?, "
				+ "home_content_comment = ? "
				//+ "end_datetime = ? "
				+ "where home_content_id = ?");
		updateContentsPst.setInt(1, updateBean.getHomeContentId());
		updateContentsPst.setString(2, updateBean.getEmployeeId());
		updateContentsPst.setString(3, updateBean.getHomeContentTitle());
		updateContentsPst.setString(4, updateBean.getStartDatetime());
		updateContentsPst.setString(5, updateBean.getHomeContentComment());
		//updateContentsPst.setString(6, updateBean.getEndDatetime());
		updateContentsPst.setInt(6, updateBean.getHomeContentId());
		updateContentsPst.executeUpdate();
		updateContentsPst.close();
		return;
	}
	
	public void updateEndDate(ContentsUpdateBean updateBean) throws SQLException{
		PreparedStatement updateContentsPst = con.prepareStatement(
				"update home_contents set end_datetime = ? "
				+ "where home_content_id = ?");
		updateContentsPst.setString(1, updateBean.getEndDatetime());
		updateContentsPst.setInt(2, updateBean.getHomeContentId());
		updateContentsPst.executeUpdate();
		updateContentsPst.close();
		return;
	}
	
	public boolean changeGenre(ContentsUpdateBean updateBean) throws SQLException{
		//コンテンツの取得
		int homeContentId = updateBean.getHomeContentId();
		PreparedStatement deleteGenrePst = con.prepareStatement("delete from home_genre where home_content_id = ? ;");
		deleteGenrePst.setInt(1, homeContentId);
		deleteGenrePst.executeUpdate();
		deleteGenrePst.close();
		for(int genreId : updateBean.getGenreId()){
			PreparedStatement insertGenrePst = con.prepareStatement("insert into home_genre(home_content_id, genre_id) values(?, ?) ;");
			insertGenrePst.setInt(1, homeContentId);
			insertGenrePst.setInt(2, genreId);
			insertGenrePst.executeUpdate();
			insertGenrePst.close();
		}
		
		return true;
	}
	
	/**
	 * 接続を閉じる
	 *
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		con.close();
	}

	/**
	 * コミット
	 *
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		con.commit();
	}

	/**
	 * ロールバック
	 *
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		con.rollback();
	}
}

