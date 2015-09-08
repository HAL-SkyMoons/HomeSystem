package jp.ac.hal.skymoons.models.contents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.contents.ContentsEmployeeBean;
import jp.ac.hal.skymoons.beans.contents.ContentsGenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.contents.ContentsGenreDao;
import jp.ac.hal.skymoons.daoes.contents.ContentsSearchDao;

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
		ArrayList<ContentsGenreBean> genreList = genreDao.findGenre();
		ArrayList<ContentsGenreBean> bigGenreList = genreDao.findBigGenre();
		
	    //order用HashMap設定
	    LinkedHashMap<String, String> orderList = new LinkedHashMap<String, String>();
	    orderList.put("dateDesc", "投稿日時が新しい順");
	    orderList.put("dateAsc", "投稿日時が古い順");
	    orderList.put("startDesc", "企画開始日時が新しい順");
	    orderList.put("startAsc", "企画開始日時が古い順");
	    orderList.put("endDesc", "企画終了日時が新しい順");
	    orderList.put("endAsc", "企画終了日時が古い順");
	    orderList.put("titleDesc", "タイトル名昇順");
	    orderList.put("titleAsc", "タイトル名降順");
	    orderList.put("nameDesc", "社員名昇順");
	    orderList.put("nameAsc", "社員名降順");
		
		//結果をリクエストに保存
		request.setAttribute("employeeList",employeeList);
		request.setAttribute("genreList",genreList);
		request.setAttribute("bigGenreList",bigGenreList);
	    request.setAttribute("orderList", orderList);
		
	    //前ページの検索をリクエストに保存
		request.setAttribute("searchKeyword", request.getParameter("searchKeyword"));
	    request.setAttribute("searchPlanner", request.getParameter("searchPlanner"));
	    request.setAttribute("endContent", request.getParameter("endContent"));
	    request.setAttribute("existPlan", request.getParameter("existPlan"));
	    request.setAttribute("searchOrder", request.getParameter("searchOrder"));
	    
	    if(request.getParameter("searchGenre") != null){
			HashMap<Integer, Integer> searchGenre = new HashMap<Integer, Integer>();
			for (String genre : request.getParameterValues("searchGenre")) {
			    searchGenre.put(Integer.valueOf(genre), Integer.valueOf(genre));
			}
		    request.setAttribute("searchGenre", searchGenre);
	    }
		
	    
		//コミットと終了処理
		genreDao.close();
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/search.jsp";
	}

}
