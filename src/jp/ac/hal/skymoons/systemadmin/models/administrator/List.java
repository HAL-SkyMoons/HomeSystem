package jp.ac.hal.skymoons.systemadmin.models.administrator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.systemadmin.beans.AdministratorBean;
import jp.ac.hal.skymoons.systemadmin.daoes.AdministratorDAO;
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
		
		// 検索機能
		String[] where = null;
		if (request.getAttribute("where") != null) {
			if (request.getAttribute("where") != "") {
				where = request.getAttribute("where").toString()
						.replace("　", " ").split("[\\s]+");
				System.out.println("検索を実行します。");
				for (int i = 0; i < where.length; i++) {
					System.out.println("条件" + (i + 1) + ":" + where[i]);
				}
			}
		} else if (request.getParameter("searchBtn") != null) {
			if (request.getParameter("keyword") != "") {
				where = request.getParameter("keyword").toString()
						.replace("　", " ").split("[\\s]+");
				System.out.println("検索を実行します。");
				for (int i = 0; i < where.length; i++) {
					System.out.println("条件" + (i + 1) + ":" + where[i]);
				}
			}
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
		
		java.util.List<AdministratorBean> administratorList = null;
		try {
			administratorList = administratorDAO.getList(where);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:データベースから社員情報取得中にエラーが発生しました。");
			administratorDAO.connectionClose();
			request.setAttribute("url", "/HomeSystem/fc/SystemAdmin/menu");
			return "../../../SystemAdmin/error/error.jsp";
		}
		administratorDAO.connectionClose();
		
		request.setAttribute("administratorList", administratorList);
		if(where != null) {
			request.setAttribute("where", request.getParameter("keyword"));
		}
		return "../../../SystemAdmin/administrator/list.jsp";
	}

}
