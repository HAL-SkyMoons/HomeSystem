package jp.ac.hal.skymoons.util;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class HeaderDataGetUtil {
	//ヘッダー出力情報取得method
	public ArrayList<EmployeePageBean> getHeaderData(HttpServletRequest request,HttpServletResponse response) throws NamingException, SQLException{

		SessionController sessionController = new SessionController(request);
		SampleDao dao = new SampleDao();
		ArrayList<EmployeePageBean> employeePageReturn = (ArrayList<EmployeePageBean>) dao
							.getEmployeeDetail(sessionController.getUserId());
		dao.close();
		return employeePageReturn;
	}
}
