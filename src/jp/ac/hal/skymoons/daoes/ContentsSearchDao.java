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
	 * 主キーで検索
	 *
	 * @param languageId
	 * @return
	 * @throws SQLException
	 * 追記分　Aを追加
	 */
	/*
	public SampleBean findOne(String sample) throws SQLException {

		PreparedStatement select = con.prepareStatement("select * from home_contents hc, genre g where hc.home_content_id = g.home_content_id and g.genre_id = ? ;");
		select.setString(1, sample);
		ResultSet result = select.executeQuery();

		ContentsSearchBean record = new ContentsSearchBean();

		if (result.next()) {
			record.setHomeContentTitle(result.getString("sample"));
			record.setHomeContentDatetime(result.getString("homeContentDatetime"));
			record.setBigGenreName(result.getString("bigGenreName"));
			record.setGenreName(result.getString("genre_name"));
		}

		return record;
	}
	*/
	
	/**
	 * 全件取得する
	 *
	 * @return 全件
	 * @throws SQLException
	 */
	public ArrayList<ContentsSearchBean> findAll() throws SQLException {

		PreparedStatement state = con.prepareStatement("select * from home_contents hc, genre g where hc.home_content_id = g.home_content_id ;");
		
		ResultSet result = state.executeQuery();

		ArrayList<ContentsSearchBean> contentsList = new ArrayList<>(); 
		while (result.next()) {
			ContentsSearchBean record = new ContentsSearchBean();
			record.setHomeContentTitle(result.getString("home_content_title"));
			record.setHomeContentDatetime(result.getString("home_content_datetime"));
			record.setBigGenreName(result.getString("big_genre_name"));
			record.setGenreName(result.getString("genre_name"));
			record.setEmployeeId(result.getInt("employee_id"));
			record.setFirstName(result.getString("first_name"));
			record.setLastName(result.getString("last_name"));
			contentsList.add(record);
		}

		return contentsList;
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

		ArrayList<ContentsSearchBean> contentsList = new ArrayList<>(); 
		while (result.next()) {
			ContentsSearchBean record = new ContentsSearchBean();
			record.setHomeContentTitle(result.getString("home_content_title"));
			record.setHomeContentDatetime(result.getString("home_content_datetime"));
			record.setBigGenreName(result.getString("big_genre_name"));
			record.setGenreName(result.getString("genre_name"));
			record.setEmployeeId(result.getInt("employee_id"));
			record.setFirstName(result.getString("first_name"));
			record.setLastName(result.getString("last_name"));
			contentsList.add(record);
		}

		return contentsList;
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

		ArrayList<ContentsSearchBean> contentsList = new ArrayList<>(); 
		while (result.next()) {
			ContentsSearchBean record = new ContentsSearchBean();
			record.setHomeContentTitle(result.getString("home_content_title"));
			record.setHomeContentDatetime(result.getString("home_content_datetime"));
			record.setBigGenreName(result.getString("big_genre_name"));
			record.setGenreName(result.getString("genre_name"));
			record.setEmployeeId(result.getInt("employee_id"));
			record.setFirstName(result.getString("first_name"));
			record.setLastName(result.getString("last_name"));
			contentsList.add(record);
		}

		return contentsList;
	}
	
	

	public ArrayList<ContentsSearchBean> selectHomeContentTitle(String homeContentTitle) throws SQLException {

		PreparedStatement state = con.prepareStatement("select * from home_contents hc, genre g where hc.home_content_id = g.home_content_id and hc.employee_id = ? ;");
		state.setString(1, homeContentTitle);
		ResultSet result = state.executeQuery();

		ArrayList<ContentsSearchBean> contentsList = new ArrayList<>(); 
		while (result.next()) {
			ContentsSearchBean record = new ContentsSearchBean();
			record.setHomeContentTitle(result.getString("home_content_title"));
			record.setHomeContentDatetime(result.getString("home_content_datetime"));
			record.setBigGenreName(result.getString("big_genre_name"));
			record.setGenreName(result.getString("genre_name"));
			record.setEmployeeId(result.getInt("employee_id"));
			record.setFirstName(result.getString("first_name"));
			record.setLastName(result.getString("last_name"));
			contentsList.add(record);
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

