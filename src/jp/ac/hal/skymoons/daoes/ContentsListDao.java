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

	public ArrayList<ContentsListBean> selectContents(String titleKeyword, String commentKeyword, String employeeId, ArrayList<Integer> genreId, String orderColumn, String orderMode) throws SQLException {
		//SQLの生成
		String contentsSql = "select * from home_contents hc, home_genre hg where hc.home_content_id = hg.home_content_id ";
		String sqlword = "and ";
		if(titleKeyword != null && titleKeyword.length() > 0){
			contentsSql += sqlword + "hc.home_content_title like ? ";
		}
		if(commentKeyword != null && commentKeyword.length() > 0){
			contentsSql += sqlword + "hc.home_content_comment like ? ";
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
		contentsSql += "group by hc.home_content_id ";
		if(genreId != null){
			contentsSql += "having count(*) >= " + genreId.size() + " ";
		}
		contentsSql += "order by hc." + orderColumn + " " + orderMode + ";";
		
		PreparedStatement contentsPst = con.prepareStatement(contentsSql);
		int setCnt = 1;
		System.out.println(contentsSql);
		if(titleKeyword != null && titleKeyword.length() > 0){
			contentsPst.setString(setCnt, "%" + titleKeyword + "%");
			setCnt++;
		}
		if(commentKeyword != null && commentKeyword.length() > 0){
			contentsPst.setString(setCnt, "%" + commentKeyword + "%");
			setCnt++;
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
			
			ArrayList<Integer> bigGenreId = new ArrayList<>();
			ArrayList<String> bigGenreName = new ArrayList<>();
			
			while(bigGenreResult.next()){
				bigGenreId.add(bigGenreResult.getInt("big_genre_id"));
				bigGenreName.add(bigGenreResult.getString("big_genre_name"));
			}
			
			listBean.setBigGenreId(bigGenreId);
			listBean.setBigGenreName(bigGenreName);
			
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

