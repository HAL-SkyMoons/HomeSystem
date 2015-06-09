package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsListBean;
import jp.ac.hal.skymoons.beans.ContentsSearchBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsSearchDao;

public class ContentsSearchModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//DAOのインスタンス化
		ContentsSearchDao dao = new ContentsSearchDao();
		
		
		//結果をリクエストに保存
		//request.setAttribute("contentList",resultData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/list.jsp"	;
	}

}
