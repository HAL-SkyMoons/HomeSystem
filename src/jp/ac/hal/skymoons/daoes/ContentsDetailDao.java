package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsDataBean;
import jp.ac.hal.skymoons.beans.ContentsDetailBean;
import jp.ac.hal.skymoons.beans.ContentsDetailHomeLogBean;
import jp.ac.hal.skymoons.beans.FileBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;
import jp.ac.hal.skymoons.util.Utility;


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
	public ContentsDetailBean findDetail(int homeContentId) throws SQLException {
		//戻り値のbeanを生成
		ContentsDetailBean detailBean = new ContentsDetailBean();
		//コンテンツの取得
		PreparedStatement contentsPst = con.prepareStatement("select * from home_contents hc where hc.home_content_id = ? ;");
		contentsPst.setInt(1, homeContentId);
		ResultSet contentsResult = contentsPst.executeQuery();
		if (contentsResult.next()) {
			detailBean.setHomeContentId(contentsResult.getInt("home_content_id"));
			detailBean.setHomeContentTitle(contentsResult.getString("home_content_title"));
			detailBean.setHomeContentComment(contentsResult.getString("home_content_comment"));
			detailBean.setHomeContentDatetime(contentsResult.getString("home_content_datetime"));
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
			
			detailBean.setGenreId(genreId);
			detailBean.setGenreName(genreName);
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
			detailBean.setBigGenreId(bigGenreId);
			detailBean.setBigGenreName(bigGenreName);
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
			detailBean.setHomeDataNo(homeDataNo);
			detailBean.setHomeDataName(homeDataName);
			dataPst.close();
			*/
			
		}else{
			//取得失敗処理をここに記述
		}
		contentsPst.close();
		return detailBean;
	}
	public ArrayList<ContentsDetailHomeLogBean> findHomeLog(int homeContentId) throws SQLException {
		//戻り値のListを生成
		ArrayList<ContentsDetailHomeLogBean> homeLogList = new ArrayList<>();
		//コンテンツの取得
		PreparedStatement homeLogPst = con.prepareStatement("select * from home_log hl, batch b, users u1, users u2 where hl.home_content_id = ? and hl.home_target = u1.user_id and hl.home_user = u2.user_id and hl.batch_id = b.batch_id");
		homeLogPst.setInt(1, homeContentId);
		ResultSet homeLogResult = homeLogPst.executeQuery();
		while(homeLogResult.next()){
			ContentsDetailHomeLogBean homeLogBean = new ContentsDetailHomeLogBean();
			homeLogBean.setHomeContentId(homeContentId);
			homeLogBean.setHomeTarget(homeLogResult.getString("home_target"));
			homeLogBean.setHomeTargetFirstName(homeLogResult.getString("u1.first_name"));
			homeLogBean.setHomeTargetLastName(homeLogResult.getString("u1.last_name"));
			homeLogBean.setHomeUser(homeLogResult.getString("home_user"));
			homeLogBean.setHomeUserFirstName(homeLogResult.getString("u2.first_name"));
			homeLogBean.setHomeUserLastName(homeLogResult.getString("u2.last_name"));
			homeLogBean.setHomeDatetime(homeLogResult.getString("home_datetime"));
			homeLogBean.setBatchId(homeLogResult.getInt("batch_id"));
			homeLogBean.setBatchName(homeLogResult.getString("batch_name"));
			homeLogBean.setBatchComment(homeLogResult.getString("batch_comment"));
			homeLogBean.setHomePoint(homeLogResult.getInt("home_point"));
			homeLogBean.setHomeComment(homeLogResult.getString("home_comment"));
			homeLogList.add(homeLogBean);
		}
		homeLogPst.close();
		return homeLogList;
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

