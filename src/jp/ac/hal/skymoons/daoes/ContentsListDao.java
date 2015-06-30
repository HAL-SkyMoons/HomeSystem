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

	public ArrayList<ContentsListBean> selectContents(String keyword, String employeeId, ArrayList<Integer> genreId, ArrayList<Integer> bigGenreId, String orderColumn, String orderMode) throws SQLException {
		//SQLの生成
		String contentsSql = "select * from home_contents hc, home_genre hg, genre g, users u "
				+ "where hc.delete_flag != '1' "
				+ "and hc.home_content_id = hg.home_content_id "
				+ "and hg.genre_id = g.genre_id "
				+ "and u.user_id = hc.employee_id ";
		String sqlword = "and ";
		if(keyword != null && keyword.length() > 0){
			contentsSql += sqlword + "hc.home_content_title like ? or hc.home_content_comment like ? ";
		}
		if(employeeId != null && employeeId.length() > 0){
			contentsSql += sqlword + "hc.employee_id = ? ";
		}
		if(genreId != null && genreId.size() > 0){
			String genre = "?";
			for(int i = 1;i < genreId.size();i++){
				genre += ",?";
			}
			contentsSql += sqlword + "hg.genre_id in(" + genre + ") ";
		}
		if(bigGenreId != null && bigGenreId.size() > 0){
			String bigGenre = "?";
			for(int i = 1;i < genreId.size();i++){
				bigGenre += ",?";
			}
			contentsSql += sqlword + "g.big_genre_id in(" + bigGenre + ") ";
		}
		contentsSql += "group by hc.home_content_id ";
		if(genreId != null){
			contentsSql += "having count(*) >= " + genreId.size() + " ";
		}
		
		//並び替え
		if(orderColumn == null || orderColumn.length() <= 0){
			orderColumn = "hc.home_content_id";
		}
		contentsSql += "order by " + orderColumn + ";";
		
		PreparedStatement contentsPst = con.prepareStatement(contentsSql);
		int setCnt = 1;
		System.out.println("生成SQL[" + contentsSql + "]");
		if(keyword != null && keyword.length() > 0){
			contentsPst.setString(setCnt, "%" + keyword + "%");
			contentsPst.setString(setCnt + 1, "%" + keyword + "%");
			setCnt += 2;
		}
		if(employeeId != null && employeeId.length() > 0){
			contentsPst.setString(setCnt, employeeId);
			setCnt++;
		}
		if(genreId != null && genreId.size() > 0){
			for(int genre : genreId){
				contentsPst.setInt(setCnt, genre);
				setCnt++;
			}
		}
		if(bigGenreId != null && bigGenreId.size() > 0){
			for(int bigGenre : bigGenreId){
				contentsPst.setInt(setCnt, bigGenre);
				setCnt++;
			}
		}
		
		//データ取得処理
		ResultSet contentsResult = contentsPst.executeQuery();		
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
			listBean.setStartDatetime(contentsResult.getString("start_datetime"));
			listBean.setEmployeeId(contentsResult.getString("employee_id"));
			//listBean.setDeleteFlag(contentsResult.getInt("delete_flag"));
			
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
			
			ArrayList<Integer> genreIdList = new ArrayList<>();
			ArrayList<String> genreNameList = new ArrayList<>();
			
			while(genreResult.next()){
				genreIdList.add(genreResult.getInt("genre_id"));
				genreNameList.add(genreResult.getString("genre_name"));
			}
			
			listBean.setGenreId(genreIdList);
			listBean.setGenreName(genreNameList);
			
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
			
			ArrayList<Integer> bigGenreIdList = new ArrayList<>();
			ArrayList<String> bigGenreNameList = new ArrayList<>();
			
			while(bigGenreResult.next()){
				bigGenreIdList.add(bigGenreResult.getInt("big_genre_id"));
				bigGenreNameList.add(bigGenreResult.getString("big_genre_name"));
			}
			
			listBean.setBigGenreId(bigGenreIdList);
			listBean.setBigGenreName(bigGenreNameList);
			
			bigGenrePst.close();
						
			contentsList.add(listBean);
		}
		contentsResult.close();
		contentsPst.close();
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

