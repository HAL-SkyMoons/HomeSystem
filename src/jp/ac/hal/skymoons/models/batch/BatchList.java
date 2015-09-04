package jp.ac.hal.skymoons.models.batch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.BatchBean;
import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.batch.BatchDao;;

public class BatchList extends AbstractModel{
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	BatchDao dao = new BatchDao();
	List<BatchBean> batchList = dao.getBatchList();
	request.setAttribute("batchList", batchList);
	dao.close();

	return "/batch/batchList.jsp";
    }
}
