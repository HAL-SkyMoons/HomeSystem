package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.SampleBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


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
	 * 全件取得する
	 *
	 * @return 全件
	 * @throws SQLException
	 */
	public List<SampleBean> findAll() throws SQLException {

		PreparedStatement select = con.prepareStatement("select * from sample;");

		ResultSet result = select.executeQuery();

		ArrayList<SampleBean> table = new ArrayList<SampleBean>();
		while (result.next()) {

			SampleBean record = new SampleBean();

			record.setSumple(result.getString("sample"));

			table.add(record);
		}
		return table;
	}

	/**
	 * 主キーで検索
	 *
	 * @param languageId
	 * @return
	 * @throws SQLException
	 * 追記分　Aを追加
	 */
	public SampleBean findOne(String sample) throws SQLException {

		PreparedStatement select = con.prepareStatement("select * from sample where sample = ? ;");

		select.setString(1, sample);
		ResultSet result = select.executeQuery();

		SampleBean record = new SampleBean();

		if (result.next()) {
			record.setSumple(result.getString("sample"));
		}

		return record;
	}

	/**
	 * 更新処理
	 *
	 * @param updateRecord 更新データ
	 * @return 影響のあった行数
	 * @throws SQLException
	 */
	public int update(SampleBean updateRecord) throws SQLException {

		PreparedStatement update =
			con.prepareStatement("update sample set sample = ? where sample = ? ;");

		update.setString(1, updateRecord.getSumple());

		return update.executeUpdate();
	}


	/**
	 * 新規保存
	 *
	 * @param newRecord 保存データ
	 * @return 影響のあった行数
	 * @throws SQLException
	 */
	public int insert(SampleBean newRecord) throws SQLException {

		PreparedStatement insert = con.prepareStatement("insert into sample (sample) values (?);");
		insert.setString(1, newRecord.getSumple());

		return insert.executeUpdate();
	}

	/**
	 * 削除処理
	 *
	 * @param languageId 削除対象
	 * @return 影響のあった行数
	 * @throws SQLException
	 */
	public int delete(String sample) throws SQLException {

		PreparedStatement delete = con.prepareStatement("delete from sample where sample = ?; ");
		delete.setString(1, sample);
		return delete.executeUpdate();
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

