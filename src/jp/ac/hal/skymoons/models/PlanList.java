package jp.ac.hal.skymoons.models;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.PlanListBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanList extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

//		SampleDao dao = new SampleDao();
//		List<PlanListBean> planList = dao.planList();
//
//		request.setAttribute("planList", planList);

		return "/pages/planList.jsp"	;
	}

}
