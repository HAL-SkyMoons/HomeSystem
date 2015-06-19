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
import jp.ac.hal.skymoons.beans.EmployeeBadgeBean;
import jp.ac.hal.skymoons.beans.EmployeeGenreBean;
import jp.ac.hal.skymoons.beans.EmployeeHomeLogBean;
import jp.ac.hal.skymoons.beans.EmployeeListBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.EmployeePlanBean;
import jp.ac.hal.skymoons.beans.EmployeePlanCommentBean;
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
			PreparedStatement select = con.prepareStatement("SELECT e.employee_ID,u.last_name,u.first_name , d.department_name "
					+ "FROM Employees AS e JOIN Users AS u ON e.employee_ID = u.user_ID "
					+ "JOIN Departments AS d ON e.department_ID = d.department_ID  WHERE e.department_ID = ? ORDER BY e.employee_ID");
			select.setInt(1,departmentId);

			ResultSet result = select.executeQuery();
			while(result.next()){
				System.out.println("Where Department Selecting ...");
				EmployeeListBean record = new EmployeeListBean();
				record.setEmployeeId(result.getString("e.employee_ID"));
				record.setEmployeeName(result.getString("u.last_name")+result.getString("u.first_name"));
				record.setPhotoSrc("");
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
	 * 2015/06/02
	 * 中野 裕史郎
	 * ジャンルで絞り込む①
	 * 指定されたジャンルを網羅したコンテンツ情報の取得
	 */
	public List<EmployeeListBean> getEmployeeByGenre(String genres[]){
		ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();
		ArrayList<ContentGenreBean> contentGenreTable = new ArrayList<ContentGenreBean>();
		//取得ジャンル数に応じた絞込み項目数
		int idCount = genres.length;
		System.out.println("Genre All Count is "+idCount);
		String where = "?";
		int genreCount = 1;
		for (genreCount = 1; genreCount < idCount; genreCount++){
			where += ",?";
		}

		System.out.println("Where Text is 「"+where+"」");

		//条件を満たすコンテンツIDのString
		ArrayList<String> contents = new ArrayList<String>();

		try {
			PreparedStatement select = con.prepareStatement("SELECT hc.home_content_id,hc.employee_id,count(*) FROM home_contents AS hc "
					+"JOIN home_genre AS hg ON hc.home_content_id = hg.home_content_id "
					+"WHERE genre_id in ("+ where +") GROUP BY home_content_id HAVING count(*) = ?");
		for (genreCount = 0; genreCount < idCount; genreCount++){
			select.setInt(genreCount+1,Integer.parseInt(genres[genreCount]) );
			System.out.println("GenreID is "+genres[genreCount]);
		}
			select.setInt(idCount+1, genres.length);
		ResultSet result = select.executeQuery();
			while(result.next()){
				contents.add(result.getString("hc.home_content_id"));
			}
			resultTable = getEmployeeByContents(contents);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}

	/*
	 * 2015/06/16
	 * 中野 裕史郎
	 * ジャンルから絞り込む②
	 * コンテンツリストから社員情報を取得
	 */
	public ArrayList<EmployeeListBean> getEmployeeByContents(ArrayList<String> contents){
		ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();
		//取得コンテンツに対応したwhere句の生成
		int idCount = contents.size();
		String where = "?";
		int contentCount = 1;
		for (contentCount = 1; contentCount < idCount; contentCount++){
			where += ",?";
		}
		//SQL発行
		try {
			PreparedStatement select = con.prepareStatement("SELECT e.employee_id ,u.last_name,u.first_name ,d.department_name,count(*) FROM employees AS e "
				+"JOIN home_contents AS hc ON hc.employee_id = e.employee_id "
				+"JOIN users AS u ON u.user_id = e.employee_ID "
				+"JOIN departments AS d ON d.department_ID = e.department_ID "
				+"WHERE home_content_id in ("+where+") GROUP BY e.employee_id "
				+"ORDER BY COUNT(*) DESC ,e.employee_id");

			for (contentCount = 0; contentCount < idCount; contentCount++){
				select.setInt(contentCount+1, Integer.parseInt(contents.get(contentCount)));
				System.out.println("ContentID is "+contents.get(contentCount));
			}
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeListBean recode = new EmployeeListBean();
				recode.setEmployeeId(result.getString("e.employee_id"));
				recode.setEmployeeName(result.getString("u.last_name")+result.getString("u.first_name"));
				recode.setDepartmentName(result.getString("d.department_name"));
				recode.setEmployeeGenre(getEmployeeGenre(result.getString("e.employee_id")));
				System.out.println(result.getString("e.employee_id")+result.getString("u.last_name")+","+result.getInt("COUNT(*)"));
				resultTable.add(recode);
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
	 * 2015/06/09
	 * 中野裕史郎
	 * ジャンル情報を全権取得
	 */
	public List<GenreBean> getGenreList(){
		ArrayList<GenreBean> resultTable = new ArrayList<GenreBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT genre_id, genre_name , big_genre_id FROM genre ORDER BY big_genre_id , genre_id DESC");
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
	 * 社員一覧取得
	 */
	public List<EmployeeListBean> getEmployee(){
		ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();

		try {
			PreparedStatement select = con.prepareStatement("SELECT e.employee_ID,u.last_name,u.first_name , d.department_name "
					+ "FROM Employees AS e JOIN Users AS u ON e.employee_ID = u.user_ID "
					+ "JOIN Departments AS d ON e.department_ID = d.department_ID ORDER BY e.employee_ID");
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeListBean record = new EmployeeListBean();
				record.setEmployeeId(result.getString("e.employee_ID"));
				record.setEmployeeName(result.getString("u.last_name")+result.getString("u.first_name"));
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
	/*
	 * 2015/06/17
	 * 中野 裕史郎
	 * 社員の詳細情報取得
	 */
	public List<EmployeePageBean> getEmployeeDetail(String employeeId){
		ArrayList<EmployeePageBean> resultTable = new ArrayList<EmployeePageBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT e.employee_ID , e.comment , d.department_name , u.last_name ,u.first_name , e.level ,e.experience "
					+"FROM employees AS e JOIN departments AS d ON e.department_id = d.department_id "
					+"JOIN users AS u ON e.employee_ID = u.user_id WHERE e.employee_id LIKE ?");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeePageBean recode = new EmployeePageBean();
				recode.setEmployeeId(result.getString("e.employee_ID"));
				recode.setEmployeeName(result.getString("u.last_name")+result.getString("u.first_name"));
				recode.setDepartmentName(result.getString("d.department_name"));
				recode.setEmployeeComment(result.getString("e.comment"));
				recode.setLevel(result.getInt("e.level"));
				recode.setExperience(result.getInt("e.experience"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}

	/*
	 * 2015/06/17
	 * 中野 裕史郎
	 * 社員一人が持つバッジ情報の取得
	 */
	public List<EmployeeBadgeBean> getEmployeeDetailOfBadge(String employeeId){
		ArrayList<EmployeeBadgeBean> resultTable = new ArrayList<EmployeeBadgeBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
					+"GROUP BY hl.batch_id ORDER BY hl.batch_id");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeBadgeBean recode = new EmployeeBadgeBean();
				recode.setBadgeName(result.getString("b.batch_name"));
				recode.setBadgeCount(result.getInt("COUNT(*)"));
				recode.setBadgeImgPath(result.getString("hl.batch_id"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return resultTable;
	}

	/*
	 * 2015/6/19
	 * 中野 裕史郎
	 * 社員一人が持つジャンル情報の取得
	 */
	public List<EmployeeGenreBean> getEmployeeDetailOfGenre(String employeeId){
		ArrayList<EmployeeGenreBean> resultTable = new ArrayList<EmployeeGenreBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT hg.genre_id , g.genre_name , COUNT(*) FROM home_contents AS hc "
					+"JOIN home_genre AS hg ON hc.home_content_ID = hg.home_content_id JOIN genre AS g ON hg.genre_id = g.genre_id "
					+"WHERE hc.employee_id LIKE ? GROUP BY hg.genre_id ORDER BY COUNT(*) DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeGenreBean recode = new EmployeeGenreBean();
				recode.setGenreName(result.getString("g.genre_name"));
				recode.setGenreCount(result.getInt("COUNT(*)"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return resultTable;
	}

	/*
	 * 2015/6/19
	 * 中野 裕史郎
	 * 社員一人の立案企画情報取得
	 */
	public List<EmployeePlanBean> getEmployeeDetailOfPlan(String employeeId){
		ArrayList<EmployeePlanBean> resultTable = new ArrayList<EmployeePlanBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT plan_title , plan_datetime FROM `plan` WHERE planner LIKE ? ORDER BY plan_datetime DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeePlanBean recode = new EmployeePlanBean();
				recode.setPlanTitle(result.getString("plan_title"));
				recode.setDays(result.getDate("plan_datetime"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/6/19
	 * 中野 裕史郎
	 * 社員一人の企画コメント取得
	 */
	public List<EmployeePlanCommentBean> getEmployeeDetailOfPlanComment(String employeeId){
		ArrayList<EmployeePlanCommentBean> resultTable = new ArrayList<EmployeePlanCommentBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT p.plan_title , pc.comment_datetime ,  u.last_name , u.first_name "
					+"FROM plan_comment AS pc JOIN plan AS p ON pc.plan_id = p.plan_id JOIN users AS u ON p.planner = u.user_id "
					+"WHERE comment_user LIKE ? ORDER BY pc.comment_datetime DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeePlanCommentBean recode = new EmployeePlanCommentBean();
				recode.setPlannerName(result.getString("u.last_name")+result.getString("u.first_name"));
				recode.setPlanName(result.getString("p.plan_title"));
				recode.setDays(result.getDate("pc.comment_datetime"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/6/19
	 * 中野 裕史郎
	 * 社員一人のほめほめログ取得
	 */
	public List<EmployeeHomeLogBean> getEmployeeDetailOfHomeLog(String employeeId){
		ArrayList<EmployeeHomeLogBean> resultTable = new ArrayList<EmployeeHomeLogBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT hl.home_target , hl.home_datetime , u.last_name ,u.first_name FROM home_log AS hl "
					+"JOIN users AS u ON hl.home_target = u.user_id WHERE home_user LIKE ? ORDER BY home_datetime DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeHomeLogBean recode = new EmployeeHomeLogBean();
				recode.setTargetName(result.getString("u.last_name")+result.getString("u.first_name"));
				recode.setDays(result.getDate("hl.home_datetime"));
				resultTable.add(recode);
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

