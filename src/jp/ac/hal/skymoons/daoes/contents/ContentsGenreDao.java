package jp.ac.hal.skymoons.daoes.contents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.contents.ContentsGenreBean;
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

	public ArrayList<ContentsGenreBean> findGenre() throws SQLException{
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
	
	public ArrayList<ContentsGenreBean> findGenre(ArrayList<Integer> genreId) throws SQLException{
		//戻り値のListを生成
		ArrayList<ContentsGenreBean> genreList = new ArrayList<>();
		
		//sqlの生成
		String sql = "select * from genre, big_genre where genre.big_genre_id = big_genre.big_genre_id ";
		if(genreId != null && genreId.size() > 0){
			sql += "and genre.genre_id in(?";
			for(int i = 1;i < genreId.size();i++){
				sql += ",?";
			}
			sql += ") ";
		}
		sql += "order by genre.genre_name;";
		
		//コンテンツの取得
		PreparedStatement genrePst = con.prepareStatement(sql);
		
		int setCnt = 1;
		if(genreId != null && genreId.size() > 0){
			for(int genre : genreId){
				genrePst.setInt(setCnt, genre);
				setCnt++;
			}
		}
		
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
	
	public ArrayList<ContentsGenreBean> findBigGenre() throws SQLException{
		//戻り値のListを生成
		ArrayList<ContentsGenreBean> bigGenreList = new ArrayList<>();
		//コンテンツの取得
		PreparedStatement bigGenrePst = con.prepareStatement("select * from big_genre;");
		ResultSet bigGenreResult = bigGenrePst.executeQuery();
		while (bigGenreResult.next()) {
			ContentsGenreBean genreBean = new ContentsGenreBean();
			genreBean.setBigGenreId(bigGenreResult.getInt("big_genre_id"));
			genreBean.setBigGenreName(bigGenreResult.getString("big_genre_name"));
			bigGenreList.add(genreBean);
		}
		bigGenreResult.close();
		bigGenrePst.close();
		return bigGenreList;
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

