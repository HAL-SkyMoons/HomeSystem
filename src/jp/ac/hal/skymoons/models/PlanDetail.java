package jp.ac.hal.skymoons.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.FileBean;
import jp.ac.hal.skymoons.beans.GenreBean;
import jp.ac.hal.skymoons.beans.PlanBean;
import jp.ac.hal.skymoons.beans.PlanPointBean;
import jp.ac.hal.skymoons.beans.UserBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.Utility;

public class PlanDetail extends AbstractModel {

    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub

	if (request.getParameter("download") != null) {
	    System.out.println("download");
	    Utility utility = new Utility();
	    utility.download_v2(request, response);
	    return null;
	}

	int planId = 0;

	SessionController sessionController = new SessionController(request);

	if (ServletFileUpload.isMultipartContent(request)) {
	    int commentNo = 0;

	    SampleDao dao = new SampleDao();

	    CommentBean comment = new CommentBean();

	    // (1)アップロードファイルを格納するPATHを取得
	    String path = request.getServletContext()
		    .getRealPath("/files/plan");
	    System.out.println(path);

	    // (2)ServletFileUploadオブジェクトを生成
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    ServletFileUpload upload = new ServletFileUpload(factory);

	    // (3)アップロードする際の基準値を設定
	    factory.setSizeThreshold(1024);
	    upload.setSizeMax(-1);
	    upload.setHeaderEncoding("UTF-8");
	    try {
		// (4)ファイルデータ(FileItemオブジェクト)を取得し、
		// Listオブジェクトとして返す
		List list = upload.parseRequest(request);

		// (5)ファイルデータ(FileItemオブジェクト)を順に処理
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
		    FileItem fItem = (FileItem) iterator.next();

		    // (6)ファイルデータの場合、if内を実行
		    if (!(fItem.isFormField())) {
			// (7)ファイルデータのファイル名(PATH名含む)を取得
			String fileName = fItem.getName();
			if ((fileName != null) && (!fileName.equals(""))) {
			    // (8)PATH名を除くファイル名のみを取得
			    fileName = (new File(fileName)).getName();
			    String newPath = null;

			    // コメント送信時
			    if (comment.getCommentNo() != 0 && comment.getComment() == null) {
				int nextUploadNo = dao.commentFileUpload(
					planId, commentNo, fileName);
				dao.commit();
				newPath = path + "/comment/" + planId + "/"
					+ commentNo + "/" + nextUploadNo;
				System.out.println("指定コメントファイルアップロード");

			    } else if (comment.getCommentNo() == 0
				    && comment.getComment() != null) {
				int nextUploadNo = dao.commentFileUpload(
					planId, commentNo, fileName);
				dao.commit();
				newPath = path + "/comment/" + planId + "/"
					+ commentNo + "/" + nextUploadNo;

				System.out.println("新規コメントファイルアップロード");
			    } else {

				int nextUploadNo = dao.planFileUpload(planId,
					fileName);
				dao.commit();
				newPath = path + "/master/" + planId + "/"
					+ nextUploadNo;
				System.out.println("企画ファイルアップロード");

			    }

			    File dir = new File(newPath);
			    dir.mkdirs();
			    // (9)ファイルデータを指定されたファイルに書き出し
			    fItem.write(new File(newPath + "/" + fileName));
			}
		    } else {
			if (fItem.getFieldName().equals("planId")) {
			    planId = Integer.valueOf(fItem.getString());
			    comment.setPlanID(Integer.valueOf(fItem.getString()));
			}

			if (fItem.getFieldName().equals("commentNo")) {
			    comment.setCommentNo(Integer.valueOf(fItem
				    .getString()));
			    commentNo = Integer.valueOf(fItem.getString());
			}

			if (fItem.getFieldName().equals("comment")) {
			    byte[] bytes = fItem.getString().getBytes(
				    "iso-8859-1");
			    comment.setComment(new String(bytes, "UTF-8"));
			    System.out.println(fItem.getString());
			}

			if (fItem.getFieldName().equals("commentUserId")) {
			    comment.setCommentUser(fItem.getString());
			}

		    }

		    if (commentNo == 0 && comment.getPlanID() != 0
			    && comment.getComment() != null
			    && comment.getCommentUser() != null) {

			commentNo = dao.commentInsert(comment);
			dao.commit();
			System.out.println("コメント投稿");
		    }

		}
	    } catch (FileUploadException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {

		dao.close();
	    }

	} else {
	    System.out.println("no upload");
	}

	if ((request.getParameter("detail") != null || planId != 0
		|| request.getParameter("upload") != null
		|| request.getParameter("commentSubmit") != null
		|| request.getParameter("delete") != null
		|| request.getParameter("fileDelete") != null
		|| request.getParameter("commentFileDelete") != null
		|| request.getParameter("planEnd") != null
		|| request.getParameter("edit") != null || request
		.getParameter("evaluationSubmit") != null)
		&& sessionController.checkUserSession() == null) {
	    SampleDao dao = new SampleDao();
	    if (planId == 0) {
		planId = Integer.parseInt(request.getParameter("planId"));
	    }
	    String employeeId = sessionController.getUserId();
	    UserBean user = dao.getUser(employeeId);
	    request.setAttribute("user", user);

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

		SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
		planEdit.setStartDate(sdf.parse(request
			.getParameter("startYear")
			+ "-"
			+ request.getParameter("startMonth")
			+ "-"
			+ request.getParameter("startDay")
			+ " "
			+ request.getParameter("startHour")
			+ ":"
			+ request.getParameter("startMinutes") + ":00"));
		planEdit.setEndDate(sdf.parse(request.getParameter("endYear")
			+ "-" + request.getParameter("endMonth") + "-"
			+ request.getParameter("endDay") + " "
			+ request.getParameter("endHour") + ":"
			+ request.getParameter("endMinutes") + ":00"));

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
	    } else if (request.getParameter("evaluationSubmit") != null) {
		int point = Integer.valueOf(request.getParameter("evaluation"));

		PlanPointBean pointBean = new PlanPointBean();
		pointBean.setPlanId(planId);
		pointBean.setEmployeeId(employeeId);
		pointBean.setPoint(point);

		dao.planPointRegister(pointBean);

		dao.commit();
	    } else if (request.getParameter("fileDelete") != null) {
		int delPlanId = Integer.valueOf(request.getParameter("planId"));
		int dataNo = Integer.valueOf(request.getParameter("dataNo"));

		FileBean fileBean = new FileBean();
		fileBean.setPlanId(delPlanId);
		fileBean.setDataNo(dataNo);

		int result = dao.planFileDelete(fileBean);
		dao.commit();

	    } else if (request.getParameter("commentFileDelete") != null) {
		int delPlanId = Integer.valueOf(request.getParameter("planId"));
		int dataNo = Integer.valueOf(request.getParameter("dataNo"));
		int commentNo = Integer.valueOf(request
			.getParameter("commentNo"));

		FileBean fileBean = new FileBean();
		fileBean.setPlanId(delPlanId);
		fileBean.setDataNo(dataNo);
		fileBean.setCommentNo(commentNo);

		int result = dao.commentFileDelete(fileBean);
		dao.commit();

	    } else if (request.getParameter("planEnd") != null) {
		int endPlanId = Integer.valueOf(request.getParameter("planId"));
		dao.endPlan(endPlanId);
		dao.commit();

	    }

	    // test
	    // dao.commentFileDeleteComplete(request);
	    // dao.planFileDeleteComplete(request);

	    // 企画詳細情報の取得
	    PlanBean planDetail = dao.planDetail(planId);
	    request.setAttribute("planDetail", planDetail);

	    // 企画に紐づくジャンル取得
	    List<GenreBean> genre = dao.planGenreList(planDetail.getPlanId());

	    request.setAttribute("genre", genre);

	    // アップロードファイル一覧取得
	    List<FileBean> fileList = dao.uploadFileList(planId);
	    request.setAttribute("fileList", fileList);

	    // コメントファイル一覧取得
	    List<FileBean> commentFileList = dao.uploadCommentFileList(planId);
	    request.setAttribute("commentFileList", commentFileList);

	    // コメント取得
	    List<CommentBean> commentList = dao.planCommentList(planId);
	    request.setAttribute("commentList", commentList);

	    // すべての評価の数
	    int[] points = dao.planPointAll(planId);
	    request.setAttribute("points", points);

	    // 自分の評価を取得
	    PlanPointBean planPointBean = new PlanPointBean();
	    planPointBean.setPlanId(planId);
	    planPointBean.setEmployeeId(employeeId);
	    planPointBean = dao.planPointGet(planPointBean);

	    request.setAttribute("planPoint", planPointBean);
	    dao.close();

	    return "/pages/PlanDetail.jsp";
	} else if (sessionController.checkUserSession() != null) {
	    return sessionController.checkUserSession();
	}

	return "/pages/error.html";

    }
}
