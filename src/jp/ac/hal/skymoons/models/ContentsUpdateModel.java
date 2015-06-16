package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsDetailBean;
import jp.ac.hal.skymoons.beans.ContentsDetailHomeLogBean;
import jp.ac.hal.skymoons.beans.ContentsUpdateBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsDetailDao;
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
			String year = request.getParameter("contentsYear");
			String month = request.getParameter("contentsMonth");
			String day = request.getParameter("contentsDay");
			String hour = request.getParameter("contentsHour");
			String minute = request.getParameter("contentsMinute");
			String contentsDatetime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";		
			updateBean.setHomeContentDatetime(contentsDatetime);
	
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
		//}
		//遷移先を指定
		return "/contents/edit.jsp?homeContentId=" + request.getAttribute("homeContentId");
	}

}
