package jp.ac.hal.skymoons.models.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.customer.CustomerUsersBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.customer.CustomerDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 顧客詳細画面作成処理。<br />
 * 管理者ユーザ用、社員・顧客ユーザ用切り替え
 * @author YAMAZAKI GEN
 * @since 2015/06/17
 * @version 1.0
 */
public class CreateDetail extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String url = null;
		// セッションチェック
		SessionController sessionController = new SessionController(request);
		if(sessionController.checkUserSession2()) {
			url = "/customer/detail_u.jsp";
		} else if(sessionController.checkAdministratorSession2()) {
			url = "/customer/detail_a.jsp";
		} else {
			// セッションが無効
			return sessionController.getForwardSessionErrorPageUrl();
		}
		
		CustomerDAO customerDAO = null;
		CustomerUsersBean record = null;
		try {
			customerDAO = new CustomerDAO();
			record = customerDAO.getCustomerUsersDetail(request.getParameter("id").toString());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:顧客詳細情報の取得処理中に問題が発生しました。");
		} finally {
			customerDAO.close();
		}
		if(record != null) {
			request.setAttribute("record", record);
		}
		return url;
	}

}
