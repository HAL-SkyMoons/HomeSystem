package jp.ac.hal.skymoons.models.companycapacity;

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
import jp.ac.hal.skymoons.beans.companycapacity.CompanyCapacityBean;
import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.beans.trophy.TrophyBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.SampleDao;
import jp.ac.hal.skymoons.daoes.batch.BatchDao;
import jp.ac.hal.skymoons.daoes.companycapacity.CompanyCapacityDao;
import jp.ac.hal.skymoons.daoes.trophy.TrophyDao;

public class CompanyCapacityChange extends AbstractModel {
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	CompanyCapacityDao dao = new CompanyCapacityDao();

	if (ServletFileUpload.isMultipartContent(request)) {
	    CompanyCapacityBean companyCapacityBean = new CompanyCapacityBean();
	    int updateFlag = 0;
	    HashMap<Integer, Integer> batchIds = new HashMap<Integer, Integer>();
	    HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
	    HashMap<Integer, Integer> capacityDetailData = new HashMap<Integer, Integer>();

	    // (1)アップロードファイルを格納するPATHを取得
	    String path = request.getServletContext().getRealPath(
		    "/images/companyCapacity");

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
				    + companyCapacityBean.getCapacityId()
				    + ".png"));
			}
		    } else {
			if (fItem.getFieldName().equals("capacityName")) {
			    byte[] bytes = fItem.getString().getBytes(
				    "iso-8859-1");
			    companyCapacityBean.setCapacityName(new String(
				    bytes, "UTF-8"));
			} else if (fItem.getFieldName().equals(
				"capacityComment")) {
			    byte[] bytes = fItem.getString().getBytes(
				    "iso-8859-1");
			    companyCapacityBean.setCapacityComment(new String(
				    bytes, "UTF-8"));
			} else if (fItem.getFieldName().startsWith("batchId_")) {
			    String[] splits = fItem.getFieldName().split("_");
			    batchIds.put(Integer.valueOf(splits[1]),
				    Integer.valueOf(fItem.getString()));

			} else if (fItem.getFieldName().startsWith("count_")) {
			    String[] splits = fItem.getFieldName().split("_");
			    counts.put(Integer.valueOf(splits[1]),
				    Integer.valueOf(fItem.getString()));
			} else if (fItem.getFieldName().equals("capacityId")) {
			    companyCapacityBean.setCapacityId(Integer
				    .valueOf(fItem.getString()));
			}

		    }

		}

		// トロフィー情報登録
		if (companyCapacityBean.getCapacityName() != null
			&& companyCapacityBean.getCapacityComment() != null
			&& updateFlag == 0) {

		    companyCapacityBean.setCapacityId(dao.CompanyCapacityChange(companyCapacityBean));
		    dao.commit();

		    updateFlag = 1;
		}
		// トロフィー条件登録
		for (HashMap.Entry<Integer, Integer> entry : batchIds
			.entrySet()) {

		    int nowKey = entry.getKey();
		    capacityDetailData
			    .put(entry.getValue(), counts.get(nowKey));
		}
		dao.CompanyCapacityDetailDelete(companyCapacityBean.getCapacityId());
		dao.CompanyCapacityDetailRegister(companyCapacityBean.getCapacityId(), capacityDetailData);
		dao.commit();

	    } catch (FileUploadException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    request.setAttribute("capacityList", dao.getCompanyCapacityList());
	    dao.close();

	    return "/companyCapacity/capacityList.jsp";
	}

	request.setAttribute("batchList", dao.getBatchList());
	request.setAttribute("capacity", dao.getCompanyCapacity(Integer.valueOf(request.getParameter("capacityId"))));
	request.setAttribute("detail", dao.getCompanyCapacityDetail(Integer.valueOf(request.getParameter("capacityId"))));
	dao.close();

	return "/companyCapacity/capacityChange.jsp";
    }
}
