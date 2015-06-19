package jp.ac.hal.skymoons.models.customer;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.CustomerDAO;

/**
 * 顧客情報データベース登録処理。
 * @author YAMAZAKI GEN
 * @since 2015/06/16
 * @version 1.0
 */
public class InsertCustomer extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("CustomerAddValue") == null) {
			return "/fc/customer/add";
		}
		HashMap<String, String> value = getValue(session);
		session.removeAttribute("CustomerAddValue");
		CustomerDAO customerDAO = new CustomerDAO();
		
		try {
			customerDAO.insertCustomer(value);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:顧客情報データベース追加処理中に問題が発生しました。");
			return "/SystemError";
		} finally {
			customerDAO.close();
		}
		return "/fc/customer/list";
	}
	
	/**
	 * 入力情報の取得。
	 * @return
	 * value
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getValue(HttpSession session) {
		return (HashMap<String, String>)session.getAttribute("CustomerAddValue");
	}

}
