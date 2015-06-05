package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentsRegistBean;
import jp.ac.hal.skymoons.beans.SampleBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class ContentsRegistDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public ContentsRegistDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public ContentsRegistDao(Connection con){
		this.con = con;
	}


	/**
	 * 新規保存
	 *
	 * @param newRecord 保存データ
	 * @return 影響のあった行数
	 * @throws SQLException
	 */
	public int insertContents(ContentsRegistBean bean) throws SQLException {

		PreparedStatement pst = con.prepareStatement("insert into home_contents(employee_id,home_content_title,home_content_date,home_content_comment) values (?,?,?,?);");
		pst.setString(1, "10000");
		pst.setString(2, "ｇｇｇｇｇｇｇｇｇｇｇ");
		pst.setString(3, "2000/01/01");
		pst.setString(4, "ｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒ");

		return pst.executeUpdate();
	}

	public int insertGenre(ContentsRegistBean bean) throws SQLException {
		
		PreparedStatement pst = con.prepareStatement("insert into genre() values (?,?);");
		pst.setString(1, "2000/01/01");
		pst.setString(2, "ｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒ");

		return pst.executeUpdate();
	}
	public int insertBigGenre(ContentsRegistBean bean) throws SQLException {
		
		PreparedStatement pst = con.prepareStatement("insert into big_genre() values (?,?);");
		pst.setString(1, "2000/01/01");
		pst.setString(2, "ｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒｒ");

		return pst.executeUpdate();
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

