package jp.ac.hal.skymoons.models.customer;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * 顧客情報追加処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/12
 * @version 1.0
 */
public class AddCustomer extends AbstractModel {

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
			
			HashMap<String, String> value = new HashMap<>();
			value.put("company",	request.getParameter("company"));
			value.put("lastname",	request.getParameter("lastname"));
			value.put("firstname",	request.getParameter("firstname"));
			value.put("id",			request.getParameter("id"));
			value.put("password",	request.getParameter("password"));
			value.put("password2",	request.getParameter("password2"));
			
			// 「企業名」の入力値チェック
			if(checkInputString(value.get("company").toString(), 20, "「企業名」") != null) {
				message += checkInputString(value.get("company").toString(), 20, "「企業名」");
			}
			
			// 「姓」の入力値チェック
			if(checkInputString(value.get("lastname").toString(), 6, "「性」") != null) {
				message += checkInputString(value.get("lastname").toString(), 6, "「性」");
			}
			
			// 「名」の入力値チェック
			if(checkInputString(value.get("firstname").toString(), 6, "「名」") != null) {
				message += checkInputString(value.get("firstname").toString(), 6, "「名」");
			}
			
			// 「ログインID」の入力値チェック
			if(checkInputString(value.get("id").toString(), 24, "「ログインID」") != null) {
				message += checkInputString(value.get("id").toString(), 24, "「ログインID」");
			}
			
			// 「パスワード」の入力値チェック
			if(value.get("password").length() < 8) {
				message += "<p>「パスワード」は8～24文字以内で入力してください。</p>";
			} else if(value.get("password").equals(value.get("password2"))) {
				// 一致する
			} else {
				// 一致しない
				message += "<p>「パスワード(確認)」が「パスワード」と一致しません。</p>";
			}
			
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(60 * 5);
			session.setAttribute("CustomerAddValue", value);
			
			// 入力値エラーチェック
			if(message == "") {
				// 入力値エラー無し
				return "/customer/check.jsp";
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
}
