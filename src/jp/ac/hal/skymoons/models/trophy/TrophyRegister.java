package jp.ac.hal.skymoons.models.trophy;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.CommentBean;
import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.daoes.batch.BatchDao;
import jp.ac.hal.skymoons.daoes.trophy.TrophyDao;

public class TrophyRegister extends AbstractModel {
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	TrophyDao dao = new TrophyDao();

	if (ServletFileUpload.isMultipartContent(request)) {
	    TrophyBean trophyBean = new TrophyBean();
	    HashMap<Integer, Integer> batchIds = new HashMap<Integer, Integer>();
	    HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
	    HashMap<Integer, Integer> trophyDetailData = new HashMap<Integer, Integer>();

	    // (1)アップロードファイルを格納するPATHを取得
	    String path = request.getServletContext().getRealPath(
		    "/images/trophy");

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
			    // (9)ファイルデータを指定されたファイルに書き出し
			    fItem.write(new File(path + "/"
				    + trophyBean.getTrophyId() + ".png"));
			}
		    } else {
			if (fItem.getFieldName().equals("trophyName")) {
			    byte[] bytes = fItem.getString().getBytes(
				    "iso-8859-1");
			    trophyBean
				    .setTrophyName(new String(bytes, "UTF-8"));
			} else if (fItem.getFieldName().equals("trophyComment")) {
			    byte[] bytes = fItem.getString().getBytes(
				    "iso-8859-1");
			    trophyBean.setTrophyComment(new String(bytes,
				    "UTF-8"));
			} else if (fItem.getFieldName().startsWith("batchId_")) {
			    String[] splits = fItem.getFieldName().split("_");
			    batchIds.put(Integer.valueOf(splits[1]),
				    Integer.valueOf(fItem.getString()));

			} else if (fItem.getFieldName().startsWith("count_")) {
			    String[] splits = fItem.getFieldName().split("_");
			    counts.put(Integer.valueOf(splits[1]),
				    Integer.valueOf(fItem.getString()));
			}

		    }

		    // トロフィー情報登録
		    if (trophyBean.getTrophyName() != null
			    && trophyBean.getTrophyComment() != null
			    && trophyBean.getTrophyId() == 0) {

			trophyBean.setTrophyId(dao.trophyRegister(trophyBean));
			dao.commit();
		    }

		}
		// トロフィー条件登録
		for (HashMap.Entry<Integer, Integer> entry : batchIds
			.entrySet()) {

		    int nowKey = entry.getKey();
		    trophyDetailData.put(entry.getValue(), counts.get(nowKey));
		}

		dao.trophyDetailRegister(trophyBean.getTrophyId(),
			trophyDetailData);
		dao.commit();

	    } catch (FileUploadException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    request.setAttribute("trophyList", dao.getTrophyList());
	    dao.close();

	    return "/trophy/trophyList.jsp";
	}

	request.setAttribute("batchList", dao.getBatchList());
	dao.close();

	return "/trophy/trophyRegister.jsp";
    }
}
