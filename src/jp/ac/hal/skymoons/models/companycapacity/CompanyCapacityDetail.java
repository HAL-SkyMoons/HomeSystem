package jp.ac.hal.skymoons.models.companycapacity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.trophy.TrophyDao;

public class CompanyCapacityDetail extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	return "/companyCapacity/capacityDetail.jsp";
    }
}
