package jp.ac.hal.skymoons.models.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 顧客ユーザ情報編集画面作成処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/19
 * @version 1.0
 */
public class CreateEdit extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッションチェック
		SessionController sessionController = new SessionController(request);
		if (sessionController.checkAdministratorSession() != null) {
			// セッションが無い　エラーページへ遷移
			return "/HomeSystem/Error/SessionErrorPage";
		}
		// セッションがある　処理を続行
		
		return "/customer/edit.jsp";
	}

}
