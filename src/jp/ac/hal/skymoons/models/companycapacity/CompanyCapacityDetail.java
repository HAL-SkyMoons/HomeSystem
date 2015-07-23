package jp.ac.hal.skymoons.models.companycapacity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.companycapacity.CompanyCapacityBean;
import jp.ac.hal.skymoons.beans.companycapacity.CompanyCapacityDetailBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyDetailBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.companycapacity.CompanyCapacityDao;
import jp.ac.hal.skymoons.daoes.trophy.TrophyDao;

public class CompanyCapacityDetail extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	CompanyCapacityDao dao = new CompanyCapacityDao();
	int capacityId = Integer.valueOf(request.getParameter("capacityId"));
	CompanyCapacityBean capacity = dao.getCompanyCapacity(capacityId);
	request.setAttribute("capacity", capacity);


	List<CompanyCapacityDetailBean> detail = dao.getCompanyCapacityDetail(capacityId);
	request.setAttribute("detail", detail);
	dao.close();


	return "/companyCapacity/capacityDetail.jsp";
    }
}
