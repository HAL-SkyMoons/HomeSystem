package jp.ac.hal.skymoons.models;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.PlanPointBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanDetail extends AbstractModel {

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		String employeeId = "E0001";

		if (request.getParameter("detail") != null
				|| request.getParameter("commentSubmit") != null
				|| request.getParameter("delete") != null
				|| request.getParameter("edit") != null
				|| request.getParameter("evaluationSubmit") != null) {
			SampleDao dao = new SampleDao();
			int planId = Integer.valueOf(request.getParameter("planId"));

			// コメントが送信された時
			if (request.getParameter("commentSubmit") != null) {

				CommentBean newRecord = new CommentBean();
				newRecord.setPlanID(planId);
				newRecord.setCommentUser(request.getParameter("commentUserId"));
				newRecord.setComment(request.getParameter("comment"));

				dao.commentInsert(newRecord);

				dao.commit();
			} else if (request.getParameter("delete") != null) {
				CommentBean deleteRecord = new CommentBean();
				deleteRecord.setPlanID(planId);
				deleteRecord.setCommentNo(Integer.valueOf(request
						.getParameter("commentNo")));

				dao.commentDelete(deleteRecord);

				dao.commit();
			} else if (request.getParameter("edit") != null) {
				PlanBean planEdit = new PlanBean();

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
			}else if(request.getParameter("evaluationSubmit") != null) {
				int point = Integer.valueOf(request.getParameter("evaluation"));


				PlanPointBean pointBean = new PlanPointBean();
				pointBean.setPlanId(planId);
				pointBean.setEmployeeId(employeeId);
				pointBean.setPoint(point);

				dao.planPointRegister(pointBean);

				dao.commit();
			}

			// 企画詳細情報の取得
			PlanBean planDetail = dao.planDetail(planId);
			request.setAttribute("planDetail", planDetail);

			// 企画に紐づくジャンル取得
			List<GenreBean> genre = dao.planGenreList(planDetail.getPlanId());

			request.setAttribute("genre", genre);

			// コメント取得
			List<CommentBean> commentList = dao.planCommentList(planId);
			request.setAttribute("commentList", commentList);

			//すべての評価の数
			int[] points = dao.planPointAll(planId);
			request.setAttribute("points", points);

			//自分の評価を取得
			PlanPointBean planPointBean = new PlanPointBean();
			planPointBean.setPlanId(planId);
			planPointBean.setEmployeeId(employeeId);
			planPointBean = dao.planPointGet(planPointBean);

			request.setAttribute("planPoint", planPointBean);
			dao.close();

			return "/pages/PlanDetail.jsp";
		}

		return "/pages/error.html";


	}
}
