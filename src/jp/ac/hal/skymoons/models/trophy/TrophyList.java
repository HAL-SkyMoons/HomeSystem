package jp.ac.hal.skymoons.models.trophy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.trophy.TrophyDao;

public class TrophyList extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	TrophyDao dao = new TrophyDao();
	List<TrophyBean> trophyList = dao.getTrophyList();
	request.setAttribute("trophyList", trophyList);
	dao.close();

	return "/trophy/trophyList.jsp";
    }
}
