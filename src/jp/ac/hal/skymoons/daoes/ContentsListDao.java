package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

	public ArrayList<ContentsListBean> selectContents(String keyword, String employeeId, ArrayList<Integer> genreId, ArrayList<Integer> bigGenreId, String endContent, String existPlan, String order) throws SQLException {
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
		if(endContent != null && endContent.length() > 0){
			contentsSql += sqlword + "hc.end_datetime is null ";
		}
		if(existPlan != null && existPlan.length() > 0){
			contentsSql += sqlword + "hc.plan_id is not null ";
		}
		contentsSql += "group by hc.home_content_id ";
		if(genreId != null){
			contentsSql += "having count(*) >= " + genreId.size() + " ";
		}
		
		//並び替え
		//if(orderColumn == null || orderColumn.length() <= 0){
		//	orderColumn = "hc.home_content_id";
		//}	if (order != null) {
		String orderColumn = "hc.home_content_id desc";
		if(order != null && order.length() > 0){
		    switch (order) {
			    case "dateDesc":
			    	orderColumn = "hc.home_content_id desc";
			    	break;
			    case "dateAsc":
			    	orderColumn = "hc.home_content_id asc";
			    	break;
			    case "startDesc":
			    	orderColumn = "hc.start_datetime desc";
			    	break;
			    case "startAsc":
			    	orderColumn = "hc.start_datetime asc";
			    	break;
			    case "endDesc":
			    	orderColumn = "hc.end_datetime desc";
			    	break;
			    case "endAsc":
			    	orderColumn = "hc.end_datetime asc";
			    	break;
			    case "titleDesc":
			    	orderColumn = "hc.home_content_title desc";
			    	break;
			    case "titleAsc":
			    	orderColumn = "hc.home_content_title asc";
			    	break;
			    case "nameDesc":
			    	orderColumn = "u.last_name, u.first_name desc";
			    	break;
			    case "nameAsc":
			    	orderColumn = "u.last_name, u.first_name asc";
			    	break;
			}
		}
		contentsSql += "order by " + orderColumn + ";";
		System.out.println("生成SQL[" + contentsSql + "]");
		
		PreparedStatement contentsPst = con.prepareStatement(contentsSql);
		int setCnt = 1;
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
			//直接nullが入らない為企画IDをnullかどうか判定する
			String planId = contentsResult.getString("plan_id");
			if(planId != null){
				listBean.setPlanId(Integer.parseInt(planId));
			}else{
				listBean.setPlanId(null);
			}
			//beanに値を追加
			listBean.setHomeContentId(homeContentId);
			listBean.setHomeContentTitle(contentsResult.getString("home_content_title"));
			listBean.setHomeContentComment(contentsResult.getString("home_content_comment"));
			listBean.setStartDatetime(new SimpleDateFormat("yyyy年MM月dd日hh時MM分").format(contentsResult.getDate("start_datetime")));
			Date endDatetime = contentsResult.getDate("end_datetime");
			//値が無ければ現在時刻を入れる
			if(contentsResult.getDate("end_datetime") == null){
				listBean.setEndDatetime("未完了");
			}else{
				listBean.setEndDatetime(new SimpleDateFormat("yyyy年MM月dd日hh時MM分").format(endDatetime));
			}
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
			
			//ホメ数の取得
			PreparedStatement homeLogPst = con.prepareStatement(
					"select sum(home_point) from home_log group by home_content_id having home_content_id = ?;");

			homeLogPst.setInt(1, homeContentId);
			ResultSet homeLogResult = homeLogPst.executeQuery();
			
			if(homeLogResult.next()){
				listBean.setHomeCount(homeLogResult.getInt("sum(home_point)"));
			}
			
			homeLogPst.close();
			
			contentsList.add(listBean);
		}
		contentsResult.close();
		contentsPst.close();
		return contentsList;
	}
	
	
	public String findEmployeeName(String employeeId) throws SQLException{
		PreparedStatement employeePst = con.prepareStatement("select last_name, first_name from users where users.user_id = ?;");
		employeePst.setString(1, employeeId);
		ResultSet employeeResult = employeePst.executeQuery();
		String employeeName = null;
		if(employeeResult.next()){
			employeeName = employeeResult.getString("last_name") + employeeResult.getString("first_name");
		}
		employeeResult.close();
		employeePst.close();
		return employeeName;
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

