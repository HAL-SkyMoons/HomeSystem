package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsDataBean;
import jp.ac.hal.skymoons.beans.ContentsEditBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;
import jp.ac.hal.skymoons.util.Utility;


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

	public ContentsEditBean findDetail(int homeContentId) throws SQLException {
		//戻り値のbeanを生成
		ContentsEditBean editBean = new ContentsEditBean();
		//コンテンツの取得
		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents where home_contents.home_content_id = ? ;");
		contentsPst.setInt(1, homeContentId);
		ResultSet contentsResult = contentsPst.executeQuery();
		if (contentsResult.next()) {
			//直接nullが入らない為企画IDをnullかどうか判定する
			String planId = contentsResult.getString("plan_id");
			if(planId != null){
				editBean.setPlanId(Integer.parseInt(planId));
			}else{
				editBean.setPlanId(null);
			}
			//コンテンツ各種データの取得
			editBean.setHomeContentId(contentsResult.getInt("home_content_id"));
			editBean.setHomeContentTitle(Utility.nlToBR(contentsResult.getString("home_content_title")));
			editBean.setHomeContentComment(Utility.nlToBR(contentsResult.getString("home_content_comment")));
			editBean.setEmployeeId(contentsResult.getString("employee_id"));
			
			//日付の取得
			Date startDatetime = contentsResult.getDate("start_datetime");
			editBean.setStartDatetime(new SimpleDateFormat("yyyy年MM月dd日hh時MM分").format(startDatetime));
			editBean.setStartYear(new SimpleDateFormat("yyyy").format(startDatetime));
			editBean.setStartMonth(new SimpleDateFormat("MM").format(startDatetime));
			editBean.setStartDay(new SimpleDateFormat("dd").format(startDatetime));
			editBean.setStartHour(new SimpleDateFormat("hh").format(startDatetime));
			editBean.setStartMinute(new SimpleDateFormat("MM").format(startDatetime));
			
			Date endDatetime = contentsResult.getDate("end_datetime");
			//値が無ければ現在時刻を入れる
			if(contentsResult.getDate("end_datetime") == null){
				endDatetime = (Date)new java.util.Date();
			}
			editBean.setEndDatetime(new SimpleDateFormat("yyyy年MM月dd日").format(endDatetime));
			editBean.setEndYear(new SimpleDateFormat("yyyy").format(endDatetime));
			editBean.setEndMonth(new SimpleDateFormat("MM").format(endDatetime));
			editBean.setEndDay(new SimpleDateFormat("dd").format(endDatetime));
			editBean.setEndHour(new SimpleDateFormat("hh").format(endDatetime));
			editBean.setEndMinute(new SimpleDateFormat("MM").format(endDatetime));
			
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
			
			genrePst.setInt(1, homeContentId);
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
			bigGenrePst.setInt(1, homeContentId);
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

			//企画日の取得
			editBean.setPlanDatetime("関連企画無し");
			if(editBean.getPlanId() != null){
				PreparedStatement planDatetimePst = con.prepareStatement(
						"select plan_datetime from plan where plan_id = ?;");
				planDatetimePst.setInt(1, editBean.getPlanId());
				ResultSet planDatetimeResult = planDatetimePst.executeQuery();
				if(planDatetimeResult.next()){
					if(planDatetimeResult.getDate("plan_datetime") != null){
						editBean.setPlanDatetime(new SimpleDateFormat("yyyy年MM月dd日hh時MM分").format(planDatetimeResult.getDate("plan_datetime")));
					}
				}
				planDatetimePst.close();
			}
			//添付資料の取得
			/*
			ArrayList<Integer> homeDataNo = new ArrayList<>();
			ArrayList<String> homeDataName = new ArrayList<>();
			PreparedStatement dataPst = con.prepareStatement("select * from home_data where home_content_id = ? ;");
			dataPst.setInt(1, homeContentId);
			ResultSet dataResult = dataPst.executeQuery();		
			while(dataResult.next()){
				homeDataNo.add(dataResult.getInt("home_data_no"));
				homeDataName.add(dataResult.getString("home_data_name"));
			}
			editBean.setHomeDataNo(homeDataNo);
			editBean.setHomeDataName(homeDataName);
			dataPst.close();
			*/
			
		}else{
			//取得失敗処理をここに記述
		}
		contentsPst.close();
		return editBean;
	}
	
	public ArrayList<ContentsDataBean> findData(int homeContentId) throws SQLException {
		//戻り値のListを生成
		ArrayList<ContentsDataBean> homeDataList = new ArrayList<>();
		//コンテンツの取得
		PreparedStatement homeDataPst = con.prepareStatement("select * from home_data where home_content_id = ? and delete_flag = 0 order by home_data_no;");
		homeDataPst.setInt(1, homeContentId);
		ResultSet homeDataResult = homeDataPst.executeQuery();
		while(homeDataResult.next()){
			ContentsDataBean homeDataBean = new ContentsDataBean();
			homeDataBean.setHomeContentId(homeContentId);
			homeDataBean.setHomeDataNo(homeDataResult.getInt("home_data_no"));
			homeDataBean.setHomeDataName(homeDataResult.getString("home_data_name"));
			homeDataBean.setDeleteFlag(homeDataResult.getInt("delete_flag"));
			homeDataBean.setFileImagePath("../" + Utility.getFileImage(homeDataResult.getString("home_data_name")));
			homeDataList.add(homeDataBean);
		}
		homeDataPst.close();
		return homeDataList;
	}
	
	public int homeDataUpload(int homeContentId, String fileName) throws SQLException {
		// 重複確認
		int nextFileNo = getHomeDataUploadNo(homeContentId);

		// 新規作成
		PreparedStatement insertDataPst = con.prepareStatement("insert into home_data (home_content_id,home_data_no,home_data_name) values (?,?,?);");
		insertDataPst.setInt(1, homeContentId);
		insertDataPst.setInt(2, nextFileNo);
		insertDataPst.setString(3, fileName);
		insertDataPst.executeUpdate();
		return nextFileNo;
	}
	
	public int getHomeDataUploadNo(int homeContentId) throws SQLException {
		PreparedStatement dataNoPst = con.prepareStatement("select max(home_data_no) from home_data where home_content_id = ? group by home_content_id;");
		dataNoPst.setInt(1, homeContentId);
		ResultSet result = dataNoPst.executeQuery();
		int ret = 1;
		if (result.next()) {
			ret = result.getInt("max(home_data_no)") + 1;
		}
		dataNoPst.close();
		return ret;
	}
	
	
	public int homeDataDelete(ContentsDataBean dataBean) throws SQLException {

		PreparedStatement deleteDataPst = con
				.prepareStatement("update home_data set delete_flag = '1' where home_content_id = ? and home_data_no = ?; ");
		deleteDataPst.setInt(1, dataBean.getHomeContentId());
		deleteDataPst.setInt(2, dataBean.getHomeDataNo());
		return deleteDataPst.executeUpdate();
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

