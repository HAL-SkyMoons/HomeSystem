package jp.ac.hal.skymoons.models.login;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.daoes.login.LoginDAO;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

/**
 * 顧客・社員ユーザログイン認証機能。
 *
 * @author YAMAZAKI GEN
 * @since 2015/06/02
 * @version 1.0
 */

public class Index extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			// ヘッダー情報取得処理(getHeaderDataメソッド 引数：HttpRequestとHttpResponse ,
			// 返り値：ArrayList<EmployeePageBean>
			HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
			ArrayList<EmployeePageBean> employeePageReturn = headerUtil
					.getHeaderData(request, response);
			request.setAttribute("employeeDetail", employeePageReturn);
			return "/index.jsp";
	}
}