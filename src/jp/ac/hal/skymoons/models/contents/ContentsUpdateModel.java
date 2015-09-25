package jp.ac.hal.skymoons.models.contents;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.EmployeePageBean;
import jp.ac.hal.skymoons.beans.contents.ContentsUpdateBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.contents.ContentsUpdateDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.HeaderDataGetUtil;

public class ContentsUpdateModel extends AbstractModel{

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
			
			//ログインユーザーが社員であるかを確認する
			SessionController sc = new SessionController(request);
			if(sc.checkUserSession2() == true && sc.getUserClass_flag() != null && sc.getUserClass_flag().equals("1") && sc.getUserId() != null){
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
				updateBean.setStartDatetime(startDatetime);
	
				//終了日の設定
				if(request.getParameter("addEndDate") != null && request.getParameter("addEndDate").equals("true")){
					String endYear = request.getParameter("endYear");
					String endMonth = request.getParameter("endMonth");
					String endDay = request.getParameter("endDay");
					String endHour = request.getParameter("endHour");
					String endMinute = request.getParameter("endMinute");
					String endDatetime = endYear + "-" + endMonth + "-" + endDay + " " + endHour + ":" + endMinute + ":00";		
					updateBean.setEndDatetime(endDatetime);
				}
				
				//コンテンツタイトル
				updateBean.setHomeContentTitle(request.getParameter("homeContentTitle"));
				
				//コンテンツ内容
				updateBean.setHomeContentComment(request.getParameter("homeContentComment"));
				
				//社員ID
				//updateBean.setEmployeeId(sc.getUserId());
				
				//添付資料
				
				//ジャンル
				String[] genreArray = request.getParameterValues("genreId");
				ArrayList<Integer> genreId = new ArrayList<>();
				if(genreArray != null){
					for(String genreString : genreArray){
						genreId.add(Integer.parseInt(genreString));
					}
				}
				updateBean.setGenreId(genreId);
		
				//DAOのインスタンス化
				ContentsUpdateDao dao = new ContentsUpdateDao();
				dao.updateContent(updateBean);
				dao.changeGenre(updateBean);
				
				//終了日の設定
				if(request.getParameter("addEndDate") != null){
					dao.updateEndDate(updateBean);
				}
				
				//結果をリクエストに保存
				//request.setAttribute("detailList",detailData);
				//request.setAttribute("homeLogList",homeLogData);
				
				//コミットと終了処理
				dao.commit();
				dao.close();
				request.setAttribute("message","<p>更新が完了しました。<br/>"
						+ "<a href='/HomeSystem/fc/contents/detail?homeContentId="
						+ updateBean.getHomeContentId() + "'>"
						+ "<input type='button' value='更新したコンテンツへ移動' class='btn btn-2 btn-2c'></a></p>");
				return "/contents/complete.jsp";
			}
			//エラーメッセージ
			request.setAttribute("message","<p>更新に失敗しました。<br/>"
					+ "投稿ユーザーでないため更新が出来ません。<br/>"
					+ "<a href='/HomeSystem/fc/contents/list'>"
					+ "<input type='button' value='コンテンツ一覧へ移動' class='btn btn-2 btn-2c'></a></p>");
			return "/contents/complete.jsp";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message","<p>削除処理中にエラーが発生しました。<br/>"
					+ "<a href='/HomeSystem/fc/contents/list'>コンテンツ一覧画面へ戻る</a></p>");
			return "/contents/complete.jsp";
		}
	}
}
