package jp.ac.hal.skymoons.models.genre;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.hal.skymoons.beans.genre.GenreBean;
import jp.ac.hal.skymoons.controllers.AbstractModel;
import jp.ac.hal.skymoons.daoes.genre.GenreDao;

public class BigGenreChange extends AbstractModel {
    @Override
    public String doService(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	if (request.getParameter("change") != null) {
	    GenreDao dao = new GenreDao();
	    GenreBean genreBean = dao.getBigGenreDetail(Integer.valueOf(request
		    .getParameter("bigGenreId")));

	    request.setAttribute("detail", genreBean);
	    dao.close();

	} else if (request.getParameter("bigGenreChange") != null) {
	    GenreDao dao = new GenreDao();

	    String bigGenreName = request.getParameter("bigGenreName");
	    int bigGenreId = Integer
		    .valueOf(request.getParameter("bigGenreId"));

	    GenreBean updateRecord = new GenreBean();
	    updateRecord.setBigGenreId(bigGenreId);
	    updateRecord.setBigGenreName(bigGenreName);
	    if (dao.bigGenreChange(updateRecord) == 1) {
		List<GenreBean> bigGenreList = dao.getBigGenreList();
		request.setAttribute("bigGenreList", bigGenreList);
		dao.commit();
		dao.close();

		return "/genre/bigGenreList.jsp";
	    } else {
		GenreBean genreBean = dao.getBigGenreDetail(Integer
			.valueOf(request.getParameter("bigGenreId")));

		request.setAttribute("detail", genreBean);
	    }

	}

	return "/genre/bigGenreChange.jsp";

    }
}
