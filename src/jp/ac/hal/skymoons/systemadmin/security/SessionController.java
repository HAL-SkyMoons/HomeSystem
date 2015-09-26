package jp.ac.hal.skymoons.systemadmin.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理システム専用セッション管理
 * @author YAMAZAKI GEN
 * @since 2015/08/21
 * @version 1.0
 */
public class SessionController {

	private HttpSession session = null;
	private int time = 60 * 30; // 30m
	private String id = "AdministratorId";
	private String sessionErrorGetURL = "/HomeSystem/fc/SystemAdmin/login";
	private String sessionErrorPostURL = "login";
	
	/**
	 * セッションを取得し、操作準備を行います。
	 * @param request
	 */
	public SessionController(HttpServletRequest request) {
		this.session = request.getSession();
		this.session.setMaxInactiveInterval(time);
	}
	
	// ==========================================================================================
	
	/**
	 * 管理者ユーザのIDを登録します。
	 * @param administratorId
	 */
	public void setAdministratorId(String administratorId) {
		this.session.setAttribute(this.id, administratorId);
	}
	
	/**
	 * 管理者ユーザのセッション状況を取得します。
	 * @return
	 * TRUE:セッションが継続されています<br />
	 * FALSE:セッションがありません
	 */
	public Boolean getSessionStatus() {
		if(this.session.getAttribute(this.id) != null) {
			return true;
		} else {
			System.out.println("SESSION ERROR:セッションがありません。");
			return false;
		}
	}
	
	/**
	 * セッションから管理者ユーザのIDを取得します。
	 * @return
	 * ID
	 */
	public String getAdministratorId() {
		return this.session.getAttribute(this.id).toString();
	}
	
	/**
	 * セッションを終了します。
	 */
	public void discardSession() {
		this.session.removeAttribute(this.id);
	}
	
	/**
	 * セッションエラー時のGET送信用URLを取得します。
	 * @return
	 */
	public String getSessionErrorGetURL() {
		return this.sessionErrorGetURL;
	}
	
	/**
	 * セッションエラー時のPOST送信用URLを取得します。
	 * @return
	 */
	public String getSessionErrorPostURL() {
		return this.sessionErrorPostURL;
	}
}
