package jp.ac.hal.skymoons.systemadmin.models.administrator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.security.SessionController;

/**
 * 管理者ユーザ一覧画面作成機能
 * @author YAMAZAKI GEN
 * @since 2015/09/27
 * @version　1.0
 */
public class List extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッション確認
		SessionController sessionController = new SessionController(request);
		if (sessionController.getSessionStatus() == false) {
			// セッションが無い
			// 画面遷移
			return "../../../SystemAdmin/login.jsp";
		}
				
		return "../../../SystemAdmin/administrator/list.jsp";
	}

}
