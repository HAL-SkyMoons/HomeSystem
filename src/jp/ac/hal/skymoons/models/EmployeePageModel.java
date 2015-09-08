package jp.ac.hal.skymoons.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BigGenreBean;
import jp.ac.hal.skymoons.beans.EmployeeBatchBean;
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
		ArrayList<EmployeeBatchBean> employeeBatchReturn = new ArrayList<EmployeeBatchBean>();
		ArrayList<EmployeeGenreBean> employeeGenreReturn = new ArrayList<EmployeeGenreBean>();
		ArrayList<EmployeePlanBean> employeePlanReturn = new ArrayList<EmployeePlanBean>();
		ArrayList<EmployeePlanCommentBean> employeePlanCommentReturn = new ArrayList<EmployeePlanCommentBean>();
		ArrayList<EmployeeHomeLogBean> employeeHomeLogReturn = new ArrayList<EmployeeHomeLogBean>();
		ArrayList<EmployeeBatchBean> employeeBatchMonthReturn = new ArrayList<EmployeeBatchBean>();
		ArrayList<EmployeeBatchBean> employeeBatchYearReturn = new ArrayList<EmployeeBatchBean>();
		ArrayList<BigGenreBean>  bigGenreList = new ArrayList<BigGenreBean>();

		//チャート描画用変数
		String[] employeeChartBatchName = {};
		int[] employeeChartBatchCount = {};
		int batchKindCount=0;
		//月間
		int[] employeeChartBatchCountMonth = {};
		//年間
		int[] employeeChartBatchCountYear = {};
		//通算
		int[] employeeChartBatchCountTotal = {};

		//引数取得
		String employeeId = request.getParameter("employeeId");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.add(Calendar.MONTH, -1);
		String monthDate = df.format(cal.getTime());
		cal.setTime(nowDate);
		cal.add(Calendar.YEAR, -1);
		String yearDate = df.format(cal.getTime());
		//DBコネクション取得
		SampleDao dao = new SampleDao();

		//情報取得処理
		employeePageReturn = (ArrayList<EmployeePageBean>)dao.getEmployeeDetail(employeeId);
		employeeBatchReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatch(employeeId);
		employeeGenreReturn = (ArrayList<EmployeeGenreBean>)dao.getEmployeeDetailOfGenre(employeeId);
		employeePlanReturn = (ArrayList<EmployeePlanBean>)dao.getEmployeeDetailOfPlan(employeeId);
		employeePlanCommentReturn = (ArrayList<EmployeePlanCommentBean>)dao.getEmployeeDetailOfPlanComment(employeeId);
		employeeHomeLogReturn = (ArrayList<EmployeeHomeLogBean>)dao.getEmployeeDetailOfHomeLog(employeeId);
		employeeBatchMonthReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatchInLimited(employeeId,monthDate);
		employeeBatchYearReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatchInLimited(employeeId,yearDate);
		bigGenreList = (ArrayList<BigGenreBean>) dao.getAllBigGenre();

		//チャート描画用情報取得処理
		employeeChartBatchName = (String[])dao.getEmployeeDetailOfBadgeNameForChart(employeeId);
		batchKindCount = employeeChartBatchName.length;
		employeeChartBatchCount = (int[])dao.getEmployeeDetailOfBadgeCountForChart(employeeId,"total","total",batchKindCount);
		employeeChartBatchCountMonth =(int[])dao.getEmployeeDetailOfBadgeCountForChart(employeeId,"month",monthDate,batchKindCount);
		employeeChartBatchCountYear =(int[])dao.getEmployeeDetailOfBadgeCountForChart(employeeId,"year",yearDate,batchKindCount);
		employeeChartBatchCountTotal =(int[])dao.getEmployeeDetailOfBadgeCountForChart(employeeId,"total","total",batchKindCount);
		dao.close();

		//出力ページ用の引数をsetAtribute
		request.setAttribute("employeeDetail", employeePageReturn);
		request.setAttribute("employeeGenreDetail",employeeGenreReturn);
		request.setAttribute("employeeBadgeDetail", employeeBatchReturn);
		request.setAttribute("employeePlanDetail", employeePlanReturn);
		request.setAttribute("employeePlanCommentDetail", employeePlanCommentReturn);
		request.setAttribute("employeeHomeLogDetail",employeeHomeLogReturn);
		request.setAttribute("sessionId", (String)sessionController.getUserId());
		request.setAttribute("employeeBadgeMonth", employeeBatchMonthReturn);
		request.setAttribute("employeeBadgeYear", employeeBatchYearReturn);
		request.setAttribute("bigGenreList", bigGenreList);

		//チャート用の引数をsetAttribute
		request.setAttribute("chartName", employeeChartBatchName);
		request.setAttribute("chartCount", employeeChartBatchCount);
		request.setAttribute("chartCountMonth", employeeChartBatchCountMonth);
		request.setAttribute("chartCountYear", employeeChartBatchCountYear);
		request.setAttribute("chartCountTotal", employeeChartBatchCountTotal);
		//参照ファイルパスの指定
		return "/Employee/EmployeePage.jsp";
	}
}

