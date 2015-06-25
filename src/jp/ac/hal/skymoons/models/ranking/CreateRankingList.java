package jp.ac.hal.skymoons.models.ranking;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.ranking.TopNumRankingBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ranking.RankingDAO;

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
		
		RankingDAO rankingDAO = null;
		try{
			rankingDAO = new RankingDAO();
			List<TopNumRankingBean> list = rankingDAO.getTopNumRanking1();
			rankingDAO.close();
			request.setAttribute("list", list);
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("獲得数ランキングリスト作成処理中に問題が発生。");
			rankingDAO.close();
		}
		
		
		return "/ranking/topnum.jsp";
	}

}
