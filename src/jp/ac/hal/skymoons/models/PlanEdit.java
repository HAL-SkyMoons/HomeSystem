package jp.ac.hal.skymoons.models;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanEdit extends AbstractModel {

    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub

	if (request.getParameter("editSubmit") != null) {

	    int planId = Integer.valueOf(request.getParameter("planId"));
	    SampleDao dao = new SampleDao();
	    PlanBean plan = dao.planDetail(planId);
	    List<GenreBean> genreList = dao.genreAll();
	    List<GenreBean> planGenre = dao.planGenreList(planId);

	    request.setAttribute("plan", plan);
	    request.setAttribute("genreList", genreList);
	    request.setAttribute("planGenre", planGenre);

	    dao.close();

	    return "/pages/PlanEdit.jsp";
	} else {
	    // エラー
	    return "/pages/error.html";
	}

    }

}
