package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeeHomeBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class HomeView extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	    SessionController sessionController = new SessionController(request);
	    if(sessionController.checkUserSession() == null){

		SampleDao dao = new SampleDao();
		ArrayList<EmployeeHomeBean> homeList = dao.getHomeLog(sessionController.getUserId());
		dao.close();

		request.setAttribute("homeList", homeList);
		return "/Employee/HomeView.jsp";

	    }else{
		return sessionController.checkUserSession();
	    }


	}

}
