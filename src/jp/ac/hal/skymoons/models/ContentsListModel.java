package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsListBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsListDao;

public class ContentsListModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//DAOのインスタンス化
		ContentsListDao dao = new ContentsListDao();
		
		//検索に使用するDAOを選択しRequestにコンテンツを設定
		ArrayList<ContentsListBean> resultData = null;
		String searchMode = request.getParameter("searchMode");
		if(searchMode != null){
			//検索条件を指定された場合
			switch (searchMode) {
				//全検索
				case "all":
					resultData = dao.findAll(request.getParameter("orderColumn"), request.getParameter("orderMode"));
					break;
				//会員ID検索
				case "employeeId":
					resultData = dao.selectEmployee(request.getParameter("employeeId"),request.getParameter("orderColumn"), request.getParameter("orderMode"));
					break;
				//タイトル検索
				case "homeContentTitle":
					resultData = dao.selectHomeContentTitle(request.getParameter("keyword"),request.getParameter("orderColumn"), request.getParameter("orderMode"));
					break;
				//ジャンル検索
				case "genreId":
					//ジャンル
					String[] genreArray = request.getParameterValues("genreId");
					if(genreArray != null){
						ArrayList<Integer> genreId = new ArrayList<>();
						for(String genreString : genreArray){
							genreId.add(Integer.parseInt(genreString));
						}
						resultData = dao.selectGenre(genreId,request.getParameter("orderColumn"), request.getParameter("orderMode"));
					}else{
						//未指定エラーメッセージ
					}
					break;
			}
		}else{
			//検索条件を指定ページからのアクセスではない場合
			resultData = dao.findAll("home_content_id", "asc");
		}
		
		//結果をリクエストに保存
		request.setAttribute("contentsList",resultData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/list.jsp"	;
	}

}
