package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			editBean.setHomeContentId(contentsResult.getInt("home_content_id"));
			editBean.setHomeContentTitle(contentsResult.getString("home_content_title"));
			editBean.setHomeContentComment(contentsResult.getString("home_content_comment"));
			editBean.setHomeContentDatetime(contentsResult.getString("home_content_datetime"));
			editBean.setEmployeeId(contentsResult.getString("employee_id"));
			editBean.setEndDate(contentsResult.getString("end_date"));
			
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

