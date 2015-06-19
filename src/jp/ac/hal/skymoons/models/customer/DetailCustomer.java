package jp.ac.hal.skymoons.models.customer;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.CustomerDAO;

/**
 * 顧客詳細情報の取得。
 * @author YAMAZAKI GEN
 * @since 2015/06/17
 * @version 1.0
 */
public class DetailCustomer extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CustomerDAO customerDAO = new CustomerDAO();
		HashMap<String, String> value = null;
		try {
			value = customerDAO.getCustomerDetail(request.getParameter("id").toString());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("顧客詳細情報の取得処理中に問題が発生しました。");
		} finally {
			customerDAO.close();
		}
		request.setAttribute("value", value);
		return "/customer/detail.jsp";
	}

}
