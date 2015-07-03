package jp.ac.hal.skymoons.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsDeleteDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class ContentsDeleteModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		SessionController sc = new SessionController(request);
		/*
		System.out.println(request.getParameter("employeeId"));
		System.out.println(request.getParameter("homeContentId"));
		System.out.println(sc.checkUserSession2());
		System.out.println(sc.getUserClass_flag() );
		*/
		
		//ログインユーザーが社員であるかを確認する
		if(request.getParameter("homeContentId") != null 
				&& request.getParameter("employeeId") != null 
				&& sc.checkUserSession2() == true 
				&& sc.getUserId().equals(request.getParameter("employeeId")) 
				&& sc.getUserClass_flag().equals("1")){

			//コンテンツIDの取得
			int homeContentId = Integer.parseInt((String)request.getParameter("homeContentId"));
			
			//DAOのインスタンス化
			ContentsDeleteDao deleteDao = new ContentsDeleteDao();
			deleteDao.deleteDetail(homeContentId);
			
			//結果をリクエストに保存
			//request.setAttribute("detailList",detailData);
			//request.setAttribute("homeLogList",homeLogData);
			
			//コミットと終了処理
			deleteDao.commit();
			deleteDao.close();
			request.setAttribute("scriptMessage","<script>alert('削除が完了しました。')</script>");
			return "/fc/contents/list";
		}
		//遷移先を指定
		request.setAttribute("scriptMessage","<script>alert('削除に失敗しました。')</script>");
		return "/fc/contents/detail";
	}

}
