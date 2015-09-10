package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.PlanPointsBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

public class PlanList extends AbstractModel {

    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub
	SessionController sessionController = new SessionController(request);

	if (sessionController.checkUserSession() == null
		&& sessionController.getUserClass_flag().equals("1")) {
	    SampleDao dao = new SampleDao();
	    List<PlanBean> planList = dao.planListNoEnd(request
		    .getParameter("order"));
	    List<PlanBean> result = null;

	    if (request.getParameter("search") != null) {
		// 終了済み企画を含める
		if (request.getParameter("endPlan") != null) {
		    planList = dao.planListAll(request.getParameter("order"));
		    request.setAttribute("endPlan",
			    request.getParameter("endPlan"));
		}

		// キーワード検索
		if (request.getParameter("keyword") != null
			&& !request.getParameter("keyword").equals("")) {
		    System.out.println("キーワード検索");
		    List<PlanBean> searchKeywordList = dao
			    .planKeywordSearch(request.getParameter("keyword"));
		    Map<Integer, PlanBean> mapT = new HashMap<Integer, PlanBean>();
		    for (PlanBean plan : searchKeywordList) {
			mapT.put(plan.getPlanId(), plan);
		    }

		    result = new ArrayList<PlanBean>();
		    for (PlanBean plan : planList) {
			if (mapT.containsKey(plan.getPlanId())) {
			    result.add(plan);
			}
		    }

		    planList = result;
		    request.setAttribute("searchKeyword",
			    request.getParameter("keyword"));
		}

		// ジャンル検索
		if (request.getParameterValues("genre") != null) {
		    System.out.println("ジャンル検索");
		    List<PlanBean> searchGenreList = dao
			    .planGenreSearch(request
				    .getParameterValues("genre"));
		    Map<Integer, PlanBean> mapT = new HashMap<Integer, PlanBean>();
		    for (PlanBean plan : searchGenreList) {
			mapT.put(plan.getPlanId(), plan);
		    }

		    result = new ArrayList<PlanBean>();
		    for (PlanBean plan : planList) {
			if (mapT.containsKey(plan.getPlanId())) {
			    result.add(plan);
			}
		    }

		    planList = result;
		    request.setAttribute("searchGenre",
			    dao.getGenres(request.getParameterValues("genre")));
		}

		// 人検索
		if (request.getParameter("planner") != null
			&& !request.getParameter("planner").equals("")) {
		    System.out.println("人検索");
		    List<PlanBean> searchPlannerList = dao
			    .planPlannerSearch(request.getParameter("planner"));
		    Map<Integer, PlanBean> mapT = new HashMap<Integer, PlanBean>();
		    for (PlanBean plan : searchPlannerList) {
			mapT.put(plan.getPlanId(), plan);
		    }

		    result = new ArrayList<PlanBean>();
		    for (PlanBean plan : planList) {
			if (mapT.containsKey(plan.getPlanId())) {
			    result.add(plan);
			}
		    }

		    planList = result;
		    request.setAttribute("searchPlanner",
			    request.getParameter("planner"));
		    request.setAttribute("searchPlannerName", dao
			    .getEmployeeName(request.getParameter("planner")));
		}
	    }
	    List<GenreBean> genreList = dao.genreAll();
	    List<UserBean> employeeList = dao.getAllEmployeeId();

	    HashMap<Integer, PlanPointsBean> pointMap = dao.planPointAll();
	    HashMap<Integer, List<GenreBean>> genreMap = dao.planGenreAll();

	    dao.close();
	    request.setAttribute("planList", planList);
	    request.setAttribute("genreList", genreList);
	    request.setAttribute("employeeList", employeeList);
	    request.setAttribute("pointMap", pointMap);
	    request.setAttribute("genreMap", genreMap);

	    if (request.getParameter("order") != null
		    && !request.getParameter("order").equals("")) {
		request.setAttribute("order", request.getParameter("order"));
	    }

	    HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
	    ArrayList<EmployeePageBean> employeePageReturn = headerUtil
		    .getHeaderData(request, response);
	    request.setAttribute("employeeDetail", employeePageReturn);

	    return "/pages/PlanList.jsp";
	} else if (sessionController.checkUserSession() != null) {
	    return sessionController.checkUserSession();
	} else {
	    return "/pages/error.html";
	}

    }

}
