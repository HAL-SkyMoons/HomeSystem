package jp.ac.hal.skymoons.models;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BigGenreBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class PlanEdit extends AbstractModel {

    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub

	SessionController sessionController = new SessionController(request);
	if (sessionController.checkUserSession() != null) {
	    return sessionController.checkUserSession();
	}

	if (request.getParameter("editSubmit") != null) {

	    int planId = Integer.valueOf(request.getParameter("planId"));
	    SampleDao dao = new SampleDao();
	    PlanBean plan = dao.planDetail(planId);
	    List<GenreBean> genreList = dao.genreAll();

	    String[] genres = dao.planGenreArray(planId);

	    HashMap<Integer, Integer> planGenre = new HashMap<Integer, Integer>();
	    for (String genre : genres) {
		planGenre.put(Integer.valueOf(genre), Integer.valueOf(genre));
	    }

	    List<BigGenreBean> bigGenreList = dao.getAllBigGenre();

	    request.setAttribute("plan", plan);
	    request.setAttribute("genreList", genreList);
	    request.setAttribute("planGenre", planGenre);
	    request.setAttribute("bigGenreList", bigGenreList);

	    dao.close();

	    return "/pages/PlanEdit.jsp";
	} else {
	    // エラー
	    return "/pages/error.html";
	}

    }

}
