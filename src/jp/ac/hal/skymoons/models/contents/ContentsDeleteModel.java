package jp.ac.hal.skymoons.models.contents;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.contents.ContentsDeleteDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

public class ContentsDeleteModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		try{
			//header情報取得
			HeaderDataGetUtil headerUtil = new HeaderDataGetUtil();
			ArrayList<EmployeePageBean> employeePageReturn = headerUtil
					.getHeaderData(request, response);
			request.setAttribute("employeeDetail", employeePageReturn);
			
			/*
			System.out.println(request.getParameter("employeeId"));
			System.out.println(request.getParameter("homeContentId"));
			System.out.println(sc.checkUserSession2());
			System.out.println(sc.getUserClass_flag() );
			*/
			SessionController sc = new SessionController(request);			
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
				request.setAttribute("message","<p>削除が完了しました。<br/>"
						+ "<a href='/HomeSystem/fc/contents/list'>"
						+ "<input type='button' value='コンテンツ一覧へ移動' class='btn btn-2 btn-2c'></a></p>");
				return "/contents/complete.jsp";
			}
			//遷移先を指定
			request.setAttribute("message","<p>削除に失敗しました。<br/>既に削除されているか、データが存在しません。<br/>"
					+ "<a href='/HomeSystem/fc/contents/regist'>コンテンツ投稿画面へ戻る</a></p>");
			//遷移先を指定
			return "/contents/complete.jsp";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message","<p>削除処理中にエラーが発生しました。<br/>"
					+ "<a href='/HomeSystem/fc/contents/list'>コンテンツ一覧画面へ戻る</a></p>");
			return "/contents/complete.jsp";
		}
	}

}
