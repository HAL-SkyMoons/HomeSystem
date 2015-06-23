package jp.ac.hal.skymoons.models;

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

import jp.ac.hal.skymoons.beans.ContentsDataBean;
import jp.ac.hal.skymoons.beans.ContentsEditBean;
import jp.ac.hal.skymoons.beans.ContentsGenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.ContentsDetailDao;
import jp.ac.hal.skymoons.daoes.ContentsEditDao;
import jp.ac.hal.skymoons.daoes.ContentsGenreDao;
import jp.ac.hal.skymoons.util.Utility;

public class ContentsEditModel extends AbstractModel{

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
			return null;
		}

		//添付資料削除処理
		if (request.getParameter("fileDelete") != null) {
			int dataNo = Integer.valueOf(request.getParameter("homeDataNo"));
			String fileName = request.getParameter("fileName");

			ContentsEditDao deleteDao = new ContentsEditDao();
			ContentsDataBean dataBean = new ContentsDataBean();
			dataBean.setHomeContentId(homeContentId);
			dataBean.setHomeDataNo(dataNo);
			int result = deleteDao.homeDataDelete(dataBean);
			if (result != 0) {
				String path = request.getServletContext().getRealPath(
						"/files/contents/master/")
						+ homeContentId + "\\" + dataNo;
				System.out.println("del:" + path);
				File delFile = new File(path + "\\" + fileName);
				if (delFile.exists()) {
					if (delFile.delete()) {
						System.out.println("ファイルを削除しました");
						File delFolder = new File(path);
						delFolder.delete();
						deleteDao.commit();
					} else {
						System.out.println("ファイルの削除に失敗しました");
						deleteDao.rollback();
					}

				} else {
					System.out.println("ファイルが見つかりません");
				}
			}
			deleteDao.close();
		}

		//添付資料アップロード処理
		if (ServletFileUpload.isMultipartContent(request)) {
			// (1)アップロードファイルを格納するPATHを取得
			String path = request.getServletContext().getRealPath("/files");
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

							ContentsEditDao uploadDao = new ContentsEditDao();
							int nextUploadNo = uploadDao.homeDataUpload(homeContentId,
									fileName);
							uploadDao.commit();
							uploadDao.close();
							String newFileName = path + "/contents/master/"
									+ homeContentId + "/" + nextUploadNo;
							File dir = new File(newFileName);
							dir.mkdirs();
							// (9)ファイルデータを指定されたファイルに書き出し
							fItem.write(new File(newFileName + "/" + fileName));
						}
					} else {
						if (fItem.getFieldName().equals("planId")) {
							homeContentId = Integer.valueOf(fItem.getString());
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("no upload");
		}
		
		//検索に使用するDAOを選択しIDを指定
		ContentsEditDao dao = new ContentsEditDao();
		ContentsEditBean editData = null;
		editData = dao.findDetail(homeContentId);
		
		//ジャンル検索
		ContentsGenreDao genreDao = new ContentsGenreDao();
		ArrayList<ContentsGenreBean> genreData = null;
		genreData = genreDao.findAll();
		genreDao.close();
		
		ArrayList<ContentsDataBean> homeData = null;
		homeData = dao.findData(homeContentId);
		
		//結果をリクエストに保存
		request.setAttribute("editData",editData);
		request.setAttribute("genreList",genreData);
		request.setAttribute("dataList",homeData);
		
		//コミットと終了処理
		dao.commit();
		dao.close();
		
		//遷移先を指定
		return "/contents/edit.jsp";
	}

}
