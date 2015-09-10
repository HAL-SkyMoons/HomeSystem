package jp.ac.hal.skymoons.models.login;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.daoes.login.LoginDAO;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

/**
 * 顧客・社員ユーザログイン認証機能。
 *
 * @author YAMAZAKI GEN
 * @since 2015/06/02
 * @version 1.0
 */

public class LoginUser extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			if (request.getParameter("submit") == null) {
				// ログインボタンが押されていない
				return "/login/cs.jsp";
			}
			// ログインボタンが押された
			if (request.getParameter("id").equals("")
					|| request.getParameter("pass").equals("")) {
				// 未入力項目あり
				request.setAttribute("message", "未入力項目があります。");
				return "/login/cs.jsp";
			}
			// 未入力項目なし

			// DB問合せ
			LoginDAO loginDAO = null;
			String class_flag = null;
			try {
				loginDAO = new LoginDAO();
				class_flag = loginDAO.checkCsUser(request.getParameter("id"),
						request.getParameter("pass"));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ログイン認証処理に問題が発生。");
				return "/error/system.jsp";
			} finally {
				loginDAO.close();
			}

			if (class_flag != null) {
				// 認証成功
				// セッション開始
				// SessionController sessionController = new
				// SessionController(request);
				SessionController sessionController = new SessionController(
						request);
				sessionController.setUserIdAndGroup(request.getParameter("id")
						.toString(), class_flag);

				return "../Index";
			} else {
				// 認証失敗
				request.setAttribute("message", "認証に失敗しました。");
				return "/login/cs.jsp";
			}

	}
}