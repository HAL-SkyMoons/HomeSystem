package jp.ac.hal.skymoons.models;

import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanConfirmation extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		// POSTで企画内容が送信されているか
		if (request.getParameter("submit") != null) {

			PlanBean plan = new PlanBean();
			plan.setPlanner(request.getParameter("planner"));
			plan.setPlanTitle(request.getParameter("planTitle"));
			plan.setPlanComment(request.getParameter("planComment"));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			plan.setStartDate(sdf.parse(request.getParameter("startDate")));
			plan.setEndDate(sdf.parse(request.getParameter("endDate")));

			String[] genreIds = request.getParameterValues("id");

			SampleDao dao = new SampleDao();
			int planId = dao.planRegister(plan);
			if (planId != 0) {

				if(genreIds != null){
					for (String genreId : genreIds) {
						dao.planGenreInsert(planId, Integer.valueOf(genreId));
					}
				}

				// 企画登録確認画面へ
				dao.commit();
				dao.close();
				return "/pages/PlanCompletion.jsp";
			} else {
				return "/pages/error.html";
			}
		}

		// 企画登録画面へ
		return "/pages/PlanRegister.jsp";
	}

}
