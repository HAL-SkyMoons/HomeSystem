package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.List;

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

			String[] genreIds = request.getParameterValues("id");

			SampleDao dao = new SampleDao();
			int planId = dao.planRegister(plan);
			if (planId != 0) {

				for (String genreId : genreIds) {
					dao.planGenreInsert(planId, Integer.valueOf(genreId));
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
