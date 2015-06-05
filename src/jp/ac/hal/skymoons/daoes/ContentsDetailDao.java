package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsDetailBean;
import jp.ac.hal.skymoons.beans.ContentsSearchBean;
import jp.ac.hal.skymoons.beans.SampleBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsDetailDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsDetailDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsDetailDao(Connection con){
		this.con = con;
	}

	/**
	 * 主キーで検索
	 *
	 * @param homeContentId
	 * @return
	 * @throws SQLException
	 * 追記分　Aを追加
	 */
	public ContentsDetailBean findDetail(String homeContentId) throws SQLException {
		//戻り値のbeanを生成
		ContentsDetailBean detailBean = new ContentsDetailBean();
		System.out.println(homeContentId);
		//コンテンツの取得
		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents hc where hc.home_content_id = ? ;");
		contentsPst.setString(1, homeContentId);
		ResultSet contentsResult = contentsPst.executeQuery();
		if (contentsResult.next()) {
			detailBean.setHomeContentId(contentsResult.getInt("home_content_id"));
			detailBean.setHomeContentTitle(contentsResult.getString("home_content_title"));
			//detailBean.setHomeContentDatetime(contentsResult.getString("home_content_datetime"));
			detailBean.setEmployeeId(contentsResult.getString("employee_id"));
			
			//名前の取得
			PreparedStatement namePst = con.prepareStatement("select * from users where user_id = ? ;");
			namePst.setString(1, contentsResult.getString("employee_id"));
			ResultSet nameResult = namePst.executeQuery();
			if(nameResult.next()){
				detailBean.setFirstName(nameResult.getString("first_name"));
				detailBean.setLastName(nameResult.getString("last_name"));
			}else{
				//取得失敗時の処理
			}

			//ジャンルの取得			
			PreparedStatement genrePst = con.prepareStatement(
					"select * from home_genre hg, genre g, big_genre bg "
					+ "where hg.home_content_id = ? "
					+ "and hg.genre_id = g.genre_id "
					+ "and g.big_genre_id = bg.big_genre_id;");
			
			genrePst.setString(1, homeContentId);
			ResultSet genreResult = genrePst.executeQuery();

			ArrayList<Integer> bigGenreId = new ArrayList<>();
			ArrayList<String> bigGenreName = new ArrayList<>();
			ArrayList<Integer> genreId = new ArrayList<>();
			ArrayList<String> genreName = new ArrayList<>();
			
			while(genreResult.next()){
				bigGenreId.add(genreResult.getInt("big_genre_id"));
				bigGenreName.add(genreResult.getString("big_genre_name"));
				genreId.add(genreResult.getInt("genre_id"));
				genreName.add(genreResult.getString("genre_name"));
			}
			detailBean.setBigGenreId(bigGenreId);
			detailBean.setBigGenreName(bigGenreName);
			detailBean.setGenreId(genreId);
			detailBean.setGenreName(genreName);
			
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
			detailBean.setHomeSourceNo(homeSourceNo);
			detailBean.setHomeSourceName(homeSourceName);
			
		}else{
			//取得失敗処理をここに記述
		}
		return detailBean;
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

