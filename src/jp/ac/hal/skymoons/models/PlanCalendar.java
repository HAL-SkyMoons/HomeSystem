package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class PlanCalendar extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		SampleDao dao = new SampleDao();
		List<PlanBean> planList = dao.planListNoEnd(null);
		dao.close();

		request.setAttribute("planList", planList);

		return "/pages/PlanCalendar.jsp";


	}

}
