package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

public class PlanCalendar extends AbstractModel {

    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub

	HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
	ArrayList<EmployeePageBean> employeePageReturn = headerUtil
			.getHeaderData(request, response);
	request.setAttribute("employeeDetail", employeePageReturn);

	SessionController sessionController = new SessionController(request);

	if (sessionController.checkUserSession() != null) {
	    System.out.println("Sessionなし");
	    return sessionController.checkUserSession();
	} else {
	    SampleDao dao = new SampleDao();
	    List<PlanBean> planList = dao.planListNoEnd(null);
	    dao.close();

	    request.setAttribute("planList", planList);

	    return "/pages/PlanCalendar.jsp";
	}

    }

}
