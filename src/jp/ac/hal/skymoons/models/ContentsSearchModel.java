package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsSearchBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsSearchDao;

public class ContentsSearchModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//DAOのインスタンス化
		ContentsSearchDao dao = new ContentsSearchDao();
		
		//検索に使用するDAOを選択しRequestにコンテンツを設定
		ArrayList<ContentsSearchBean> resultData = null;
		switch (request.getParameter("searchMode")) {
		case "all":
			resultData = dao.findAll();
			break;
		case "employeeId":
			resultData = dao.selectEmployee(request.getParameter("searchMode"));
			break;
		case "homeContentTitle":
			resultData = dao.selectHomeContentTitle(request.getParameter("homeContentTitle"));
			break;
		case "genreId":
			resultData = dao.selectEmployee(request.getParameter("genreId"));
			break;

		default:
			break;
		}
		
		//結果をリクエストに保存
		request.setAttribute("contentList",resultData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/list.jsp"	;
	}

}
