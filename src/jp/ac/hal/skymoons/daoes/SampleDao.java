package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.PlanPointBean;
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
	public List<PlanBean> planList() throws SQLException {

		PreparedStatement select = con
				.prepareStatement("select * from plan p,users u where p.planner = u.user_id;");

		ResultSet result = select.executeQuery();

		ArrayList<PlanBean> table = new ArrayList<PlanBean>();
		while (result.next()) {

			PlanBean record = new PlanBean();
			record.setPlanId(result.getInt("plan_id"));
			record.setPlanner(result.getString("planner"));
			record.setPlannerName(result.getString("u.last_name")
					+ result.getString("u.fast_name"));
			record.setPlanTitle(result.getString("plan_title"));
			record.setPlanDatetime(result.getDate("plan_datetime"));
			record.setPlanComment(result.getString("plan_comment"));

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
				.prepareStatement("insert into plan(planner,plan_title,plan_datetime,plan_comment) values (?,?,now(),?);");
		insert.setString(1, newRecord.getPlanner());
		insert.setString(2, newRecord.getPlanTitle());
		insert.setString(3, newRecord.getPlanComment());

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
					+ result.getString("u.fast_name"));
			record.setPlanTitle(result.getString("plan_title"));
			record.setPlanDatetime(result.getDate("plan_datetime"));
			record.setPlanComment(result.getString("plan_comment"));
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

			return insert.executeUpdate();
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
					+ result.getString("u.fast_name"));
			record.setDeleteFrag(result.getInt("delete_flag"));
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

	/**
	 * ジャンル検索
	 * @param genreIds
	 * @return
	 * @throws SQLException
	 */
	public List<PlanBean> planGenreSearch(String[] genreIds) throws SQLException {

		System.out.println("search");
		int idCount = genreIds.length;
		String where = "?";
		for (int i = 1; i < idCount; i++)
			where += ",?";

		PreparedStatement select = con
				.prepareStatement("select p.plan_id,p.planner,u.last_name,u.fast_name,p.plan_title,p.plan_datetime,p.plan_comment,count(*) from plan p, users u, plan_genre g where p.planner = u.user_id and p.plan_id = g.plan_id and genre_id in ("
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
						+ result.getString("u.fast_name"));
				record.setPlanTitle(result.getString("p.plan_title"));
				record.setPlanDatetime(result.getDate("p.plan_datetime"));
				record.setPlanComment(result.getString("p.plan_comment"));

				table.add(record);
			}

		}
		return table;
	}

	/**
	 * 企画内容編集
	 * @param updateRecord
	 * @return
	 * @throws SQLException
	 */
	public int planEdit(PlanBean updateRecord) throws SQLException {

		PreparedStatement update = con
				.prepareStatement("update plan set plan_title = ?, plan_comment = ? where plan_id = ? ;");

		update.setString(1, updateRecord.getPlanTitle());
		update.setString(2, updateRecord.getPlanComment());
		update.setInt(3, updateRecord.getPlanId());

		return update.executeUpdate();
	}

	/**
	 * 企画に紐づくジャンルを削除
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
	 * @param planPoint
	 * @return
	 * @throws SQLException
	 */
	public int planPointRegister(PlanPointBean planPoint) throws SQLException {

		PreparedStatement select = con
				.prepareStatement("select count(*) from plan_point where plan_id = ? and employee_id = ? ;");

		select.setInt(1, planPoint.getPlanId());
		select.setString(2, planPoint.getEmployeeId());
		ResultSet result = select.executeQuery();

		PlanBean record = new PlanBean();

		result.next();
		if(result.getInt("count(*)") == 0){
			//新規追加
			PreparedStatement insert = con
					.prepareStatement("insert into plan_point (plan_id,employee_id,point) values (?,?,?);");
			insert.setInt(1, planPoint.getPlanId());
			insert.setString(2, planPoint.getEmployeeId());
			insert.setInt(3, planPoint.getPoint());

			return insert.executeUpdate();

		}else{
			//更新
			PreparedStatement update = con
					.prepareStatement("update plan_point set point = ? where plan_id = ? and employee_id = ?;");

			update.setInt(1, planPoint.getPoint());
			update.setInt(2, planPoint.getPlanId());
			update.setString(3, planPoint.getEmployeeId());


			return update.executeUpdate();
		}
	}

	/**
	 * 社員ごと企画評価確認
	 * @param planPoint
	 * @return
	 * @throws SQLException
	 */
	public PlanPointBean planPointGet(PlanPointBean planPoint) throws SQLException {

		PreparedStatement select = con
				.prepareStatement("select * from plan_point where plan_id = ? and employee_id = ? ;");

		select.setInt(1, planPoint.getPlanId());
		select.setString(2, planPoint.getEmployeeId());

		ResultSet result = select.executeQuery();

		PlanPointBean record = new PlanPointBean();

		if (result.next()) {
			record.setPlanId(result.getInt("plan_id"));
			record.setEmployeeId(result.getString("employee_id"));
			record.setPoint(result.getInt("point"));
		}

		return record;
	}

	/**
	 * 企画評価数取得
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

}