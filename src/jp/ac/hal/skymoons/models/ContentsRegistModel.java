package jp.ac.hal.skymoons.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ContentsGenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsGenreDao;

public class ContentsRegistModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//ジャンル検索
		ContentsGenreDao genreDao = new ContentsGenreDao();
		ArrayList<ContentsGenreBean> genreData = null;
		genreData = genreDao.findGenre();

		//結果をリクエストに保存
		request.setAttribute("genreList",genreData);
		
		genreDao.commit();
		genreDao.close();
		
		//遷移先を指定
		return "/contents/regist.jsp";
	}

}
