package jp.ac.hal.skymoons.systemadmin.models.staff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.beans.StaffBean;
import jp.ac.hal.skymoons.systemadmin.daoes.StaffDAO;
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
		
		// 社員IDの取得
		String staff_id = request.getParameter("id");
		if (staff_id == null) {
			// 社員IDが取得できない
			request.setAttribute("message", "社員ユーザが選択されていません。");
			return "../../../SystemAdmin/administrator/detail.jsp";
		}
		
		// データベース問合せ
		StaffDAO staffDAO = null;
		try {
			staffDAO = new StaffDAO();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
			request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/menu");
			return "../../../SystemAdmin/error/error.jsp";
		}
		if (staffDAO.getConnectionStatus() == false) {
			System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
			request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/menu");
			return "../../../SystemAdmin/error/error.jsp";
		}

		StaffBean result = null;
		try {
			result = staffDAO.getDetail(staff_id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:データベースから社員情報取得中にエラーが発生しました。");
			staffDAO.connectionClose();
			request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/menu");
			return "../../../SystemAdmin/error/error.jsp";
		}
		staffDAO.connectionClose();

		if (result != null) {
			request.setAttribute("result", result);
		} else {
			request.setAttribute("message", "社員ID[" + staff_id + "]のユーザ情報は見つかりません。");
		}

		return "../../../SystemAdmin/staff/detail.jsp";
	}

}
