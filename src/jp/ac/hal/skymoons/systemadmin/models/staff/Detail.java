package jp.ac.hal.skymoons.systemadmin.models.staff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.security.SessionController;

/**
 * 社員ユーザ詳細画面作成機能
 * @author YAMAZAKI GEN
 * @since 2015/09/09
 * @version 1.0
 */
public class Detail extends AbstractModel {

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

		return "../../../SystemAdmin/staff/detail.jsp";
	}

}
