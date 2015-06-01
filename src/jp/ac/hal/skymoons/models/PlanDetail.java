package jp.ac.hal.skymoons.models;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;

public class PlanDetail extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		if(request.getParameter("detail")!=null || request.getParameter("commentSubmit") != null || request.getParameter("delete") != null){
			SampleDao dao = new SampleDao();

			//企画詳細情報の取得
			PlanBean planDetail = dao.planDetail(request.getParameter("planId"));
			request.setAttribute("planDetail", planDetail);

			//企画に紐づくジャンル取得
			List<GenreBean> genre = dao.planGenreList(planDetail.getPlanId());

			request.setAttribute("genre", genre);

			//コメントが送信された時
			if(request.getParameter("commentSubmit") != null){

				CommentBean newRecord = new CommentBean();
				newRecord.setPlanID(Integer.valueOf(request.getParameter("planId")));
				newRecord.setCommentUser(request.getParameter("commentUserId"));
				newRecord.setComment(request.getParameter("comment"));

				dao.commentInsert(newRecord);

				dao.commit();
			}else if(request.getParameter("delete") != null){
				CommentBean deleteRecord = new CommentBean();
				deleteRecord.setPlanID(Integer.valueOf(request.getParameter("planId")));
				deleteRecord.setCommentNo(Integer.valueOf(request.getParameter("commentNo")));

				dao.commentDelete(deleteRecord);

				dao.commit();
			}

			//コメント取得

			List<CommentBean> commentList = dao.planCommentList(Integer.valueOf(request.getParameter("planId")));
			request.setAttribute("commentList", commentList);


			dao.close();


			return "/pages/PlanDetail.jsp";
		}


		return "/pages/PlanList.jsp";
	}




}
