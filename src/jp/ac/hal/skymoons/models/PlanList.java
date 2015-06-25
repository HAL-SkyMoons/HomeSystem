package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class PlanList extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		SessionController sessionController = new SessionController(request);

		if(sessionController.checkUserSession() == null && sessionController.getUserClass_flag().equals("1")){
			SampleDao dao = new SampleDao();
			List<PlanBean> planList = dao.planList();
			List<PlanBean> result = null;

			if(request.getParameter("search")!=null){

				//キーワード検索
				if(request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")){
					System.out.println("キーワード検索");
					List<PlanBean> searchKeywordList = dao.planKeywordSearch(request.getParameter("keyword"));
					Map<Integer, PlanBean> mapT = new HashMap<Integer, PlanBean>();
			          for (PlanBean plan : searchKeywordList) {
			               mapT.put(plan.getPlanId(), plan);
			          }

			         result = new ArrayList<PlanBean>();
			          for (PlanBean plan : planList) {
			               if(mapT.containsKey(plan.getPlanId())) {
			                    result.add(plan);
			               }
			          }

			          planList = result;
			          request.setAttribute("searchKeyword", request.getParameter("keyword"));
				}

				//ジャンル検索
				if(request.getParameterValues("genre") != null){
					System.out.println("ジャンル検索");
					List<PlanBean> searchGenreList = dao.planGenreSearch(request.getParameterValues("genre"));
					Map<Integer, PlanBean> mapT = new HashMap<Integer, PlanBean>();
			          for (PlanBean plan : searchGenreList) {
			               mapT.put(plan.getPlanId(), plan);
			          }

			         result = new ArrayList<PlanBean>();
			          for (PlanBean plan : planList) {
			               if(mapT.containsKey(plan.getPlanId())) {
			                    result.add(plan);
			               }
			          }

			          planList = result;
			          request.setAttribute("searchGenre", request.getParameterValues("genre"));
				}

				//人検索
				if(request.getParameter("planner") != null && !request.getParameter("planner").equals("")){
					System.out.println("人検索");
					List<PlanBean> searchPlannerList = dao.planPlannerSearch(request.getParameter("planner"));
					Map<Integer, PlanBean> mapT = new HashMap<Integer, PlanBean>();
			          for (PlanBean plan : searchPlannerList) {
			               mapT.put(plan.getPlanId(), plan);
			          }

			         result = new ArrayList<PlanBean>();
			          for (PlanBean plan : planList) {
			               if(mapT.containsKey(plan.getPlanId())) {
			                    result.add(plan);
			               }
			          }

			          planList = result;
			          request.setAttribute("searchPlanner", request.getParameter("planner"));
				}
			}
			List<GenreBean> genreList = dao.genreAll();
			List<UserBean> employeeList = dao.getAllEmployeeId();

			dao.close();
			request.setAttribute("planList", planList);
			request.setAttribute("genreList", genreList);
			request.setAttribute("employeeList", employeeList);


			return "/pages/PlanList.jsp"	;
		}else{
			return "/pages/error.html";
		}


	}

}
