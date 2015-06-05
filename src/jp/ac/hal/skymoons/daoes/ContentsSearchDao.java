package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsSearchBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsSearchDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsSearchDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsSearchDao(Connection con){
		this.con = con;
	}

	
	/**
	 * 全件取得する
	 *
	 * @return 全件
	 * @throws SQLException
	 */
	public ArrayList<ContentsSearchBean> findAll() throws SQLException {

		PreparedStatement state = con.prepareStatement("select * from home_contents hc, genre g where hc.home_content_id = g.home_content_id ;");
		ResultSet result = state.executeQuery();	
		
		return convertList(result);
	}

	

	/**
	 * ジャンルIDで検索
	 *
	 * @param genreId
	 * @return
	 * @throws SQLException
	 * 追記分　Aを追加
	 */
	
	public ArrayList<ContentsSearchBean> selectGenre(String genreId) throws SQLException {

		PreparedStatement state = con.prepareStatement("select * from home_contents hc, genre g where hc.home_content_id = g.home_content_id and g.genre_id = ? ;");
		state.setString(1, genreId);
		ResultSet result = state.executeQuery();		
		
		return convertList(result);
	}
	
	

	/**
	 * 投稿者で検索
	 *
	 * @param employeeId
	 * @return
	 * @throws SQLException
	 * 追記分　Aを追加
	 */
	
	public ArrayList<ContentsSearchBean> selectEmployee(String employeeId) throws SQLException {

		PreparedStatement state = con.prepareStatement("select * from home_contents hc, genre g where hc.home_content_id = g.home_content_id and hc.employee_id = ? ;");
		state.setString(1, employeeId);
		ResultSet result = state.executeQuery();		
		
		return convertList(result);
	}
	
	

	public ArrayList<ContentsSearchBean> selectHomeContentTitle(String homeContentTitle) throws SQLException {

		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents where employee_id = ? ;");
		contentsPst.setString(1, homeContentTitle);
		ResultSet result = contentsPst.executeQuery();		
		
		return convertList(result);
	}
	
	public ArrayList<ContentsSearchBean> convertList(ResultSet result) throws SQLException{
		ArrayList<ContentsSearchBean> contentsList = new ArrayList<>(); 
		while (result.next()) {
			ContentsSearchBean bean = new ContentsSearchBean();
			
			//コンテンツの取得
			bean.setHomeContentId(result.getInt("home_content_id"));
			bean.setHomeContentTitle(result.getString("home_content_title"));
			bean.setHomeContentDatetime(result.getString("home_content_datetime"));
			bean.setEmployeeId(result.getInt("employee_id"));
			bean.setFirstName(result.getString("first_name"));
			bean.setLastName(result.getString("last_name"));
		
			PreparedStatement genrePst = con.prepareStatement(
					"select * from home_genre hg, genre g, big_genre bg "
					+ "where hg.home_content_id = ? "
					+ "and hg.genre_id = g.genre_id "
					+ "and g.big_genre_id = bg.big_genre_id;");
			
			genrePst.setInt(1, result.getInt("home_content_id"));
			ResultSet genreResult = genrePst.executeQuery();

			//ジャンルの取得
			ArrayList<Integer> bigGenreId = new ArrayList<>();
			ArrayList<String> bigGenreName = new ArrayList<>();
			ArrayList<Integer> genreId = new ArrayList<>();
			ArrayList<String> genreName = new ArrayList<>();
			
			while(genreResult.next()){
				bigGenreId.add(result.getInt("big_genre_id"));
				bigGenreName.add(result.getString("big_genre_name"));
				genreId.add(result.getInt("genre_id"));
				genreName.add(result.getString("genre_name"));
			}
			bean.setBigGenreId(bigGenreId);
			bean.setBigGenreName(bigGenreName);
			bean.setGenreId(genreId);
			bean.setGenreName(genreName);
		
			contentsList.add(bean);
		}
		return contentsList;
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

