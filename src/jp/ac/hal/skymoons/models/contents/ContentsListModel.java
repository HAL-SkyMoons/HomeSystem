package jp.ac.hal.skymoons.models.contents;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.contents.ContentsGenreBean;
import jp.ac.hal.skymoons.beans.contents.ContentsListBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.contents.ContentsGenreDao;
import jp.ac.hal.skymoons.daoes.contents.ContentsListDao;

public class ContentsListModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		try{
			//キーワードの取得
			String keyword = request.getParameter("keyword");
			
			//社員IDの取得
			String employeeId = request.getParameter("employeeId");
			if(employeeId != null && employeeId.length() == 0){
				employeeId = null;
			}
			
			//ジャンルの取得
			String[] genreArray = request.getParameterValues("genre");
			ArrayList<Integer> genreId = new ArrayList<>();
			if(genreArray != null && genreArray.length > 0){
				for(String genreString : genreArray){
					genreId.add(Integer.parseInt(genreString));
				}
			}
			
			//大ジャンルの取得
			/*
			String[] bigGenreArray = request.getParameterValues("bigGenreId");
			ArrayList<Integer> bigGenreId = new ArrayList<>();
			if(bigGenreArray != null && bigGenreArray.length > 0){
				for(String bigGenreString : bigGenreArray){
					bigGenreId.add(Integer.parseInt(bigGenreString));
				}
			}
			*/
	
			//検索条件の取得
			String existPlan = request.getParameter("existPlan");
			String endContent = request.getParameter("endContent");
			
			//並び替え条件の取得
			//String orderColumn = request.getParameter("orderColumn");
			String order = request.getParameter("order");
			if(order != null && order.length() == 0){
				order = null;
			}
			
			//DAOのインスタンス化
			ContentsListDao dao = new ContentsListDao();
			
			//検索に使用するDAOを選択しRequestにコンテンツを設定
			ArrayList<ContentsListBean> resultData = null;
			resultData = dao.selectContents(keyword, employeeId, genreId, null, endContent, existPlan, order);
			
			//結果をリクエストに保存
			request.setAttribute("contentsList",resultData);
			
			//詳細欄表示用の社員名取得
			String searchPlannerName = null;
			if(employeeId != null){
				searchPlannerName = dao.findEmployeeName(employeeId);
			}
			
			//コミットと終了処理
			dao.commit();
			dao.close();
	
			//ジャンルによる検索をした場合、詳細欄表示用のジャンル取得
			ArrayList<ContentsGenreBean> searchGenre = null;
			if(genreId.size() > 0){
				ContentsGenreDao genreDao = new ContentsGenreDao();
				searchGenre = genreDao.findGenre(genreId);
				genreDao.commit();
				genreDao.close();
			}
			
			//詳細欄表示用に値をセット
			request.setAttribute("endContent", endContent);
			request.setAttribute("existPlan", existPlan);
			request.setAttribute("searchGenre", searchGenre);
			request.setAttribute("searchPlanner", employeeId);
			request.setAttribute("searchPlannerName", searchPlannerName);
			request.setAttribute("keyword", keyword);
			request.setAttribute("order", order);
			
			//遷移先を指定
			return "/contents/list.jsp"	;
		}catch(Exception e){
			e.printStackTrace();
			return "/pages/error.html";
		}
	}

}
