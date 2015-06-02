package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanDetail extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		if (request.getParameter("detail") != null
				|| request.getParameter("commentSubmit") != null
				|| request.getParameter("delete") != null
				|| request.getParameter("edit") != null) {
			SampleDao dao = new SampleDao();

			// コメントが送信された時
			if (request.getParameter("commentSubmit") != null) {

				CommentBean newRecord = new CommentBean();
				newRecord.setPlanID(Integer.valueOf(request
						.getParameter("planId")));
				newRecord.setCommentUser(request.getParameter("commentUserId"));
				newRecord.setComment(request.getParameter("comment"));

				dao.commentInsert(newRecord);

				dao.commit();
			} else if (request.getParameter("delete") != null) {
				CommentBean deleteRecord = new CommentBean();
				deleteRecord.setPlanID(Integer.valueOf(request
						.getParameter("planId")));
				deleteRecord.setCommentNo(Integer.valueOf(request
						.getParameter("commentNo")));

				dao.commentDelete(deleteRecord);

				dao.commit();
			} else if (request.getParameter("edit") != null) {
				PlanBean planEdit = new PlanBean();

				int planId = Integer.valueOf(request.getParameter("planId"));

				planEdit.setPlanId(planId);
				planEdit.setPlanTitle(request.getParameter("planTitle"));
				planEdit.setPlanComment(request.getParameter("planComment"));

				dao.planEdit(planEdit);

				dao.planGenreDelete(planId);

				if (request.getParameterValues("genre") != null) {
					String[] genres = request.getParameterValues("genre");
					for (String genre : genres) {
						try {
							dao.planGenreInsert(planId, Integer.valueOf(genre));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				dao.commit();
			}

			// 企画詳細情報の取得
			PlanBean planDetail = dao.planDetail(Integer.valueOf(request
					.getParameter("planId")));
			request.setAttribute("planDetail", planDetail);

			// 企画に紐づくジャンル取得
			List<GenreBean> genre = dao.planGenreList(planDetail.getPlanId());

			request.setAttribute("genre", genre);

			// コメント取得
			List<CommentBean> commentList = dao.planCommentList(Integer
					.valueOf(request.getParameter("planId")));
			request.setAttribute("commentList", commentList);

			dao.close();

			return "/pages/PlanDetail.jsp";
		}

		return "/pages/PlanList.jsp";
	}
}
