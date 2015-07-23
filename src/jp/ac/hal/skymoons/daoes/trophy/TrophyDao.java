package jp.ac.hal.skymoons.daoes.trophy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyDetailBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;

public class TrophyDao {

    /** Connection */
    private Connection con;

    /**
     * コンストラクタ
     *
     * @throws NamingException
     * @throws SQLException
     */
    public TrophyDao() {
	ConnectionGet get = new ConnectionGet();
	con = get.getCon();
    }

    /**
     * コンストラクタ
     *
     * @param con
     */
    public TrophyDao(Connection con) {
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

    public List<TrophyBean> getTrophyList() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from trophy;");

	ResultSet result = select.executeQuery();

	ArrayList<TrophyBean> table = new ArrayList<TrophyBean>();
	while (result.next()) {

	    TrophyBean record = new TrophyBean();

	    record.setTrophyId(result.getInt("trophy_id"));
	    record.setTrophyName(result.getString("trophy_name"));
	    record.setTrophyComment(result.getString("trophy_comment"));
	    record.setDeleteFlag(result.getInt("delete_flag"));

	    table.add(record);
	}
	return table;
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

    public int trophyRegister(TrophyBean newRecord) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into trophy(trophy_name,trophy_comment) values (?,?);");
	insert.setString(1, newRecord.getTrophyName());
	insert.setString(2, newRecord.getTrophyComment());

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

    public void trophyDetailRegister(int trophyId,
	    HashMap<Integer, Integer> detail) throws SQLException {

	for (HashMap.Entry<Integer, Integer> entry : detail.entrySet()) {

	    PreparedStatement insert = con
		    .prepareStatement("insert into trophy_detail(trophy_id,batch_id,type_count) values (?,?,?);");
	    insert.setInt(1, trophyId);
	    insert.setInt(2, entry.getKey());
	    insert.setInt(3, entry.getValue());

	    insert.executeUpdate();

	}

    }

    public TrophyBean getTrophy(int trophyId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from trophy where trophy_id = ?;");

	select.setInt(1, trophyId);

	ResultSet result = select.executeQuery();

	TrophyBean record = new TrophyBean();
	if (result.next()) {

	    record.setTrophyId(result.getInt("trophy_id"));
	    record.setTrophyName(result.getString("trophy_name"));
	    record.setTrophyComment(result.getString("trophy_comment"));
	    record.setDeleteFlag(result.getInt("delete_flag"));

	}
	return record;
    }

    public List<TrophyDetailBean>  getTrophyDetail(int trophyId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select t.trophy_id,t.batch_id,b.batch_name,t.type_count from trophy_detail t,batch b where t.batch_id = b.batch_id and trophy_id = ?;");

	select.setInt(1, trophyId);

	ResultSet result = select.executeQuery();

	ArrayList<TrophyDetailBean> table = new ArrayList<TrophyDetailBean>();

	while (result.next()) {

	    TrophyDetailBean record = new TrophyDetailBean();

	    record.setTrophyId(result.getInt("t.trophy_id"));
	    record.setBatchId(result.getInt("t.batch_id"));
	    record.setBatchName(result.getString("b.batch_name"));
	    record.setTypeCount(result.getInt("t.type_count"));

	    table.add(record);

	}
	return table;
    }

    public int trophyDetailDelete(int trophyId) throws SQLException {

	PreparedStatement delete = con
		.prepareStatement("delete from trophy_detail where trophy_id = ?; ");
	delete.setInt(1, trophyId);
	return delete.executeUpdate();
    }

    public int trophyChange(TrophyBean updateRecord) throws SQLException {

	PreparedStatement update = con
		.prepareStatement("update trophy set trophy_name = ?,trophy_comment = ? where trophy_id = ? ;");

	update.setString(1, updateRecord.getTrophyName());
	update.setString(2, updateRecord.getTrophyComment());
	update.setInt(3, updateRecord.getTrophyId());

	return update.executeUpdate();

    }

}
