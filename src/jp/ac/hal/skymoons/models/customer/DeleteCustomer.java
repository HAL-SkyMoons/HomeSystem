package jp.ac.hal.skymoons.models.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.customer.CustomerDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * データベースから顧客ユーザ情報を削除する。
 * @author YAMAZAKI GEN
 * @since 2015/06/18
 * @version 1.0
 */
public class DeleteCustomer extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// セッションチェック
		SessionController sessionController = new SessionController(request);
		if(sessionController.checkAdministratorSession2() == false) {
			// セッションが無効
			return sessionController.getForwardSessionErrorPageUrl();
		}
		
		// データベースから指定の顧客ユーザ情報を削除
		CustomerDAO customerDAO = null;
		try {
			customerDAO = new CustomerDAO();
			customerDAO.updateCustomer(request.getParameter("id").toString());
			System.out.println("削除完了");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:顧客ユーザ情報の削除処理中に問題が発生しました。");
		} finally {
			customerDAO.close();
		}
		
		// 顧客一覧ページに遷移
		return "/fc/customer/list";
	}

}
