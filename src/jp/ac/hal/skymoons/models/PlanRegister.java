package jp.ac.hal.skymoons.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BigGenreBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

public class PlanRegister extends AbstractModel {

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

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String startDateStr = request.getParameter("startYear") + "-"
		    + request.getParameter("startMonth") + "-"
		    + request.getParameter("startDay") + " "
		    + request.getParameter("startHour") + ":"
		    + request.getParameter("startMinutes") + ":00";

	    plan.setStartDate(sdf.parse(startDateStr));

	    String endDateStr = request.getParameter("endYear") + "-"
		    + request.getParameter("endMonth") + "-"
		    + request.getParameter("endDay") + " "
		    + request.getParameter("endHour") + ":"
		    + request.getParameter("endMinutes") + ":00";

	    plan.setEndDate(sdf.parse(endDateStr));

	    // [id]:[genreName]
	    String[] genres = request.getParameterValues("genre");

	    ArrayList<Integer> genreIds = new ArrayList<Integer>();
	    ArrayList<String> genreNames = new ArrayList<String>();

	    if (genres != null) {
		for (String genre : genres) {
		    String[] split = genre.split(":");
		    genreIds.add(Integer.valueOf(split[0]));
		    genreNames.add(split[1]);
		}
	    }

	    SampleDao dao = new SampleDao();
	    UserBean user = dao.getUser(request.getParameter("planner"));
	    System.out.println(user.getFirstName());
	    dao.close();

	    request.setAttribute("plan", plan);
	    request.setAttribute("user", user);
	    request.setAttribute("genreIds", genreIds);
	    request.setAttribute("genreNames", genreNames);

	    HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
	    ArrayList<EmployeePageBean> employeePageReturn = headerUtil
		    .getHeaderData(request, response);
	    request.setAttribute("employeeDetail", employeePageReturn);

	    // 企画登録確認画面へ
	    return "/pages/PlanConfirmation.jsp";
	}

	SessionController sessionController = new SessionController(request);
	if (sessionController.checkUserSession() == null) {

	    SampleDao dao = new SampleDao();
	    List<GenreBean> genreList = dao.genreAll();
	    List<BigGenreBean> bigGenreList = dao.getAllBigGenre();
	    UserBean user = dao.getUser(sessionController.getUserId());
	    dao.close();
	    request.setAttribute("genreList", genreList);
	    request.setAttribute("bigGenreList", bigGenreList);
	    request.setAttribute("user", user);

	    HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
	    ArrayList<EmployeePageBean> employeePageReturn = headerUtil
		    .getHeaderData(request, response);
	    request.setAttribute("employeeDetail", employeePageReturn);

	    // 企画登録画面へ
	    return "/pages/PlanRegister.jsp";
	} else {
	    return sessionController.checkUserSession();
	}

    }

}
