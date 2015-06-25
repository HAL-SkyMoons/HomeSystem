package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;

import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsDeleteDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsDeleteDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsDeleteDao(Connection con){
		this.con = con;
	}

	//データの完全削除
	public void hardDeleteDetail(int homeContentId) throws SQLException {
		//ジャンルの削除
		PreparedStatement genrePst = con.prepareStatement(
				"delete from home_genre where home_genre.home_content_id = ?;");
		genrePst.setInt(1, homeContentId);
		genrePst.executeUpdate();
		genrePst.close();
		
		//添付資料の削除
		PreparedStatement dataPst = con.prepareStatement("delete from home_data where home_content_id = ? ;");
		dataPst.setInt(1, homeContentId);
		dataPst.executeUpdate();
		dataPst.close();
		
		//コンテンツの削除
		PreparedStatement contentsPst = con.prepareStatement("delete from home_contents where home_contents.home_content_id = ? ;");
		contentsPst.setInt(1, homeContentId);
		contentsPst.executeUpdate();
		contentsPst.close();
		
		return;
	}

	//データの削除（データは削除しない）
	public void deleteDetail(int homeContentId) throws SQLException {
		//添付資料の削除
		PreparedStatement dataPst = con.prepareStatement("update home_data set delete_flag = '1' where home_content_id = ? ;");
		dataPst.setInt(1, homeContentId);
		dataPst.executeUpdate();
		dataPst.close();
		
		//コンテンツの削除
		PreparedStatement contentsPst = con.prepareStatement("update home_contents set delete_flag = '1' where home_content_id = ? ;");
		contentsPst.setInt(1, homeContentId);
		contentsPst.executeUpdate();
		contentsPst.close();
		
		return;
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

