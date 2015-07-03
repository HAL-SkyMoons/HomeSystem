package jp.ac.hal.skymoons.models.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.hal.skymoons.beans.customer.CustomerUsersBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.customer.CustomerDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 顧客情報追加処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/12
 * @version 1.0
 */
public class CreateAdd extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッションチェック
		SessionController sessionController = new SessionController(request);
		String url = sessionController.checkAdministratorSession();
		if(url != null) {
			// セッションが無効
			return url;
		}
		
		// 確認ボタンが押されたか？
		if(request.getParameter("submit") != null) {
			// 確認ボタンが押された
			String message = "";
			
			CustomerUsersBean record = new CustomerUsersBean();
			record.setUser_id(request.getParameter("id"));
			record.setPassword(request.getParameter("password"));
			record.setLast_name(request.getParameter("lastname"));
			record.setFirst_name(request.getParameter("firstname"));
			record.setCustomer_company(request.getParameter("company"));
			
			// 「企業名」の入力値チェック
			if(checkInputString(record.getCustomer_company(), 20, "「企業名」") != null) {
				message += checkInputString(record.getCustomer_company(), 20, "「企業名」");
			}
			
			// 「姓」の入力値チェック
			if(checkInputString(record.getLast_name(), 6, "「性」") != null) {
				message += checkInputString(record.getLast_name(), 6, "「性」");
			}
			
			// 「名」の入力値チェック
			if(checkInputString(record.getFirst_name(), 6, "「名」") != null) {
				message += checkInputString(record.getFirst_name(), 6, "「名」");
			}
			
			// 「ログインID」の入力値チェック
			if(checkInputString(record.getUser_id(), 24, "「ログインID」") != null) {
				message += checkInputString(record.getUser_id(), 24, "「ログインID」");
			} else {				
				// 「ログインID」の重複チェック
				CustomerDAO customerDAO = null;
				try {
					customerDAO = new CustomerDAO();
					if(customerDAO.checkUserId(record.getUser_id()) == true) {
						// 重複あり
						message += "<p>「ログインID」の" + record.getUser_id() + "は既に使用されていますので変更してください。</p>";
					}
				} catch(Exception e) {
					e.printStackTrace();
					System.out.print("ERROR:「ログインID」の重複チェック処理中に問題が発生しました。");
				}
			}
			
			// 「パスワード」の入力値チェック
			if(record.getPassword().length() < 8) {
				message += "<p>「パスワード」は8～24文字以内で入力してください。</p>";
			} else if(record.getPassword().equals(request.getParameter("password2"))) {
				// 一致する
			} else {
				// 一致しない
				message += "<p>「パスワード(確認)」が「パスワード」と一致しません。</p>";
			}
			
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60 * 5);
			session.setAttribute("CustomerAddValue", record);
			
			// 入力値エラーチェック
			if(message == "") {
				// 入力値エラー無し
				return "/customer/signup_check.jsp";
			} else {
				// 入力値エラー有り
				request.setAttribute("message", message);
				return "/customer/signup.jsp";
			}
		} else {
			// 確認ボタンが押されていない
			return "/customer/signup.jsp";
		}
	}
	
	/**
	 * インプット入力値チェックを行う。
	 * @param value
	 * チェックする値
	 * @param maxlength
	 * 最大文字数
	 * @param word
	 * エラーメッセージ対象ワード
	 * @return
	 */
	private String checkInputString(String value, int maxlength, String word) {
		if(value.equals("")) {
			// 未入力
			return "<p>" + word + "を入力してください。</p>";
		} else {			
			// 入力済み
			// 最大文字数チェック
			if(value.length() <= maxlength) {
				// 最大文字数許容範囲内
				return null;
			} else {
				// 最大文字数許容範囲外
				return "<p>" + word + "の文字数は" + maxlength + "文字以内にしてください。";
			}
		}
	}
	
	/**
	 * 
	 * @param checkValue
	 * チェックする文字列
	 * @param checktype
	 * 1:小文字のみの文字列か確認する<br />
	 * 2:大文字のみの文字列か確認する
	 * @return
	 * 
	 * @throws Exception
	 */
	private String checkPatternInputString(String checkValue, byte checktype) throws Exception {
		byte[] code = null;
		if(checkValue != null) {
			code = checkValue.getBytes("UTF-8");
		} else {
			System.out.println("ERROR:文字列チェック処理に問題が発生しました。");
			System.out.println("checkValueがnullなので文字列チェックが出来ません。");
			return "checkValueがnullなので文字列チェックが出来ません。";
		}

		String message = null;
		switch (checktype) {
		case 1:
			for(int i = 0; i < code.length; i++) {
				if(code[i] < 2) {
					System.out.println(code[i] + ":半:" + Integer.toHexString(code[i]));
				} else {
					System.out.println(code[i] + ":全:" + Integer.toHexString(code[i]));
				}
			}
			break;
		case 2:
		default:
			break;
		}
		return "";
	}
}
