package jp.ac.hal.skymoons.models.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.customer.CustomerUsersBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.customer.CustomerDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 顧客ユーザ情報編集画面作成処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/19
 * @version 1.0
 */
public class CreateEdit extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッションチェック
		String url = null;
		SessionController sessionController = new SessionController(request);
		if(sessionController.checkUserSession2()) {
			url = "/customer/edit_u.jsp";
		} else if(sessionController.checkAdministratorSession2()) {
			url = "/customer/edit_a.jsp";
		} else {
			// セッションが無効
			return sessionController.getForwardSessionErrorPageUrl();
		}
		
		if(request.getParameter("id") == null) {
			return "/error/system.jsp";
		}
		
		// 顧客詳細情報の取得
		CustomerUsersBean record = null;
		CustomerDAO customerDAO = null;
		try {
			customerDAO = new CustomerDAO();
			record = customerDAO.getCustomerUsersDetail(request.getParameter("id"));
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:顧客詳細情報の取得中に問題が発生しました。");
		} finally {
			if(customerDAO != null)
				customerDAO.close();
		}
		request.setAttribute("record", record);
		
		String message = "";
		// 名前変更機能
		if(request.getParameter("namebtn") != null) {
			if(request.getParameter("lastname").length() > 0) {
				
			} else {
				message += "<p>「姓」が未入力です。</p>";
			}
			if(request.getParameter("firstname").length() > 0) {
			
			} else {
				message += "<p>「名」が未入力です。</p>";
			}
			if(message.equals("")) {
				// 名前更新実行
				record.setLast_name(request.getParameter("lastname"));
				record.setFirst_name(request.getParameter("firstname"));
				
				customerDAO = null;
				try {
					customerDAO = new CustomerDAO();
					customerDAO.updateCustomerName(record.getUser_id(), record.getLast_name(), record.getFirst_name());
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR:名前更新処理中に問題が発生しました。");
				} finally {
					if(customerDAO != null) {
						customerDAO.close();
					}
				}
				
				message = "<p>名前を変更しました。</p>";
			}
		// 企業名変更機能
		} else if(request.getParameter("companybtn") != null) {
			if(request.getParameter("company").length() > 0) {
			
			} else {
				message += "<p>「企業名」が未入力です。</p>";
			}
			if(message.equals("")) {
				// 企業名更新処理
				record.setCustomer_company(request.getParameter("company"));
				
				customerDAO = null;
				try {
					customerDAO = new CustomerDAO();
					customerDAO.updateCustomerCompany(record.getUser_id(), record.getCustomer_company());
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR:企業名更新処理中に問題が発生しまいた。");
				} finally {
					if(customerDAO != null) {
						customerDAO.close();
					}
				}
				
				message = "<p>企業名を更新しました。</p>";
			}
		// パスワード変更機能
		} else if(request.getParameter("passwordbtn") != null) {
			if(request.getParameter("password").length() >= 8) {
				if(request.getParameter("password").equals(request.getParameter("password2"))) {
					
				} else {
					message += "<p>「パスワード」と「パスワード(確認)が一致しません。」</p>";
				}
			} else {
				message += "<p>「パスワード」は８文字以上２０字以内で入力してください。</p>";
			}
			if(message.equals("")) {
				// パスワード変更処理
				record.setPassword(request.getParameter("password"));
				
				customerDAO = null;
				try {
					customerDAO = new CustomerDAO();
					customerDAO.updateCustomerPassword(record.getUser_id(), record.getPassword());
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR:パスワード変更処理中に問題が発生しました。");
				} finally {
					if(customerDAO != null) {
						customerDAO.close();
					}
				}
			
				message  = "<p>パスワードを変更しました。</p>";
			}
		}
		
		if(message.equals("") == false) {
			request.setAttribute("message", message);
		}
		return url;
	}
}
