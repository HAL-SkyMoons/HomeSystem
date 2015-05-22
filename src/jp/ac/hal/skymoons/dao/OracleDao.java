package jp.ac.hal.skymoons.dao;

import java.sql.*;
import java.util.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.ac.hal.skymoons.data.OracleData;
import jp.ac.hal.skymoons.selectors.DataSelector;



public class OracleDao {
	//コネクション保持用
	private Connection con = null;
	
	//コンストラクタ
	//コネクションの確立を行う
	public OracleDao() throws NamingException, SQLException{
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/context");
		this.con = ds.getConnection();
	}

	//SELECT文メソッド
	public List<OracleData> select(String sql, String uri) throws SQLException{
		ArrayList<OracleData> list = new ArrayList<OracleData>();
		//ステートメントの生成
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet result = pst.executeQuery();
		list = new ArrayList<OracleData>();
		while(result.next()){
			DataSelector Selector = new DataSelector();
			OracleData oData = Selector.choice(uri);
			oData.setResultValues(result);
			list.add(oData);
		}
		//ステートメントの解放
		pst.close();
		return list;
	}

	//INSERT文メソッド
	public int insert(OracleData oData, String sql)throws SQLException{
		//ステートメントの生成
		PreparedStatement pst = this.con.prepareStatement(sql);
		
		//要相談
		int cnt = 1;
		for(String data : oData.getValues()){
			System.out.println(data);
			pst.setString(cnt, data);
			cnt++;
		}
		
		
		//SQLの実行
		int ret = pst.executeUpdate();
		//ステートメントの解放
		pst.close();
		return ret;
	}

	//UPDATE文メソッド
	public int update(OracleData oData, String sql)throws SQLException{
		//ステートメントの生成
		PreparedStatement pst = this.con.prepareStatement(sql);
		
		//要相談
		int cnt = 1;
		for(String data : oData.getValues()){
			System.out.println(data);
			pst.setString(cnt, data);
			cnt++;
		}
		
		//SQLの実行
		int ret = pst.executeUpdate();
		//ステートメントの解放
		pst.close();
		return ret;
	}
	
	//DELETE文メソッド
	public int delete(OracleData oData, String sql)throws SQLException{
		//ステートメントの生成
		PreparedStatement pst = this.con.prepareStatement(sql);
		
		//要相談
		int cnt = 1;
		for(String data : oData.getValues()){
			System.out.println(data);
			pst.setString(cnt, data);
			cnt++;
		}
		
		//SQLの実行
		int ret = pst.executeUpdate();
		//ステートメントの解放
		pst.close();
		return ret;
	}

	
	//コミット処理
	public void commit() throws SQLException{
		this.con.commit();
	}
	
	//ロールバック処理
	public void rollback() throws SQLException{
		this.con.rollback();
	}
	
	//コネクションの解放
	public void close() throws SQLException{
		try{
			this.con.close();
		}catch(SQLException e){
			this.con = null;
			throw e;
		}
	}
	
	//オートコミットの設定メソッド
	public void setAutoCommit(boolean bool) throws SQLException{
		this.con.setAutoCommit(bool);
	}
}
