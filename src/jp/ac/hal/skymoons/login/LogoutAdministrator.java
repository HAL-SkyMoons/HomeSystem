package jp.ac.hal.skymoons.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 管理者ユーザのログアウト処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/02
 * @version 1.0
 */
public class LogoutAdministrator extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SessionController sessionController = new SessionController(request);
		sessionController.discardAdministratorSession();
		return "/login/a.jsp";
	}

}
