package jp.ac.hal.skymoons.systemadmin.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.daoes.LoginDAO;
import jp.ac.hal.skymoons.systemadmin.security.SessionController;

/**
 * 管理システムログイン認証機能
 * @author YAMAZAKI GEN
 * @since 2015/08/18
 * @version 1.0
 */
public class Login extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// ログインボタン判定
		if(request.getParameter("submit") != null) {
			// ログインボタンが押された
			if(request.getParameter("id").equals("") == false
				&& request.getParameter("password").equals("") == false) {
				// 未入力項目無し
				// データベース問合せ
				LoginDAO loginDAO = null;
				Boolean flg = false;
				try {
					// データベース接続
					loginDAO = new LoginDAO();
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
					request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/login");
					return "../../SystemAdmin/error/error.jsp";
				}
				if(loginDAO.getConnectionStatus() == false) {
					System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
					request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/login");
					return "../../SystemAdmin/error/error.jsp";
				}
				try {
					// 認証処理
					flg = loginDAO.loginAuthentication(
						request.getParameter("id"), request.getParameter("password"));
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR:認証処理中にエラーが発生しました。");
					request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/login");
					return "../../SystemAdmin/error/error.jsp";
				}
				loginDAO.connectionClose();
				if(flg != null) {
					if(flg) {
						// 認証成功
						// セッション開始
						SessionController sessionController = new SessionController(request);
						sessionController.setAdministratorId(request.getParameter("id"));
						// メニュー画面へ遷移
						return "menu";
					} else {
						// 認証失敗
						request.setAttribute("message", "ログインID、又はパスワードが間違っています。");
					}
				} else {
					// ユーザ重複による重大エラー
					request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/login");
					return "../../SystemAdmin/error/error";
				}
			} else {
				// 未入力項目有り
				request.setAttribute("message", "未入力項目があります。");
			}
		}
		
		// ログイン画面へ遷移
		return "../../SystemAdmin/login.jsp";
	}
}
