package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
		ArrayList<ContentsGenreBean> genreList = genreDao.findGenre();
		ArrayList<ContentsGenreBean> bigGenreList = genreDao.findBigGenre();
		
	    //order用HashMap設定
	    LinkedHashMap<String, String> orderList = new LinkedHashMap<String, String>();
	    orderList.put("dateDesc", "投稿日時が新しい順");
	    orderList.put("dateAsc", "投稿日時が古い順");
	    orderList.put("commentDesc", "企画開始日時が新しい順");
	    orderList.put("commentAsc", "企画開始日時が古い順");
	    orderList.put("likeDesc", "企画終了日時が新しい順");
	    orderList.put("likeAsc", "企画終了日時が古い順");
	    orderList.put("titleDesc", "タイトル名昇順");
	    orderList.put("titleAsc", "タイトル名降順");
	    orderList.put("nameDesc", "社員名昇順");
	    orderList.put("nameAsc", "社員名降順");
		
		//結果をリクエストに保存
		request.setAttribute("employeeList",employeeList);
		request.setAttribute("genreList",genreList);
		request.setAttribute("bigGenreList",bigGenreList);
	    request.setAttribute("orderList", orderList);
		
		
		//コミットと終了処理
		genreDao.close();
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/search.jsp";
	}

}
