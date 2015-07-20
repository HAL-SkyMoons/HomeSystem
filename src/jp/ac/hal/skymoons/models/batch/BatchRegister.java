package jp.ac.hal.skymoons.models.batch;

import java.io.File;
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
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.daoes.batch.BatchDao;

public class BatchRegister extends AbstractModel {
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	BatchDao dao = new BatchDao();

	if (ServletFileUpload.isMultipartContent(request)) {
	    BatchBean batchBean = new BatchBean();

	    // (1)アップロードファイルを格納するPATHを取得
	    String path = request.getServletContext().getRealPath(
		    "/images/batch");

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
				    + batchBean.getBatchId() + ".png"));
			}
		    } else {
			if (fItem.getFieldName().equals("batchName")) {
			    byte[] bytes = fItem.getString().getBytes("iso-8859-1");
			    batchBean.setBatchName(new String(bytes, "UTF-8"));
			} else if (fItem.getFieldName().equals("batchComment")) {
			    byte[] bytes = fItem.getString().getBytes("iso-8859-1");
			    batchBean.setBatchComment(new String(bytes, "UTF-8"));
			}

		    }

		    //バッチ情報登録
		    if (batchBean.getBatchName() != null
			    && batchBean.getBatchComment() != null
			    && batchBean.getBatchId() == 0) {

			batchBean.setBatchId(dao.batchRegister(batchBean));
			dao.commit();
		    }

		}
	    } catch (FileUploadException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		dao.close();
	    }

	}

	return "/batch/batchRegister.jsp";
    }
}
