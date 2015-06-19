package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsEmployeeBean;
import jp.ac.hal.skymoons.beans.ContentsGenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsGenreDao;
import jp.ac.hal.skymoons.daoes.ContentsSearchDao;

public class ContentsSearchModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//DAOのインスタンス化
		ContentsSearchDao dao = new ContentsSearchDao();
		
		//ジャンルとユーザーの取得
		ArrayList<ContentsEmployeeBean> employeeList = dao.findEmployee();
		
		ContentsGenreDao genreDao = new ContentsGenreDao();
		ArrayList<ContentsGenreBean> genreList = genreDao.findAll();
		
		//結果をリクエストに保存
		request.setAttribute("employeeList",employeeList);
		request.setAttribute("genreList",genreList);
		
		//コミットと終了処理
		genreDao.close();
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/search.jsp";
	}

}
