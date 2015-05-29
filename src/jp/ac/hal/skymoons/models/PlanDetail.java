package jp.ac.hal.skymoons.models;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanDetail extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		if(request.getParameter("detail")!=null){
			SampleDao dao = new SampleDao();
			PlanBean planDetail = dao.planDetail(request.getParameter("planId"));
			request.setAttribute("planDetail", planDetail);
			dao.close();

			return "/pages/PlanDetail.jsp";
		}


		return "/pages/PlanList.jsp";
	}

}
