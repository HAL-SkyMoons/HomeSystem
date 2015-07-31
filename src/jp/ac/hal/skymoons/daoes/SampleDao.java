package jp.ac.hal.skymoons.daoes;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.BigGenreBean;
import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.ContentGenreBean;
import jp.ac.hal.skymoons.beans.DepartmentBean;
import jp.ac.hal.skymoons.beans.EmployeeBatchBean;
import jp.ac.hal.skymoons.beans.EmployeeCapacityBean;
import jp.ac.hal.skymoons.beans.EmployeeCompanyCapacityBean;
import jp.ac.hal.skymoons.beans.EmployeeGenreBean;
import jp.ac.hal.skymoons.beans.EmployeeHomeLogBean;
import jp.ac.hal.skymoons.beans.EmployeeListBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.EmployeePlanBean;
import jp.ac.hal.skymoons.beans.EmployeePlanCommentBean;
import jp.ac.hal.skymoons.beans.EmployeeTrophyBean;
import jp.ac.hal.skymoons.beans.FileBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.HomeBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.PlanPointBean;
import jp.ac.hal.skymoons.beans.PlanPointsBean;
import jp.ac.hal.skymoons.beans.SampleBean;
import jp.ac.hal.skymoons.beans.UserBean;
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
	if (con != null) {
	    System.out.println("con get");
	} else {
	    System.out.println("con can't get");
	}
	con = get.getCon();
    }

    /**
     * コンストラクタ
     *
     * @param con
     */
    public SampleDao(Connection con) {
	this.con = con;
    }

    /**
     * 全件取得する
     *
     * @return 全件
     * @throws SQLException
     */
    public List<SampleBean> findAll() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from sample;");

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
     *             追記分　Aを追加
     */
    public SampleBean findOne(String sample) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from sample where sample = ? ;");

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
     * @param updateRecord
     *            更新データ
     * @return 影響のあった行数
     * @throws SQLException
     */
    public int update(SampleBean updateRecord) throws SQLException {

	PreparedStatement update = con
		.prepareStatement("update sample set sample = ? where sample = ? ;");

	update.setString(1, updateRecord.getSumple());

	return update.executeUpdate();
    }

    /**
     * 新規保存
     *
     * @param newRecord
     *            保存データ
     * @return 影響のあった行数
     * @throws SQLException
     */
    public int insert(SampleBean newRecord) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into sample (sample) values (?);");
	insert.setString(1, newRecord.getSumple());

	return insert.executeUpdate();
    }

    /**
     * 削除処理
     *
     * @param languageId
     *            削除対象
     * @return 影響のあった行数
     * @throws SQLException
     */
    public int delete(String sample) throws SQLException {

	PreparedStatement delete = con
		.prepareStatement("delete from sample where sample = ?; ");
	delete.setString(1, sample);
	return delete.executeUpdate();
    }

    /*
     * 2015/05/26 中野 裕史郎 部署ごとの社員検索メソッド 引数:String(部署ID)
     * 返り値:EmployeeListBean(社員名,ジャンル名(上位3つ),顔写真,部署名)
     */
    public List<EmployeeListBean> getEmployeeByDepartment(int departmentId) {

	ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();

	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT e.employee_ID,u.last_name,u.first_name , d.department_name "
			    + "FROM Employees AS e JOIN Users AS u ON e.employee_ID = u.user_ID "
			    + "JOIN Departments AS d ON e.department_ID = d.department_ID  WHERE e.department_ID = ? ORDER BY e.employee_ID");
	    select.setInt(1, departmentId);

	    ResultSet result = select.executeQuery();
	    while (result.next()) {
		System.out.println("Where Department Selecting ...");
		EmployeeListBean record = new EmployeeListBean();
		record.setEmployeeId(result.getString("e.employee_ID"));
		record.setEmployeeName(result.getString("u.last_name")
			+ result.getString("u.first_name"));
		record.setPhotoSrc("");
		record.setDepartmentName(result.getString("d.department_name"));
		record.setEmployeeGenre(getEmployeeGenre(result
			.getString("e.employee_ID")));
		resultTable.add(record);
	    }
	} catch (SQLException e) {
	    // TODO 自動生成された catch ブロック
	    e.printStackTrace();
	}
	return resultTable;
    }

    /*
     * 2015/06/02 中野 裕史郎 ジャンルで絞り込む① 指定されたジャンルを網羅したコンテンツ情報の取得
     */
    public List<EmployeeListBean> getEmployeeByGenre(String genres[]) {
	ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();
	ArrayList<ContentGenreBean> contentGenreTable = new ArrayList<ContentGenreBean>();
	// 取得ジャンル数に応じた絞込み項目数
	int idCount = genres.length;
	System.out.println("Genre All Count is " + idCount);
	String where = "?";
	int genreCount = 1;
	for (genreCount = 1; genreCount < idCount; genreCount++) {
	    where += ",?";
	}

	System.out.println("Where Text is 「" + where + "」");

	// 条件を満たすコンテンツIDのString
	ArrayList<String> contents = new ArrayList<String>();

	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT hc.home_content_id,hc.employee_id,count(*) FROM home_contents AS hc "
			    + "JOIN home_genre AS hg ON hc.home_content_id = hg.home_content_id "
			    + "WHERE genre_id in ("
			    + where
			    + ") GROUP BY home_content_id HAVING count(*) = ?");
	    for (genreCount = 0; genreCount < idCount; genreCount++) {
		select.setInt(genreCount + 1,
			Integer.parseInt(genres[genreCount]));
		System.out.println("GenreID is " + genres[genreCount]);
	    }
	    select.setInt(idCount + 1, genres.length);
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
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
     * 2015/06/16 中野 裕史郎 ジャンルから絞り込む② コンテンツリストから社員情報を取得
     */
    public ArrayList<EmployeeListBean> getEmployeeByContents(
	    ArrayList<String> contents) {
	ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();
	// 取得コンテンツに対応したwhere句の生成
	int idCount = contents.size();
	String where = "?";
	int contentCount = 1;
	for (contentCount = 1; contentCount < idCount; contentCount++) {
	    where += ",?";
	}
	// SQL発行
	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT e.employee_id ,u.last_name,u.first_name ,d.department_name,count(*) FROM employees AS e "
			    + "JOIN home_contents AS hc ON hc.employee_id = e.employee_id "
			    + "JOIN users AS u ON u.user_id = e.employee_ID "
			    + "JOIN departments AS d ON d.department_ID = e.department_ID "
			    + "WHERE home_content_id in ("
			    + where
			    + ") GROUP BY e.employee_id "
			    + "ORDER BY COUNT(*) DESC ,e.employee_id");

	    for (contentCount = 0; contentCount < idCount; contentCount++) {
		select.setInt(contentCount + 1,
			Integer.parseInt(contents.get(contentCount)));
		System.out
			.println("ContentID is " + contents.get(contentCount));
	    }
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
		EmployeeListBean recode = new EmployeeListBean();
		recode.setEmployeeId(result.getString("e.employee_id"));
		recode.setEmployeeName(result.getString("u.last_name")
			+ result.getString("u.first_name"));
		recode.setDepartmentName(result.getString("d.department_name"));
		recode.setEmployeeGenre(getEmployeeGenre(result
			.getString("e.employee_id")));
		System.out.println(result.getString("e.employee_id")
			+ result.getString("u.last_name") + ","
			+ result.getInt("COUNT(*)"));
		resultTable.add(recode);
	    }
	} catch (SQLException e) {
	    // TODO 自動生成された catch ブロック
	    e.printStackTrace();
	}
	return resultTable;
    }

    /*
     * 2015/05/26 中野 裕史郎 社員1人のジャンル経験数上位三件を取得するメソッド 引数:String(社員ID)
     * 返り値:String型の配列(ジャンル名)
     */
    public ArrayList<String> getEmployeeGenre(String employeeId) {

	ArrayList<String> resultTable = new ArrayList<String>();
	int count = 0;

	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT hg.genre_ID,g.genre_name,hc.employee_ID,COUNT(hg.genre_ID) "
			    + "FROM home_contents AS hc JOIN home_genre AS hg ON hc.home_content_ID = hg.home_content_ID "
			    + "JOIN genre AS g ON hg.genre_ID = g.genre_ID WHERE hc.employee_ID = ? "
			    + "GROUP BY hg.genre_ID ORDER BY COUNT(hg.genre_ID) DESC");
	    select.setString(1, employeeId);

	    ResultSet result = select.executeQuery();
	    while (result.next()) {
		resultTable.add(result.getString("g.genre_name"));
		if (count >= 2) {
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
     * 2015/06/09 中野裕史郎 ジャンル情報を全権取得
     */
    public List<GenreBean> getGenreList() {
	ArrayList<GenreBean> resultTable = new ArrayList<GenreBean>();
	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT genre_id, genre_name , big_genre_id FROM genre ORDER BY big_genre_id , genre_id DESC");
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
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
     * 2015/06/09 中野裕史郎 部署情報を全権取得
     */
    public List<DepartmentBean> getDepartmentList() {
	ArrayList<DepartmentBean> resultTable = new ArrayList<DepartmentBean>();
	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT department_id , department_name FROM departments");
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
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
     * 2015/06/09 中野裕史郎 社員一覧取得
     */
    public List<EmployeeListBean> getEmployee() {
	ArrayList<EmployeeListBean> resultTable = new ArrayList<EmployeeListBean>();

	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT e.employee_ID,u.last_name,u.first_name , d.department_name "
			    + "FROM Employees AS e JOIN Users AS u ON e.employee_ID = u.user_ID "
			    + "JOIN Departments AS d ON e.department_ID = d.department_ID ORDER BY e.employee_ID");
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
		EmployeeListBean record = new EmployeeListBean();
		record.setEmployeeId(result.getString("e.employee_ID"));
		record.setEmployeeName(result.getString("u.last_name")
			+ result.getString("u.first_name"));
		record.setPhotoSrc("/users/"
			+ result.getString("e.employee_ID") + ".png");
		record.setDepartmentName(result.getString("d.department_name"));
		record.setEmployeeGenre(getEmployeeGenre(result
			.getString("e.employee_ID")));
		resultTable.add(record);
	    }
	} catch (SQLException e) {
	    // TODO 自動生成された catch ブロック
	    e.printStackTrace();
	}
	return resultTable;
    }

    /*
     * 2015/06/17 中野 裕史郎 社員の詳細情報取得
     */
    public List<EmployeePageBean> getEmployeeDetail(String employeeId) {
	ArrayList<EmployeePageBean> resultTable = new ArrayList<EmployeePageBean>();
	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT e.employee_ID , e.comment , d.department_name , u.last_name ,u.first_name , e.level ,e.experience "
			    + "FROM employees AS e JOIN departments AS d ON e.department_id = d.department_id "
			    + "JOIN users AS u ON e.employee_ID = u.user_id WHERE e.employee_id LIKE ?");
	    select.setString(1, employeeId);
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
		EmployeePageBean recode = new EmployeePageBean();
		recode.setEmployeeId(result.getString("e.employee_ID"));
		recode.setEmployeeName(result.getString("u.last_name")
			+ result.getString("u.first_name"));
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
     * 2015/6/23 中野 裕史郎 チャート描画用配列の取得
     */
    public int[] getEmployeeDetailOfBadgeCountForChart(String employeeId) {
	int max = 0;
	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
			    + "JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? GROUP BY hl.batch_id "
			    + "ORDER BY b.batch_id");
	    select.setString(1, employeeId);
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
		max++;
	    }
	} catch (SQLException e) {
	    // TODO 自動生成された catch ブロック
	    e.printStackTrace();
	}
	int[] resultTable = new int[max];
	int count = 0;
	try {
	    PreparedStatement select = con
		    .prepareStatement("SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
			    + "JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
			    + "GROUP BY hl.batch_id ORDER BY hl.batch_id");
	    select.setString(1, employeeId);
	    ResultSet result = select.executeQuery();
	    while (result.next()) {
		resultTable[count] = result.getInt("COUNT(*)");
		count++;
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

    /**
     * @author Taiga 企画一覧
     */
    public List<PlanBean> planListAll(String order) throws SQLException {
	String sql = "select p.plan_id,p.planner,u.last_name,u.first_name,p.plan_title,p.plan_datetime,p.plan_comment"
		+ ",p.delete_flag,p.start_date,p.end_date,p.execute_flag ,count(c.plan_id) as comment_count,"
		+ "count(po.plan_id) as point_count,count(po.point = 1 or null) as like_count, DATEDIFF( p.start_date, p.end_date ) AS period"
		+ " from plan as p left join plan_comment as c on p.plan_id = c.plan_id left join plan_point as po on p.plan_id = po.plan_id  join users u on p.planner = u.user_id group by p.plan_id";

	if (order != null) {
	    switch (order) {
	    case "dateDesc":
		sql += " order by p.plan_datetime desc";
		break;
	    case "dateAsc":
		sql += " order by p.plan_datetime asc";
		break;
	    case "commentDesc":
		sql += " order by comment_count desc";
		break;
	    case "commentAsc":
		sql += " order by comment_count asc";
		break;
	    case "evaluationDesc":
		sql += " order by point_count desc";
		break;
	    case "evaluationAsc":
		sql += " order by point_count asc";
		break;
	    case "likeDesc":
		sql += " order by like_count desc";
		break;
	    case "likeAsc":
		sql += " order by like_count asc";
		break;
	    case "startDesc":
		sql += " order by p.start_date desc";
		break;
	    case "startAsc":
		sql += " order by p.start_date asc";
		break;
	    case "periodDesc":
		sql += " order by period desc";
		break;
	    case "periodAsc":
		sql += " order by period asc";
		break;

	    }
	}else{
	    sql += " order by p.plan_datetime desc";
	}

	PreparedStatement select = con.prepareStatement(sql);

	ResultSet result = select.executeQuery();

	ArrayList<PlanBean> table = new ArrayList<PlanBean>();
	while (result.next()) {

	    PlanBean record = new PlanBean();
	    record.setPlanId(result.getInt("plan_id"));
	    record.setPlanner(result.getString("planner"));
	    record.setPlannerName(result.getString("u.last_name")
		    + result.getString("u.first_name"));
	    record.setPlanTitle(result.getString("plan_title"));
	    record.setPlanDatetime(result.getDate("plan_datetime"));
	    record.setPlanComment(result.getString("plan_comment"));
	    record.setStartDate(result.getTimestamp("start_date"));
	    record.setEndDate(result.getTimestamp("end_date"));
	    record.setExecuteFlag(result.getInt("execute_flag"));

	    record.setCommentCount(result.getInt("comment_count"));
	    record.setPointCount(result.getInt("point_count"));

	    table.add(record);
	}
	return table;
    }

    /**
     * 企画一覧(終了済み企画を除く)
     *
     * @return
     * @throws SQLException
     */
    public List<PlanBean> planListNoEnd(String order) throws SQLException {

	String sql = "select p.plan_id,p.planner,u.last_name,u.first_name,p.plan_title,p.plan_datetime,p.plan_comment"
		+ ",p.delete_flag,p.start_date,p.end_date,p.execute_flag ,count(c.plan_id) as comment_count,"
		+ "count(po.plan_id) as point_count,count(po.point = 1 or null) as like_count, DATEDIFF( p.start_date, p.end_date ) AS period"
		+ " from plan as p left join plan_comment as c on p.plan_id = c.plan_id left join plan_point as po on p.plan_id = po.plan_id join users u on p.planner = u.user_id"
		+ " where p.execute_flag = 0 group by p.plan_id";

	if (order != null) {
	    switch (order) {
	    case "dateDesc":
		sql += " order by p.plan_datetime desc";
		break;
	    case "dateAsc":
		sql += " order by p.plan_datetime asc";
		break;
	    case "commentDesc":
		sql += " order by comment_count desc";
		break;
	    case "commentAsc":
		sql += " order by comment_count asc";
		break;
	    case "evaluationDesc":
		sql += " order by point_count desc";
		break;
	    case "evaluationAsc":
		sql += " order by point_count asc";
		break;
	    case "likeDesc":
		sql += " order by like_count desc";
		break;
	    case "likeAsc":
		sql += " order by like_count asc";
		break;
	    case "startDesc":
		sql += " order by p.start_date desc";
		break;
	    case "startAsc":
		sql += " order by p.start_date asc";
		break;
	    case "periodDesc":
		sql += " order by period desc";
		break;
	    case "periodAsc":
		sql += " order by period asc";
		break;

	    }
	}else{
	    sql += " order by p.plan_datetime desc";
	}

	PreparedStatement select = con.prepareStatement(sql);

	ResultSet result = select.executeQuery();

	ArrayList<PlanBean> table = new ArrayList<PlanBean>();
	while (result.next()) {

	    PlanBean record = new PlanBean();
	    record.setPlanId(result.getInt("plan_id"));
	    record.setPlanner(result.getString("planner"));
	    record.setPlannerName(result.getString("u.last_name")
		    + result.getString("u.first_name"));
	    record.setPlanTitle(result.getString("plan_title"));
	    record.setPlanDatetime(result.getDate("plan_datetime"));
	    record.setPlanComment(result.getString("plan_comment"));
	    record.setStartDate(result.getTimestamp("start_date"));
	    record.setEndDate(result.getTimestamp("end_date"));
	    record.setExecuteFlag(result.getInt("execute_flag"));

	    record.setCommentCount(result.getInt("comment_count"));
	    record.setPointCount(result.getInt("point_count"));

	    table.add(record);
	}
	return table;
    }

    /**
     * 企画新規登録
     *
     * @param newRecord
     *            保存データ
     * @return 生成されたplanid
     * @throws SQLException
     */
    public int planRegister(PlanBean newRecord) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into plan(planner,plan_title,plan_datetime,plan_comment,start_date,end_date) values (?,?,now(),?,cast(? as datetime),cast(? as datetime));");
	insert.setString(1, newRecord.getPlanner());
	insert.setString(2, newRecord.getPlanTitle());
	insert.setString(3, newRecord.getPlanComment());

	insert.setTimestamp(4, new java.sql.Timestamp(newRecord.getStartDate()
		.getTime()));
	if (newRecord.getEndDate() != null) {
	    insert.setTimestamp(5, new java.sql.Timestamp(newRecord
		    .getEndDate().getTime()));
	} else {
	    insert.setTimestamp(5, null);
	}

	int planId = 0;

	if (insert.executeUpdate() == 1) {
	    PreparedStatement select = con
		    .prepareStatement("select LAST_INSERT_ID()");

	    ResultSet result = select.executeQuery();
	    result.next();
	    planId = result.getInt(1);
	}

	return planId;
    }

    /**
     * 企画詳細取得
     *
     * @param planId
     * @return
     * @throws SQLException
     */
    public PlanBean planDetail(int planId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan p, users u where plan_id = ? and p.planner = u.user_id ;");

	select.setInt(1, planId);
	ResultSet result = select.executeQuery();

	PlanBean record = new PlanBean();

	if (result.next()) {
	    record.setPlanId(result.getInt("plan_id"));
	    record.setPlanner(result.getString("planner"));
	    record.setPlannerName(result.getString("u.last_name")
		    + result.getString("u.first_name"));
	    record.setPlanTitle(result.getString("plan_title"));
	    record.setPlanDatetime(result.getDate("plan_datetime"));
	    record.setPlanComment(result.getString("plan_comment"));
	    record.setStartDate(result.getTimestamp("start_date"));
	    record.setEndDate(result.getTimestamp("end_date"));
	    record.setExecuteFlag(result.getInt("execute_flag"));

	}

	return record;
    }

    /**
     * ジャンル全件取得
     *
     * @return
     * @throws SQLException
     */
    public List<GenreBean> genreAll() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from genre g, big_genre b where g.big_genre_id = b.big_genre_id ;");

	ResultSet result = select.executeQuery();

	ArrayList<GenreBean> table = new ArrayList<GenreBean>();
	while (result.next()) {

	    GenreBean record = new GenreBean();

	    record.setGenreId(result.getInt("genre_id"));
	    record.setGenreName(result.getString("genre_name"));
	    record.setBigGenreId(result.getInt("big_genre_id"));
	    record.setBigGenreName(result.getString("big_genre_name"));

	    table.add(record);
	}
	return table;
    }

    /**
     * 企画に紐づくジャンルの登録
     *
     * @param planId
     * @param genreId
     * @return
     * @throws SQLException
     */
    public int planGenreInsert(int planId, int genreId) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into plan_genre (plan_id,genre_id) values (?,?);");
	insert.setInt(1, planId);
	insert.setInt(2, genreId);

	return insert.executeUpdate();
    }

    /**
     * 企画に紐付いたジャンル取得
     *
     * @param planId
     * @return
     * @throws SQLException
     */
    public List<GenreBean> planGenreList(int planId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select p.genre_id ,g.genre_name from plan_genre p, genre g where p.plan_id = ? and p.genre_id = g.genre_id;");

	select.setInt(1, planId);
	ResultSet result = select.executeQuery();

	ArrayList<GenreBean> table = new ArrayList<GenreBean>();
	while (result.next()) {

	    GenreBean record = new GenreBean();

	    record.setGenreId(result.getInt("p.genre_id"));
	    record.setGenreName(result.getString("g.genre_name"));

	    table.add(record);
	}
	return table;
    }

    public String[] planGenreArray(int planId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select p.genre_id ,g.genre_name from plan_genre p, genre g where p.plan_id = ? and p.genre_id = g.genre_id;");

	select.setInt(1, planId);
	ResultSet result = select.executeQuery();

	ArrayList<String> table = new ArrayList<String>();
	while (result.next()) {

	    table.add(String.valueOf(result.getInt("p.genre_id")));
	}

	String[] output = (String[])table.toArray(new String[0]);
	return output;
    }

    /**
     * コメント登録
     *
     * @param newRecord
     * @return
     * @throws SQLException
     */
    public int commentInsert(CommentBean newRecord) throws SQLException {

	// コメント件数取得
	int commentCount = commentCount(newRecord.getPlanID());

	if (commentCount != -1) {
	    // コメント登録処理
	    PreparedStatement insert = con
		    .prepareStatement("insert into plan_comment (plan_id,comment_no,comment_user,comment_datetime,delete_flag,comment) values (?,?,?,now(),0,?);");

	    insert.setInt(1, newRecord.getPlanID());
	    insert.setInt(2, commentCount + 1);
	    insert.setString(3, newRecord.getCommentUser());
	    insert.setString(4, newRecord.getComment());
	    insert.executeUpdate();
	    return commentCount + 1;

	} else {
	    return 0;
	}

    }

    /**
     * 企画内のコメント数取得
     *
     * @param planId
     * @return
     * @throws SQLException
     */
    public int commentCount(int planId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select count(*) from plan_comment where plan_id = ? ;");

	select.setInt(1, planId);
	ResultSet result = select.executeQuery();

	if (result.next()) {
	    return result.getInt("count(*)");
	} else {
	    return -1;
	}
    }

    /**
     * 企画に紐づくコメント一覧取得
     *
     * @param planId
     * @return
     * @throws SQLException
     */
    public List<CommentBean> planCommentList(int planId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan_comment p, users u where plan_id = ? and p.comment_user = u.user_id;");

	select.setInt(1, planId);

	ResultSet result = select.executeQuery();

	ArrayList<CommentBean> table = new ArrayList<CommentBean>();
	while (result.next()) {

	    CommentBean record = new CommentBean();

	    record.setPlanID(result.getInt("plan_id"));
	    record.setCommentNo(result.getInt("comment_no"));
	    record.setCommentUser(result.getString("comment_user"));
	    record.setCommentName(result.getString("u.last_name")
		    + result.getString("u.first_name"));
	    record.setDeleteFlag(result.getInt("delete_flag"));
	    record.setCommentDatetime(result.getDate("comment_datetime"));
	    record.setComment(result.getString("comment"));

	    table.add(record);
	}
	return table;
    }

    /**
     * コメント削除
     *
     * @param deleteRecord
     * @return
     * @throws SQLException
     */
    public int commentDelete(CommentBean deleteRecord) throws SQLException {

	PreparedStatement update = con
		.prepareStatement("update plan_comment set delete_flag = ? where plan_id = ? and comment_no = ? ;");
	update.setInt(1, 1);
	update.setInt(2, deleteRecord.getPlanID());
	update.setInt(3, deleteRecord.getCommentNo());
	return update.executeUpdate();
    }
/*
 * 2015/06/17
 * 中野 裕史郎
 * 社員一人が持つバッジ情報の取得
 */
public List<EmployeeBatchBean> getEmployeeDetailOfBadge(String employeeId){
	ArrayList<EmployeeBatchBean> resultTable = new ArrayList<EmployeeBatchBean>();
	try {
		PreparedStatement select = con.prepareStatement("SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
				+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
				+"GROUP BY hl.batch_id ORDER BY hl.batch_id");
		select.setString(1, employeeId);
		ResultSet result = select.executeQuery();
		while(result.next()){
			EmployeeBatchBean recode = new EmployeeBatchBean();
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
	 * 2015/06/30
	 * 中野 裕史郎
	 * 社員の取得バッジの期間絞りアリ取得
	 */
	public List<EmployeeBatchBean>getEmployeeDetailOfBadgeInLimited(String employeeId ,String outPutDate){
		ArrayList<EmployeeBatchBean> resultTable = new ArrayList<EmployeeBatchBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? AND hl.home_datetime >= ? "
					+"GROUP BY hl.batch_id ORDER BY hl.batch_id");
			select.setString(1, employeeId);
			select.setString(2, outPutDate);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeBatchBean recode = new EmployeeBatchBean();
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
	 * 2015/6/23
	 * 中野 裕史郎
	 * チャート描画用配列の取得
	 */
	public String[] getEmployeeDetailOfBadgeNameForChart(String employeeId,String limit,String outPutDate){
		int max =0;
		String sql="";
		if(limit.equals("month")||limit.equals("year")){
			sql = "SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
					+"AND home_datetime >= ? GROUP BY hl.batch_id ORDER BY b.batch_id";
		}else if(limit.equals("total")){
			sql = "SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
					+"GROUP BY hl.batch_id ORDER BY b.batch_id";
		}
		try {
			PreparedStatement select = con.prepareStatement(sql);
			select.setString(1, employeeId);
			if(limit.equals("month")||limit.equals("year")){
				select.setString(2, outPutDate);
			}
			ResultSet result = select.executeQuery();
			while(result.next()){
				max++;
			}
			System.out.println("Name for Chart Max is" +max);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		String[] resultTable = new String[max];
		int count =0;
		try {
			PreparedStatement select = con.prepareStatement(sql);
			select.setString(1, employeeId);
			if(limit.equals("month")||limit.equals("year")){
				select.setString(2, outPutDate);
			}
			ResultSet result = select.executeQuery();
			while(result.next()){
				System.out.println("Name for Chart is" +result.getString("b.batch_name"));
				resultTable[count] = result.getString("b.batch_name");
				count++;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/6/23
	 * 中野 裕史郎
	 * チャート描画用配列の取得
	 */
	public int[] getEmployeeDetailOfBadgeCountForChart(String employeeId, String limit, String outPutDate){
		int max =0;
		String sql="";
		if(limit.equals("month")||limit.equals("year")){
			sql = "SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
					+"AND home_datetime >= ? GROUP BY hl.batch_id ORDER BY b.batch_id";
		}else if(limit.equals("total")){
			sql = "SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
					+"GROUP BY hl.batch_id ORDER BY b.batch_id";
		}
		try {
			PreparedStatement select = con.prepareStatement(sql);
			select.setString(1, employeeId);
			if(limit.equals("month")||limit.equals("year")){
				select.setString(2, outPutDate);
			}
			ResultSet result = select.executeQuery();
			while(result.next()){
				max++;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		int[] resultTable = new int[max];
		int count =0;
		try {
			PreparedStatement select = con.prepareStatement(sql);
			select.setString(1, employeeId);
			if(limit.equals("month")||limit.equals("year")){
				select.setString(2, outPutDate);
			}
			ResultSet result = select.executeQuery();
			while(result.next()){
				resultTable[count]=result.getInt("COUNT(*)");
				count++;
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
			PreparedStatement select = con.prepareStatement("SELECT hg.genre_id , g.genre_name , COUNT(*)  , bg.big_genre_id FROM home_contents AS hc "
					+"JOIN home_genre AS hg ON hc.home_content_ID = hg.home_content_id JOIN genre AS g ON hg.genre_id = g.genre_id "
					+"JOIN big_genre AS bg ON g.big_genre_id = bg.big_genre_id WHERE hc.employee_id LIKE ? GROUP BY hg.genre_id ORDER BY COUNT(*) DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeGenreBean recode = new EmployeeGenreBean();
				recode.setGenreName(result.getString("g.genre_name"));
				recode.setGenreCount(result.getInt("COUNT(*)"));
				recode.setBigGenreId(result.getString("bg.big_genre_id"));
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
			PreparedStatement select = con.prepareStatement("SELECT plan_title , plan_datetime , plan_id FROM `plan` WHERE planner LIKE ? ORDER BY plan_datetime DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeePlanBean recode = new EmployeePlanBean();
				recode.setPlanTitle(result.getString("plan_title"));
				recode.setDays(result.getDate("plan_datetime"));
				recode.setPlanId(result.getString("plan_id"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
//------------------------------------------------------------------------------------------------------------------------------
	/*
	 * 2015/06/17
	 * 中野 裕史郎
	 * 社員一人が持つバッジ情報の取得
	 */
	public List<EmployeeBatchBean> getEmployeeDetailOfBatch(String employeeId){
		ArrayList<EmployeeBatchBean> resultTable = new ArrayList<EmployeeBatchBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? "
					+"GROUP BY hl.batch_id ORDER BY hl.batch_id");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeBatchBean recode = new EmployeeBatchBean();
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
	 * 2015/06/30
	 * 中野 裕史郎
	 * 社員の取得バッジの期間絞りアリ取得
	 */
	public List<EmployeeBatchBean>getEmployeeDetailOfBatchInLimited(String employeeId ,String outPutDate){
		ArrayList<EmployeeBatchBean> resultTable = new ArrayList<EmployeeBatchBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT hl.batch_id , b.batch_name , COUNT(*) FROM home_log AS hl "
					+"JOIN batch AS b ON hl.batch_id = b.batch_id WHERE hl.home_target LIKE ? AND hl.home_datetime >= ? "
					+"GROUP BY hl.batch_id ORDER BY hl.batch_id");
			select.setString(1, employeeId);
			select.setString(2, outPutDate);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeBatchBean recode = new EmployeeBatchBean();
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
	 * 2015/07/03
	 * 中野 裕史郎
	 * 社員一人の資格情報取得
	 */
	public List<EmployeeCapacityBean> getMyEmployeeDetailOfCapacity(String employeeId){
		ArrayList<EmployeeCapacityBean> resultTable = new ArrayList<EmployeeCapacityBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT c.capacity_name FROM employee_capacity AS ec "
					+"JOIN capacity AS c ON ec.capacity_id = c.capacity_id "
					+"WHERE employee_id = ? ORDER BY c.capacity_id");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeCapacityBean recode = new EmployeeCapacityBean();
				recode.setCapacityName(result.getString("c.capacity_name"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}

	/*
	 * 2015/07/03
	 * 中野 裕史郎
	 * 社員一人の社内資格情報取得
	 */
	public List<EmployeeCompanyCapacityBean> getMyEmployeeDetailOfCompanyCapacity(String employeeId){
		ArrayList<EmployeeCompanyCapacityBean> resultTable = new ArrayList<EmployeeCompanyCapacityBean>();
//		DateFormat df = new SimpleDateFormat();

		try {
			PreparedStatement select = con.prepareStatement("SELECT cc.capacity_name , DATE_FORMAT(ecc.get_datetime, '%Y/%m/%d') "
					+ "FROM employee_company_capacity AS ecc JOIN company_capacity AS cc ON ecc.capacity_ID = cc.capacity_id "
					+"WHERE ecc.employee_id = ? ORDER BY cc.capacity_id");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeCompanyCapacityBean recode = new EmployeeCompanyCapacityBean();
				recode.setCapacityName(result.getString("cc.capacity_name"));
				recode.setCapacityDate(result.getString("DATE_FORMAT(ecc.get_datetime, '%Y/%m/%d')"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/07/03
	 * 中野 裕史郎
	 * 社員一人のトロフィー情報取得
	 */
	public List<EmployeeTrophyBean> getMyEmployeeDetailOfTrophy(String employeeId){
		ArrayList<EmployeeTrophyBean> resultTable = new ArrayList<EmployeeTrophyBean>();
		try {
			PreparedStatement select = con.prepareStatement("SELECT t.trophy_id , t.trophy_name , COUNT(*) FROM employee_trophy AS et "
					+"JOIN trophy AS t ON et.trophy_id = t.trophy_id "
					+"WHERE et.employee_id = ? GROUP BY et.trophy_id ORDER BY t.trophy_id");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeTrophyBean recode = new EmployeeTrophyBean();
				recode.setTrophyName(result.getString("t.trophy_name"));
				recode.setTrophyCount(result.getInt("COUNT(*)"));
				recode.setTrophyImg(String.valueOf(result.getInt("t.trophy_id")));
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
			PreparedStatement select = con.prepareStatement("SELECT p.plan_id , p.plan_title , pc.comment_datetime ,  u.last_name , u.first_name "
					+"FROM plan_comment AS pc JOIN plan AS p ON pc.plan_id = p.plan_id JOIN users AS u ON p.planner = u.user_id "
					+"WHERE comment_user LIKE ? ORDER BY pc.comment_datetime DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeePlanCommentBean recode = new EmployeePlanCommentBean();
				recode.setPlannerName(result.getString("u.last_name")+result.getString("u.first_name"));
				recode.setPlanName(result.getString("p.plan_title"));
				recode.setDays(result.getDate("pc.comment_datetime"));
				recode.setPlanId(result.getString("p.plan_id"));
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
			PreparedStatement select = con.prepareStatement("SELECT hl.home_user , hl.home_target , hl.home_datetime , u.last_name ,u.first_name FROM home_log AS hl "
					+"JOIN users AS u ON hl.home_target = u.user_id WHERE home_user LIKE ? ORDER BY home_datetime DESC");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				EmployeeHomeLogBean recode = new EmployeeHomeLogBean();
				recode.setTargetName(result.getString("u.last_name")+result.getString("u.first_name"));
				recode.setDays(result.getDate("hl.home_datetime"));
				recode.setEmployeeId(result.getString("hl.home_target"));
				resultTable.add(recode);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultTable;
	}
	/*
	 * 2015/07/14
	 * 中野 裕史郎
	 * 社員プロフィールの更新
	 */
	public boolean setEmployeeDetailCommentUpdate(String employeeId ,String comment){
		int result = 0;
		boolean jud = false;
		try{
			PreparedStatement update = con.prepareStatement("UPDATE employees SET comment = ? WHERE employee_id = ?");
			update.setString(1, comment);
			update.setString(2, employeeId);
			result = update.executeUpdate();
			if(result==1){
				jud = true;
			}else{
				jud = false;
			}
		}catch (SQLException e){
			jud = false;
		}
		return jud;
	}
	/*
	 * 2015/07/14
	 * 中野 裕史郎
	 * 社員コメントの取得
	 */
	public String getEmployeeDetailComment(String employeeId){
		String resultStr = "";
		try {
			PreparedStatement select = con.prepareStatement("SELECT comment FROM employees WHERE employee_id = ?");
			select.setString(1, employeeId);
			ResultSet result = select.executeQuery();
			while(result.next()){
				resultStr = result.getString("comment");
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return resultStr;
	}

    /**
     * ジャンル検索
     *
     * @param genreIds
     * @return
     * @throws SQLException
     */
    public List<PlanBean> planGenreSearch(String[] genreIds)
	    throws SQLException {

	System.out.println("search");
	int idCount = genreIds.length;
	String where = "?";
	for (int i = 1; i < idCount; i++)
	    where += ",?";

	PreparedStatement select = con
		.prepareStatement("select p.plan_id,p.planner,u.last_name,u.first_name,p.plan_title,p.plan_datetime,p.plan_comment,p.start_date,p.end_date,p.execute_flag,count(*) from plan p, users u, plan_genre g where p.planner = u.user_id and p.plan_id = g.plan_id and genre_id in ("
			+ where + ") group by p.plan_id;");

	for (int i = 1; i <= idCount; i++)
	    select.setInt(i, Integer.valueOf(genreIds[i - 1]));
	ResultSet result = select.executeQuery();

	ArrayList<PlanBean> table = new ArrayList<PlanBean>();
	while (result.next()) {

	    if (result.getInt("count(*)") == idCount) {
		PlanBean record = new PlanBean();

		record.setPlanId(result.getInt("p.plan_id"));
		record.setPlanner(result.getString("p.planner"));
		record.setPlannerName(result.getString("u.last_name")
			+ result.getString("u.first_name"));
		record.setPlanTitle(result.getString("p.plan_title"));
		record.setPlanDatetime(result.getDate("p.plan_datetime"));
		record.setPlanComment(result.getString("p.plan_comment"));
		record.setStartDate(result.getTimestamp("p.start_date"));
		record.setEndDate(result.getTimestamp("p.end_date"));
		record.setExecuteFlag(result.getInt("p.execute_flag"));

		table.add(record);
	    }

	}
	return table;
    }

    /**
     * 企画内容編集
     *
     * @param updateRecord
     * @return
     * @throws SQLException
     */
    public int planEdit(PlanBean updateRecord) throws SQLException {

	PreparedStatement update = con
		.prepareStatement("update plan set plan_title = ?, plan_comment = ? ,start_date = cast(? as datetime),end_date = cast(? as datetime) where plan_id = ? ;");

	update.setString(1, updateRecord.getPlanTitle());
	update.setString(2, updateRecord.getPlanComment());
	// update.setDate(3, new java.sql.Date(
	// updateRecord.getImplementationDate().getTime()));
	update.setTimestamp(3, new java.sql.Timestamp(updateRecord
		.getStartDate().getTime()));
	update.setTimestamp(4, new java.sql.Timestamp(updateRecord.getEndDate()
		.getTime()));
	update.setInt(5, updateRecord.getPlanId());

	return update.executeUpdate();
    }

    /**
     * 企画に紐づくジャンルを削除
     *
     * @param planId
     * @return
     * @throws SQLException
     */
    public int planGenreDelete(int planId) throws SQLException {

	PreparedStatement delete = con
		.prepareStatement("delete from plan_genre where plan_id = ?; ");
	delete.setInt(1, planId);
	return delete.executeUpdate();
    }

    /**
     * 企画評価登録・更新
     *
     * @param planPoint
     * @return
     * @throws SQLException
     */
    public int planPointRegister(PlanPointBean planPoint) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select count(*) from plan_point where plan_id = ? and user_id = ? ;");

	select.setInt(1, planPoint.getPlanId());
	select.setString(2, planPoint.getEmployeeId());
	ResultSet result = select.executeQuery();

	PlanBean record = new PlanBean();

	result.next();
	if (result.getInt("count(*)") == 0) {
	    // 新規追加
	    PreparedStatement insert = con
		    .prepareStatement("insert into plan_point (plan_id,user_id,point) values (?,?,?);");
	    insert.setInt(1, planPoint.getPlanId());
	    insert.setString(2, planPoint.getEmployeeId());
	    insert.setInt(3, planPoint.getPoint());

	    return insert.executeUpdate();

	} else {
	    // 更新
	    PreparedStatement update = con
		    .prepareStatement("update plan_point set point = ? where plan_id = ? and user_id = ?;");

	    update.setInt(1, planPoint.getPoint());
	    update.setInt(2, planPoint.getPlanId());
	    update.setString(3, planPoint.getEmployeeId());

	    return update.executeUpdate();
	}
    }

    /**
     * 社員ごと企画評価確認
     *
     * @param planPoint
     * @return
     * @throws SQLException
     */
    public PlanPointBean planPointGet(PlanPointBean planPoint)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan_point where plan_id = ? and user_id = ? ;");

	select.setInt(1, planPoint.getPlanId());
	select.setString(2, planPoint.getEmployeeId());

	ResultSet result = select.executeQuery();

	PlanPointBean record = new PlanPointBean();

	if (result.next()) {
	    record.setPlanId(result.getInt("plan_id"));
	    record.setEmployeeId(result.getString("user_id"));
	    record.setPoint(result.getInt("point"));
	}

	return record;
    }

    public HashMap<Integer, PlanPointsBean> planPointAll() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select plan_id,count(point = 1 or null),count(point = 3 or null) from plan_point group by plan_id ;");

	ResultSet result = select.executeQuery();

	HashMap<Integer, PlanPointsBean> pointMap = new HashMap<Integer, PlanPointsBean>();

	while (result.next()) {
	    PlanPointsBean record = new PlanPointsBean();
	    record.setPlanId(result.getInt("plan_id"));
	    record.setGoodCount(result.getInt("count(point = 1 or null)"));
	    record.setBadCount(result.getInt("count(point = 3 or null)"));

	    pointMap.put(record.getPlanId(), record);
	}

	return pointMap;
    }

    public HashMap<Integer, List<GenreBean>> planGenreAll() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select plan_id from plan;");

	ResultSet result = select.executeQuery();

	HashMap<Integer, List<GenreBean>> genreMap = new HashMap<Integer, List<GenreBean>>();

	while (result.next()) {

	    int planId = result.getInt("plan_id");
	    List<GenreBean> genreList = planGenreList(planId);

	    genreMap.put(planId, genreList);
	}

	return genreMap;
    }

    /**
     * 企画評価数取得
     *
     * @param planId
     * @return
     * @throws SQLException
     */
    public int[] planPointAll(int planId) throws SQLException {

	int[] points = new int[3];

	PreparedStatement good = con
		.prepareStatement("select count(*) from plan_point where plan_id = ? and point = 1;");

	good.setInt(1, planId);

	ResultSet result = good.executeQuery();

	if (result.next()) {
	    points[0] = result.getInt("count(*)");
	}

	//

	PreparedStatement hold = con
		.prepareStatement("select count(*) from plan_point where plan_id = ? and point = 2;");

	hold.setInt(1, planId);

	result = hold.executeQuery();

	if (result.next()) {
	    points[1] = result.getInt("count(*)");
	}

	//

	PreparedStatement bad = con
		.prepareStatement("select count(*) from plan_point where plan_id = ? and point = 3;");

	bad.setInt(1, planId);

	result = bad.executeQuery();

	if (result.next()) {
	    points[2] = result.getInt("count(*)");
	}

	return points;
    }

    /**
     * ユーザー情報取得
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public UserBean getUser(String userId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select user_id,last_name,first_name from users where user_id = ? ;");

	select.setString(1, userId);
	ResultSet result = select.executeQuery();

	UserBean record = new UserBean();

	if (result.next()) {
	    record.setUserId(result.getString("user_id"));
	    record.setLastName(result.getString("last_name"));
	    record.setFirstName(result.getString("first_name"));
	}

	return record;
    }

    /**
     * バッチ全取得
     *
     * @return
     * @throws SQLException
     */
    public List<BatchBean> batchAll() throws SQLException {

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

    /**
     * ホメ機能(ホメ登録用)
     *
     * @param newRecord
     * @return
     * @throws SQLException
     */
    public int home(HomeBean newRecord) throws SQLException {

	PreparedStatement insert = con
		.prepareStatement("insert into home_log (home_target,home_user,home_datetime,batch_id,home_point,home_comment,home_content_id) values (?,?,now(),?,?,?,?);");
	insert.setString(1, newRecord.getToUserId());
	insert.setString(2, newRecord.getFromUserId());
	insert.setInt(3, newRecord.getBatchId());
	insert.setInt(4, newRecord.getHomePoint());
	insert.setString(5, newRecord.getComment());
	if (newRecord.getContentsId() != 0) {
	    insert.setInt(6, newRecord.getContentsId());
	} else {
	    insert.setString(6, null);

	}
	return insert.executeUpdate();
    }

    /**
     * トロフィーの取得
     *
     * @param employeeId
     * @throws SQLException
     */
    public void newTrophy(String employeeId) throws SQLException {

	System.out.println("新規トロフィー検査開始");

	// 社員のバッチ総数取得
	HashMap<Integer, Integer> batchCountMap = batchCount(employeeId);

	// 社員の所持中のトロフィー総数取得
	HashMap<Integer, Integer> trophyCountMap = trophyCount(employeeId);

	// トロフィー全件取得
	PreparedStatement trophySelect = con
		.prepareStatement("select * from trophy where delete_flag = 0;");

	ResultSet trophyResult = trophySelect.executeQuery();
	// 1トロフィー毎の処理
	while (trophyResult.next()) {

	    int trophyId = trophyResult.getInt("trophy_id");
	    System.out.println(trophyId);

	    PreparedStatement detailSelect = con
		    .prepareStatement("select * from trophy_detail where trophy_id = ?;");
	    detailSelect.setInt(1, trophyId);
	    ResultSet detailResult = detailSelect.executeQuery();
	    // トロフィー取得条件精査
	    int trophyCount = Integer.MAX_VALUE;
	    while (detailResult.next()) {

		int batchId = detailResult.getInt("batch_id");

		if (batchCountMap.get(batchId) != null) {
		    int count = batchCountMap.get(batchId)
			    / detailResult.getInt("type_count");
		    if (trophyCount > count) {
			trophyCount = count;
			System.out.println("トロフィー取得可能性あり");
		    }
		} else {
		    System.out.println("バッチ未取得");
		    trophyCount = Integer.MAX_VALUE;
		    break;
		}
	    }

	    // 新規取得トロフィー数の出力
	    if (trophyCountMap.get(trophyId) != null
		    && trophyCountMap.get(trophyId) < trophyCount) {
		trophyCount = trophyCount - trophyCountMap.get(trophyId);
		System.out.println("トロフィーかぶり排除");
	    } else if (trophyCountMap.get(trophyId) != null
		    && trophyCountMap.get(trophyId) == trophyCount) {
		trophyCount = Integer.MAX_VALUE;
		System.out.println("トロフィー同数");
	    } else {
		System.out.println("既存トロフィーなし");
	    }

	    // 新規トロフィー登録処理
	    if (trophyCount != Integer.MAX_VALUE && trophyCount != 0) {

		System.out.println("新規トロフィー登録");
		// 現在の該当社員の該当トロフィー数取得
		int nowTrophyNo = getTrophyNo(employeeId, trophyId);

		for (int i = 0; i < trophyCount; i++) {
		    nowTrophyNo++;
		    PreparedStatement insert = con
			    .prepareStatement("insert into employee_trophy (employee_id,trophy_id,trophy_No,get_datetime) values (?,?,?,now());");
		    insert.setString(1, employeeId);
		    insert.setInt(2, trophyId);
		    insert.setInt(3, nowTrophyNo);
		    insert.executeUpdate();
		}
	    } else {
		System.out.println("登録失敗");
	    }
	}
    }

    /**
     * 社員のバッチ毎の個数取得
     *
     * @param employeeId
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, Integer> batchCount(String employeeId)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select batch_id,count(*) from home_log where home_target = ? group by batch_id;");

	select.setString(1, employeeId);
	ResultSet result = select.executeQuery();

	HashMap<Integer, Integer> record = new HashMap<Integer, Integer>();

	while (result.next()) {
	    record.put(result.getInt("batch_id"), result.getInt("count(*)"));
	    System.out.println(result.getInt("batch_id") + ":"
		    + result.getInt("count(*)"));
	}

	return record;
    }

    /**
     * 社員のトロフィー毎の個数取得
     *
     * @param employeeId
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, Integer> trophyCount(String employeeId)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select trophy_id,count(*) from employee_trophy where employee_id = ? group by trophy_id;");

	select.setString(1, employeeId);
	ResultSet result = select.executeQuery();

	HashMap<Integer, Integer> record = new HashMap<Integer, Integer>();

	while (result.next()) {
	    record.put(result.getInt("trophy_id"), result.getInt("count(*)"));
	}

	return record;
    }

    /**
     * 現在の指定トロフィーの数
     *
     * @param employeeId
     * @param trophyId
     * @return
     * @throws SQLException
     */
    public int getTrophyNo(String employeeId, int trophyId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select count(*) from employee_trophy where employee_id = ? and trophy_id = ? group by trophy_id ;");

	select.setString(1, employeeId);
	select.setInt(2, trophyId);
	ResultSet result = select.executeQuery();

	int trophyNo = 0;
	if (result.next()) {
	    trophyNo = result.getInt("count(*)");
	}

	return trophyNo;
    }

    /**
     * 社内資格取得
     *
     * @param employeeId
     * @throws SQLException
     */
    public void newCompanyCapacity(String employeeId) throws SQLException {

	System.out.println("新規社内資格検査開始");

	// 社員のバッチ総数取得
	HashMap<Integer, Integer> batchCountMap = batchCount(employeeId);

	// 社員の所持中の社内資格総数取得
	HashMap<Integer, Integer> capacityCountMap = capacityCount(employeeId);

	// 社内資格全件取得
	PreparedStatement capacitySelect = con
		.prepareStatement("select * from company_capacity delete_flag = 0;");

	ResultSet capacityResult = capacitySelect.executeQuery();
	// 社内資格毎の処理
	while (capacityResult.next()) {

	    int capacityId = capacityResult.getInt("capacity_id");
	    System.out.println(capacityId);

	    PreparedStatement detailSelect = con
		    .prepareStatement("select * from capacity_detail where capacity_id = ?;");
	    detailSelect.setInt(1, capacityId);
	    ResultSet detailResult = detailSelect.executeQuery();
	    // トロフィー取得条件精査
	    int capacityCount = Integer.MAX_VALUE;
	    while (detailResult.next()) {

		int batchId = detailResult.getInt("batch_id");

		if (batchCountMap.get(batchId) != null) {
		    int count = batchCountMap.get(batchId)
			    / detailResult.getInt("type_count");
		    if (capacityCount > count) {
			capacityCount = count;
			System.out.println("社内資格取得可能性あり");
		    }
		} else {
		    System.out.println("バッチ未取得");
		    capacityCount = Integer.MAX_VALUE;
		    break;
		}
	    }

	    // 新規取得社内資格数の出力
	    if (capacityCountMap.get(capacityId) != null) {
		capacityCount = Integer.MAX_VALUE;
		System.out.println("社内資格存在");
	    } else {
		System.out.println("既存社内資格なし");
	    }

	    // 新規社内資格登録処理
	    if (capacityCount != Integer.MAX_VALUE && capacityCount != 0) {

		System.out.println("新規社内資格登録");

		for (int i = 0; i < capacityCount; i++) {
		    PreparedStatement insert = con
			    .prepareStatement("insert into employee_company_capacity (employee_id,capacity_id,get_datetime) values (?,?,now());");
		    insert.setString(1, employeeId);
		    insert.setInt(2, capacityId);
		    insert.executeUpdate();
		}
	    } else {
		System.out.println("登録失敗");
	    }
	}
    }

    /**
     * 社内資格取得確認
     *
     * @param employeeId
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, Integer> capacityCount(String employeeId)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select capacity_id,count(*) from employee_company_capacity where employee_id = ? group by capacity_id;");

	select.setString(1, employeeId);
	ResultSet result = select.executeQuery();

	HashMap<Integer, Integer> record = new HashMap<Integer, Integer>();

	while (result.next()) {
	    record.put(result.getInt("capacity_id"), result.getInt("count(*)"));
	}

	return record;
    }

    /**
     * 経験値付与/レベルアップ処理
     *
     * @param employee_id
     * @param point
     * @throws SQLException
     */
    public void level(String employee_id, int point) throws SQLException {

	// 経験値追加
	PreparedStatement experienceUpdate = con
		.prepareStatement("update employees set experience = experience + ? where employee_id = ? ;");

	experienceUpdate.setInt(1, point);
	experienceUpdate.setString(2, employee_id);

	experienceUpdate.executeUpdate();

	// レベルアップ処理

	PreparedStatement employeeSelect = con
		.prepareStatement("select * from employees where employee_id = ? ;");

	employeeSelect.setString(1, employee_id);
	ResultSet employeeResult = employeeSelect.executeQuery();

	if (employeeResult.next()) {
	    PreparedStatement levelSelect = con
		    .prepareStatement("select * from level where level = ? ;");

	    levelSelect.setInt(1, employeeResult.getInt("level") + 1);
	    ResultSet levelResult = levelSelect.executeQuery();
	    if (levelResult.next()) {
		if (employeeResult.getInt("experience") >= levelResult
			.getInt("experience")) {
		    PreparedStatement levelUpdate = con
			    .prepareStatement("update employees set level = level + 1 where employee_id = ? ;");

		    levelUpdate.setString(1, employee_id);

		    levelUpdate.executeUpdate();
		}
	    }
	}
    }

    /**
     * ホメコンテンツタイトル取得
     *
     * @param contentId
     * @return
     * @throws SQLException
     */
    public String getContentTitle(int contentId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select home_content_title from home_contents where home_content_id = ? ;");

	select.setInt(1, contentId);
	ResultSet result = select.executeQuery();

	String record = null;

	if (result.next()) {
	    record = result.getString("home_content_title");
	}

	return record;
    }

    /**
     * 企画ファイルアップロード登録処理
     *
     * @param planId
     * @param fileName
     * @return
     * @throws SQLException
     */
    public int planFileUpload(int planId, String fileName) throws SQLException {
	// 重複確認

	int nextFileNo = getPlanFileUploadNo(planId);

	// PreparedStatement select = con
	// .prepareStatement("select count(*) from plan_data where plan_id = ? and data_name = ? group by plan_id;");
	// select.setInt(1, planId);
	// select.setString(2, fileName);
	//
	// ResultSet result = select.executeQuery();

	// 新規作成
	PreparedStatement insert = con
		.prepareStatement("insert into plan_data (plan_id,data_no,data_name) values (?,?,?);");
	insert.setInt(1, planId);
	insert.setInt(2, nextFileNo);
	insert.setString(3, fileName);

	insert.executeUpdate();

	return nextFileNo;

    }

    public int commentFileUpload(int planId, int commentNo, String fileName)
	    throws SQLException {
	// 重複確認

	int nextFileNo = getComentFileUploadNo(planId, commentNo);

	// PreparedStatement select = con
	// .prepareStatement("select count(*) from plan_data where plan_id = ? and data_name = ? group by plan_id;");
	// select.setInt(1, planId);
	// select.setString(2, fileName);
	//
	// ResultSet result = select.executeQuery();

	// 新規作成
	PreparedStatement insert = con
		.prepareStatement("insert into plan_comment_data (plan_id,comment_no,data_no,data_name) values (?,?,?,?);");
	insert.setInt(1, planId);
	insert.setInt(2, commentNo);
	insert.setInt(3, nextFileNo);
	insert.setString(4, fileName);

	insert.executeUpdate();

	return nextFileNo;

    }

    /**
     * 企画ごとの次のアップロードファイルNo
     *
     * @param planId
     * @param fileName
     * @return
     * @throws SQLException
     */
    public int getPlanFileUploadNo(int planId) throws SQLException {
	PreparedStatement select = con
		.prepareStatement("select max(data_no) from plan_data where plan_id = ? group by plan_id;");
	select.setInt(1, planId);

	ResultSet result = select.executeQuery();

	if (result.next()) {
	    return result.getInt("max(data_no)") + 1;
	}

	return 1;

    }

    public int getComentFileUploadNo(int planId, int commentNo)
	    throws SQLException {
	PreparedStatement select = con
		.prepareStatement("select max(data_no) from plan_comment_data where plan_id = ? and comment_No = ? group by plan_id;");
	select.setInt(1, planId);
	select.setInt(2, commentNo);

	ResultSet result = select.executeQuery();

	if (result.next()) {
	    return result.getInt("max(data_no)") + 1;
	}

	return 1;

    }

    public List<FileBean> uploadFileList(int planId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan_data where plan_id = ? and delete_flag = 0;");

	select.setInt(1, planId);

	ResultSet result = select.executeQuery();

	ArrayList<FileBean> table = new ArrayList<FileBean>();
	while (result.next()) {

	    FileBean record = new FileBean();

	    record.setPlanId(result.getInt("plan_id"));
	    record.setDataNo(result.getInt("data_no"));
	    record.setDataName(result.getString("data_name"));

	    table.add(record);
	}
	return table;
    }

    public List<FileBean> uploadCommentFileList(int planId) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan_comment_data where plan_id = ? and delete_flag = 0;");

	select.setInt(1, planId);

	ResultSet result = select.executeQuery();

	ArrayList<FileBean> table = new ArrayList<FileBean>();
	while (result.next()) {

	    FileBean record = new FileBean();

	    record.setPlanId(result.getInt("plan_id"));
	    record.setCommentNo(result.getInt("comment_no"));
	    record.setDataNo(result.getInt("data_no"));
	    record.setDataName(result.getString("data_name"));

	    table.add(record);
	}
	return table;
    }

    public int planFileDelete(FileBean file) throws SQLException {

	PreparedStatement delete = con
		.prepareStatement("update plan_data set delete_flag = 1 where plan_id = ? and data_no = ?; ");
	delete.setInt(1, file.getPlanId());
	delete.setInt(2, file.getDataNo());

	return delete.executeUpdate();
    }

    public int commentFileDelete(FileBean file) throws SQLException {

	PreparedStatement delete = con
		.prepareStatement("update plan_comment_data set delete_flag = 1 where plan_id = ? and data_no = ? and comment_no = ?; ");
	delete.setInt(1, file.getPlanId());
	delete.setInt(2, file.getDataNo());
	delete.setInt(3, file.getCommentNo());

	return delete.executeUpdate();
    }

    public void planFileDeleteComplete(HttpServletRequest request)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan_data where delete_flag = 1;");

	ResultSet result = select.executeQuery();

	while (result.next()) {

	    String path = request.getServletContext().getRealPath(
		    "/files/plan/master/")
		    + result.getInt("plan_id")
		    + "\\"
		    + result.getInt("data_no");
	    System.out.println("del:" + path);
	    File delFile = new File(path + "\\" + result.getString("data_name"));
	    if (delFile.exists()) {
		if (delFile.delete()) {
		    System.out.println("ファイルを削除しました");
		    File delFolder = new File(path);
		    delFolder.delete();
		} else {
		    System.out.println("ファイルの削除に失敗しました");
		}

	    } else {
		System.out.println("ファイルが見つかりません");
	    }

	}
    }

    public void commentFileDeleteComplete(HttpServletRequest request)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan_comment_data where delete_flag = 1;");

	ResultSet result = select.executeQuery();

	while (result.next()) {

	    String path = request.getServletContext().getRealPath(
		    "/files/plan/comment/")
		    + result.getInt("plan_id")
		    + "\\"
		    + result.getInt("comment_no")
		    + "\\"
		    + result.getInt("data_no");
	    System.out.println("del:" + path);
	    File delFile = new File(path + "\\" + result.getString("data_name"));
	    if (delFile.exists()) {
		if (delFile.delete()) {
		    System.out.println("ファイルを削除しました");
		    File delFolder = new File(path);
		    delFolder.delete();
		} else {
		    System.out.println("ファイルの削除に失敗しました");
		}

	    } else {
		System.out.println("ファイルが見つかりません");
	    }

	}
    }

    public List<PlanBean> planKeywordSearch(String keyword) throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan p,users u where p.planner = u.user_id and plan_title like ? or plan_comment like ?;");

	keyword.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");

	select.setString(1, "%" + keyword + "%");
	select.setString(2, "%" + keyword + "%");
	ResultSet result = select.executeQuery();

	ArrayList<PlanBean> table = new ArrayList<PlanBean>();
	while (result.next()) {

	    PlanBean record = new PlanBean();
	    record.setPlanId(result.getInt("plan_id"));
	    record.setPlanner(result.getString("planner"));
	    record.setPlannerName(result.getString("u.last_name")
		    + result.getString("u.first_name"));
	    record.setPlanTitle(result.getString("plan_title"));
	    record.setPlanDatetime(result.getDate("plan_datetime"));
	    record.setPlanComment(result.getString("plan_comment"));
	    record.setStartDate(result.getTimestamp("start_date"));
	    record.setEndDate(result.getTimestamp("end_date"));
	    record.setExecuteFlag(result.getInt("execute_flag"));

	    table.add(record);
	}
	return table;
    }

    public List<UserBean> getAllEmployeeId() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from employees e,users u where e.employee_id = u.user_id;");

	ResultSet result = select.executeQuery();

	ArrayList<UserBean> table = new ArrayList<UserBean>();
	while (result.next()) {

	    UserBean record = new UserBean();
	    record.setUserId(result.getString("employee_id"));
	    record.setFirstName(result.getString("first_name"));
	    record.setLastName(result.getString("last_name"));

	    table.add(record);
	}
	return table;
    }

    public List<PlanBean> planPlannerSearch(String employeeId)
	    throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from plan p,users u where p.planner = u.user_id and planner = ?;");

	select.setString(1, employeeId);
	ResultSet result = select.executeQuery();

	ArrayList<PlanBean> table = new ArrayList<PlanBean>();
	while (result.next()) {

	    PlanBean record = new PlanBean();
	    record.setPlanId(result.getInt("plan_id"));
	    record.setPlanner(result.getString("planner"));
	    record.setPlannerName(result.getString("u.last_name")
		    + result.getString("u.first_name"));
	    record.setPlanTitle(result.getString("plan_title"));
	    record.setPlanDatetime(result.getDate("plan_datetime"));
	    record.setPlanComment(result.getString("plan_comment"));
	    record.setStartDate(result.getTimestamp("start_date"));
	    record.setEndDate(result.getTimestamp("end_date"));
	    record.setExecuteFlag(result.getInt("execute_flag"));

	    table.add(record);
	}
	return table;
    }

    public int endPlan(int planId) throws SQLException {

	PreparedStatement update = con
		.prepareStatement("update plan set execute_flag = 1 where plan_id = ? ;");

	update.setInt(1, planId);

	if (update.executeUpdate() != 0) {
	    PreparedStatement select = con
		    .prepareStatement("select * from plan where plan_id = ?;");

	    select.setInt(1, planId);
	    ResultSet plan = select.executeQuery();

	    plan.next();

	    PreparedStatement insert = con
		    .prepareStatement("insert into home_contents (employee_id,home_content_title,home_content_comment,end_flag,start_datetime,end_datetime,plan_id) values (?,?,?,1,?,?,?);");
	    insert.setString(1, plan.getString("planner"));
	    insert.setString(2, plan.getString("plan_title"));
	    insert.setString(3, plan.getString("plan_comment"));
	    insert.setTimestamp(4, plan.getTimestamp("start_date"));
	    insert.setTimestamp(5, plan.getTimestamp("end_date"));
	    insert.setInt(6, plan.getInt("plan_id"));

	    return insert.executeUpdate();
	} else {
	    return 0;
	}

    }

    public List<BigGenreBean> getAllBigGenre() throws SQLException {

	PreparedStatement select = con
		.prepareStatement("select * from big_genre;");

	ResultSet result = select.executeQuery();

	ArrayList<BigGenreBean> table = new ArrayList<BigGenreBean>();
	while (result.next()) {

	    BigGenreBean record = new BigGenreBean();

	    record.setBigGenreId(result.getInt("big_genre_id"));
	    record.setBigGenreName(result.getString("big_genre_name"));

	    table.add(record);
	}
	return table;
    }

    /**
     * 社員の名前を取得
     *
     * @param employeeId
     * @return
     * @throws SQLException
     */
    public String getEmployeeName(String employeeId) throws SQLException {
	ArrayList<EmployeePageBean> resultTable = new ArrayList<EmployeePageBean>();

	PreparedStatement select = con
		.prepareStatement("select u.last_name,u.first_name from users u,employees e where u.user_id = e.employee_id and e.employee_id = ?");
	select.setString(1, employeeId);
	ResultSet result = select.executeQuery();
	String name = "";
	if (result.next()) {
	    name = result.getString("u.last_name") + " "
		    + result.getString("u.first_name");

	}

	return name;
    }

    public List<GenreBean> getGenres(String[] genreIds) throws SQLException {
	ArrayList<EmployeePageBean> resultTable = new ArrayList<EmployeePageBean>();

	String sql = "select * from genre where genre_id in (?";
	for (int i = 1; i < genreIds.length; i++) {
	    sql += ",?";
	}
	sql += ")";

	System.out.println(sql);

	PreparedStatement select = con.prepareStatement(sql);

	for (int i = 1; i <= genreIds.length; i++) {
	    select.setString(i, genreIds[i - 1]);
	}

	ResultSet result = select.executeQuery();

	ArrayList<GenreBean> table = new ArrayList<GenreBean>();

	while (result.next()) {
	    GenreBean record = new GenreBean();
	    record.setGenreId(result.getInt("genre_id"));
	    record.setGenreName(result.getString("genre_name"));
	    record.setBigGenreId(result.getInt("big_genre_id"));
	    table.add(record);
	}

	return table;
    }

}
