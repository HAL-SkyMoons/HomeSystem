package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsEditBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsEditDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsEditDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsEditDao(Connection con){
		this.con = con;
	}

	public ContentsEditBean findDetail(String homeContentId) throws SQLException {
		//戻り値のbeanを生成
		ContentsEditBean editBean = new ContentsEditBean();
		//コンテンツの取得
		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents where home_contents.home_content_id = ? ;");
		contentsPst.setString(1, homeContentId);
		ResultSet contentsResult = contentsPst.executeQuery();
		if (contentsResult.next()) {
			editBean.setHomeContentId(contentsResult.getInt("home_content_id"));
			editBean.setHomeContentTitle(contentsResult.getString("home_content_title"));
			editBean.setHomeContentComment(contentsResult.getString("home_content_comment"));
			editBean.setHomeContentDatetime(contentsResult.getString("home_content_datetime"));
			editBean.setEmployeeId(contentsResult.getString("employee_id"));
			
			//名前の取得
			PreparedStatement namePst = con.prepareStatement("select * from users where user_id = ? ;");
			namePst.setString(1, contentsResult.getString("employee_id"));
			ResultSet nameResult = namePst.executeQuery();
			if(nameResult.next()){
				editBean.setFirstName(nameResult.getString("first_name"));
				editBean.setLastName(nameResult.getString("last_name"));
			}else{
				//取得失敗時の処理
			}
			namePst.close();

			//ジャンルの取得
			PreparedStatement genrePst = con.prepareStatement(
					"select * from home_genre, genre "
					+ "where home_genre.home_content_id = ? "
					+ "and home_genre.genre_id = genre.genre_id;");
			
			genrePst.setString(1, homeContentId);
			ResultSet genreResult = genrePst.executeQuery();
			
			ArrayList<Integer> genreId = new ArrayList<>();
			ArrayList<String> genreName = new ArrayList<>();
			
			while(genreResult.next()){
				genreId.add(genreResult.getInt("genre_id"));
				genreName.add(genreResult.getString("genre_name"));
			}
			
			editBean.setGenreId(genreId);
			editBean.setGenreName(genreName);
			genrePst.close();

			//大ジャンルの取得			
			PreparedStatement bigGenrePst = con.prepareStatement(
					"select * from home_genre, genre, big_genre "
					+ "where home_genre.home_content_id = ? "
					+ "and home_genre.genre_id = genre.genre_id "
					+ "and genre.big_genre_id = big_genre.big_genre_id "
					+ "group by big_genre.big_genre_id;");
			bigGenrePst.setString(1, homeContentId);
			ResultSet bigGenreResult = bigGenrePst.executeQuery();
			
			ArrayList<Integer> bigGenreId = new ArrayList<>();
			ArrayList<String> bigGenreName = new ArrayList<>();
			while(bigGenreResult.next()){
				bigGenreId.add(bigGenreResult.getInt("big_genre_id"));
				bigGenreName.add(bigGenreResult.getString("big_genre_name"));
			}
			editBean.setBigGenreId(bigGenreId);
			editBean.setBigGenreName(bigGenreName);
			bigGenrePst.close();
			
			//添付資料の取得
			ArrayList<Integer> homeSourceNo = new ArrayList<>();
			ArrayList<String> homeSourceName = new ArrayList<>();
			PreparedStatement sourcePst = con.prepareStatement("select * from home_source where home_content_id = ? ;");
			sourcePst.setString(1, homeContentId);
			ResultSet sourceResult = sourcePst.executeQuery();		
			while(sourceResult.next()){
				homeSourceNo.add(sourceResult.getInt("home_source_no"));
				homeSourceName.add(sourceResult.getString("home_source_name"));
			}
			editBean.setHomeSourceNo(homeSourceNo);
			editBean.setHomeSourceName(homeSourceName);
			sourcePst.close();
			
		}else{
			//取得失敗処理をここに記述
		}
		return editBean;
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

