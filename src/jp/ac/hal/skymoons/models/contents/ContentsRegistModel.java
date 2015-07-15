package jp.ac.hal.skymoons.models.contents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.contents.ContentsGenreBean;
import jp.ac.hal.skymoons.beans.contents.ContentsRegistBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.contents.ContentsGenreDao;
import jp.ac.hal.skymoons.daoes.contents.ContentsRegistDao;
import jp.ac.hal.skymoons.security.session.SessionController;

public class ContentsRegistModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		//ログインユーザーが社員であるかを確認する
		SessionController sc = new SessionController(request);
		if(sc.checkUserSession2() == true && sc.getUserClass_flag() != null && sc.getUserClass_flag().equals("1") && sc.getUserId() != null){
					
			//ジャンル検索
			ContentsGenreDao genreDao = new ContentsGenreDao();
			ArrayList<ContentsGenreBean> genreList = genreDao.findGenre();
			ArrayList<ContentsGenreBean> bigGenreList = genreDao.findBigGenre();
			genreDao.close();

			//ユーザーIDを追加
			ContentsRegistDao registDao = new ContentsRegistDao();
			ContentsRegistBean registData = registDao.findEmployee(sc.getUserId());
			registDao.close();
			
			//入力欄デフォルト日付の設定
			//現在時刻の取得
			Date datetime = (Date)new java.util.Date();
			registData.setStartYear(new SimpleDateFormat("yyyy").format(datetime));
			registData.setStartMonth(new SimpleDateFormat("MM").format(datetime));
			registData.setStartDay(new SimpleDateFormat("dd").format(datetime));
			registData.setStartHour(new SimpleDateFormat("hh").format(datetime));
			registData.setStartMinute(new SimpleDateFormat("MM").format(datetime));
			
			registData.setEndYear(new SimpleDateFormat("yyyy").format(datetime));
			registData.setEndMonth(new SimpleDateFormat("MM").format(datetime));
			registData.setEndDay(new SimpleDateFormat("dd").format(datetime));
			registData.setEndHour(new SimpleDateFormat("hh").format(datetime));
			registData.setEndMinute(new SimpleDateFormat("MM").format(datetime));
			
			
			//結果をリクエストに保存
			request.setAttribute("registData", registData);
			request.setAttribute("genreList", genreList);
			request.setAttribute("bigGenreList", bigGenreList);
			
		}
		//遷移先を指定
		return "/contents/regist.jsp";
	}

}
