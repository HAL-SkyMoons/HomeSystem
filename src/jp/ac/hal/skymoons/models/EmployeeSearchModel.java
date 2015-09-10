package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BigGenreBean;
import jp.ac.hal.skymoons.beans.DepartmentBean;
import jp.ac.hal.skymoons.beans.EmployeeListBean;
import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;
public class EmployeeSearchModel extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("employee search model in");

		//返り値設定
		ArrayList<GenreBean> genreReturn = new ArrayList<GenreBean>();
		ArrayList<DepartmentBean> departmentReturn = new ArrayList<DepartmentBean>();
		ArrayList<EmployeeListBean> employeeReturn = new ArrayList<EmployeeListBean>();
		ArrayList<BigGenreBean>  bigGenreList = new ArrayList<BigGenreBean>();
		//Session取得
		SessionController sessionController = new SessionController(request);
		System.out.println("SessionID is"+sessionController.getUserId());
		//Daoインスタンス化
		SampleDao dao = new SampleDao();

		//引数有無による機能変化
		if(request.getParameter("submit")!=null){
			//引数取得
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			String[] genres = request.getParameterValues("genre");
			//部署検索orジャンル検索判定、実行結果取得
			if(departmentId==0&&genres!=null){
				employeeReturn = (ArrayList<EmployeeListBean>)dao.getEmployeeByGenre(genres);
			}else if(departmentId>0&&genres==null){
				employeeReturn = (ArrayList<EmployeeListBean>)dao.getEmployeeByDepartment(departmentId);
			}else{
				employeeReturn=(ArrayList<EmployeeListBean>)dao.getEmployee();
			}
		}else{
			employeeReturn=(ArrayList<EmployeeListBean>)dao.getEmployee();
		}

		//ジャンル、部署取得
		genreReturn = (ArrayList<GenreBean>)dao.getGenreList();
		departmentReturn = (ArrayList<DepartmentBean>)dao.getDepartmentList();
		bigGenreList = (ArrayList<BigGenreBean>) dao.getAllBigGenre();

		//Daoクローズ
		dao.close();
		//引数の送信
		request.setAttribute("employees", employeeReturn);
		request.setAttribute("genres", genreReturn);
		request.setAttribute("departments", departmentReturn);
		request.setAttribute("sessionId", (String)sessionController.getUserId());
		request.setAttribute("bigGenreList", bigGenreList);
		//Header用データ取得
		HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
		ArrayList<EmployeePageBean> employeePageReturn = headerUtil
				.getHeaderData(request, response);
		request.setAttribute("employeeDetail", employeePageReturn);
		//参照jspファイルパスの指定
		return "/Employee/EmployeeList.jsp";
	}
}
