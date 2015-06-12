package jp.ac.hal.skymoons.daoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.HomeBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.PlanPointBean;
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
					+ result.getString("u.first_name"));
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
					+ result.getString("u.first_name"));
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
					+ result.getString("u.first_name"));
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
				.prepareStatement("select p.plan_id,p.planner,u.last_name,u.first_name,p.plan_title,p.plan_datetime,p.plan_comment,count(*) from plan p, users u, plan_genre g where p.planner = u.user_id and p.plan_id = g.plan_id and genre_id in ("
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
				.prepareStatement("update plan set plan_title = ?, plan_comment = ? where plan_id = ? ;");

		update.setString(1, updateRecord.getPlanTitle());
		update.setString(2, updateRecord.getPlanComment());
		update.setInt(3, updateRecord.getPlanId());

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
	 * トロフィーの新規登録
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
				.prepareStatement("select * from trophy;");

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
	 * 社内資格取得登録
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
				.prepareStatement("select * from company_capacity;");

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
				if ( employeeResult.getInt("experience") >= levelResult.getInt("experience")) {
					PreparedStatement levelUpdate = con
							.prepareStatement("update employees set level = level + 1 where employee_id = ? ;");

					levelUpdate.setString(1, employee_id);

					levelUpdate.executeUpdate();
				}
			}
		}
	}

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

}