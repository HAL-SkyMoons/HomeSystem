package jp.ac.hal.skymoons.models.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.hal.skymoons.beans.customer.CustomerUsersBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.customer.CustomerDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 顧客情報データベース登録処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/16
 * @version 1.0
 */
public class InsertCustomer extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッションチェック
		SessionController sessionController = new SessionController(request);
		if(sessionController.getAdministratorId() == null) {
			// セッションが無効
			return "/error/session.jsp";
		}
		HttpSession session = request.getSession();
		if(session.getAttribute("CustomerAddValue") == null) {
			// セッションが無効
			return "/fc/customer/add";
		}
		
		CustomerUsersBean record = (CustomerUsersBean)session.getAttribute("CustomerAddValue");
		// セッション破棄
		session.removeAttribute("CustomerAddValue");
		
		// DBに追加
		CustomerDAO customerDAO = new CustomerDAO();
		try {
			customerDAO.insertCustomerUsers(record);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:顧客情報データベース追加処理中に問題が発生しました。");
			return "/SystemError";
		} finally {
			customerDAO.close();
		}
		return "/fc/customer/list";
	}
}
