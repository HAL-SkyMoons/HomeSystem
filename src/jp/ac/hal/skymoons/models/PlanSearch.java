package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BigGenreBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

public class PlanSearch extends AbstractModel {

    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub
	SessionController sessionController = new SessionController(request);

	if (sessionController.checkUserSession() == null
		&& sessionController.getUserClass_flag().equals("1")) {
	    SampleDao dao = new SampleDao();

	    // 全画面のフォーム情報取得
	    if (request.getParameter("searchKeyword") != null
		    && !request.getParameter("searchKeyword").equals("")) {
		request.setAttribute("searchKeyword",
			request.getParameter("searchKeyword"));

	    }
	    if (request.getParameter("searchPlanner") != null
		    && !request.getParameter("searchPlanner").equals("")) {
		request.setAttribute("searchPlanner",
			request.getParameter("searchPlanner"));

	    }
	    if (request.getParameter("endPlan") != null
		    && !request.getParameter("endPlan").equals("")) {
		request.setAttribute("endPlan", request.getParameter("endPlan"));

	    }
	    if (request.getParameter("searchOrder") != null
		    && !request.getParameter("searchOrder").equals("")) {
		request.setAttribute("searchOrder",
			request.getParameter("searchOrder"));

	    }
	    if (request.getParameterValues("searchGenre") != null
		    && !request.getParameterValues("searchGenre").equals("")) {

		String[] genres = request.getParameterValues("searchGenre");

		HashMap<Integer, Integer> searchGenre = new HashMap<Integer, Integer>();
		for (String genre : genres) {
		    searchGenre.put(Integer.valueOf(genre),
			    Integer.valueOf(genre));
		}

		request.setAttribute("searchGenre", searchGenre);

	    }

	    // order用HashMap設定
	    LinkedHashMap<String, String> orderList = new LinkedHashMap<String, String>();

	    orderList.put("dateDesc", "投稿日時が新しい順");
	    orderList.put("dateAsc", "投稿日時が古い順");
	    orderList.put("commentDesc", "コメントが多い順");
	    orderList.put("commentAsc", "コメントが少ない順");
	    orderList.put("evaluationDesc", "評価数が多い順");
	    orderList.put("evaluationAsc", "評価数が少ない順");
	    orderList.put("likeDesc", "いいねが多い順");
	    orderList.put("likeAsc", "いいねが少ない順");
	    orderList.put("startDesc", "企画開始日時が新しい順");
	    orderList.put("startAsc", "企画開始日時が古い順");
	    orderList.put("periodDesc", "企画期間が長い順");
	    orderList.put("periodAsc", "企画期間が短い順");

	    request.setAttribute("orderList", orderList);

	    List<GenreBean> genreList = dao.genreAll();
	    List<UserBean> employeeList = dao.getAllEmployeeId();
	    List<BigGenreBean> bigGenreList = dao.getAllBigGenre();

	    dao.close();
	    request.setAttribute("genreList", genreList);
	    request.setAttribute("bigGenreList", bigGenreList);
	    request.setAttribute("employeeList", employeeList);

	    HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
	    ArrayList<EmployeePageBean> employeePageReturn = headerUtil
		    .getHeaderData(request, response);
	    request.setAttribute("employeeDetail", employeePageReturn);

	    return "/pages/PlanSearch.jsp";
	} else {
	    return sessionController.checkUserSession();
	}

    }

}
