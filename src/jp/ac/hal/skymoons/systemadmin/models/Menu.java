package jp.ac.hal.skymoons.systemadmin.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.security.SessionController;

/**
 * 管理システムメニュー画面アクセス
 * @author YAMAZAKI GEN
 * @since 2015/08/20
 * @version 1.0
 */
public class Menu extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッション確認
		SessionController sessionController = new SessionController(request);
		if(sessionController.getSessionStatus() == false) {
			// セッションが無い
			// 画面遷移
			return "../../SystemAdmin/login.jsp";
		}		
		
		// メニュー画面へ遷移
		return "../../SystemAdmin/menu.jsp";
	}

}
