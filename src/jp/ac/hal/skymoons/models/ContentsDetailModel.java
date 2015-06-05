package jp.ac.hal.skymoons.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsDetailBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsDetailDao;

public class ContentsDetailModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//DAOのインスタンス化
		ContentsDetailDao dao = new ContentsDetailDao();
		
		//検索に使用するDAOを選択しRequestにコンテンツを設定
		ContentsDetailBean resultData = null;
		resultData = dao.findDetail(request.getParameter("homeContentId"));
		
		//結果をリクエストに保存
		request.setAttribute("detailList",resultData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/detail.jsp";
	}

}
