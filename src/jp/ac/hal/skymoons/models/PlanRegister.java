package jp.ac.hal.skymoons.models;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanRegister extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//POSTで企画内容が送信されているか
		if(request.getParameter("submit")!=null){


			PlanBean plan = new PlanBean();
			plan.setPlanner(request.getParameter("planner"));
			plan.setPlanTitle(request.getParameter("planTitle"));
			plan.setPlanComment(nlToBR(request.getParameter("planComment")));

			request.setAttribute("plan", plan);
			//企画登録確認画面へ
			return "/pages/PlanConfirmation.jsp";
		}

		//企画登録画面へ
		return "/pages/PlanRegister.jsp";
	}

	public static String nlToBR(String str) {
	    if (str == null || "".equals(str)) {
	      return "";
	    }
	    str = str.replaceAll("\r\n", "<br>");
	    str = str.replaceAll("\n", "<br>");
	    return str;
	  }

}
