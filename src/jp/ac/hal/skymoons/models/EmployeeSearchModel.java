package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.DepartmentBean;
import jp.ac.hal.skymoons.beans.EmployeeListBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
public class EmployeeSearchModel extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		//返り値設定
		ArrayList<GenreBean> genreReturn = new ArrayList<GenreBean>();
		ArrayList<DepartmentBean> departmentReturn = new ArrayList<DepartmentBean>();
		ArrayList<EmployeeListBean> employeeReturn = new ArrayList<EmployeeListBean>();

		//Daoインスタンス化
		SampleDao dao = new SampleDao();

		//引数有無による機能変化
		if(request.getParameter("submit")!=null){
			//引数取得
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			String[] genres = request.getParameterValues("genre");
			//部署検索orジャンル検索判定、実行結果取得
			if(departmentId==0&&genres!=null){
				employeeReturn.addAll(dao.getEmployeeByGenre(genres));
			}else{
				employeeReturn.addAll(dao.getEmployeeByDepartment(departmentId));
			}
		}else{
			employeeReturn.addAll(dao.getEmployee());
		}

		//ジャンル、部署取得
		genreReturn.addAll(dao.getGenreList());
		departmentReturn.addAll(dao.getDepartmentList());

		//Daoクローズ
		dao.close();
		//引数の送信
		request.setAttribute("employees", employeeReturn);
		request.setAttribute("genres", genreReturn);
		request.setAttribute("departments", departmentReturn);
		//参照jspファイルパスの指定
		return "/Employee/EmployeeList.jsp";
	}
}
