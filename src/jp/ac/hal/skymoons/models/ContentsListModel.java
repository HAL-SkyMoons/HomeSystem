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
		
		//キーワードの取得
		String titleKeyword = request.getParameter("titleKeyword");
		String commentKeyword = request.getParameter("commentKeyword");
		
		//社員IDの取得
		String employeeId = request.getParameter("employeeId");
		
		//ジャンルの取得
		String[] genreArray = request.getParameterValues("genreId");
		ArrayList<Integer> genreId = new ArrayList<>();
		if(genreArray != null && genreArray.length > 0){
			for(String genreString : genreArray){
				genreId.add(Integer.parseInt(genreString));
			}
		}
		
		//大ジャンルの取得
		String[] bigGenreArray = request.getParameterValues("bigGenreId");
		ArrayList<Integer> bigGenreId = new ArrayList<>();
		if(bigGenreArray != null && bigGenreArray.length > 0){
			for(String bigGenreString : bigGenreArray){
				bigGenreId.add(Integer.parseInt(bigGenreString));
			}
		}
		
		//並び替え条件の取得
		String orderColumn = request.getParameter("orderColumn");
		String orderMode = request.getParameter("orderMode");
		
		//DAOのインスタンス化
		ContentsListDao dao = new ContentsListDao();
		
		//検索に使用するDAOを選択しRequestにコンテンツを設定
		ArrayList<ContentsListBean> resultData = null;
		resultData = dao.selectContents(titleKeyword, commentKeyword, employeeId, genreId, bigGenreId, orderColumn, orderMode);
		
		//結果をリクエストに保存
		request.setAttribute("contentsList",resultData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/list.jsp"	;
	}

}
