package jp.ac.hal.skymoons.systemadmin.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.security.SessionController;

/**
 * 管理システムログアウト処理
 * @author YAMAZAKI GEN
 * @since 2015/08/21
 * @version 1.0
 */
public class Logout extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッションを終了
		SessionController sessionController = new SessionController(request);
		sessionController.discardSession();
		
		// ログイン画面へ遷移
		return "../../SystemAdmin/login.jsp";
	}

}
