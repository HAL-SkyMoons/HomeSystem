package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsListBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsListDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsListDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsListDao(Connection con){
		this.con = con;
	}

	
	/**
	 * 全件取得する
	 *
	 * @return 全件
	 * @throws SQLException
	 */
	public ArrayList<ContentsListBean> findAll() throws SQLException {

		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents order by home_content_id desc;");
		ResultSet contentsResult = contentsPst.executeQuery();	

		ArrayList<ContentsListBean> contentsList = convertList(contentsResult);
		contentsPst.close();
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
	
	public ArrayList<ContentsListBean> selectGenre(String genreId) throws SQLException {

		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents hc, home_genre hg where hc.home_content_id = hg.home_content_id and hg.genre_id = ? order by home_content_id desc;");
		contentsPst.setString(1, genreId);
		ResultSet contentsResult = contentsPst.executeQuery();		

		ArrayList<ContentsListBean> contentsList = convertList(contentsResult);
		contentsPst.close();
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
	
	public ArrayList<ContentsListBean> selectEmployee(String employeeId) throws SQLException {

		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents hc where hc.employee_id = ? order by home_content_id desc;");
		contentsPst.setString(1, employeeId);
		ResultSet contentsResult = contentsPst.executeQuery();		

		ArrayList<ContentsListBean> contentsList = convertList(contentsResult);
		contentsPst.close();
		return contentsList;
	}

	public ArrayList<ContentsListBean> selectHomeContentTitle(String homeContentTitle) throws SQLException {

		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents where home_content_title like '%?%' order by home_content_id desc;");
		contentsPst.setString(1, homeContentTitle);
		ResultSet contentsResult = contentsPst.executeQuery();		
		
		ArrayList<ContentsListBean> contentsList = convertList(contentsResult);
		contentsPst.close();
		return contentsList;
	}
	
	public ArrayList<ContentsListBean> convertList(ResultSet contentsResult) throws SQLException{
		ArrayList<ContentsListBean> contentsList = new ArrayList<>();
		//コンテンツの取得
		while(contentsResult.next()) {
			ContentsListBean listBean = new ContentsListBean();
			//検索条件となる値の取得
			int homeContentId = contentsResult.getInt("home_content_id");
			//beanに値を追加
			listBean.setHomeContentId(homeContentId);
			listBean.setHomeContentTitle(contentsResult.getString("home_content_title"));
			listBean.setHomeContentComment(contentsResult.getString("home_content_comment"));
			listBean.setHomeContentDatetime(contentsResult.getString("home_content_datetime"));
			listBean.setEmployeeId(contentsResult.getString("employee_id"));
			
			//名前の取得
			PreparedStatement namePst = con.prepareStatement("select * from users where user_id = ? ;");
			namePst.setString(1, contentsResult.getString("employee_id"));
			ResultSet nameResult = namePst.executeQuery();
			if(nameResult.next()){
				listBean.setFirstName(nameResult.getString("first_name"));
				listBean.setLastName(nameResult.getString("last_name"));
			}else{
				//取得失敗時の処理
			}
			namePst.close();

			//ジャンルの取得
			PreparedStatement genrePst = con.prepareStatement(
					"select * from home_genre, genre "
					+ "where home_genre.home_content_id = ? "
					+ "and home_genre.genre_id = genre.genre_id;");
			
			genrePst.setInt(1, homeContentId);
			ResultSet genreResult = genrePst.executeQuery();
			
			ArrayList<Integer> genreId = new ArrayList<>();
			ArrayList<String> genreName = new ArrayList<>();
			
			while(genreResult.next()){
				genreId.add(genreResult.getInt("genre_id"));
				genreName.add(genreResult.getString("genre_name"));
			}
			
			listBean.setGenreId(genreId);
			listBean.setGenreName(genreName);
			
			genrePst.close();

			//大ジャンルの取得			
			PreparedStatement bigGenrePst = con.prepareStatement(
					"select * from home_genre, genre, big_genre "
					+ "where home_genre.home_content_id = ? "
					+ "and home_genre.genre_id = genre.genre_id "
					+ "and genre.big_genre_id = big_genre.big_genre_id "
					+ "group by big_genre.big_genre_id;");

			bigGenrePst.setInt(1, homeContentId);
			ResultSet bigGenreResult = bigGenrePst.executeQuery();
			
			ArrayList<Integer> bigGenreId = new ArrayList<>();
			ArrayList<String> bigGenreName = new ArrayList<>();
			
			while(bigGenreResult.next()){
				bigGenreId.add(bigGenreResult.getInt("big_genre_id"));
				bigGenreName.add(bigGenreResult.getString("big_genre_name"));
			}
			
			listBean.setBigGenreId(bigGenreId);
			listBean.setBigGenreName(bigGenreName);
			
			bigGenrePst.close();
			
			//添付資料の取得
			//検索時には不要?
			/*
			ArrayList<Integer> homeSourceNo = new ArrayList<>();
			ArrayList<String> homeSourceName = new ArrayList<>();
			PreparedStatement sourcePst = con.prepareStatement("select * from home_source where home_content_id = ? ;");
			sourcePst.setInt(1, homeContentId);
			ResultSet sourceResult = sourcePst.executeQuery();		
			while(sourceResult.next()){
				homeSourceNo.add(sourceResult.getInt("home_source_no"));
				homeSourceName.add(sourceResult.getString("home_source_name"));
			}
			listBean.setHomeSourceNo(homeSourceNo);
			listBean.setHomeSourceName(homeSourceName);
			sourcePst.close();
			*/
			
			contentsList.add(listBean);
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

