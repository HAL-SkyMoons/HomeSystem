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
import jp.ac.hal.skymoons.beans.ContentsDetailHomeLogBean;
import jp.ac.hal.skymoons.beans.ContentsGenreBean;
import jp.ac.hal.skymoons.beans.ContentsSearchBean;
import jp.ac.hal.skymoons.beans.SampleBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsGenreDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsGenreDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsGenreDao(Connection con){
		this.con = con;
	}

	public ArrayList<ContentsGenreBean> findAll() throws SQLException{
		//戻り値のListを生成
		ArrayList<ContentsGenreBean> genreList = new ArrayList<>();
		//コンテンツの取得
		PreparedStatement genrePst = con.prepareStatement("select * from genre, big_genre where genre.big_genre_id = big_genre.big_genre_id order by genre.big_genre_id, genre.genre_id;");
		ResultSet genreResult = genrePst.executeQuery();
		while (genreResult.next()) {
			ContentsGenreBean genreBean = new ContentsGenreBean();
			genreBean.setGenreId(genreResult.getInt("genre_id"));
			genreBean.setGenreName(genreResult.getString("genre_name"));
			genreBean.setBigGenreId(genreResult.getInt("big_genre_id"));
			genreBean.setBigGenreName(genreResult.getString("big_genre_name"));
			genreList.add(genreBean);
		}
		genreResult.close();
		genrePst.close();
		return genreList;
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

