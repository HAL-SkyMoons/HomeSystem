package jp.ac.hal.skymoons.systemadmin.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;

/**
 * 管理システムメニュー画面アクセス
 * @author YAMAZAKI GEN
 * @since 2015/08/20
 * @version 1.0
 */
public class Menu extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッション確認
		
		// メニュー画面へ遷移
		return "../../SystemAdmin/menu.jsp";
	}

}
