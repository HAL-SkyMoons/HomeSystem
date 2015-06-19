package jp.ac.hal.skymoons.models;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
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
			List<PlanBean> planList = null;

			if(request.getParameter("search")!=null && request.getParameterValues("genre") != null){
				//検索あり
				planList = dao.planGenreSearch(request.getParameterValues("genre"));
			}else{
				//検索なし
				planList = dao.planList();
			}
			List<GenreBean> genreList = dao.genreAll();

			dao.close();
			request.setAttribute("planList", planList);
			request.setAttribute("genreList", genreList);
			request.setAttribute("searchGenre", request.getParameterValues("genre"));

			return "/pages/PlanList.jsp"	;
		}else{
			return "/pages/error.html";
		}


	}

}
