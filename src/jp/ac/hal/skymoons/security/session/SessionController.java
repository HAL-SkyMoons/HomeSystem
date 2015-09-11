package jp.ac.hal.skymoons.security.session;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.daoes.SampleDao;

/**
 * セッションに関する操作と管理を行う。
 * @author YAMAZAKI GEN
 * @since 2015/06/05
 * @version 2.0
 */
public class SessionController {



	private HttpSession session = null;
	// リダイレクト用セッションエラーページURL
	private String redirectSessionErrorPageUrl =
		"/HomeSystem/error/session.jsp";
	// フォワード用セッションエラーページURL
	private String forwardSessionErrorPageUrl =
			"/login/cs.jsp";
		//"/error/session.jsp";
	// 社員・顧客ユーザのフォワード用セッションエラーページURL
	private String csUserforwardSessionErrorPageUrl =
		"/login/cs.jsp";
	// 社員・顧客ユーザのフォワード用セッションエラーページURL
	private String administratorUserforwardSessionErrorPageUrl =
		"/login/a.jsp";
	// セッションタイム
	private int sessionTime = 60 * 30; // 30分

	/**
	 * セッションを取得し、操作する為の準備を行う。
	 * @param request
	 */
	public SessionController(HttpServletRequest request) {
		this.session = request.getSession();
		this.session.setMaxInactiveInterval(sessionTime);
	}

// ==========================================================================================
//  セッションの登録機能
// ==========================================================================================

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
	public void setUserIdAndGroup(String userId, String classFlag) {
		setId("uId", userId);
		setId("classFlag", classFlag);
	}

	/**
	 * セッション登録用共通機能。
	 * @param id
	 * セッションのキー
	 * @param value
	 * セッションに登録する値
	 */
	private void setId(String key, String value) {
		this.session.setAttribute(key, value);
	}

// ==========================================================================================
//  セッションの継続確認機能
// ==========================================================================================

	/**
	 * 管理者ユーザのセッションが有効か確認を行う。
	 * @return
	 * セッションが切れている:nullを返す<br />
	 * セッションが継続されている:セッションエラーページのURLを返す
	 */
	public String checkAdministratorSession() {
		return checkSession("aId");
	}

	/**
	 * 顧客ユーザ又は社員ユーザのセッションが有効か確認を行う。
	 * @return
	 * セッションが切れている:nullを返す<br />
	 * セッションが継続されている:セッションエラーページのURLを返す
	 */
	public String checkUserSession() {
		return checkSession("uId");
	}

	/**
	 * セッションが有効か確認を行う共通機能。
	 * @param key
	 * セッションのキー
	 * @return
	 * セッションが切れている:nullを返す<br />
	 * セッションが継続されている:セッションエラーページのURLを返す
	 */
	private String checkSession(String key) {
		if(this.session.getAttribute(key) != null) {
			setId(key, this.session.getAttribute(key).toString());
			if(key.equals("uId")) {
				if(this.session.getAttribute("classFlag") != null) {

					setId("classFlag", this.session.getAttribute("classFlag").toString());
					return null;
				} else {
					if(key.equals("uId")) {
						return csUserforwardSessionErrorPageUrl;
					} else {
						return administratorUserforwardSessionErrorPageUrl;
					}
				}
			} else {
				return null;
			}
		} else {
			if(key.equals("uId")) {

				return csUserforwardSessionErrorPageUrl;
			} else {
				return administratorUserforwardSessionErrorPageUrl;
			}
		}
	}

// ==========================================================================================
//  セッションの継続確認機能（２）
// ==========================================================================================

	/**
	 * 管理者ユーザのセッションが有効か確認を行う。
	 * @return
	 * セッションが切れている:falseを返す<br />
	 * セッションが継続されている:trueを返す
	 */
	public Boolean checkAdministratorSession2() {
		return checkSession2("aId");
	}

	/**
	 * 顧客ユーザ又は社員ユーザのセッションが有効か確認を行う。
	 * @return
	 * セッションが切れている:falseを返す<br />
	 * セッションが継続されている:trueを返す
	 */
	public Boolean checkUserSession2() {
		return checkSession2("uId");
	}

	/**
	 * セッションが有効か確認を行う共通機能。
	 * @param key
	 * セッションのキー
	 * @return
	 * セッションが切れている:falseを返す<br />
	 * セッションが継続されている:trueを返す
	 */
	private Boolean checkSession2(String key) {
		if(this.session.getAttribute(key) != null) {
			setId(key, this.session.getAttribute(key).toString());
			if(key.equals("uId")) {
				if(this.session.getAttribute("classFlag") != null) {
					setId("classFlag", this.session.getAttribute("classFlag").toString());
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

// ==========================================================================================
//  セッションの破棄機能
// ==========================================================================================

	/**
	 * 管理者ユーザのセッションを破棄する。
	 */
	public void discardAdministratorSession() {
		discardSession("aId");
	}

	/**
	 * 顧客ユーザ又は社員ユーザのセッションを破棄する。
	 */
	public void discardUserSession() {
		discardSession("uId");
		discardSession("classFlag");
	}

	/**
	 * セッション破棄を行う共通機能。
	 * @param key
	 * セッションの名前
	 */
	private void discardSession(String key) {
		this.session.removeAttribute(key);
	}

// ==========================================================================================
//  セッション情報の取得機能
// ==========================================================================================

	/**
	 * セッションから管理者ユーザIDを取得する。
	 * @return
	 * 管理者ユーザID
	 */
	public String getAdministratorId() {
		return this.session.getAttribute("aId").toString();
	}

	/**
	 * セッションから顧客ユーザ又は社員ユーザIDを取得する。
	 * @return
	 * 顧客ユーザ又は社員ユーザID
	 */
	public String getUserId() {
		return this.session.getAttribute("uId").toString();
	}

	/**
	 * セッションから顧客ユーザ又は社員ユーザのクラスフラグを取得する。
	 * @return
	 * 顧客ユーザ又は社員ユーザのクラスフラグ
	 */
	public String getUserClass_flag() {
		return this.session.getAttribute("classFlag").toString();
	}

// ==========================================================================================
//  セッションエラーページURLの取得機能
// ==========================================================================================

	/**
	 * リダイレクト用のセッションエラーページのURLを取得する。
	 * @return
	 */
	public String getRedirectSessionErrorPageUrl() {
		return this.redirectSessionErrorPageUrl;
	}

	/**
	 * フォワード用のセッションエラーページのURLを取得する。
	 * @return
	 */
	public String getForwardSessionErrorPageUrl() {
		return this.forwardSessionErrorPageUrl;
	}
}