package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeeBadgeBean;
import jp.ac.hal.skymoons.beans.EmployeeGenreBean;
import jp.ac.hal.skymoons.beans.EmployeeHomeLogBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.EmployeePlanBean;
import jp.ac.hal.skymoons.beans.EmployeePlanCommentBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class EmployeePageModel extends AbstractModel{

	/*
	 * 2015/06/17
	 * 中野 裕史郎
	 * 社員情報ページ取得モデル
	 * 引数：社員ID
	 * 帰り値：社員情報Bean,バッジ情報Bean,ジャンル情報Bean(未)
	 */
	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		SessionController sessionController = new SessionController(request);
		//返り値設定
		ArrayList<EmployeePageBean> employeePageReturn = new ArrayList<EmployeePageBean>();
		ArrayList<EmployeeBadgeBean> employeeBadgeReturn = new ArrayList<EmployeeBadgeBean>();
		ArrayList<EmployeeGenreBean> employeeGenreReturn = new ArrayList<EmployeeGenreBean>();
		ArrayList<EmployeePlanBean> employeePlanReturn = new ArrayList<EmployeePlanBean>();
		ArrayList<EmployeePlanCommentBean> employeePlanCommentReturn = new ArrayList<EmployeePlanCommentBean>();
		ArrayList<EmployeeHomeLogBean> employeeHomeLogReturn = new ArrayList<EmployeeHomeLogBean>();
		//チャート描画用変数
		String[] employeeChartBadgeName = {};
		int[] employeeChartBadgeCount = {};
		//引数取得
		String employeeId = request.getParameter("employeeId");

		//DBコネクション取得
		SampleDao dao = new SampleDao();

		//情報取得処理
		employeePageReturn = (ArrayList<EmployeePageBean>)dao.getEmployeeDetail(employeeId);
		employeeBadgeReturn = (ArrayList<EmployeeBadgeBean>)dao.getEmployeeDetailOfBadge(employeeId);
		employeeGenreReturn = (ArrayList<EmployeeGenreBean>)dao.getEmployeeDetailOfGenre(employeeId);
		employeePlanReturn = (ArrayList<EmployeePlanBean>)dao.getEmployeeDetailOfPlan(employeeId);
		employeePlanCommentReturn = (ArrayList<EmployeePlanCommentBean>)dao.getEmployeeDetailOfPlanComment(employeeId);
		employeeHomeLogReturn = (ArrayList<EmployeeHomeLogBean>)dao.getEmployeeDetailOfHomeLog(employeeId);
		//チャート描画用情報取得処理
		employeeChartBadgeName = (String[])dao.getEmployeeDetailOfBadgeNameForChart(employeeId);
		employeeChartBadgeCount = (int[])dao.getEmployeeDetailOfBadgeCountForChart(employeeId);
		dao.close();

		//出力ページ用の引数をsetAtribute
		request.setAttribute("employeeDetail", employeePageReturn);
		request.setAttribute("employeeGenreDetail",employeeGenreReturn);
		request.setAttribute("employeeBadgeDetail", employeeBadgeReturn);
		request.setAttribute("employeePlanDetail", employeePlanReturn);
		request.setAttribute("employeePlanCommentDetail", employeePlanCommentReturn);
		request.setAttribute("employeeHomeLogDetail",employeeHomeLogReturn);
		request.setAttribute("sessionId", (String)sessionController.getUserId());
		//チャート用の引数をsetAttribute
		request.setAttribute("chartName", employeeChartBadgeName);
		request.setAttribute("chartCount", employeeChartBadgeCount);
		//参照ファイルパスの指定
		return "/Employee/EmployeePage.jsp";
	}

}
