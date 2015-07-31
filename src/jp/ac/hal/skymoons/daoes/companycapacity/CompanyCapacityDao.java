package jp.ac.hal.skymoons.daoes.companycapacity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.companycapacity.CompanyCapacityBean;
import jp.ac.hal.skymoons.beans.companycapacity.CompanyCapacityDetailBean;
import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;

public class CompanyCapacityDao {

    /** Connection */
    private Connection con;

    /**
     * コンストラクタ
     *
     * @throws NamingException
     * @throws SQLException
     */
    public CompanyCapacityDao() {
	ConnectionGet get = new ConnectionGet();
	con = get.getCon();
    }

    /**
     * コンストラクタ
     *
     * @param con
     */
    public CompanyCapacityDao(Connection con) {
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

    public List<CompanyCapacityBean> getCompanyCapacityList()
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from company_capacity;");

	ResultSet result = select.executeQuery();

	ArrayList<CompanyCapacityBean> table = new ArrayList<CompanyCapacityBean>();
	while (result.next()) {

	    CompanyCapacityBean record = new CompanyCapacityBean();

	    record.setCapacityId(result.getInt("capacity_id"));
	    record.setCapacityName(result.getString("capacity_name"));
	    record.setCapacityComment(result.getString("capacity_comment"));
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

    public int CompanyCapacityRegister(CompanyCapacityBean newRecord)
	    throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into company_capacity(capacity_name,capacity_comment) values (?,?);");
	insert.setString(1, newRecord.getCapacityName());
	insert.setString(2, newRecord.getCapacityComment());

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

    public void CompanyCapacityDetailRegister(int capacityId,
	    HashMap<Integer, Integer> detail) throws SQLException {

	for (HashMap.Entry<Integer, Integer> entry : detail.entrySet()) {

	    PreparedStatement insert = con
		    .prepareStatement("insert into capacity_detail(capacity_id,batch_id,type_count) values (?,?,?);");
	    insert.setInt(1, capacityId);
	    insert.setInt(2, entry.getKey());
	    insert.setInt(3, entry.getValue());

	    insert.executeUpdate();

	}

    }

    public CompanyCapacityBean getCompanyCapacity(int capacityId)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from company_capacity where capacity_id = ?;");

	select.setInt(1, capacityId);

	ResultSet result = select.executeQuery();

	CompanyCapacityBean record = new CompanyCapacityBean();
	if (result.next()) {

	    record.setCapacityId(result.getInt("capacity_id"));
	    record.setCapacityName(result.getString("capacity_name"));
	    record.setCapacityComment(result.getString("capacity_comment"));
	    record.setDeleteFlag(result.getInt("delete_flag"));

	}
	return record;
    }

    public List<CompanyCapacityDetailBean> getCompanyCapacityDetail(
	    int capacityId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select c.capacity_id,c.batch_id,b.batch_name,c.type_count from capacity_detail c, batch b where c.batch_id = b.batch_id and capacity_id = ?;");

	select.setInt(1, capacityId);

	ResultSet result = select.executeQuery();

	ArrayList<CompanyCapacityDetailBean> table = new ArrayList<CompanyCapacityDetailBean>();

	while (result.next()) {

	    CompanyCapacityDetailBean record = new CompanyCapacityDetailBean();

	    record.setCapacityId(result.getInt("c.capacity_id"));
	    record.setBatchId(result.getInt("c.batch_id"));
	    record.setTypeCount(result.getInt("c.type_count"));
	    record.setBatchName(result.getString("b.batch_name"));

	    table.add(record);
	}
	return table;
    }

    public int CompanyCapacityDetailDelete(int capacityId) throws SQLException {

	PreparedStatement delete = con
		.prepareStatement("delete from capacity_detail where capacity_id = ?; ");
	delete.setInt(1, capacityId);
	return delete.executeUpdate();
    }

    public int CompanyCapacityChange(CompanyCapacityBean updateRecord)
	    throws SQLException {

	PreparedStatement update = con
		.prepareStatement("update company_capacity set capacity_name = ?,capacity_comment = ? where capacity_id = ? ;");

	update.setString(1, updateRecord.getCapacityName());
	update.setString(2, updateRecord.getCapacityComment());
	update.setInt(3, updateRecord.getCapacityId());

	return update.executeUpdate();
    }

}
