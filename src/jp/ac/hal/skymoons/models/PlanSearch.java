package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.PlanPointsBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class PlanSearch extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		SessionController sessionController = new SessionController(request);

		if(sessionController.checkUserSession() == null && sessionController.getUserClass_flag().equals("1")){
			SampleDao dao = new SampleDao();

			List<GenreBean> genreList = dao.genreAll();
			List<UserBean> employeeList = dao.getAllEmployeeId();


			dao.close();
			request.setAttribute("genreList", genreList);
			request.setAttribute("employeeList", employeeList);


			return "/pages/PlanSearch.jsp"	;
		}else{
			return sessionController.checkUserSession();
		}


	}

}
