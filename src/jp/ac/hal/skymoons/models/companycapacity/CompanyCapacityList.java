package jp.ac.hal.skymoons.models.companycapacity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.companycapacity.CompanyCapacityBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.companycapacity.CompanyCapacityDao;
import jp.ac.hal.skymoons.daoes.trophy.TrophyDao;

public class CompanyCapacityList extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	CompanyCapacityDao dao = new CompanyCapacityDao();
	List<CompanyCapacityBean> capacityList = dao.getCompanyCapacityList();
	request.setAttribute("capacityList", capacityList);
	dao.close();

	return "/companyCapacity/capacityList.jsp";
    }
}
