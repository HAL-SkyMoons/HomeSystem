package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.ContentGenreBean;
import jp.ac.hal.skymoons.beans.DepartmentBean;
import jp.ac.hal.skymoons.beans.EmployeeListBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.SampleBean;
import jp.ac.hal.skymoons.controllers.ConnectionGet;


public class SampleDao {

	/** Connection */
	private Connection con;

	/**
	 * コンストラクタ
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public SampleDao() throws NamingException, SQLException {
		ConnectionGet get = new ConnectionGet();
		if(con!=null){
			System.out.println("con get");
		}else{
			System.out.println("con can't get");
		}
		con = get.getCon();
		}

	/**
	 *コンストラクタ
	 * @param con
	 */
	public SampleDao(Connection con){
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

	/*
	 * 2015/05/26
	 * 中野 裕史郎
	 * 部署ごとの社員検索メソッド
	 * 引数:String(部署ID) 返り値:EmployeeListBean(社員名,ジャンル名(上位3つ),顔写真,部署名)
	 */
	public List<EmployeeListBean> getEmployeeByDepartment(int departmentId){

		ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();

		try {
			PreparedStatement select = con.prepareStatement("SELECT e.employee_ID,u.last_name,u.first_name d.department_name "
					+ "FROM Employees AS e JOIN Users AS u ON e.employee_ID = u.user_ID "
					+ "JOIN Departments AS d ON e.department_ID = d.department_ID WHERE e.department_ID = ?");
			select.setInt(1,departmentId);

			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeListBean record = new EmployeeListBean();
				record.setEmployeeId(result.getString("e.employee_ID"));
				record.setEmployeeName(result.getString("u.first_name")+result.getString("u.last_name"));
				record.setPhotoSrc(result.getString(""));
				record.setDepartmentName(result.getString("d.department_name"));
				record.setEmployeeGenre(getEmployeeGenre(result.getString("e.employee_ID")));
				resultTable.add(record);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/05/26
	 * 中野 裕史郎
	 * 社員1人のジャンル経験数上位三件を取得するメソッド
	 * 引数:String(社員ID) 返り値:String型の配列(ジャンル名)
	 */
	public ArrayList<String> getEmployeeGenre(String employeeId){

		ArrayList<String> resultTable = new ArrayList<String>();
		int count = 0;

		try {
			PreparedStatement select = con.prepareStatement("SELECT hg.genre_ID,g.genre_name,hc.employee_ID,COUNT(hg.genre_ID) "
					+ "FROM home_contents AS hc JOIN home_genre AS hg ON hc.home_content_ID = hg.home_content_ID "
					+ "JOIN genre AS g ON hg.genre_ID = g.genre_ID WHERE hc.employee_ID = ? "
					+ "GROUP BY hg.genre_ID ORDER BY COUNT(hg.genre_ID) DESC");
			select.setString(1, employeeId);

			ResultSet result = select.executeQuery();
			while(result.next()){
				resultTable.add(result.getString("g.genre_name"));
				if(count>=2){
					break;
				}
				count++;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/06/02
	 * 中野 裕史郎
	 * ジャンルで絞り込む
	 */
	public List<EmployeeListBean> getEmployeeByGenre(String genres[]){
		ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();
		ArrayList<ContentGenreBean> contentGenreTable = new ArrayList<ContentGenreBean>();
		//取得ジャンル数に応じた絞込み項目数
		int idCount = genres.length;
		String where = "?";
		int genreCount = 1;
		for (genreCount = 1; genreCount < idCount; genreCount++){
			where += ",?";
		}

		try {
			PreparedStatement select = con.prepareStatement("SELECT hc.home_content_id,hc.employee_id,count(*) FROM home_content AS hc "
					+"JOIN home_genre AS hg ON hc.home_content_id = hg.home_content_id "
					+"WHERE genre_id in ("+ where +") GROUP BY home_content_id");

			ResultSet result = select.executeQuery();
			while(result.next()){
				if(result.getInt("COUNT(*)")==genreCount){
					ContentGenreBean record = new ContentGenreBean();
					record.setEmployeeId(result.getString("hc.employee_id"));
					record.setContentId(result.getInt("hc.home_content_id"));
					record.setGenreCount(result.getInt("COUNT(*)"));
					contentGenreTable.add(record);
				}
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}

	/*
	 * 2015/06/09
	 * 中野裕史郎
	 * ジャンル情報を全権取得
	 */
	public List<GenreBean> getGenreList(){
		ArrayList<GenreBean> resultTable = new ArrayList<GenreBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT genre_id, genre_name , big_genre_id FROM genre");
			ResultSet result = select.executeQuery();
			while (result.next()){
				GenreBean record = new GenreBean();
				record.setGenreId(result.getInt("genre_id"));
				record.setGenreName(result.getString("genre_name"));
				record.setBigGenreId(result.getInt("big_genre_id"));
				resultTable.add(record);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}

	/*
	 * 2015/06/09
	 * 中野裕史郎
	 * 部署情報を全権取得
	 */
	public List<DepartmentBean> getDepartmentList(){
		ArrayList<DepartmentBean> resultTable = new ArrayList<DepartmentBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT department_id , department_name FROM departments");
			ResultSet result = select.executeQuery();
			while(result.next()){
			DepartmentBean record = new DepartmentBean();
			record.setDepartmentId(result.getInt("department_id"));
			record.setDepartmentName(result.getString("department_name"));
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/06/09
	 * 中野裕史郎
	 * 社員一覧取得
	 */
	public List<EmployeeListBean> getEmployee(){
		ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();

		try {
			PreparedStatement select = con.prepareStatement("SELECT e.employee_ID,u.last_name,u.first_name , d.department_name "
					+ "FROM Employees AS e JOIN Users AS u ON e.employee_ID = u.user_ID "
					+ "JOIN Departments AS d ON e.department_ID = d.department_ID");
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeListBean record = new EmployeeListBean();
				record.setEmployeeId(result.getString("e.employee_ID"));
				record.setEmployeeName(result.getString("u.first_name")+result.getString("u.last_name"));
				record.setPhotoSrc("/users/"+result.getString("e.employee_ID")+".png");
				record.setDepartmentName(result.getString("d.department_name"));
				record.setEmployeeGenre(getEmployeeGenre(result.getString("e.employee_ID")));
				resultTable.add(record);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
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

