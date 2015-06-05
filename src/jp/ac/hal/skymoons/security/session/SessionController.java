package jp.ac.hal.skymoons.security.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * セッションに関する操作と管理を行う。
 * @author YAMAZAKI GEN
 * @since 2015/05/26
 * @version 1.0
 */
public class SessionController {
	
	private HttpSession session = null;
	
	/**
	 * セッションを取得し、操作する為の準備を行う。
	 * @param request
	 */
	public SessionController(HttpServletRequest request) {
		this.session = request.getSession();
	}
	
	/**
	 * 管理者ユーザのユーザIDをセッションに登録する。
	 * @param aId
	 * 管理者ユーザID
	 */
	public void setAdministratorId(String aId) {
		setId("aId", aId);
	}
	
	/**
	 * 顧客ユーザ又は社員ユーザのユーザIDとユーザグループをセッションに登録する。
	 * @param uId
	 * 顧客ユーザ又は社員ユーザのユーザID
	 * @param group
	 * 顧客ユーザ又は社員ユーザのユーザグループ
	 */
	public void setUserIdAndGroup(String uId, String group) {
		setId("uId", uId);
		setId("group", group);
	}
	
	/**
	 * セッション登録用共通機能。
	 * @param id
	 * セッションの名前
	 * @param value
	 * セッションに登録する値
	 */
	private void setId(String id, String value) {
		this.session.setAttribute(id, value);
	}
	
	/**
	 * 管理者ユーザのセッションが有効か確認を行う。
	 * @return
	 * 有効:nullを返す/無効:セッションエラーページのURLを返す
	 */
	public String checkAdministratorSession() {
		return checkSession("aId");
	}
	
	/**
	 * 顧客ユーザ・社員ユーザのセッションが有効か確認を行う。
	 * @return
	 * 有効:nullを返す/無効:セッションエラーページのURLを返す
	 */
	public String checkUserSession() {
		return checkSession("uId");
	}
	
	/**
	 * セッションが有効か確認を行う共通機能。
	 * @param id
	 * セッションの名前
	 * @return
	 * 有効:nullを返す/無効:セッションエラーページのURLを返す
	 */
	private String checkSession(String id) {
		if(this.session.getAttribute(id) != null) {
			setId(id, this.session.getAttribute(id).toString());
			if(id.equals("uId")) {
				setId("group", this.session.getAttribute("group").toString());
			}
		} else {
			return "ERROR_PAGE_URL";
		}
		return null;
	}
	
	/**
	 * 管理者ユーザのセッションを破棄する。
	 */
	public void discardAdministratorSession() {
		discardSession("aId");
	}
	
	/**
	 * 顧客ユーザ・社員ユーザのセッションを破棄する。
	 */
	public void discardUserSession() {
		discardSession("uId");
		discardSession("group");
	}
	
	/**
	 * セッション破棄を行う共通機能。
	 * @param id
	 * セッションの名前
	 */
	private void discardSession(String id) {
		this.session.removeAttribute(id);
	}
	
	/**
	 * セッションから管理者ユーザIDを取得。
	 * @return
	 * 管理者ユーザID
	 */
	public String getAdministratorId() {
		return this.session.getAttribute("aId").toString();
	}
	
	/**
	 * セッションから顧客ユーザ又は社員ユーザIDを取得。
	 * @return
	 * 顧客ユーザ又は社員ユーザID
	 */
	public String getUserId() {
		return this.session.getAttribute("uId").toString();
	}
	
	/**
	 * セッションから顧客ユーザ又は社員ユーザクラスフラグを取得。
	 * @return
	 * 顧客ユーザ又は社員ユーザクラスフラグ
	 */
	public String getUserClass_flag() {
		return this.session.getAttribute("group").toString();
	}
	
}
