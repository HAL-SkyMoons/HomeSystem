package jp.ac.hal.skymoons.models.contents;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.FileBean;
import jp.ac.hal.skymoons.beans.contents.ContentsDataBean;
import jp.ac.hal.skymoons.beans.contents.ContentsDetailBean;
import jp.ac.hal.skymoons.beans.contents.ContentsDetailHomeLogBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.daoes.contents.ContentsDetailDao;
import jp.ac.hal.skymoons.security.session.SessionController;
import jp.ac.hal.skymoons.util.Utility;

public class ContentsDetailModel extends AbstractModel{

	@Override
	public String doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//IDの取得
		int homeContentId = Integer.parseInt(request.getParameter("homeContentId"));
		
		//添付資料ダウンロード処理
		if (request.getParameter("download") != null) {
			System.out.println("download");
			Utility utility = new Utility();
			utility.download(request, response);
		}
		
		//DAOのインスタンス化
		ContentsDetailDao dao = new ContentsDetailDao();
		
		//検索に使用するDAOを選択しIDを指定
		ContentsDetailBean detailData = null;
		detailData = dao.findDetail(homeContentId);
		ArrayList<ContentsDetailHomeLogBean> homeLogData = null;
		homeLogData = dao.findHomeLog(homeContentId);
		ArrayList<ContentsDataBean> homeData = null;
		homeData = dao.findData(homeContentId);
		
		//ユーザーIDを追加
		SessionController sc = new SessionController(request);
		if(sc.checkUserSession2()){
			String userId = sc.getUserId();
			detailData.setUserId(userId);
		}
		
		//結果をリクエストに保存
		request.setAttribute("detailList",detailData);
		request.setAttribute("homeLogList",homeLogData);
		request.setAttribute("dataList",homeData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/detail.jsp";
	}

}
