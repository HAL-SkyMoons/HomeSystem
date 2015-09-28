package jp.ac.hal.skymoons.systemadmin.models.administrator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.beans.AdministratorBean;
import jp.ac.hal.skymoons.systemadmin.daoes.AdministratorDAO;
import jp.ac.hal.skymoons.systemadmin.security.SessionController;

/**
 * 管理者ユーザ詳細画面作成機能
 * @author YAMAZAKI GEN
 * @since 2015/09/28
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
		
		// 管理者IDの取得
		String administrator_id = request.getParameter("id");
		if(administrator_id == null) {
			// 管理者IDが取得できない
			request.setAttribute("message", "管理者ユーザが選択されていません。");
			return "../../../SystemAdmin/administrator/detail.jsp";
		}
		
		// データベース問合せ
		AdministratorDAO administratorDAO = null;
		try {
			administratorDAO = new AdministratorDAO();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
			request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/menu");
			return "../../../SystemAdmin/error/error.jsp";
		}
		if(administratorDAO.getConnectionStatus() == false) {
			System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
			request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/menu");
			return "../../../SystemAdmin/error/error.jsp";
		}
		
		AdministratorBean result = null;
		try {
			result = administratorDAO.getDetail(administrator_id);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:データベースから管理者情報取得中にエラーが発生しました。");
			administratorDAO.connectionClose();
			request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/menu");
			return "../../../SystemAdmin/error/error.jsp";
		}
		administratorDAO.connectionClose();
		
		if(result != null) {
			request.setAttribute("result", result);
		} else {
			request.setAttribute("message", "管理者ID[" + administrator_id + "]のユーザ情報は見つかりません。");
		}
		
		return "../../../SystemAdmin/administrator/detail.jsp";
	}

}
