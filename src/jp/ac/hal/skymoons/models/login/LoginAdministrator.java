package jp.ac.hal.skymoons.models.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.login.LoginDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 管理者ユーザログイン認証機能。
 * @author YAMAZAKI GEN
 * @since 2015/05/26
 * @version 1.0
 */
public class LoginAdministrator extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		
		if(request.getParameter("submit") == null) {
			// ログインボタンが押されていない
			return "/login/a.jsp";
		}
		
		
		
		// ログインボタンが押された
		if(	request.getParameter("id").equals("") ||
			request.getParameter("pass").equals("")) {
			// 未入力項目あり
			request.setAttribute("message", "未入力項目があります。");
			return "/login/a.jsp";
		}
		
		
		
		// 未入力項目なし
		// DB問合せ
		LoginDAO loginDAO = null;
		try{
			loginDAO = new LoginDAO();
			if(loginDAO.checkAdministratorUser(
					request.getParameter("id"),
					request.getParameter("pass"))
					== false) {
				// 認証失敗
				request.setAttribute("message", "認証に失敗しました。");
				return "/login/a.jsp";
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:ログイン認証処理中に問題が発生しました。");
			return "/error/system.jsp";
		} finally {
			loginDAO.close();
		}
		
		
		
		// 認証成功
		// セッション開始
		SessionController sessionController = new SessionController(request);
		sessionController.setAdministratorId(request.getParameter("id").toString());
		return "/login/topa.jsp";
	}
}