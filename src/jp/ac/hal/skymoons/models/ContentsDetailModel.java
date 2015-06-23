package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsDetailBean;
import jp.ac.hal.skymoons.beans.ContentsDetailHomeLogBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsDetailDao;

public class ContentsDetailModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//DAOのインスタンス化
		ContentsDetailDao dao = new ContentsDetailDao();
		
		//検索に使用するDAOを選択しIDを指定
		String homeContentId = request.getParameter("homeContentId");
		ContentsDetailBean detailData = null;
		detailData = dao.findDetail(homeContentId);
		ArrayList<ContentsDetailHomeLogBean> homeLogData = null;
		homeLogData = dao.findHomeLog(homeContentId);
		
		//結果をリクエストに保存
		request.setAttribute("detailList",detailData);
		request.setAttribute("homeLogList",homeLogData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/detail.jsp";
	}

}
