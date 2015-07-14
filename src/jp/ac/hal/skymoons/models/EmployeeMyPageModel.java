package jp.ac.hal.skymoons.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeeBatchBean;
import jp.ac.hal.skymoons.beans.EmployeeCapacityBean;
import jp.ac.hal.skymoons.beans.EmployeeCompanyCapacityBean;
import jp.ac.hal.skymoons.beans.EmployeeGenreBean;
import jp.ac.hal.skymoons.beans.EmployeeHomeLogBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.EmployeePlanBean;
import jp.ac.hal.skymoons.beans.EmployeePlanCommentBean;
import jp.ac.hal.skymoons.beans.EmployeeTrophyBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class EmployeeMyPageModel extends AbstractModel{
	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("employeeMyPageModel in");
		// TODO 自動生成されたメソッド・スタブ
		SessionController sessionController = new SessionController(request);
		//返り値準備
		ArrayList<EmployeePageBean> employeePageReturn = new ArrayList<EmployeePageBean>();
		ArrayList<EmployeeBatchBean> employeeBatchReturn = new ArrayList<EmployeeBatchBean>();
		ArrayList<EmployeeGenreBean> employeeGenreReturn = new ArrayList<EmployeeGenreBean>();
		ArrayList<EmployeePlanBean> employeePlanReturn = new ArrayList<EmployeePlanBean>();
		ArrayList<EmployeePlanCommentBean> employeePlanCommentReturn = new ArrayList<EmployeePlanCommentBean>();
		ArrayList<EmployeeHomeLogBean> employeeHomeLogReturn = new ArrayList<EmployeeHomeLogBean>();
		ArrayList<EmployeeBatchBean> employeeBatchMonthReturn = new ArrayList<EmployeeBatchBean>();
		ArrayList<EmployeeBatchBean> employeeBatchYearReturn = new ArrayList<EmployeeBatchBean>();
		//マイページ追加項目
		ArrayList<EmployeeCapacityBean> employeeCapacityReturn = new ArrayList<EmployeeCapacityBean>();
		ArrayList<EmployeeCompanyCapacityBean> employeeCompanyCapacityReturn = new ArrayList<EmployeeCompanyCapacityBean>();
		ArrayList<EmployeeTrophyBean> employeeTrophyReturn = new ArrayList<EmployeeTrophyBean>();
		//チャート描画用変数
		String[] employeeChartBatchName = {};
		int[] employeeChartBatchCount = {};

		//引数準備
		String loginUserId = (String)sessionController.getUserId();
		//日付取得(月,年単位)
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.add(Calendar.MONTH, -1);
		String monthDate = df.format(cal.getTime());
		cal.setTime(nowDate);
		cal.add(Calendar.YEAR, -1);
		String yearDate = df.format(cal.getTime());
		//DBコネクション接続
		SampleDao dao = new SampleDao();

		//情報取得処理
		employeePageReturn = (ArrayList<EmployeePageBean>)dao.getEmployeeDetail(loginUserId);
		employeeBatchReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatch(loginUserId);
		employeeGenreReturn = (ArrayList<EmployeeGenreBean>)dao.getEmployeeDetailOfGenre(loginUserId);
		employeePlanReturn = (ArrayList<EmployeePlanBean>)dao.getEmployeeDetailOfPlan(loginUserId);
		employeePlanCommentReturn = (ArrayList<EmployeePlanCommentBean>)dao.getEmployeeDetailOfPlanComment(loginUserId);
		employeeHomeLogReturn = (ArrayList<EmployeeHomeLogBean>)dao.getEmployeeDetailOfHomeLog(loginUserId);
		employeeBatchMonthReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatchInLimited(loginUserId,monthDate);
		employeeBatchYearReturn = (ArrayList<EmployeeBatchBean>)dao.getEmployeeDetailOfBatchInLimited(loginUserId,yearDate);
		//チャート描画用情報取得処理
		employeeChartBatchName = (String[])dao.getEmployeeDetailOfBadgeNameForChart(loginUserId);
		employeeChartBatchCount = (int[])dao.getEmployeeDetailOfBadgeCountForChart(loginUserId);
		//マイページ追加項目
		employeeCapacityReturn = (ArrayList<EmployeeCapacityBean>)dao.getMyEmployeeDetailOfCapacity(loginUserId);
		employeeCompanyCapacityReturn = (ArrayList<EmployeeCompanyCapacityBean>)dao.getMyEmployeeDetailOfCompanyCapacity(loginUserId);
		employeeTrophyReturn = (ArrayList<EmployeeTrophyBean>)dao.getMyEmployeeDetailOfTrophy(loginUserId);
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
		//チャート用の引数をsetAttribute
		request.setAttribute("chartName", employeeChartBatchName);
		request.setAttribute("chartCount", employeeChartBatchCount);
		//マイページ追加項目
		request.setAttribute("employeeCapacity", employeeCapacityReturn);
		request.setAttribute("employeeCompanyCapacity", employeeCompanyCapacityReturn);
		request.setAttribute("employeeTrophy", employeeTrophyReturn);
		//参照ファイルパスの指定
		return "/Employee/EmployeeMyPage.jsp";
	}
}
