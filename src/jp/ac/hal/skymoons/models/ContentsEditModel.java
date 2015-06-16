package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsDetailBean;
import jp.ac.hal.skymoons.beans.ContentsDetailHomeLogBean;
import jp.ac.hal.skymoons.beans.ContentsEditBean;
import jp.ac.hal.skymoons.beans.ContentsGenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsDetailDao;
import jp.ac.hal.skymoons.daoes.ContentsEditDao;
import jp.ac.hal.skymoons.daoes.ContentsGenreDao;

public class ContentsEditModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//検索に使用するDAOを選択しIDを指定
		String homeContentId = request.getParameter("homeContentId");
		ContentsEditDao dao = new ContentsEditDao();
		ContentsEditBean editData = null;
		editData = dao.findDetail(homeContentId);
		
		//ジャンル検索
		ContentsGenreDao genreDao = new ContentsGenreDao();
		ArrayList<ContentsGenreBean> genreData = null;
		genreData = genreDao.findAll();
		
		//結果をリクエストに保存
		request.setAttribute("editData",editData);
		request.setAttribute("genreList",genreData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/edit.jsp";
	}

}
