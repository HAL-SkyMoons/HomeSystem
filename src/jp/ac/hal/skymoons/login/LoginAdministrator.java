package jp.ac.hal.skymoons.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;

/**
 * 管理者ユーザログイン認証機能。
 * @author YAMAZAKI GEN
 * @since 2015/05/26
 */
public class LoginAdministrator extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		
		return "/login/toppage.jsp";
	}

}
