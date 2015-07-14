package jp.ac.hal.skymoons.models.contents;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.contents.ContentsAdditionBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.contents.ContentsAdditionDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class ContentsAdditionModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//ログインユーザーが社員であるかを確認する
		SessionController sc = new SessionController(request);
		if(sc.checkUserSession2() == true && sc.getUserClass_flag() != null && sc.getUserClass_flag().equals("1") && sc.getUserId() != null){
			//updateBeanに入力値を保存
			ContentsAdditionBean additionBean = new ContentsAdditionBean();
						
			//日時の文字列生成
			String year = request.getParameter("startYear");
			String month = request.getParameter("startMonth");
			String day = request.getParameter("startDay");
			String hour = request.getParameter("startHour");
			String minute = request.getParameter("startMinute");
			String startDatetime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";		
			additionBean.setStartDatetime(startDatetime);
			
			//終了日の取得
			if(request.getParameter("addEndDatetime") == null){
				String endYear = request.getParameter("endYear");
				String endMonth = request.getParameter("endMonth");
				String endDay = request.getParameter("endDay");
				String endHour = request.getParameter("endHour");
				String endMinute = request.getParameter("endMinute");
				String endDatetime = endYear + "-" + endMonth + "-" + endDay + " " + endHour + ":" + endMinute + ":00";		
				additionBean.setEndDatetime(endDatetime);
			}else{
				additionBean.setEndDatetime(null);
			}
	
			//コンテンツタイトル
			additionBean.setHomeContentTitle(request.getParameter("homeContentTitle"));
			
			//コンテンツ内容
			additionBean.setHomeContentComment(request.getParameter("homeContentComment"));
			
			//社員ID
			//additionBean.setEmployeeId("E0001");
			additionBean.setEmployeeId(sc.getUserId());
			
			//添付資料
			
			//ジャンル
			String[] genreArray = request.getParameterValues("genreId");
			ArrayList<Integer> genreId = new ArrayList<>();
			if(genreArray != null){
				for(String genreString : genreArray){
					genreId.add(Integer.parseInt(genreString));
				}
			}
			additionBean.setGenreId(genreId);
	
			//DAOのインスタンス化
			ContentsAdditionDao dao = new ContentsAdditionDao();
			dao.addContent(additionBean);
			dao.addGenre(additionBean);
			
			if(additionBean.getEndDatetime() != null){
				dao.addEndDatetime(additionBean);
			}
			
			//結果をリクエストに保存
			//request.setAttribute("detailList",detailData);
			//request.setAttribute("homeLogList",homeLogData);
			
			//コミットと終了処理
			dao.commit();
			dao.close();
			request.setAttribute("scriptMessage","<script>alert('投稿が完了しました。')</script>");
			return "/fc/contents/detail?homeContentId=" + additionBean.getHomeContentId();
		}
		//エラーメッセージ
		request.setAttribute("scriptMessage","<script>alert('ログイン情報の取得に失敗しました。\nログイン後にお試しください。')</script>");
		//遷移先を指定
		return "/fc/contents/regist";
	}
}
