package jp.ac.hal.skymoons.daoes.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;

public class BatchDao {

    /** Connection */
    private Connection con;

    /**
     * コンストラクタ
     *
     * @throws NamingException
     * @throws SQLException
     */
    public BatchDao() {
	ConnectionGet get = new ConnectionGet();
	con = get.getCon();
    }

    /**
     * コンストラクタ
     *
     * @param con
     */
    public BatchDao(Connection con) {
	this.con = con;
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

    public List<BatchBean> getBatchList() throws SQLException {

	PreparedStatement select = con.prepareStatement("select * from batch;");

	ResultSet result = select.executeQuery();

	ArrayList<BatchBean> table = new ArrayList<BatchBean>();
	while (result.next()) {

	    BatchBean record = new BatchBean();

	    record.setBatchId(result.getInt("batch_id"));
	    record.setBatchName(result.getString("batch_name"));
	    record.setBatchComment(result.getString("batch_comment"));

	    table.add(record);
	}
	return table;
    }

    public int batchRegister(BatchBean newRecord) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into batch(batch_name,batch_comment) values (?,?);");
	insert.setString(1, newRecord.getBatchName());
	insert.setString(2, newRecord.getBatchComment());

	insert.executeUpdate();

	PreparedStatement select = con
		.prepareStatement("SELECT LAST_INSERT_ID();");

	ResultSet result = select.executeQuery();

	int id = 0;

	if (result.next()) {
	    id = result.getInt(1);
	}

	return id;

    }

    public BatchBean getBatchDetail(int batchId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from batch where batch_id = ?;");

	select.setInt(1, batchId);

	ResultSet result = select.executeQuery();

	BatchBean record = new BatchBean();
	if (result.next()) {

	    record.setBatchId(result.getInt("batch_id"));
	    record.setBatchName(result.getString("batch_name"));
	    record.setBatchComment(result.getString("batch_comment"));

	}
	return record;
    }

    public int batchChange(BatchBean updateRecord) throws SQLException {

	PreparedStatement update = con
		.prepareStatement("update batch set batch_name = ?,batch_comment = ? where batch_id = ? ;");

	update.setString(1, updateRecord.getBatchName());
	update.setString(2, updateRecord.getBatchComment());
	update.setInt(3, updateRecord.getBatchId());

	return update.executeUpdate();

    }

}
