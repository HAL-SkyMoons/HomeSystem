package jp.ac.hal.skymoons.models.staff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;

/**
 * 社員一覧画面作成処理。
 * @author YAMAZAKI GEN
 * @since 2015/07/14
 * @version 1.0
 */
public class CreateList extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return "/staff/list.jsp";
	}

}
