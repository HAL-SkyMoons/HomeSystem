package jp.ac.hal.skymoons.systemadmin.models.staff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.daoes.StaffDAO;
import jp.ac.hal.skymoons.systemadmin.security.SessionController;

/**
 * 社員ユーザ登録画面作成機能
 * @author YAMAZAKI GEN
 * @since 2015/09/09
 * @version 1.0
 */
public class Add extends AbstractModel	 {

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
		
		// 入力フォーム確認
		if(request.getParameter("submitBtn") != null) {
			String[] values = {request.getParameter("lastName"), request.getParameter("firstName"), request.getParameter("lastNameKana"), request.getParameter("firstNameKana"), request.getParameter("id"), request.getParameter("password"), request.getParameter("department"), request.getParameter("useFlg")};
			if(request.getParameter("lastName") != ""
				&& request.getParameter("firstName") != ""
				&& request.getParameter("lastNameKana") != ""
				&& request.getParameter("firstNameKana") != ""
				&& Integer.parseInt(request.getParameter("department")) != -1
				&& request.getParameter("id") != ""
				&& request.getParameter("password") != ""
				&& request.getParameter("useFlg") != null) {
				
				// データベース登録処理
				StaffDAO staffDAO = null;
				try {
					staffDAO = new StaffDAO();
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
					request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/staff/list");
					return "../../../SystemAdmin/error/error.jsp";
				}
				if(staffDAO.getConnectionStatus() == false) {
					System.out.println("ERROR:データベースコネクションの取得が失敗しました。");
					request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/staff/list");
					return "../../../SystemAdmin/error/error.jsp";
				}
				try {
					staffDAO.startTransaction();
					staffDAO.addStaffUser(values);
					staffDAO.commit();
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR:社員ユーザ情報のデータベース登録処理中にエラーが発生しました。");
					staffDAO.rollback();
					staffDAO.connectionClose();
					request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/staff/list");
					return "../../../SystemAdmin/error/error.jsp";
				}
				staffDAO.connectionClose();
				
				return "list";
				
			} else {
				request.setAttribute("values", values);
				request.setAttribute("message", "未入力項目があります。");
			}
		}
		
		return "../../../SystemAdmin/staff/add.jsp";
	}

}
