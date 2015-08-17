package jp.ac.hal.skymoons.models.ranking;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ranking.BatchBean;
import jp.ac.hal.skymoons.beans.ranking.TopNumRankingBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ranking.RankingDAO;
import jp.ac.hal.skymoons.security.session.SessionController;

/**
 * ランキング作成機能。
 * @author YAMAZAKI GEN
 * @since 2015/06/05
 * @version 1.0
 */
public class CreateRankingList extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// セッションチェック
		SessionController sessionController = new SessionController(request);
		if(sessionController.checkUserSession2() == false) {
			// セッションが無効
			return sessionController.getForwardSessionErrorPageUrl();
		}
		
		// 条件指定
		String whereBatch = null;
		String whereYear = null;
		String whereMonth = null;
		// フォーム送信確認
		if(request.getParameter("submit") != null) {
			if(request.getParameter("batch").equals("0") == false) {
				whereBatch = request.getParameter("batch");
				request.setAttribute("batch", whereBatch);
			}
			if(request.getParameter("year").equals("0") == false) {
				whereYear = request.getParameter("year");
				request.setAttribute("year", whereYear);
			}
			if(request.getParameter("month").equals("0") == false) {
				whereMonth = request.getParameter("month");
				request.setAttribute("month", whereMonth);
			}
		}
		
		// Database
		RankingDAO rankingDAO = null;
		try{
			rankingDAO = new RankingDAO();
			// バッチリストの取得
			List<BatchBean> batchList = rankingDAO.getBatchList();
			// ランキングリストの取得
			List<TopNumRankingBean> list = rankingDAO.getRankingList(whereBatch, whereYear, whereMonth);
			// 最小値の年の取得
			String year = rankingDAO.getYearList2();
			
			request.setAttribute("batchList", batchList);
			request.setAttribute("list", list);
			request.setAttribute("year", year);
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("獲得数ランキングリスト作成処理中に問題が発生。");
		} finally {
			rankingDAO.close();
		}
		
		return "/ranking/topnum.jsp";
	}

}
