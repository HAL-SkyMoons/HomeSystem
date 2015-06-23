package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsUpdateBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsUpdateDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class ContentsUpdateModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//ログインユーザーが社員であるかを確認する
		SessionController sc = new SessionController(request);
		//if(sc.getUserClass_flag() != null && sc.getUserClass_flag() == "1"){
			//updateBeanに入力値を保存
			ContentsUpdateBean updateBean = new ContentsUpdateBean();
			
			//コンテンツID
			updateBean.setHomeContentId(Integer.parseInt((String) request.getParameter("homeContentId")));
			
			//日時の文字列生成
			String startYear = request.getParameter("startYear");
			String startMonth = request.getParameter("startMonth");
			String startDay = request.getParameter("startDay");
			String startHour = request.getParameter("startHour");
			String startMinute = request.getParameter("startMinute");
			String startDatetime = startYear + "-" + startMonth + "-" + startDay + " " + startHour + ":" + startMinute + ":00";		
			updateBean.setHomeContentDatetime(startDatetime);
			
			String endYear = request.getParameter("endYear");
			String endMonth = request.getParameter("endMonth");
			String endDay = request.getParameter("endDay");
			String endDatetime = endYear + "-" + endMonth + "-" + endDay;		
			updateBean.setEndDate(endDatetime);
	
			//コンテンツタイトル
			updateBean.setHomeContentTitle(request.getParameter("homeContentTitle"));
			
			//コンテンツ内容
			updateBean.setHomeContentComment(request.getParameter("homeContentComment"));
			
			//社員ID
			//updateBean.setEmployeeId(sc.getUserId());
			updateBean.setEmployeeId("E0001");
			
			//添付資料
			
			//ジャンル
			String[] genreArray = request.getParameterValues("genreId");
			ArrayList<Integer> genreId = new ArrayList<>();
			for(String genreString : genreArray){
				genreId.add(Integer.parseInt(genreString));
			}
			updateBean.setGenreId(genreId);
	
			//DAOのインスタンス化
			ContentsUpdateDao dao = new ContentsUpdateDao();
			dao.updateContent(updateBean);
			dao.changeGenre(updateBean);
			
			//結果をリクエストに保存
			//request.setAttribute("detailList",detailData);
			//request.setAttribute("homeLogList",homeLogData);
			
			//コミットと終了処理
			dao.commit();
			dao.close();
			request.setAttribute("scriptMessage","<script>alert('更新が完了しました。')</script>");
		//}
		//遷移先を指定
		return "/fc/contents/detail?homeContentId=" + updateBean.getHomeContentId();
	}

}
