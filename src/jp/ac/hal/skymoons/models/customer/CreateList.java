package jp.ac.hal.skymoons.models.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.customer.CustomerUsersBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.customer.CustomerDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 顧客一覧画面作成処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/09
 * @version 1.0
 */
public class CreateList extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッションチェック
		SessionController sessionController = new SessionController(request);
		String url = sessionController.checkAdministratorSession();
		if(url != null) {
			// セッションが無効
			return url;
		}
		
		// データベース問合せ
		CustomerDAO customerDAO = null;
		try {
			customerDAO = new CustomerDAO();
			List<CustomerUsersBean> list = customerDAO.getCustomerUsersList(request.getParameter("keyword"));
			request.setAttribute("list", list);
		} catch(Exception e) {
			// 例外処理
			e.printStackTrace();
			System.out.print("ERROR:顧客一覧リスト作成処理中に問題が発生しました。");
		} finally {
			customerDAO.close();
		}

		return "/customer/list.jsp";
	}

}
