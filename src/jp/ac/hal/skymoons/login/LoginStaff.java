package jp.ac.hal.skymoons.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;

/**
 * 社員ユーザログイン認証機能。
 * @author YAMAZAKI GEN
 * @since 2015/05/29
 * @version 1.0
 */
public class LoginStaff extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		
		return "/login/toppage.jsp";
	}

}
