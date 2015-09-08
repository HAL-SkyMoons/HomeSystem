package jp.ac.hal.skymoons.models;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.HomeBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class Home extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// データが送信された時
		if (request.getParameter("submit") != null) {

			HomeBean homeBean = new HomeBean();
			homeBean.setFromUserId(request.getParameter("fromId"));
			homeBean.setToUserId(request.getParameter("toId"));
			homeBean.setBatchId(Integer.valueOf(request.getParameter("batchId")));
			homeBean.setHomePoint(Integer.valueOf(request.getParameter("point")));
			homeBean.setComment(request.getParameter("comment"));
			homeBean.setContentsId(Integer.valueOf(request
					.getParameter("contentsId")));

			SampleDao dao = new SampleDao();
			dao.home(homeBean);

			dao.newTrophy(request.getParameter("toId"));
			dao.newCompanyCapacity(request.getParameter("toId"));
			dao.level(request.getParameter("toId"),
					Integer.valueOf(request.getParameter("point")));
			dao.commit();
			dao.close();

			return "/pages/HomeEnd.html";
		} else {

			// データが送信されていない時
			SessionController sessionController = new SessionController(request);

			if (request.getParameter("toUser") != null
					&& sessionController.checkUserSession() == null) {
				if (request.getParameter("toUser").equals(
						(sessionController.getUserId()))) {
					return "/pages/error.html";
				}

				SampleDao dao = new SampleDao();
				List<BatchBean> batchList = dao.batchAll();
				UserBean toUser = dao.getUser(request.getParameter("toUser"));
				UserBean fromUser = dao.getUser(sessionController.getUserId());

				if (request.getParameter("contentsId") != null) {
					String contentTitle = dao.getContentTitle(Integer
							.valueOf(request.getParameter("contentsId")));
					request.setAttribute("contentTitle", contentTitle);
					request.setAttribute("contentsId", request.getParameter("contentsId") );
				}
				dao.close();

				request.setAttribute("batchList", batchList);
				request.setAttribute("toUser", toUser);
				request.setAttribute("fromUser", fromUser);

				return "/pages/Home.jsp";
			} else {
				if (request.getParameter("toUser") == null) {
					System.out.println("宛先なし");
				}

				if (sessionController.checkUserSession() != null) {
					System.out.println("Sessionなし");
					return sessionController.checkUserSession();
				}

				return "/pages/error.html";

			}
		}
	}
}
