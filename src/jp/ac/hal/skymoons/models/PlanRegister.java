package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanRegister extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//POSTで企画内容が送信されているか
		if(request.getParameter("submit")!=null){


			PlanBean plan = new PlanBean();
			plan.setPlanner(request.getParameter("planner"));
			plan.setPlanTitle(request.getParameter("planTitle"));
			plan.setPlanComment(nlToBR(request.getParameter("planComment")));

			//[id]:[genreName]
			String[] genres = request.getParameterValues("genre");

			ArrayList<Integer> genreIds = new ArrayList<Integer>();
			ArrayList<String> genreNames = new ArrayList<String>();
			for (String genre : genres) {
				String[] split = genre.split(":");
				genreIds.add(Integer.valueOf(split[0]));
				genreNames.add(split[1]);
			}

			request.setAttribute("plan", plan);
			request.setAttribute("genreIds", genreIds);
			request.setAttribute("genreNames", genreNames);

			//企画登録確認画面へ
			return "/pages/PlanConfirmation.jsp";
		}

		SampleDao dao = new SampleDao();
		List<GenreBean> genreList = dao.genreAll();
		dao.close();
		request.setAttribute("genreList", genreList);

		//企画登録画面へ
		return "/pages/PlanRegister.jsp";
	}

	public static String nlToBR(String str) {
	    if (str == null || "".equals(str)) {
	      return "";
	    }
	    str = str.replaceAll("\r\n", "<br>");
	    str = str.replaceAll("\n", "<br>");
	    return str;
	  }

}
