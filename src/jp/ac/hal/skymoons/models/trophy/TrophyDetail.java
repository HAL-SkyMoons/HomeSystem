package jp.ac.hal.skymoons.models.trophy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyDetailBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.trophy.TrophyDao;

public class TrophyDetail extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	TrophyDao dao = new TrophyDao();
	int trophyId = Integer.valueOf(request.getParameter("trophyId"));
	TrophyBean trophy = dao.getTrophy(trophyId);
	request.setAttribute("trophy", trophy);


	List<TrophyDetailBean> detail = dao.getTrophyDetail(trophyId);
	request.setAttribute("detail", detail);
	dao.close();

	return "/trophy/trophyDetail.jsp";
    }
}
