package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsAdditionBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsAdditionDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsAdditionDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsAdditionDao(Connection con){
		this.con = con;
	}

	public void addContent(ContentsAdditionBean additionBean) throws SQLException{
		PreparedStatement contentsAdditionPst = con.prepareStatement(
				"insert into home_contents(employee_id, "
				+ "home_content_title, start_datetime, home_content_comment) "
				+ "values(?,?,?,?);");
		contentsAdditionPst.setString(1, additionBean.getEmployeeId());
		contentsAdditionPst.setString(2, additionBean.getHomeContentTitle());
		contentsAdditionPst.setString(3, additionBean.getStartDatetime());
		contentsAdditionPst.setString(4, additionBean.getHomeContentComment());
		contentsAdditionPst.executeUpdate();
		contentsAdditionPst.close();
		
		//追加されたContentIDを取得
		PreparedStatement getContentIdPst = con.prepareStatement("select last_insert_id();");
		ResultSet getContentIdResult = getContentIdPst.executeQuery();
		getContentIdResult.next();
		additionBean.setHomeContentId(getContentIdResult.getInt("last_insert_id()"));
		getContentIdResult.close();
	}
	
	public boolean addGenre(ContentsAdditionBean additionBean) throws SQLException{
		//コンテンツの取得
		int homeContentId = additionBean.getHomeContentId();
		PreparedStatement deleteGenrePst = con.prepareStatement("delete from home_genre where home_content_id = ? ;");
		deleteGenrePst.setInt(1, homeContentId);
		deleteGenrePst.executeUpdate();
		deleteGenrePst.close();
		if(additionBean.getGenreId() != null){
			for(int genreId : additionBean.getGenreId()){
				PreparedStatement insertGenrePst = con.prepareStatement("insert into home_genre(home_content_id, genre_id) values(?, ?) ;");
				insertGenrePst.setInt(1, homeContentId);
				insertGenrePst.setInt(2, genreId);
				insertGenrePst.executeUpdate();
				insertGenrePst.close();
			}
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

